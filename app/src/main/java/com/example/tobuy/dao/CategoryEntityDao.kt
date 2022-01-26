package com.example.tobuy.dao

import androidx.room.*
import com.example.tobuy.intity.CategoryEntity
import com.example.tobuy.intity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao  //data access object
interface CategoryEntityDao {

    @Query("SELECT * FROM category_entity")
    fun getAllCategoryEntities(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)

    @Update
    suspend fun update(categoryEntity: CategoryEntity)
}