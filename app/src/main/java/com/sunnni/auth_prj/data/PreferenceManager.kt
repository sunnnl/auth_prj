package com.sunnni.auth_prj.data

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

    private const val AUTH_PREF = "auth_pref"

    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"

    private fun getUserPreferences(ctx : Context) : SharedPreferences {
        return ctx.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE)
    }

    fun setAccessToken(context: Context, token: String) {
        val pref = getUserPreferences(context)
        val editor = pref.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getAccessToken(context: Context): String {
        val pref = getUserPreferences(context)
        return pref.getString(ACCESS_TOKEN, "")!!
    }

    fun clearAccessToken(context: Context) {
        val pref = getUserPreferences(context)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    fun setRefreshToken(context: Context, token: String) {
        val pref = getUserPreferences(context)
        val editor = pref.edit()
        editor.putString(REFRESH_TOKEN, token)
        editor.apply()
    }

    fun getRefreshToken(context: Context): String {
        val pref = getUserPreferences(context)
        return pref.getString(REFRESH_TOKEN, "")!!
    }

    fun clearRefreshToken(context: Context) {
        val pref = context.getSharedPreferences(REFRESH_TOKEN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }
}