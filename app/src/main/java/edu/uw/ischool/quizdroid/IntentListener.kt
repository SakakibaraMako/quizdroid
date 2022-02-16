package edu.uw.ischool.quizdroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class IntentListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val app = context as QuizApp
        if (intent.action == app.UPDATE) {
            if (app.isConnected()) app.update()
        }
        if (intent.action == app.DOWNLOAD_COMPLETE) {
            app.checkDownload()
        }
    }

}