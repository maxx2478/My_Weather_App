package com.library.dialogutils.searchdialog

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.library.dialogutils.BR
import com.library.dialogutils.R
import com.library.dialogutils.databinding.SearchDialogFragmentBinding
import com.library.dialogutils.hideKeyboard

class SearchDialogFragment : DialogFragment() {
    private lateinit var binding: SearchDialogFragmentBinding
    private lateinit var viewModel: SearchDialogViewModel

    private var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>? = null

    var itemSelectListener: SearchItemSelectListener? = null

    companion object {

        val TAG = SearchDialogFragment::class.java.simpleName

        fun newInstance(list: List<SearchEntry>): SearchDialogFragment {
            val fragment = SearchDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable("LIST", ArrayList(list))
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenSearchDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_dialog_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(
            this,
            Factory(arguments?.getSerializable("LIST")!! as List<SearchEntry>)
        ).get(SearchDialogViewModel::class.java)
        binding.setVariable(BR.dropDownSearchDialogViewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
            return@setOnKeyListener true;
        }
        setUpBottomSheetBehavior()
        handleCoordinatorLayoutClicks()
        initRecyclerView()

    }


    private fun handleCoordinatorLayoutClicks() {
        binding.coordinatorSearchDialog.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun initRecyclerView() {
        val dropDownRecyclerViewAdapter = SearchRecyclerViewAdapter {
            requireActivity().hideKeyboard()
            itemSelectListener?.onItemSelected(it)
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        viewModel.filteredList.observe(viewLifecycleOwner) {
            val recycleViewState: Parcelable? =
                binding.recyclerViewDropDown.layoutManager?.onSaveInstanceState()
            dropDownRecyclerViewAdapter.submitList(it)
            binding.recyclerViewDropDown.layoutManager?.onRestoreInstanceState(recycleViewState)

        }
        binding.recyclerViewDropDown.adapter = dropDownRecyclerViewAdapter
    }

    private fun setUpBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.cardDropDown)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        Handler(Looper.myLooper()!!).postDelayed({
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }, 250)
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }


}