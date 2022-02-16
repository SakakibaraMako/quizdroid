package edu.uw.ischool.quizdroid

import android.app.*
import android.content.*
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import java.io.File

class QuizApp() : Application() {

    private val tag = "QuizApp"
    private val initializationMessage = "QuizApp has been initiated!"
    lateinit var mainActivity: MainActivity
    var topic = ""
    var current = 0
    var correct = 0
    var total = 0

    val PREFERENCE_NAME = "settings"
    val URI_KEY = "application.dataURI"
    val INTERVAL_KEY = "application.updateInterval"
    val UPDATE = "application.update"

    var defaultPath = ""
    private val defaultFileDirectory = "/data/"
    private val defaultFileName = "questions.json"
    val defaultInterval = 24 * 60
    private var updateInterval = 0

    private lateinit var receiver: IntentListener
    private lateinit var intentFilter: IntentFilter
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var requestCode = 0

    private var alertOn = false
    private val airplaneModeAlertTitle = "Airplane Mode On!"
    private val airplaneModeAlertMessage = "Do you want to turn off airplane mode?"
    private val airplaneModeAlertPositive = "Yes"
    private val airplaneModeAlertNegative = "No"

    val defaultURL = "http://tednewardsandbox.site44.com/questions.json"
    private var url = ""
    private val jsonExtension = ".json"
    private var downloadID: Long = 0
    private val downloadFailureAlertTitle = "Download Failed!"
    private val downloadFailureAlertMessage = "Do you want to retry or quit application " +
            "and try again later"
    private val downloadFailureAlertPositive = "Retry"
    private val downloadFailureAlertNegative = "Quit"

    private val startDownloadingLog = "Start Downloading"
    private val downloadSucceededLog = "Download Succeeded! Restart to update!"
    private val downloadFailedLog = "Download Failed"
    private val noInternetError = "Error: No Internet Connection!"
    private val invalidURL = "ERROR: Invalid URL! Please check the URL and restart!"
    private val invalidDownloadingFile = "ERROR: Downloading file is not a JSON file! " +
            "Please check the URL and restart!"

    private val requestTitle = "title"
    private val requestDescription = "description"
    private val requestDirType = "download"
    private val requestSubPath = defaultFileName

    val DOWNLOAD_COMPLETE = DownloadManager.ACTION_DOWNLOAD_COMPLETE
    private val DOWNLOAD_START = "Start Downloading! URL: %s"
    private val DOWNLOAD_SUCCESSFUL = downloadSucceededLog
    private val DOWNLOAD_FAIL = "ERROR: Download Failed!"

    override fun onCreate() {
        super.onCreate()
        defaultPath = filesDir.parent!! + defaultFileDirectory + defaultFileName
        Log.i(tag, initializationMessage)

        val prefs: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        updateInterval = prefs.getInt(INTERVAL_KEY, defaultInterval)
        url = prefs.getString(URI_KEY, defaultURL).toString()

        receiver = IntentListener()
        intentFilter = IntentFilter()
        intentFilter.addAction(UPDATE)
        intentFilter.addAction(DOWNLOAD_COMPLETE)
        registerReceiver(receiver, intentFilter)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = (updateInterval * 60 * 1000).toLong()
        val time = System.currentTimeMillis() + 1 * 60 * 1000
//        val time = System.currentTimeMillis()
        val intent = Intent(UPDATE)
        pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent,
            PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pendingIntent)
        requestCode += 1
    }

    fun getRepository(): TopicRepository {
        return MemoryTopicRepository(Uri.fromFile(File(defaultPath)))
    }

    fun isConnected(): Boolean {
        return when {
            isAirplaneModeOn() -> {
                airplaneModeAlert()
                false
            }
            hasInternetConnection() -> {
                true
            }
            else -> {
                noInternetConnectionAlert()
                false
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val network = connectivityManager.activeNetworkInfo
        return network?.isConnected ?: false
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.System.getInt(this.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

    private fun noInternetConnectionAlert() {
        Log.e(tag, noInternetError)
        Toast.makeText(mainActivity, noInternetError, Toast.LENGTH_LONG).show()
    }

    private fun alert(title: String, message: String,
              positive: String, positiveAction: DialogInterface.OnClickListener,
              negative: String, negativeAction: DialogInterface.OnClickListener) {
        if (!alertOn) {
            alertOn = true
            val builder: AlertDialog.Builder = AlertDialog.Builder(mainActivity)
            builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, positiveAction)
                .setNegativeButton(negative, negativeAction)
                .create().show()
        }
    }

    private fun airplaneModeAlert() {
        alert(airplaneModeAlertTitle, airplaneModeAlertMessage, airplaneModeAlertPositive,
            { dialog, _ ->
                startActivity(Intent(Settings.ACTION_SETTINGS))
                alertOn = false
                dialog.dismiss() },
            airplaneModeAlertNegative,
            { dialog, _ ->
                alertOn = false
                dialog.dismiss() })
    }

    private fun downloadFailureAlert() {
        alert(downloadFailureAlertTitle, downloadFailureAlertMessage, downloadFailureAlertPositive,
            {dialog, _ ->
                update()
                alertOn = false
                dialog.dismiss()
            },
            downloadFailureAlertNegative,
            {dialog, _ ->
                alertOn = false
                dialog.dismiss()
                mainActivity.finish()
            })
    }

    fun update() {
        if (URLUtil.isValidUrl(url)) {
            if (url.takeLast(5) == jsonExtension) {
                val request = DownloadManager.Request(Uri.parse(url))
                    .setTitle(requestTitle)
                    .setDescription(requestDescription)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationInExternalFilesDir(this, requestDirType, requestSubPath)
                val downloadManager = this.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                Log.i(tag, startDownloadingLog)
                Toast.makeText(mainActivity, String.format(DOWNLOAD_START, url), Toast.LENGTH_LONG).show()
                deleteOriginalFile()
                downloadID = downloadManager.enqueue(request)
            } else{
                Log.e(tag, invalidDownloadingFile)
                Toast.makeText(mainActivity, invalidDownloadingFile, Toast.LENGTH_LONG).show()
            }
        } else {
            Log.e(tag, invalidURL)
            Toast.makeText(mainActivity, invalidURL, Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteOriginalFile() {
        val originalFile = File(this.getExternalFilesDir(requestDirType), requestSubPath)
        if (originalFile.exists()) originalFile.delete()
    }

    fun checkDownload() {
        val query = DownloadManager.Query()
        query.setFilterById(downloadID)
        val downloadManager = this.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            val status = cursor.getInt(index)
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                Log.i(tag, downloadSucceededLog)
                Toast.makeText(mainActivity, DOWNLOAD_SUCCESSFUL, Toast.LENGTH_LONG).show()
                copyFile()
            } else if (status == DownloadManager.STATUS_FAILED) {
                Log.i(tag, downloadFailedLog)
                Toast.makeText(mainActivity, DOWNLOAD_FAIL, Toast.LENGTH_LONG).show()
                downloadFailureAlert()
            }
        }
    }

    private fun copyFile() {
        val downloadedFile = File(this.getExternalFilesDir(requestDirType), requestSubPath)
        val targetFile = File(defaultPath)
        if (downloadedFile.exists()) {
            downloadedFile.copyTo(targetFile, true)
        }
    }

    fun unregisterReceivers() {
        unregisterReceiver(receiver)
    }

    fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
    }
}