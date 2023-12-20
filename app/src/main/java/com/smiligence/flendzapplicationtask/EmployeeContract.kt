package com.smiligence.flendzapplicationtask

import android.provider.BaseColumns

object EmployeeContract {
    class EmployeeEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "employee"
            const val COLUMN_ID = "id"
            const val COLUMN_NAME = "name"
            const val COLUMN_EMAIL = "email"
        }
    }
}
