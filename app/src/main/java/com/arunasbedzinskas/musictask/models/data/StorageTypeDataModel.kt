package com.arunasbedzinskas.musictask.models.data

import com.arunasbedzinskas.musictask.models.enums.StorageType

data class StorageTypeDataModel(
    val storageType: StorageType,
    val totalLength: String
)
