package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 17/11/2017.
 */

class HistoryPayment {
    var id: Int? = null
    var ref_code: String? = null
    var client_fullname: String? = null
    var client_fullname_ascii: String? = null
    var client_email: String? = null
    var client_mobile: String? = null
    var card_amount: String? = null
    var transaction_amount: Long? = null
    var transaction_id: String? = null
    var channel_type: Int? = null
    var channel_name: String? = null
    var user_id: Int?=null
    var created_at: String?=null
    var type_card: String?=null
    var card_serial: String?=null
    var pin_card: String?=null
    var merchant_account: String?=null
    var merchant_id: String?=null
    var error_code: String?=null
    var transaction_status: String?=null
    var tax_amount: Int?=null
    var fee_shipping: Int?=null
    var discount_amount: Int?=null
    var payment_type: String?=null

}
