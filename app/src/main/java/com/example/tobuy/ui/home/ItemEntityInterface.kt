package com.example.tobuy.ui.home

import com.example.tobuy.intity.ItemEntity

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
    fun onDeleteItemEntity(itemEntity: ItemEntity)
    //fun onItemSelected(itemEntity: ItemEntity)
}