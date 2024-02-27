package id.co.tonim.bniqris.data

import android.content.Context

object SharedPreferencesManager {
    private const val PREFS_NAME = "MyPrefs"
    private const val KEY_SALDO = "saldo"
    private const val DEFAULT_SALDO = 1000000

    fun saveSaldo(context: Context, saldo: Int?) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_SALDO, saldo!!).apply()
    }

    fun getSaldo(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_SALDO, DEFAULT_SALDO)
    }
}

