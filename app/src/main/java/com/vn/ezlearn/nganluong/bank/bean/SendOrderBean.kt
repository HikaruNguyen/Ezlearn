package com.vn.ezlearn.nganluong.bank.bean

/**
 * Created by DucChinh on 6/13/2016.
 */
class SendOrderBean {
    var func: String? = null
    var version: String? = null
    var merchantID: String? = null
    var merchantAccount: String? = null
    var orderCode: String? = null
    var totalAmount: Int = 0
    var currency: String? = null
    var language: String? = null
    var returnUrl: String? = null
    var cancelUrl: String? = null
    var notifyUrl: String? = null
    var buyerFullName: String? = null
    var buyerEmail: String? = null
    var buyerMobile: String? = null
    var buyerAddress: String? = null
    var checksum: String? = null
}
