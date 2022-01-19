package com.example.tobuy.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobuy.AppDatabase
import com.example.tobuy.intity.ItemEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ToBuyViewModel: ViewModel() {
    private lateinit var repository:ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

     fun init(appDatabase: AppDatabase){
        repository = ToBuyRepository(appDatabase)

        viewModelScope.launch {
            val items = repository.getAllItems()
            itemEntitiesLiveData.postValue(items)
        }
    }

    fun insertItem(itemEntity: ItemEntity) {
        repository.insertItem(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        repository.deleteItem(itemEntity)
    }
}