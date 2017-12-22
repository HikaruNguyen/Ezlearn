package com.vn.ezlearn.nganluong.bank.modelResult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 25/12/2017.
 */

class CheckOrderResult {
    var response_code: String? = null
    var receiver_email: String? = null
    var order_code: String? = null
    var total_amount: Int? = null
    var currency: String? = null
    var language: String? = null
    var return_url: String? = null
    var cancel_url: String? = null
    var notify_url: String? = null
    var buyer_fullname: String? = null
    var buyer_email: String? = null
    var buyer_mobile: String? = null
    var buyer_address: String? = null
    var transaction_id: Int? = null
    var transaction_status: Int? = null
    var transaction_amount: Int? = null
    var transaction_currency: String? = null
    var transaction_escrow: Int? = null
}
