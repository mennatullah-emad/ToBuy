package com.example.tobuy.dao

import androidx.room.*
import com.example.tobuy.intity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao  //data access object
interface ItemEntityDao {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntities(): Flow<List<ItemEntity>>

    /*@Transaction
    @Query("SELECT * FROM item_entity")
    fun getAllItemWithCategoryEntities(): Flow<List<ItemWithCategoryEntity>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)

    /*@Update
    suspend fun update(itemEntity: ItemEntity)*/
}