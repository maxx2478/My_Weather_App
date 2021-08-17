package com.manohar.myweather.framework.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.library.dialogutils.searchdialog.SearchDialogFragment
import com.library.dialogutils.searchdialog.SearchEntry
import com.library.dialogutils.searchdialog.SearchItemSelectListener
import com.manohar.myweather.databinding.ActivityMainBinding
import com.manohar.myweather.framework.ui.weather_screen.WeatherView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextWatcher, DatePickerDialog.OnDateSetListener {


    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    var showErrorOnlyOnce = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.hide()

        binding.lifecycleOwner = this
        binding.mainviewmodel = viewModel

        setTextChangeListeners()
        clickListeners()

    }

    private fun clickListeners() {
        binding.gender.setOnClickListener {
            val list:ArrayList<SearchEntry> = arrayListOf<SearchEntry>()
            list.add(SearchEntry("1", "Male"))
            list.add(SearchEntry("2", "Female"))
            list.add(SearchEntry("3", "Other"))

            val searchDialogFragment = SearchDialogFragment.newInstance(list)
            searchDialogFragment.itemSelectListener = object : SearchItemSelectListener {
                override fun onItemSelected(item: SearchEntry) {
                   viewModel.gender.value = item.name
                }
            }
            searchDialogFragment.show(
                supportFragmentManager,
                SearchDialogFragment::class.java.simpleName
            )

        }


        binding.dob.setOnClickListener {
            val now: Calendar = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                this@MainActivity,
                now.get(Calendar.YEAR),  // Initial year selection
                now.get(Calendar.MONTH),  // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            )
// If you're calling this from a support Fragment
// If you're calling this from a support Fragment
            dpd.show(supportFragmentManager, "Datepickerdialog")
        }


        binding.registerBtn.setOnClickListener {
            val i = Intent(this, WeatherView::class.java)
            startActivity(i)

        }

        binding.checkPincode.setOnClickListener {
            viewModel.isDataValid()
            viewModel.getStateDistrictData(viewModel.zipcode.value!!)
            showErrorOnlyOnce = true

            viewModel.pinRemoteData.observe(this, {
                if (it.isNullOrEmpty()) {
                    if (showErrorOnlyOnce) {
                        showErrorOnlyOnce = false
                        Toast.makeText(this, viewModel.error.value, Toast.LENGTH_SHORT).show()
                    }
                }
            })


        }
    }

    private fun setTextChangeListeners() {
        binding.mobileNumber.addTextChangedListener(this)
        binding.fullName.addTextChangedListener(this)
        binding.gender.addTextChangedListener(this)
        binding.dob.addTextChangedListener(this)
        binding.address1.addTextChangedListener(this)
        binding.pincode.addTextChangedListener(this)
        binding.stateValue.addTextChangedListener(this)
        binding.districtValue.addTextChangedListener(this)

    }


    private fun phoneError() {
        //   binding.mobileNumberTextInputLayout.requestFocus()
        binding.mobileNumberTextInputLayout.isErrorEnabled = true
        binding.mobileNumberTextInputLayout.error =
            "Phone number must be of 10 digits"
    }

    private fun removePhoneError() {
        binding.mobileNumberTextInputLayout.isErrorEnabled = false
    }

    private fun fullnameError() {
        //  binding.fullNameTextInputLayout.requestFocus()
        binding.fullNameTextInputLayout.isErrorEnabled = true
        binding.fullNameTextInputLayout.error =
            "name must be of at least 3 characters"
    }

    private fun removeFullnameError() {
        binding.fullNameTextInputLayout.isErrorEnabled = false
    }

    private fun genderError() {
        // binding.genderTextInputLayout.requestFocus()
        binding.genderTextInputLayout.isErrorEnabled = true
        binding.genderTextInputLayout.error =
            "Gender Must not be empty"
    }

    private fun removeGenderError() {
        binding.genderTextInputLayout.isErrorEnabled = false
    }

    private fun dobError() {
        // binding.dobTextInputLayout.requestFocus()
        binding.dobTextInputLayout.isErrorEnabled = true
        binding.dobTextInputLayout.error =
            "Dob must not be empty"
    }

    private fun removeDobError() {
        binding.dobTextInputLayout.isErrorEnabled = false
    }

    private fun address1Error() {
        // binding.address1TextInputLayout.requestFocus()
        binding.address1TextInputLayout.isErrorEnabled = true
        binding.address1TextInputLayout.error =
            "Address Must not be empty"
    }

    private fun removeAdddress1Error() {
        binding.address1TextInputLayout.isErrorEnabled = false
    }

    private fun zipError() {
        // binding.pincodeTextInputLayout.requestFocus()
        binding.pincodeTextInputLayout.isErrorEnabled = true
        binding.pincodeTextInputLayout.error =
            "Pin code must be of 6 digits"
    }

    private fun removeZipError() {
        binding.pincodeTextInputLayout.isErrorEnabled = false
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        if (binding.mobileNumber.isFocused) {

            viewModel.phone.observe(this, {
                if (it.length < 10) {
                    phoneError()
                } else {
                    removePhoneError()
                }
            })
        }



        if (binding.fullName.isFocused) {
            viewModel.name.observe(this, {
                if (it.length < 3) {
                    fullnameError()
                } else {
                    removeFullnameError()
                }
            })
        }



        if (binding.gender.isFocused) {
            viewModel.gender.observe(this, {
                if (it.isNullOrEmpty()) {
                    genderError()
                } else {
                    removeGenderError()
                }
            })
        }


        if (binding.dob.isFocused) {
            viewModel.dob.observe(this, {
                if (it.isNullOrEmpty()) {
                    dobError()
                } else {
                    removeDobError()
                }
            })
        }


        if (binding.address1.isFocused) {
            viewModel.address1.observe(this, {
                if (it.length < 3) {
                    address1Error()
                } else {
                    removeAdddress1Error()
                }
            })
        }



        if (binding.pincode.isFocused) {
            viewModel.zipcode.observe(this, {
                if (it.length != 6) {
                    zipError()
                } else {
                    removeZipError()
                }
            })
        }

        viewModel.isDataValid()


    }

    override fun afterTextChanged(s: Editable?) {
        viewModel.isDataValid()

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year
        viewModel.dob.value = date
    }


}