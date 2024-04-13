package kotools.types.plugins

import kotools.types.tasks.TaskGroup
import kotools.types.tasks.description
import kotools.types.tasks.group
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin
import java.io.File
import kotlin.reflect.KClass

/** The extension for configuring the [DocumentationPlugin]. */
public interface DocumentationExtension {
    /**
     * The display name of the module.
     *
     * The plugin will set the [DokkaTask.moduleName] property with its value.
     */
    public val moduleName: Property<String>
}

/** Plugin configuring the documentation of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.extensions.create<DocumentationExtension>("documentation")
        project.tasks.run {
            dokkaTasks(project)
            apiReferenceJar(project)
            saveApiReference(project)
            cleanDokkaHtml()
        }
    }
}

private fun TaskContainer.dokkaTasks(project: Project) {
    val documentation: DocumentationExtension = project.extensions.getByType()
    withType<DokkaTask>().configureEach {
        commonConfiguration(project)
        moduleName.set(documentation.moduleName.orNull)
        dokkaSourceSets.configureEach {
            project.rootProject.layout.projectDirectory
                .file("dokka/packages.md")
                .let { includes.setFrom(it) }
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            customAssets = project.rootProject.layout.projectDirectory
                .file("dokka/logo-icon.svg")
                .asFile
                .let(::listOf)
        }
    }
}

private fun AbstractDokkaTask.commonConfiguration(project: Project) {
    outputDirectory.set(project.layout.buildDirectory.dir("dokka"))
    failOnWarning.set(true)
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = project.rootProject.layout.projectDirectory
            .file("LICENSE.txt")
            .asFile
            .useLines { lines: Sequence<String> ->
                lines.first { it.startsWith("Copyright (c)") }
            }
    }
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = project.version.toString()
        olderVersionsDir = project.archivedApiReferences
    }
}

private val Project.archivedApiReferences: File
    get() = layout.buildDirectory.dir("api-references")
        .map(Directory::getAsFile)
        .get()

private fun TaskContainer.apiReferenceJar(project: Project) {
    val apiReferenceJar: TaskProvider<Jar> = register<Jar>("apiReferenceJar") {
        group(TaskGroup.BUILD)
        description("Archives the API reference in a JAR file.")
        from(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
    named("assemble").configure { dependsOn += apiReferenceJar }
    val publishing: KClass<PublishingExtension> = PublishingExtension::class
    project.extensions.findByType(publishing)
        ?.include(apiReferenceJar)
        ?: project.logger.info(
            "Can't find extension of type '${publishing.simpleName}'."
        )
}

private val TaskContainer.dokkaHtml: TaskProvider<DokkaTask>
    get() = named<DokkaTask>("dokkaHtml")

private fun PublishingExtension.include(jar: TaskProvider<Jar>): Unit =
    publications.withType<MavenPublication>()
        .configureEach { artifact(jar) }

private fun TaskContainer.saveApiReference(project: Project) {
    val saveApiReference: TaskProvider<Copy> =
        register<Copy>("saveApiReference") {
            group(TaskGroup.DOCUMENTATION)
            description("Saves the API reference for archives.")
            from(dokkaHtml)
            exclude("older/**")
            destinationDir =
                project.archivedApiReferences.resolve("${project.version}")
        }
    assembleApiReferenceForWebsite(saveApiReference)
}

private fun TaskContainer.assembleApiReferenceForWebsite(
    saveApiReference: TaskProvider<Copy>
): Unit = register("assembleApiReferenceForWebsite").configure {
    group(TaskGroup.BUILD)
    description("Assembles the API reference for our website.")
    dependsOn += saveApiReference
}

private fun TaskContainer.cleanDokkaHtml() {
    val dokkaHtml: TaskProvider<DokkaTask> = this.dokkaHtml
    val cleanDokkaHtml: TaskProvider<Delete> =
        register<Delete>("cleanDokkaHtml") {
            group(TaskGroup.BUILD)
            description(
                "Deletes the output directory of the '${dokkaHtml.name}' task."
            )
            val target: DirectoryProperty = dokkaHtml.get().outputDirectory
            setDelete(target)
        }
    named<Delete>("clean").configure { dependsOn += cleanDokkaHtml }
}
