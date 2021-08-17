package com.library.dialogutils.edittextdialog

open class DialogData {
    var title: String? = null
    var subtitle: String? = null
    var positiveButtonText: String? = null
    var negativeButtonText: String? = null
}

class EditTextDialogData : DialogData() {
    var placeHolderText: String? = null
    var hint: String? = null
    var text: String? = null
    var iconRes = 0
    var minNumberValidation: Float? = null
    var maxNumberValidation: Float? = null
    var validationPattern: String? = null
    var errorText: String? = null
    var minLines = 1
    var inputType = -1
    var maxLength = 10000
}