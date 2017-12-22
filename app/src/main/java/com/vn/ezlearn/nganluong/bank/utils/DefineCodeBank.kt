package com.vn.ezlearn.nganluong.bank.utils

/**
 * Created by DucChinh on 6/13/2016.
 */
interface DefineCodeBank {
    companion object {
        val TIMEOUT = 30000

        val FUNC = "sendOrder"
        val FUNC_CHECKORDER = "checkOrder"
        val VERSION = "1.0"
        val CURRENCY = "vnd"
        val LANGUAGE = "vi"

        val RETURN_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html"
        val CANCEL_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html"
        val NOTIFY_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html"
    }

}
