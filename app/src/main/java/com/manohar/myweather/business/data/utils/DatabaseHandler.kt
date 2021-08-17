package com.manohar.myweather.business.data.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, "schedulesManager",
    null, 1) {

    val DATABASE_VERSION = 1
    val DATABASE_NAME = "schedulesManager"
    val TABLE_SCHEDULES = "schedules"
    val KEY_ID = "id"
    val KEY_DATA = "data"
    val KEY_ADD_MORE_SAMPLES = "add_more_samples"
    val KEY_FINAL_RESPONSE = "final_response"
    val KEY_IMAGES_URL = "images_url"
    val KEY_UPLOAD_IMAGES = "upload_images_url"
    val KEY_SIGNATURE_URL = "signature_url"
    val KEY_SITE_ID = "site_id"



    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SCHEDULES_TABLE = ("CREATE TABLE " + TABLE_SCHEDULES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_DATA + " TEXT, " +
                KEY_ADD_MORE_SAMPLES + " TEXT, " + KEY_FINAL_RESPONSE + " TEXT, " + KEY_SITE_ID + " TEXT, "
                + KEY_IMAGES_URL + " TEXT, " + KEY_UPLOAD_IMAGES + " TEXT, " + KEY_SIGNATURE_URL + " TEXT, " +
                "UNIQUE(" + KEY_DATA + "), UNIQUE(" + KEY_ADD_MORE_SAMPLES + ")" + ")")
        db.execSQL(CREATE_SCHEDULES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCHEDULES")
        onCreate(db)
    }

    fun addData(data: String?) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val contentValues = ContentValues()
        contentValues.put(KEY_DATA, data)
        db.insert(TABLE_SCHEDULES, null, contentValues)
        db.close()
    }

    fun setAllImagesUrl(
        siteId: String?, imagesUrl: String?, uploadImagesUrl: String?, signatureUrl: String?,
        finalResponse: String?
    ) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val contentValues = ContentValues()
        contentValues.put(KEY_SITE_ID, siteId)
        contentValues.put(KEY_IMAGES_URL, imagesUrl)
        contentValues.put(KEY_UPLOAD_IMAGES, uploadImagesUrl)
        contentValues.put(KEY_SIGNATURE_URL, signatureUrl)
        contentValues.put(KEY_FINAL_RESPONSE, finalResponse)
        db.insert(TABLE_SCHEDULES, null, contentValues)
        db.close()
    }

    fun setKeyAddMoreSamples(samples: String?) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val contentValues = ContentValues()
        contentValues.put(KEY_ADD_MORE_SAMPLES, samples)
        db.insert(TABLE_SCHEDULES, null, contentValues)
        db.close()
    }

    fun setKeyFinalResponse(finalResponse: String?) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val contentValues = ContentValues()
        contentValues.put(KEY_FINAL_RESPONSE, finalResponse)
        //        db.insert(TABLE_SCHEDULES, null, contentValues);
        db.update(TABLE_SCHEDULES, contentValues, KEY_FINAL_RESPONSE + "IS NOT NULL", null)
        db.close()
    }

    fun getData(): String? {
        val selectQuery =
            ("SELECT * FROM " + TABLE_SCHEDULES + " WHERE " + KEY_DATA + " IS NOT NULL "
                    + " ORDER BY " + KEY_ID + " DESC LIMIT 1")
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        var data: String? = null

        /*if (cursor != null) {
            do {
                data = cursor.getString(0);
            } while (cursor.moveToNext());
        }*/if (cursor.moveToFirst()) {
            data = cursor.getString(1)
        }
        Objects.requireNonNull(cursor).close()
        return data
    }

    fun getKeyAddMoreSamples(): String? {
        val selectQuery =
            ("SELECT " + KEY_ADD_MORE_SAMPLES + " FROM " + TABLE_SCHEDULES + " WHERE " + KEY_ADD_MORE_SAMPLES
                    + " IS NOT NULL" + " ORDER BY " + KEY_ID + " DESC LIMIT 1")
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        var data: String? = null
        if (cursor != null && cursor.moveToFirst()) {
            data = cursor.getString(0)
        }
        Objects.requireNonNull(cursor)!!.close()
        return data
    }

    fun getKeyImagesUrl(siteId: String): String? {
        val selectQuery =
            "SELECT $KEY_IMAGES_URL FROM $TABLE_SCHEDULES WHERE $KEY_SITE_ID=$siteId"
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        var imagesUrl: String? = null
        if (cursor != null && cursor.moveToFirst()) {
            imagesUrl = cursor.getString(0)
        }
        Objects.requireNonNull(cursor)!!.close()
        return imagesUrl
    }

    fun getKeyUploadImages(siteId: String): String? {
        val selectQuery =
            "SELECT $KEY_UPLOAD_IMAGES FROM $TABLE_SCHEDULES WHERE $KEY_SITE_ID=$siteId"
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        var imagesUrl: String? = null
        if (cursor != null && cursor.moveToFirst()) {
            imagesUrl = cursor.getString(0)
        }
        Objects.requireNonNull(cursor)!!.close()
        return imagesUrl
    }

    fun getKeySignatureUrl(siteId: String): String? {
        val selectQuery =
            "SELECT $KEY_SIGNATURE_URL FROM $TABLE_SCHEDULES WHERE $KEY_SITE_ID=$siteId"
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        var imagesUrl: String? = null
        if (cursor != null && cursor.moveToFirst()) {
            imagesUrl = cursor.getString(0)
        }
        Objects.requireNonNull(cursor)!!.close()
        return imagesUrl
    }

    fun isNullAttributes(): Int {
        val query = "SELECT $KEY_SIGNATURE_URL FROM $TABLE_SCHEDULES"
        val db: SQLiteDatabase = this.getWritableDatabase()
        var empty: Int
        db.rawQuery(query, null).use { cursor -> empty = cursor.columnCount }
        return empty
    }

    fun getKeySiteId(): String? {
        val query = "SELECT " + KEY_SITE_ID + " FROM " + TABLE_SCHEDULES + " WHERE " + KEY_SITE_ID +
                " IS NOT NULL"
        val db: SQLiteDatabase = this.getWritableDatabase()
        var site_id: String? = null
        db.rawQuery(query, null).use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                site_id = cursor.getString(0)
            }
        }
        return site_id
    }

    fun getKeyFinalResponse(): String? {
        val selectQuery =
            ("SELECT " + KEY_FINAL_RESPONSE + " FROM " + TABLE_SCHEDULES + " WHERE " + KEY_FINAL_RESPONSE
                    + " IS NOT NULL" + " ORDER BY " + KEY_ID + " DESC LIMIT 1")
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        var data: String? = null
        if (cursor != null && cursor.moveToFirst()) {
            data = cursor.getString(0)
        }
        Objects.requireNonNull(cursor)!!.close()
        return data
    }

    fun removeUploadedData(site_id: String) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        db.delete(TABLE_SCHEDULES, "$KEY_SITE_ID=$site_id", null)
    }

    fun removeScheduledData() {
        val db: SQLiteDatabase = this.getWritableDatabase()
        db.delete(TABLE_SCHEDULES, "$KEY_DATA IS NOT NULL", null)
    }

    fun removeMoreSamplesData() {
        val db: SQLiteDatabase = this.getWritableDatabase()
        db.delete(TABLE_SCHEDULES, "$KEY_ADD_MORE_SAMPLES IS NOT NULL", null)
    }

    fun deleteTable() {
        val db: SQLiteDatabase = this.getWritableDatabase()
        db.delete(TABLE_SCHEDULES, null, null)
    }
}
