package com.library.dialogutils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.library.dialogutils.databinding.BottomSheetDialogLayoutBinding

fun Activity.showBottomSheet(
    title: String,
    desc: Spannable,
    closeDialogWhenClickOnButton: Boolean = true,
    cancellable: Boolean = true,
    positiveText: String? = null,
    positiveClickCallback: (() -> Unit)? = null,
    negativeText: String? = null,
    negativeClickCallback: (() -> Unit)? = null,
    neutralText: String? = null,
    neutralClickCallback: (() -> Unit)? = null,
    onDismissCallback: (() -> Unit)? = null
) {

    val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
    val binding: BottomSheetDialogLayoutBinding = DataBindingUtil.inflate(
        LayoutInflater.from(this),
        R.layout.bottom_sheet_dialog_layout,
        null,
        false
    )
    bottomSheetDialog.setContentView(binding.root)
    binding.bottomSheetTitle.text = title
    binding.bottomSheetBody.text = desc

    binding.bottomSheetBtnPositive.isVisible = positiveText != null
    binding.bottomSheetBtnNegative.isVisible = negativeText != null
    binding.bottomSheetBtnNeutral.isVisible = neutralText != null

    binding.bottomSheetBtnPositive.text = positiveText
    binding.bottomSheetBtnNegative.text = negativeText
    binding.bottomSheetBtnNeutral.text = neutralText

    binding.bottomSheetBtnNeutral.setOnClickListener {
        if (closeDialogWhenClickOnButton) {
            bottomSheetDialog.dismiss()
        }
        neutralClickCallback?.invoke()
    }

    binding.bottomSheetBtnPositive.setOnClickListener {
        if (closeDialogWhenClickOnButton) {
            bottomSheetDialog.dismiss()
        }
        positiveClickCallback?.invoke()
    }
    binding.bottomSheetBtnNegative.setOnClickListener {
        if (closeDialogWhenClickOnButton) {
            bottomSheetDialog.dismiss()
        }
        negativeClickCallback?.invoke()
    }
    bottomSheetDialog.setOnDismissListener {
        onDismissCallback?.invoke()
    }

    bottomSheetDialog.setCancelable(cancellable)

    bottomSheetDialog.show()

}

fun Activity.showBottomSheet(
    title: String,
    desc: String,
    closeDialogWhenClickOnButton: Boolean = true,
    cancellable: Boolean = true,
    positiveText: String? = null,
    positiveClickCallback: (() -> Unit)? = null,
    negativeText: String? = null,
    negativeClickCallback: (() -> Unit)? = null,
    neutralText: String? = null,
    neutralClickCallback: (() -> Unit)? = null,
    onDismissCallback: (() -> Unit)? = null
) {
    showBottomSheet(
        title,
        SpannableString(desc),
        closeDialogWhenClickOnButton,
        cancellable,
        positiveText,
        positiveClickCallback,
        negativeText,
        negativeClickCallback,
        neutralText,
        neutralClickCallback,
        onDismissCallback
    )
}


fun Context.showBasicDialogWithSpannable(
    title: String,
    message: Spannable,
    positiveText: String? = null,
    positiveClickCallback: (() -> Unit)? = null,
    negativeText: String? = null,
    negativeClickCallback: (() -> Unit)? = null,
    neutralText: String? = null,
    neutralClickCallback: (() -> Unit)? = null
) {
    val builder = MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
    positiveText?.let {
        builder.setPositiveButton(positiveText) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
            positiveClickCallback?.invoke()
        }
    }
    negativeText?.let {
        builder.setNegativeButton(negativeText) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
            negativeClickCallback?.invoke()
        }
    }

    neutralText?.let {
        builder.setNeutralButton(neutralText) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
            neutralClickCallback?.invoke()
        }
    }
    val dialog = builder.create()
    dialog.show()
}

fun Context.showBasicDialog(
    title: String,
    message: String,
    positiveText: String? = null,
    positiveClickCallback: (() -> Unit)? = null,
    negativeText: String? = null,
    negativeClickCallback: (() -> Unit)? = null,
    neutralText: String? = null,
    neutralClickCallback: (() -> Unit)? = null
) {
    showBasicDialogWithSpannable(
        title,
        SpannableString(message),
        positiveText,
        positiveClickCallback,
        negativeText,
        negativeClickCallback,
        neutralText,
        neutralClickCallback
    )
}

