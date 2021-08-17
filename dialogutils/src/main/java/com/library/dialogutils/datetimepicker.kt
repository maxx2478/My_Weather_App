package com.library.dialogutils

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.sql.Time
import java.time.Instant
import java.util.*


/*public static BottomSheetDialog showBottomSheet(FragmentManager fragmentManager) {
   BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
   bottomSheetDialog.show(fragmentManager, bottomSheetDialog.getTag());
   return bottomSheetDialog;
}*/
fun Context.createTimePicker(
    title: String? = null,
    eCalendar: Calendar?,
    disablePreviousTimeSelect: Boolean = false,
    onTimeSelectCallback: (calender: Calendar) -> Unit
): MaterialTimePicker {
    val calendar = eCalendar ?: Calendar.getInstance()

    val timePicker = MaterialTimePicker.Builder()
        .setTitleText(title)
        .setHour(calendar.get(Calendar.HOUR_OF_DAY))
        .setMinute(calendar.get(Calendar.MINUTE))
        .setTimeFormat(CLOCK_12H)
        .setInputMode(INPUT_MODE_CLOCK)
        .build()

    timePicker.addOnPositiveButtonClickListener {

        val selectedTimeCal = Calendar.getInstance()
        selectedTimeCal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        selectedTimeCal.set(Calendar.MINUTE, timePicker.minute)

        if (disablePreviousTimeSelect) {

            val now = Calendar.getInstance()

            if (selectedTimeCal.timeInMillis < now.timeInMillis) {
                Toast.makeText(this, "Please choose future time", Toast.LENGTH_LONG).show()
                return@addOnPositiveButtonClickListener
            }
        }

        onTimeSelectCallback.invoke(selectedTimeCal)
    }

    return timePicker
}


fun Context.createDatePicker(
    title: String?,
    eCalendar: Calendar?,
    disablePreviousDates: Boolean = false,
    disableFutureDates: Boolean = false,
    onDateSelectCallback: (calender: Calendar) -> Unit
): MaterialDatePicker<*> {
    var calendar = eCalendar
    if (calendar == null) {
        calendar = Calendar.getInstance()
    }


    val dateValidator = when {
        disableFutureDates -> DateValidatorPointBackward.now()
        disablePreviousDates -> DateValidatorPointForward.now()
        else -> null
    }


    val constraints = dateValidator?.let {
        CalendarConstraints.Builder().apply {
            setValidator(dateValidator)
        }.build()
    }

    val materialDatePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(calendar!!.timeInMillis)
        .setCalendarConstraints(constraints)
        .setTitleText(title)
        .build()
    materialDatePicker.addOnPositiveButtonClickListener { selection: Long? ->
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.timeInMillis = selection!!
        onDateSelectCallback.invoke(selectedCalendar)
    }
    materialDatePicker.addOnCancelListener { dialog: DialogInterface? ->
        dialog?.dismiss()
    }

    return materialDatePicker
}