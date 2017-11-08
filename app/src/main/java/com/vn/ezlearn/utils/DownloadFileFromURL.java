package com.vn.ezlearn.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.vn.ezlearn.interfaces.DownloadFileCallBack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 09/11/2017.
 */

public class DownloadFileFromURL extends AsyncTask<String, String, String> {
    private Context context;
    private String name;
    private int position;
    private ProgressDialog pDialog;
    private DownloadFileCallBack downloadFileCallBack;
    private boolean isSuccess;

    public DownloadFileFromURL(Context context, String name, int position,
                               DownloadFileCallBack downloadFileCallBack) {
        this.context = context;
        this.name = name;
        this.position = position;
        this.downloadFileCallBack = downloadFileCallBack;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        showDialog(progress_bar_type);
        pDialog.show();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            File folder = new File(Environment.getExternalStorageDirectory() + "/ezlearn");
            if (!folder.exists()) {
                folder.mkdir();
            }
            String filePath = folder.getAbsolutePath();
            filePath += "/" + name.replace(" ", "") + ".doc";
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream(filePath);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
            isSuccess = true;
        } catch (Exception e) {
            CLog.INSTANCE.e("Error: ", e.getMessage());
            isSuccess = false;
        }

        return null;
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
//        dismissDialog(progress_bar_type);.
        pDialog.dismiss();
        if (isSuccess) {
            downloadFileCallBack.onDownloadSuccess(position);
        } else {
            downloadFileCallBack.onDownloadFail();
        }
        // Displaying downloaded image into image view
        // Reading image path from sdcard
        Toast.makeText(context, "Download success", Toast.LENGTH_SHORT).show();
    }

}
