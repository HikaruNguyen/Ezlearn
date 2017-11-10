package com.vn.ezlearn.fragment


import android.Manifest
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.ExamsAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentCategoryBinding
import com.vn.ezlearn.interfaces.DownloadFileCallBack
import com.vn.ezlearn.interfaces.OnClickDownloadListener
import com.vn.ezlearn.modelresult.ListDocumentResult
import com.vn.ezlearn.modelresult.ListExamsResult
import com.vn.ezlearn.models.ContentByCategory
import com.vn.ezlearn.models.Document
import com.vn.ezlearn.utils.AppUtils
import com.vn.ezlearn.utils.CLog
import com.vn.ezlearn.utils.DownloadFileFromURL
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File

class CategoryFragment : Fragment(), OnClickDownloadListener, DownloadFileCallBack {
    private var categoryBinding: FragmentCategoryBinding? = null
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null

    private var category_id: Int = 0
    private var contentType: Int = 0
    private var mExamsResult: ListExamsResult? = null
    private var mDocumentsResult: ListDocumentResult? = null
    private lateinit var adapter: ExamsAdapter
    private lateinit var list: MutableList<ContentByCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            category_id = arguments.getInt(CATEGORY_ID)
            contentType = arguments.getInt(CONTENT_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        categoryBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_category, container, false)
        bindData()
        return categoryBinding!!.root
    }

    private fun bindData() {
        list = ArrayList()
        adapter = ExamsAdapter(activity, ArrayList())
        adapter.onClickDownloadListener = this
        categoryBinding!!.rvListExam.adapter = adapter
        getDataApi(1, contentType)
    }

    private fun getDataApi(page: Int, contentType: Int) {
        apiService = MyApplication.with(activity).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        when (contentType) {
            ContentByCategory.CONTENT_TYPE_EXAM -> getDataExam(page)
            ContentByCategory.CONTENT_TYPE_DOCUMENT -> getDataDocument(page)
        }

    }

    private fun getDataDocument(page: Int) {
        mSubscription = apiService!!.getListDocument(category_id, page, 50)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListDocumentResult>() {
                    override fun onCompleted() {
                        if (mDocumentsResult!!.success && mDocumentsResult!!.data != null
                                && mDocumentsResult!!.data != null
                                && mDocumentsResult!!.data!!.isNotEmpty()) {
                            for (document: Document in mDocumentsResult!!.data!!) {
                                var filePath = Environment.getExternalStorageDirectory().toString() + "/ezlearn"
                                filePath += "/" + document.name_en.replace(" ", "") + ".doc"
                                val file = File(filePath)
                                if (file.exists()) {
                                    CLog.d(AppUtils.getTAG(CategoryFragment::class.java), filePath + " Download exist")
                                    document.isDownloaded = true
                                } else {
                                    CLog.d(AppUtils.getTAG(CategoryFragment::class.java), filePath + " Download not exist")
                                    document.isDownloaded = false
                                }
                                list.add(ContentByCategory(
                                        exam = null, document = document, contentType = contentType))
                            }
                            adapter.addAll(list)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(documentResult: ListDocumentResult?) {
                        if (documentResult != null) {
                            mDocumentsResult = documentResult
                        }
                    }
                })
    }

    private fun getDataExam(page: Int) {
        mSubscription = apiService!!.getListExams(category_id, page, 50)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() {
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult?.data?.list != null
                                && mExamsResult?.data?.list!!.isNotEmpty()) {
                            mExamsResult?.data?.list!!
                                    .map {
                                        ContentByCategory(
                                                exam = it, document = null, contentType = contentType)
                                    }
                                    .forEach { list.add(it) }

                            adapter.addAll(list)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(examsResult: ListExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })
    }

    override fun onDetach() {
        super.onDetach()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }

    companion object {
        private val CATEGORY_ID = "CATEGORY_ID"
        private val CONTENT_TYPE = "CONTENT_TYPE"
        private val REQUEST_PERMISSION_CODE = 12
        fun newInstance(category_id: Int, contentType: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt(CATEGORY_ID, category_id)
            args.putInt(CONTENT_TYPE, contentType)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var name: String
    lateinit var url: String
    var position: Int = 0
    override fun onClick(name: String, url: String, position: Int) {
        this.name = name
        this.url = url
        this.position = position
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val rc = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            download(name, url, position)
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_CODE)
            } else {
                showPopupPermission()
            }
        }

    }

    private fun showPopupPermission() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.setting))
        builder.setMessage(getString(R.string.messsage_permission_write))
        builder.show()
    }

    private fun download(name: String, url: String, position: Int?) {
        val downloadFileFromURL = DownloadFileFromURL(activity, name, position!!, this)
        downloadFileFromURL.execute(url)
    }


    override fun onDownloadSuccess(position: Int) {
        Toast.makeText(activity, getString(R.string.download_success), Toast.LENGTH_SHORT).show()
        val item = adapter.getItemByPosition(position)
        item.document?.isDownloaded = true
        adapter.setData(position, item)
    }

    override fun onDownloadFail() {
        Toast.makeText(activity, getString(R.string.download_fail), Toast.LENGTH_SHORT).show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_PERMISSION_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            download(name, url, position)
        } else {
            showPopupPermission()
        }
    }

}
