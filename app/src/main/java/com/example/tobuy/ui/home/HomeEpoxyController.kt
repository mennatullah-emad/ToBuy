package com.example.tobuy.ui.home

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.example.tobuy.R
import com.example.tobuy.databinding.ModelItemEntityBinding
import com.example.tobuy.intity.ItemEntity
import com.example.tobuy.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(private val itemEntityInterface: ItemEntityInterface) : EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<ItemEntity>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            //todo loading state
            return
        }

        if (itemEntityList.isEmpty()) {
            //todo empty state
            return
        }

        itemEntityList.forEach { item ->
            ItemEntityEpoxyModel(item, itemEntityInterface).id(item.id).addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemEntity, val itemEntityInterface: ItemEntityInterface) :
        ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            titleTv.text = itemEntity.title

            if (itemEntity.description == null) {
                descriptionTv.isGone = true
            } else {
                descriptionTv.isVisible = true
                descriptionTv.text = itemEntity.description
            }

            deleteImg.setOnClickListener {
                itemEntityInterface.onDeleteItemEntity(itemEntity)
            }

            priorityTv.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
            }

        }
    }

}