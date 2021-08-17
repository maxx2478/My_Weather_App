package com.library.dialogutils.searchdialog

import androidx.lifecycle.*
import java.util.*

internal class SearchDialogViewModel(
    private val listData: List<SearchEntry>
) : ViewModel() {

    val filteredList: LiveData<List<SearchEntry>>

    val searchText = MutableLiveData<String>()

    init {
        filteredList = Transformations.map(searchText) { userEnterSearchText ->
            val filterListTemp = arrayListOf<SearchEntry>()
            for (i in listData) {
                if (i.name.contains(userEnterSearchText.toLowerCase(Locale.ROOT), true)) {
                    filterListTemp.add(i)
                }
            }
            return@map filterListTemp
        }
        searchText.value = ""
    }


}

open class Factory(private val list: List<SearchEntry>) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchDialogViewModel(list) as T
    }

}