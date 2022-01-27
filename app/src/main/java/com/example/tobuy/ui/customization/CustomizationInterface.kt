package com.example.tobuy.ui.customization

import com.example.tobuy.intity.CategoryEntity

interface CustomizationInterface {
    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
    fun onPrioritySelected(priorityName: String)
}