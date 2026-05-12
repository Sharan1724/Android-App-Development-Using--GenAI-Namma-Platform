package com.namma.platform.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TTSManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Try Kannada first
            val kannadaLocale = Locale("kn", "IN")
            val result = tts.setLanguage(kannadaLocale)
            isReady = if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TTSManager", "Kannada not supported, falling back to English")
                tts.setLanguage(Locale.ENGLISH)
                true
            } else {
                true
            }
            tts.setSpeechRate(0.85f) // Slightly slower for clarity
            tts.setPitch(1.0f)
        } else {
            Log.e("TTSManager", "TTS Initialization failed")
        }
    }

    fun speak(text: String) {
        if (isReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "namma_tts_${System.currentTimeMillis()}")
        }
    }

    /**
     * Builds announcement text in Kannada + English
     * e.g., "Train 16589, Platform 1, General Coach number 2"
     */
    fun announceTrainInfo(
        trainName: String,
        platformNumber: Int,
        arrivalTime: String,
        destination: String
    ) {
        val announcement = buildString {
            append("ಗಮನಿಸಿ. ") // "Attention" in Kannada
            append("ರೈಲು $trainName, ")
            append("ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ನಂಬರ್ $platformNumber, ")
            append("$arrivalTime ಗೆ ಬರುತ್ತದೆ. ")
            append("$destination ಕಡೆ ಹೋಗುತ್ತದೆ.")
        }
        speak(announcement)
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}
