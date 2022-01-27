package com.example.tobuy.ui.customization

import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyController
import com.example.tobuy.R
import com.example.tobuy.addHeaderModel
import com.example.tobuy.databinding.ModelCategoryBinding
import com.example.tobuy.databinding.ModelEmptyButtonBinding
import com.example.tobuy.databinding.ModelPriorityColorItemBinding
import com.example.tobuy.intity.CategoryEntity
import com.example.tobuy.ui.epoxy.ViewBindingKotlinModel

class CustomizationEpoxyController(private val customizationInterface: CustomizationInterface) :
    EpoxyController() {

    var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        // Categories section
        addHeaderModel("Categories")

        categories.forEach {
            CategoryEpoxyModel(it).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add Category", customizationInterface)
            .id("add_category")
            .addTo(this)

        // Priority customization section
        addHeaderModel("Priorities")
    }

    // region EpoxyModels
    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {
        override fun ModelCategoryBinding.bind() {
            textView.text = categoryEntity.name
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {
        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener {
                customizationInterface.onCategoryEmptyStateClicked()
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class PriorityColorItemEpoxyModel(
        val displayText: String,
        val displayColor: Int,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelPriorityColorItemBinding>(R.layout.model_priority_color_item) {

        override fun ModelPriorityColorItemBinding.bind() {
            textView.text = displayText
            //root.setStrokeColor(ColorStateList.valueOf(displayColor))
            imageView.setBackgroundColor(displayColor)
            imageView.setOnClickListener { customizationInterface.onPrioritySelected(displayText) }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

}