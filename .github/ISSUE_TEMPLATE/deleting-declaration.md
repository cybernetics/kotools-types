---
name: 🔥 Declaration's removal
about: Delete a deprecated declaration.
title: 🔥 Deletion of $DECLARATION
labels: feature
---

> [!IMPORTANT]
> This is an **incompatible binary change** that should be published in a **major** release according to our [evolution principles](https://github.com/kotools/types/blob/main/documentation/declarations-lifecycle.md).

## 📝 Description

We would like to remove $DECLARATION.

<!-- Uncomment this section if your issue depends on another one.
## 🔗 Dependencies

This issue is blocked by the following ones:
- [ ] #ITEM
-->

## ✅ Checklist

- [ ] Remove the declaration, dump the [Application Binary Interface (ABI)][abi] and the [unreleased changelog].
- [ ] Close this issue as completed and update tracking ones if present.

[abi]: https://github.com/kotools/types/blob/main/CONTRIBUTING.md#checking-the-application-binary-interface-abi
[unreleased changelog]: https://github.com/kotools/types/blob/main/CHANGELOG.md#unreleased
