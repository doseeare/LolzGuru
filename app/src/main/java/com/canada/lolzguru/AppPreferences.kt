package com.canada.lolzguru

import android.content.Context
import android.content.SharedPreferences
import android.util.ArraySet
import java.util.*

object AppPreferences {
    private const val NAME = "LOLZT"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var lastLoadedPageURL: String
        get() = preferences.getString("lastLoadedPageURL", "https://lolz.guru")!!
        set(value) = preferences.edit {
            it.putString("lastLoadedPageURL", value)
        }
}