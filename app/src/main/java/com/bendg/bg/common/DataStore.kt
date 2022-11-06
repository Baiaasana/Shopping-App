package com.bendg.bg.common

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bendg.bg.App
import com.bendg.bg.common.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

object DataStore {


    private val Application.store by preferencesDataStore(name = Constants.KEY)

    fun getPreferences(): Flow<Preferences> {
        return App.context.store.data
    }

    suspend fun save(key: String, value: Boolean) {
        App.context.store.edit {
            it[stringPreferencesKey(key)] = value.toString()
        }
    }

    suspend fun remove(key: String) {
        App.context.store.edit {
            it.remove(stringPreferencesKey(key))
        }
    }

    suspend fun clear(){
        App.context.store.edit {
            it.clear()
        }
    }

    suspend fun read(key: String): String? {
        return App.context.store.data.first()[stringPreferencesKey(key)]
    }
}