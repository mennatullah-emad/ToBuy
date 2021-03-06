package com.example.tobuy.ui.home

import android.content.res.ColorStateList
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.example.tobuy.R
import com.example.tobuy.addHeaderModel
import com.example.tobuy.arch.ToBuyViewModel
import com.example.tobuy.databinding.ModelEmptyStateBinding
import com.example.tobuy.databinding.ModelItemEntityBinding
import com.example.tobuy.intity.ItemWithCategoryEntity
import com.example.tobuy.ui.epoxy.LoadingEpoxyModel
import com.example.tobuy.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(private val itemEntityInterface: ItemEntityInterface) : EpoxyController() {


    var viewState: ToBuyViewModel.HomeViewState = ToBuyViewModel.HomeViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (viewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (viewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        viewState.dataList.forEach { dataItem ->
            if (dataItem.isHeader) {
                addHeaderModel(dataItem.data as String)
                return@forEach
            }

            val itemWithCategoryEntity = dataItem.data as ItemWithCategoryEntity
            ItemEntityEpoxyModel(itemWithCategoryEntity, itemEntityInterface)
                .id(itemWithCategoryEntity.itemEntity.id)
                .addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemWithCategoryEntity,
        val itemEntityInterface: ItemEntityInterface) :
        ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            titleTv.text = itemEntity.itemEntity.title

            if (itemEntity.itemEntity.description == null) {
                descriptionTv.isGone = true
            } else {
                descriptionTv.isVisible = true
                descriptionTv.text = itemEntity.itemEntity.description
            }

            priorityTv.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity.itemEntity)
            }

            val color = when (itemEntity.itemEntity.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.gray_700
            }
            priorityTv.setBackgroundResource(color)
            //root.setStrokeColor(ColorStateList.valueOf(color))
            root.setOnClickListener {
                itemEntityInterface.onItemSelected(itemEntity.itemEntity)
            }
            categoryNameTv.text = itemEntity.categoryEntity?.name
        }
    }

    class EmptyStateEpoxyModel : ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {
        override fun ModelEmptyStateBinding.bind() {
            // Nothing to do at the moment
        }
    }
}