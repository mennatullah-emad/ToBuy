package com.example.tobuy.intity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey var id: String = "",
    var title: String = "",
    var description: String? = null,
    var priority: Int = 0,
    var createdAt: Long = 0L,
    var categoryId: String = ""
)