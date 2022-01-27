package com.example.tobuy.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentAddItemEntityBinding
import com.example.tobuy.intity.CategoryEntity
import com.example.tobuy.intity.ItemEntity
import com.example.tobuy.ui.BaseFragment
import java.util.*

class AddItemEntityFragment : BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity: ItemEntity? by lazy {
        sharedViewModel.itemWithCategoryEntitiesLiveData.value?.find {
            it.itemEntity.id == safeArgs.selectedItemEntityId
        }?.itemEntity
    }

    private var isInEditMode: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtn.setOnClickListener {
            saveItemEntityToDatabase()
        }

        binding.quantitySb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentText = binding.titleEt.text.toString().trim()
                if (currentText.isEmpty()) {
                    return
                }
                val startIndex = currentText.indexOf("[") - 1
                val newText = if (startIndex > 0) {
                    "${currentText.substring(0, startIndex)} [$progress]"
                } else {
                    "$currentText [$progress]"
                }

                val sanitizedText = newText.replace("[1]", "")
                binding.titleEt.setText(sanitizedText)
                binding.titleEt.setSelection(sanitizedText.length)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //nothing to do
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //nothing to do
            }

        })

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                if (isInEditMode) {
                    navigateUp()
                    return@observe
                }

                Toast.makeText(requireActivity(), "Item saved!", Toast.LENGTH_SHORT).show()
                binding.titleEt.text = null
                binding.titleEt.requestFocus()

                binding.descriptionEt.text = null
                binding.radioGroup.check(R.id.radioButtonLow)
                binding.quantitySb.progress = 1
            }
        }

        //setup screen if we are in edit mode
        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true
            binding.titleEt.setText(itemEntity.title)
            binding.descriptionEt.setText(itemEntity.description)
            when (itemEntity.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }
            binding.saveBtn.text = "Update"
            mainActivity.supportActionBar?.title = "Update item"

            if (itemEntity.title.contains("[")) {
                val startIndex = itemEntity.title.indexOf("[") + 1
                val endIndex = itemEntity.title.indexOf("]")

                try {
                    val progress = itemEntity.title.substring(startIndex, endIndex).toInt()
                    binding.quantitySb.progress = progress
                } catch (e: Exception) {
                    // Whoops
                }
            }
        }

        val categoryViewStateEpoxyController = CategoryViewStateEpoxyController { categoryId ->
            sharedViewModel.onCategorySelected(categoryId)
        }
        binding.categoriesEpoxyRecyclerView.setController(categoryViewStateEpoxyController)
        sharedViewModel.onCategorySelected(selectedItemEntity?.categoryId ?: CategoryEntity.DEFAULT_CATEGORY_ID, true)
        sharedViewModel.categoriesViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            categoryViewStateEpoxyController.viewState = viewState
        }

    }

    private fun saveItemEntityToDatabase() {
        val itemTitle = binding.titleEt.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = "* Required field"
            return
        }
        binding.titleTextField.error = null

        var itemDescription: String? = binding.descriptionEt.text.toString().trim()
        if (itemDescription?.isEmpty() == true) {
            itemDescription = null
        }
        val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        val itemCategoryId = sharedViewModel.categoriesViewStateLiveData.value?.getSelectedCategoryId() ?: return

        if (isInEditMode) {
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority,
                categoryId = itemCategoryId
            )

            sharedViewModel.updateItem(itemEntity)
            return
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = itemCategoryId
        )

        sharedViewModel.insertItem(itemEntity)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}