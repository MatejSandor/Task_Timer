package com.sandor.tasktimer

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        testInsert()
//        testUpdate()

//        val projection = arrayOf(TasksContract.Columns.TASK_NAME,TasksContract.Columns.TASK_SORT_ORDER)
        val sortColumn = TasksContract.Columns.TASK_SORT_ORDER

        val cursor = contentResolver.query(TasksContract.CONTENT_URI,
        null,
        null,
        null,
        sortColumn)

        Log.d(TAG,"**********************************")
        cursor?.use {
            while (it.moveToNext()) {
                with(cursor) {
                    val id = getLong(0)
                    val name = getString(1)
                    val description = getString(2)
                    val sortOrder = getInt(3)
                    val result = "id: $id Name: $name Description: $description Sort order: $sortOrder"
                    Log.d(TAG,"onCreate: reading data $result")
                }
            }
        }
        Log.d(TAG,"**********************************")

    }

    private fun testInsert() {
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_NAME, "Test Task 1")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Test description 1")
            put(TasksContract.Columns.TASK_SORT_ORDER, 2)
        }

        val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
        Log.d(TAG,"testInsert: new row id (in uri) is $uri")
        if(uri != null) {
            Log.d(TAG,"testInsert: id (in uri) is ${TasksContract.getId(uri)}")
        }

    }

    private fun testUpdate() {
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_NAME, "Content Provider")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Content Provider development")
        }

        val taskUri = TasksContract.buildUriFromId(4)
        val rowsAffected = contentResolver.update(taskUri, values,null,null)
        Log.d(TAG,"testUpdate: number of rows updated is $rowsAffected")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
