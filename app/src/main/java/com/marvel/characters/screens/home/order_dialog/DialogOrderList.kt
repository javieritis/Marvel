package com.marvel.characters.screens.home.order_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.marvel.characters.R
import com.marvel.characters.databinding.DialogOrderLayoutBinding
import com.marvel.characters.screens.home.OrderType


class DialogOrderList(
    private val orderTypeSelected: OrderType,
    private val callback: ((newOrderType: OrderType?) -> Unit)
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "DialogOrderList"
    }

    private lateinit var binding: DialogOrderLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOrderLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var newOrderType: OrderType? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create chip for each order tpe.
        for (orderType in OrderType.values()) {
            val chip = Chip(context)
            chip.id = orderType.viewId
            chip.chipBackgroundColor =
                ContextCompat.getColorStateList(requireContext(), R.color.chip_bg_state_list)
            chip.text = getString(orderType.resourceName)
            chip.isCheckable = true
            chip.isChecked = orderTypeSelected == orderType
            binding.chipGroup.addView(chip)
        }

        binding.buttonApply.setOnClickListener {
            callback.invoke(newOrderType)
            dismiss()
        }

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            newOrderType = when (checkedId) {
                OrderType.NAME_ASCENDING.viewId -> OrderType.NAME_ASCENDING
                OrderType.NAME_DESCENDING.viewId -> OrderType.NAME_DESCENDING
                OrderType.MODIFICATION_DATE_ASCENDING.viewId -> OrderType.MODIFICATION_DATE_ASCENDING
                OrderType.MODIFICATION_DATE_DESCENDING.viewId -> OrderType.MODIFICATION_DATE_DESCENDING
                else -> OrderType.NAME_ASCENDING
            }
        }
    }
}