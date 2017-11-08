package com.vn.ezlearn.interfaces

/**
 * Created by FRAMGIA\nguyen.duc.manh on 09/11/2017.
 */

interface DownloadFileCallBack {
    fun onDownloadSuccess(position: Int)

    fun onDownloadFail()
}
