package com.mati_tech.masa_english_learning.databaseHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper // Constructor
    (private val mContext: Context?) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, databaseVersion) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create the database table
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade
//        if (oldVersion < 2) {
//            // For database version 2 and above, add the role column if it doesn't exist
//            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_ROLE + " TEXT;");
//        }
    }

    // Optional: onDowngrade method to handle database downgrade
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database downgrade
        onUpgrade(db, oldVersion, newVersion)
    }

    // Getter methods for accessing the constants if needed
    override fun getDatabaseName(): String {
        return DATABASE_NAME
    }

    fun insertUser(username: String, password: String, role: String): Long {
        // Get a writable database
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(columnUsername, username)
        values.put(columnPassword, password)
        values.put(COLUMN_ROLE, role)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(tableName, null, values)

        // Close the database connection
        db.close()
        return newRowId
    }

    fun readUser(username: String, password: String): Boolean {
        // Get a readable database
        val db = this.readableDatabase

        // Define a projection that specifies which columns from the database you will actually use after this query
        val projection = arrayOf(
            columnId,
            columnUsername,
            columnPassword
        )

        // Define 'where' part of query
        val selection = "$columnUsername = ? AND $columnPassword = ?"
        val selectionArgs = arrayOf(username, password)

        // Perform the query
        val cursor = db.query(
            tableName,  // The table to query
            projection,  // The array of columns to return (pass null to get all)
            selection,  // The columns for the WHERE clause
            selectionArgs,  // The values for the WHERE clause
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null // The sort order
        )
        // Check if a user was found
        val userExists = cursor.count > 0
        // Close the cursor and the database
        cursor.close()
        db.close()
        return userExists
    }


    // You can delete the user here using this command
    fun deleteDatabase() {
        mContext!!.deleteDatabase(DATABASE_NAME)
    }


    // Method to check if a user exists and return their role
    // Method to get the role of a user by username
    fun getUserRole(username: String): String? {
        val db = this.readableDatabase
        val projection = arrayOf(columnUsername, COLUMN_ROLE)
        val selection = "$columnUsername = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(tableName, projection, selection, selectionArgs, null, null, null)
        var role: String? = null
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE))
        }
        cursor.close()
        db.close()
        return role
    }

    // Method to get the email of a user by username
    fun getUserEmail(username: String): String? {
        val db = this.readableDatabase
        val projection = arrayOf(columnUsername)
        val selection = "$columnUsername = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(tableName, projection, selection, selectionArgs, null, null, null)
        var userEmail: String? = null
        if (cursor.moveToFirst()) {
            userEmail = cursor.getString(cursor.getColumnIndexOrThrow(columnUsername))
        }
        cursor.close()
        db.close()
        return userEmail
    }


    companion object {
        // Static constants
        private const val DATABASE_NAME = "UserDatabase.db"
        const val databaseVersion: Int = 1
        const val tableName: String = "LoginDetails"
        const val columnId: String = "id"
        const val columnUsername: String = "username"
        const val columnPassword: String = "password"

        private const val COLUMN_ROLE = "role"

        // SQL statement to create the table
        private const val TABLE_CREATE = "CREATE TABLE " + tableName + " (" +
                columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                columnUsername + " TEXT, " +
                columnPassword + " TEXT, " +
                COLUMN_ROLE + " TEXT);"
    }
}