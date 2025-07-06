package com.arturo254.opentune.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Declara a instância do DataStore como uma extensão de Context
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(private val context: Context) {

    // Define uma chave para armazenar o código do idioma de tradução
    private val LYRICS_TRANSLATION_LANGUAGE_KEY = stringPreferencesKey("lyrics_translation_language")

    // Valor padrão: "disabled" indica que a tradução está desativada
    private const val DEFAULT_LANGUAGE_CODE = "disabled"

    // Expõe um Flow para observar mudanças no idioma selecionado em tempo real
    val translationLanguage: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LYRICS_TRANSLATION_LANGUAGE_KEY] ?: DEFAULT_LANGUAGE_CODE
        }

    // Função para salvar a nova preferência de idioma
    suspend fun setTranslationLanguage(languageCode: String) {
        context.dataStore.edit { settings ->
            settings[LYRICS_TRANSLATION_LANGUAGE_KEY] = languageCode
        }
    }
}
