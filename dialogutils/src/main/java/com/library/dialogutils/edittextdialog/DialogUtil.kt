package com.library.dialogutils.edittextdialog

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.library.dialogutils.R
import com.library.dialogutils.databinding.DialogEdtLayoutBinding


const val TAG = "DialogUtils Debug"

fun showBottomSheetWithEdiText(
    context: Context,
    editTextDialogData: EditTextDialogData,
    positiveCallback: ((String) -> Unit)? = null,
    negativeCallback: (() -> Unit)? = null,
    dismissCallback: (() -> Unit)? = null
) {
    val bottomSheetDialog =
        BottomSheetDialog(context, R.style.BottomSheetStyle_EditText)
    bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED)
    val view: DialogEdtLayoutBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_edt_layout,
            null,
            false
        )

    val updateBtn: MaterialButton = view.bottomSheetButton2
    if (!editTextDialogData.positiveButtonText.isNullOrEmpty()) {
        updateBtn.text = editTextDialogData.positiveButtonText
    }
    updateBtn.isEnabled = false
    val cancelBtn: MaterialButton = view.bottomSheetButton1
    if (!editTextDialogData.negativeButtonText.isNullOrEmpty()) {
        cancelBtn.setText(editTextDialogData.negativeButtonText)
    }
    val titleTextView = view.titleTextView
    titleTextView.text = editTextDialogData.title

    val subTitleTextView = view.subtitleTextView
    if (!editTextDialogData.subtitle.isNullOrEmpty()) {
        subTitleTextView.visibility = View.VISIBLE
        subTitleTextView.text = editTextDialogData.subtitle
    }

    val updatedTextEdt = view.edtUpdatedText
    updatedTextEdt.minLines = editTextDialogData.minLines
    if (editTextDialogData.inputType != -1) {
        updatedTextEdt.setInputType(editTextDialogData.inputType)
    }

    val updatedTextInputLayout = view.inputLayoutUpdatedText
    updatedTextEdt.requestFocus()
    updatedTextInputLayout.placeholderText = editTextDialogData.placeHolderText
    updatedTextEdt.setText(editTextDialogData.text)
    updatedTextEdt.setSelection(if (editTextDialogData.text != null) editTextDialogData.text!!.length else 0)
    if (editTextDialogData.iconRes != -1) {
        updatedTextInputLayout.setStartIconDrawable(editTextDialogData.iconRes)
    }

    view.edtUpdatedText.setFilters(arrayOf<InputFilter>(LengthFilter(editTextDialogData.maxLength)))

    updatedTextEdt.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //number range validation
            if (editTextDialogData.maxNumberValidation != null) {
                var acutalText = s.toString()
                if (acutalText.isEmpty()) {
                    acutalText = "0"
                }
                var actualNumber = 0
                actualNumber = try {
                    acutalText.toInt()
                } catch (e: Exception) {
                    updatedTextInputLayout.setError("Not a number")
                    return
                }
                if (editTextDialogData.maxNumberValidation!! < actualNumber) {
                    updatedTextInputLayout.error = editTextDialogData.errorText
                    updateBtn.isEnabled = false
                } else {
                    updateBtn.isEnabled = true
                    updatedTextInputLayout.error = null
                }
                return
            }

            //using pattern
            if (!editTextDialogData.validationPattern.isNullOrEmpty() && !s.toString()
                    .trim { it <= ' ' }
                    .matches(Regex(editTextDialogData.validationPattern!!))
            ) {
                updatedTextInputLayout.setError(editTextDialogData.errorText)
                updateBtn.isEnabled = false
            } else {
                updateBtn.isEnabled = true
                updatedTextInputLayout.error = null
            }
        }

        override fun afterTextChanged(s: Editable) {}
    })
    updatedTextInputLayout.setHint(editTextDialogData.hint)
    updateBtn.setOnClickListener { v: View? ->
        positiveCallback?.invoke(updatedTextEdt.getText().toString())
        bottomSheetDialog.dismiss()
    }


    bottomSheetDialog.setOnDismissListener { dialog: DialogInterface? -> dismissCallback?.invoke() }
    cancelBtn.setOnClickListener { v: View? ->
        negativeCallback?.invoke()
        bottomSheetDialog.dismiss()
    }
    bottomSheetDialog.setContentView(view.root)
    bottomSheetDialog.show()
}

