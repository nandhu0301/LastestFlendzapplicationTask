package com.smiligence.flendzapplicationtask

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }




    fun insertEmployee(employeee: List<Usetdetails>) {
        val db = writableDatabase
        for (employee in employeee) {
            val values = ContentValues().apply {
                put(EmployeeContract.EmployeeEntry.COLUMN_ID, employee.id)
                put(EmployeeContract.EmployeeEntry.COLUMN_NAME, employee.name)
                put(EmployeeContract.EmployeeEntry.COLUMN_EMAIL, employee.email)
            }

            db.insert(EmployeeContract.EmployeeEntry.TABLE_NAME, null, values)
        }
        db.close()
    }

    fun getAllEmployees(): List<Usetdetails> {
        val employees = mutableListOf<Usetdetails>()
        val db = readableDatabase

        val cursor = db.query(
            EmployeeContract.EmployeeEntry.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(EmployeeContract.EmployeeEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(EmployeeContract.EmployeeEntry.COLUMN_NAME))
                val email = getString(getColumnIndexOrThrow(EmployeeContract.EmployeeEntry.COLUMN_EMAIL))

                employees.add(Usetdetails(id, name,email))
            }
            close()
        }

        return employees
    }


    companion object {
        const val DATABASE_NAME = "YourEmployee"
        const val DATABASE_VERSION = 1

        // Define your table and columns here
        private  var SQL_CREATE_ENTRIES = """
            CREATE TABLE ${EmployeeContract.EmployeeEntry.TABLE_NAME} (
                ${EmployeeContract.EmployeeEntry.COLUMN_ID} INTEGER PRIMARY KEY,
                ${EmployeeContract.EmployeeEntry.COLUMN_NAME} TEXT,
                 ${EmployeeContract.EmployeeEntry.COLUMN_EMAIL} TEXT
            )""".trimIndent()

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${EmployeeContract.EmployeeEntry.TABLE_NAME}"
    }
}






