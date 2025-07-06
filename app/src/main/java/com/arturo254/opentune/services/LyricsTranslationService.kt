package com.arturo254.opentune.services

import me.bush.translator.Language
import me.bush.translator.Translator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LyricsTranslationService @Inject constructor() {

    private val translator = Translator()

    /**
     * Traduz um texto para um idioma de destino.
     * A detecção do idioma de origem é automática.
     *
     * @param text O texto a ser traduzido.
     * @param targetLanguage O idioma para o qual o texto será traduzido.
     * @return O texto traduzido ou o texto original em caso de erro.
     */
    suspend fun translate(text: String, targetLanguage: Language): String {
        // Evita chamadas de API desnecessárias para textos vazios
        if (text.isBlank()) return text

        // Executa a operação de rede em uma thread de I/O
        return withContext(Dispatchers.IO) {
            try {
                val result = translator.translate(text, targetLanguage, Language.AUTO)
                result.translatedText ?: text // Retorna o texto original se a tradução for nula
            } catch (e: Exception) {
                // Em um app de produção, seria ideal logar o erro (ex: Timber.e(e))
                e.printStackTrace()
                text // Retorna o texto original em caso de falha na API
            }
        }
    }
}
