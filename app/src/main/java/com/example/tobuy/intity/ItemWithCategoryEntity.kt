package com.example.tobuy.intity

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithCategoryEntity(
    @Embedded
    val itemEntity: ItemEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryEntity: CategoryEntity?
)