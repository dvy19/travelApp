package com.example.travelguide

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val ROLE="role"
    }


    fun saveRole(role:String){
        prefs.edit().putString(ROLE,role).apply()

    }


    fun getRole():String?{
        return prefs.getString(ROLE,null)
    }

    // ✅ Save token after login
    fun saveAuthToken(token: String) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    // ✅ Get token
    fun getAuthToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    // ✅ Check login status
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // ✅ Logout (clear session)
    fun logout() {
        prefs.edit().clear().apply()
    }
}