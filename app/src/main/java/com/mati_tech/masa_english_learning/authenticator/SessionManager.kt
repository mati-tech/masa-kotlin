package com.mati_tech.masa_english_learning.authenticator

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveSession(username: String?, role: String?) {
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_ROLE, role)
        editor.apply()
    }

    val username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)

    val role: String?
        get() = sharedPreferences.getString(KEY_ROLE, null)

    fun logout() {
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "session"
        private const val KEY_USERNAME = "username"
        private const val KEY_ROLE = "role"
    }
}