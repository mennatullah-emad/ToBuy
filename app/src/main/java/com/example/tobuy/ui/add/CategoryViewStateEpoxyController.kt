package com.example.tobuy.ui.add

import android.graphics.Typeface
import com.airbnb.epoxy.EpoxyController
import com.example.tobuy.R
import com.example.tobuy.arch.ToBuyViewModel
import com.example.tobuy.databinding.ModelCategoryItemSelectionBinding
import com.example.tobuy.getAttrColor
import com.example.tobuy.ui.epoxy.LoadingEpoxyModel
import com.example.tobuy.ui.epoxy.ViewBindingKotlinModel

class CategoryViewStateEpoxyController(private val onCategorySelected: (String) -> Unit): EpoxyController() {

    var viewState = ToBuyViewModel.CategoriesViewState()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (viewState.isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        viewState.itemList.forEach { item ->
            CategoryViewStateItem(item, onCategorySelected).id(item.categoryEntity.id).addTo(this)
        }
    }

    data class CategoryViewStateItem(
        val item: ToBuyViewModel.CategoriesViewState.Item,
        private val onCategorySelected: (String) -> Unit
    ): ViewBindingKotlinModel<ModelCategoryItemSelectionBinding>(R.layout.model_category_item_selection) {

        override fun ModelCategoryItemSelectionBinding.bind() {

            textView.text = item.categoryEntity.name
            root.setOnClickListener { onCategorySelected(item.categoryEntity.id) }

            val colorRes = if (item.isSelected) R.attr.colorSecondary else R.attr.colorOnSurface
            val color = root.getAttrColor(colorRes)
            textView.setTextColor(color)
            textView.typeface = if (item.isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            //root.setStrokeColor(ColorStateList.valueOf(color))
        }
    }

}