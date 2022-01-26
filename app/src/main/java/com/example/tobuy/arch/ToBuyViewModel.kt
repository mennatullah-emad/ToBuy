package com.example.tobuy.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobuy.AppDatabase
import com.example.tobuy.intity.CategoryEntity
import com.example.tobuy.intity.ItemEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToBuyViewModel: ViewModel() {
    private lateinit var repository:ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()
    val categoryEntitiesLiveData = MutableLiveData<List<CategoryEntity>>()

    val transactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

     fun init(appDatabase: AppDatabase){
        repository = ToBuyRepository(appDatabase)

         // Initialize our Flow connectivity to the DB for ItemEntity and CategoryEntity
        viewModelScope.launch {
            repository.getAllItems().collect {items->
                itemEntitiesLiveData.postValue(items)
            }
         }

         viewModelScope.launch {
             repository.getAllCategories().collect {categories->
                 categoryEntitiesLiveData.postValue(categories)
             }
         }
    }

    // region ItemEntity
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    // endregion CategoryEntity

    // region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(categoryEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    // endregion CategoryEntity
}