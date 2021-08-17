package com.library.dialogutils.infodialog

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.library.dialogutils.R
import com.library.dialogutils.databinding.DialogWaitLayoutBinding

fun Activity.showProgressDialog(message: String): Dialog {
    val binding: DialogWaitLayoutBinding =
        DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_wait_layout, null, false)
    binding.txtViewWaitDialogMessage.text = message
    val materialDialog = MaterialAlertDialogBuilder(this)
        .setCancelable(false)
        .setView(binding.root)
        .create()
    materialDialog.show()
    return materialDialog
}