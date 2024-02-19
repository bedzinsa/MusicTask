package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.models.data.StorageTypeDataModel
import com.arunasbedzinskas.musictask.models.enums.StorageType

fun interface GetStorageTypesDataUseCase {

    operator fun invoke(): List<StorageTypeDataModel>
}

internal class GetStorageTypesDataUseCaseImpl(

) : GetStorageTypesDataUseCase {

    // TODO
    override fun invoke(): List<StorageTypeDataModel> = listOf(
        StorageTypeDataModel(StorageType.Memory, "0m 0s"),
        StorageTypeDataModel(StorageType.Filesystem, "0m 0s")
    )
}
