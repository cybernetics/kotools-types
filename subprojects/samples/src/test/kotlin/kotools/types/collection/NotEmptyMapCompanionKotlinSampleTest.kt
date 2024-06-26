package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyMapCompanionKotlinSampleTest {
    @Test
    fun `create(Map) with Map should pass`() {
        val expected = "{a=1, b=2}"
        val sample = NotEmptyMapCompanionKotlinSample()
        assertPrints(expected, sample::createWithMap)
    }

    @Test
    fun `create(Map) with MutableMap should pass`() {
        val expected: String = listOf(
            "{a=1, b=2}",
            "{a=1, b=2}",
            "{}",
            "{a=1, b=2}"
        ).joinToString(separator = "\n")
        val sample = NotEmptyMapCompanionKotlinSample()
        assertPrints(expected, sample::createWithMutableMap)
    }

    @Test
    fun `createOrNull(Map) with Map should pass`() {
        val expected = "{a=1, b=2}"
        val sample = NotEmptyMapCompanionKotlinSample()
        assertPrints(expected, sample::createOrNullWithMap)
    }

    @Test
    fun `createOrNull(Map) with MutableMap should pass`() {
        val expected: String = listOf(
            "{a=1, b=2}",
            "{a=1, b=2}",
            "{}",
            "{a=1, b=2}"
        ).joinToString(separator = "\n")
        val sample = NotEmptyMapCompanionKotlinSample()
        assertPrints(expected, sample::createOrNullWithMutableMap)
    }

    @Test
    fun `of(Pair, vararg Pair) should pass`() {
        val expected = "{a=1, b=2}"
        val sample = NotEmptyMapCompanionKotlinSample()
        assertPrints(expected, sample::of)
    }
}
