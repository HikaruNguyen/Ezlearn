package com.vn.ezlearn.utils

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import com.vn.ezlearn.R
import com.vn.ezlearn.interfaces.DownloadFileCallBack
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * Created by FRAMGIA\nguyen.duc.manh on 09/11/2017.
 */

class DownloadFileFromURL(private val context: Context, private val name: String, private val position: Int,
                          private val downloadFileCallBack: DownloadFileCallBack) : AsyncTask<String, String, String>() {
    private val pDialog: ProgressDialog = ProgressDialog(context)
    private var isSuccess: Boolean = false

    init {
        with(pDialog) {
            setMessage(context.getString(R.string.downloading))
            isIndeterminate = false
            max = 100
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            setCancelable(true)
        }


    }

    override fun onPreExecute() {
        super.onPreExecute()
        pDialog.show()
    }

    /**
     * Downloading file in background thread
     */
    override fun doInBackground(vararg f_url: String): String? {
        var count: Int
        try {
            val url = URL(f_url[0])
            val conection = url.openConnection()
            conection.connect()
            // getting file length
            val lenghtOfFile = conection.contentLength

            val folder = File(Environment.getExternalStorageDirectory().toString() + "/ezlearn")
            if (!folder.exists()) {
                folder.mkdir()
            }
            var filePath = folder.absolutePath
            filePath += "/" + name.replace(" ", "") + ".doc"
            // input stream to read file - with 8k buffer
            val input = BufferedInputStream(url.openStream(), 8192)

            // Output stream to write file
            val output = FileOutputStream(filePath)

            val data = ByteArray(1024)

            var total: Long = 0
            count = input.read(data)
            while (count != -1) {
                total += count.toLong()
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (total * 100 / lenghtOfFile).toInt())

                // writing data to file
                output.write(data, 0, count)
                count = input.read(data)
            }

            // flushing output
            output.flush()

            // closing streams
            output.close()
            input.close()
            isSuccess = true
        } catch (e: Exception) {
            CLog.e("Error: ", e.message!!)
            isSuccess = false
        }

        return null
    }

    /**
     * Updating progress bar
     */
    override fun onProgressUpdate(vararg progress: String) {
        // setting progress percentage
        pDialog.progress = Integer.parseInt(progress[0])
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     */
    override fun onPostExecute(file_url: String?) {
        // dismiss the dialog after the file was downloaded
        //        dismissDialog(progress_bar_type);.
        if (pDialog.isShowing) {
            pDialog.dismiss()
        }

        if (isSuccess) {
            downloadFileCallBack.onDownloadSuccess(position)
        } else {
            downloadFileCallBack.onDownloadFail()
        }
        // Displaying downloaded image into image view
        // Reading image path from sdcard
//        Toast.makeText(context, context.getString(R.string.download_success), Toast.LENGTH_SHORT).show()
    }

}
