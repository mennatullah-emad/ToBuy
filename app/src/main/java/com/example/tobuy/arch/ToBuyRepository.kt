package com.example.tobuy.arch

import com.example.tobuy.AppDatabase
import com.example.tobuy.intity.ItemEntity

class ToBuyRepository(private val appDatabase: AppDatabase) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItems(): List<ItemEntity> {
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
}