package com.sunnni.auth_prj.data

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PreferenceManager {

    private const val AUTH_PREF = "auth_pref"
    private const val MASTER_KEY_ALIAS = "_androidx_security_master_key"
    private const val KEY_SIZE = 256
    private const val PREFERENCE_FILE_KEY = "_preferences"

    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"

    private fun getUserPreferences(ctx: Context): SharedPreferences {
        return ctx.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE)
    }

    private fun getCryptoPreferences(ctx: Context): SharedPreferences {
        val spec = KeyGenParameterSpec
            .Builder(
                MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()
        val masterKey = MasterKey.Builder(ctx).setKeyGenParameterSpec(spec).build()

        return EncryptedSharedPreferences.create(
            ctx,
            ctx.packageName + PREFERENCE_FILE_KEY,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setAccessToken(context: Context, token: String) {
        val pref = getCryptoPreferences(context)
        val editor = pref.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getAccessToken(context: Context): String {
        val pref = getCryptoPreferences(context)
        return pref.getString(ACCESS_TOKEN, "")!!
    }

    fun clearAccessToken(context: Context) {
        val pref = getCryptoPreferences(context)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    fun setRefreshToken(context: Context, token: String) {
        val pref = getCryptoPreferences(context)
        val editor = pref.edit()
        editor.putString(REFRESH_TOKEN, token)
        editor.apply()
    }

    fun getRefreshToken(context: Context): String {
        val pref = getCryptoPreferences(context)
        return pref.getString(REFRESH_TOKEN, "")!!
    }

    fun clearRefreshToken(context: Context) {
        val pref = getCryptoPreferences(context)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }
}