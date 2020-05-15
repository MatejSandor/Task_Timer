package com.sandor.tasktimer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "AppDatabase"

private const val DATABASE_NAME = "TaskTimer.db"
private const val DATABASE_VERSION = 1

internal class AppDatabase private constructor(context: Context) : SQLiteOpenHelper
    (context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // CREATE TABLE Tasks (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER);
        Log.d(TAG,"onCreate: starts")
        val sSql = """CREATE TABLE ${TasksContract.TABLE_NAME}(
            ${TasksContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL,
            ${TasksContract.Columns.TASK_NAME} TEXT NOT NULL,
            ${TasksContract.Columns.TASK_DESCRIPTION} TEXT,
            ${TasksContract.Columns.TASK_SORT_ORDER} INTEGER
            ); """.replaceIndent(" ")

        Log.d(TAG,"onCreate: table created $sSql")
        db.execSQL(sSql)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG,"onUpgrade: called")
        when(oldVersion) {
            1 -> {
                //upgrade logic from version 1
            }
            else -> throw IllegalStateException("OnUpgrade with unknown $newVersion")
        }
    }

    companion object : SingletonHolder<AppDatabase,Context>(::AppDatabase)

}