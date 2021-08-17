package com.library.dialogutils.searchdialog

import java.io.Serializable

abstract class ModelToSearchEntryConverter<Model>(modelList: List<Model>?) {
    private val dropDownEntryList = arrayListOf<SearchEntry>()

    init {
        modelList?.let {
            dropDownEntryList.clear()
            for (i in it) {
                dropDownEntryList.add(
                    SearchEntry(
                        id = getId(i),
                        name = getName(i)
                    )
                )
            }
        }
    }

    abstract fun getId(model: Model): String

    abstract fun getName(model: Model): String


    fun getEntries(): List<SearchEntry> {
        return dropDownEntryList
    }
}

data class SearchEntry(
    val id: String,
    val name: String
) : Serializable