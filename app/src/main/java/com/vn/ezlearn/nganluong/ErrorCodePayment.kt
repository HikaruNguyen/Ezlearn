package com.vn.ezlearn.nganluong

import android.annotation.SuppressLint
import android.content.Context
import com.vn.ezlearn.R
import java.util.*

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/12/2017.
 */

class ErrorCodePayment @SuppressLint("UseSparseArrays")
constructor(context: Context) {
    var listErrorCodeCard: MutableMap<Int, String> = HashMap()
    var listErrorCodeBank: MutableMap<Int, String> = HashMap()

    init {
        listErrorCodeCard.put(0, context.getString(R.string.card_error_00))
        listErrorCodeCard.put(99, context.getString(R.string.card_error_99))
        listErrorCodeCard.put(1, context.getString(R.string.card_error_01))
        listErrorCodeCard.put(2, context.getString(R.string.card_error_02))
        listErrorCodeCard.put(3, context.getString(R.string.card_error_03))
        listErrorCodeCard.put(4, context.getString(R.string.card_error_04))
        listErrorCodeCard.put(5, context.getString(R.string.card_error_05))
        listErrorCodeCard.put(6, context.getString(R.string.card_error_06))
        listErrorCodeCard.put(7, context.getString(R.string.card_error_07))
        listErrorCodeCard.put(8, context.getString(R.string.card_error_08))
        listErrorCodeCard.put(9, context.getString(R.string.card_error_09))
        listErrorCodeCard.put(10, context.getString(R.string.card_error_10))
        listErrorCodeCard.put(11, context.getString(R.string.card_error_11))
        listErrorCodeCard.put(12, context.getString(R.string.card_error_12))
        listErrorCodeCard.put(13, context.getString(R.string.card_error_13))
        listErrorCodeCard.put(14, context.getString(R.string.card_error_14))
        listErrorCodeCard.put(15, context.getString(R.string.card_error_15))
        listErrorCodeCard.put(16, context.getString(R.string.card_error_16))
        listErrorCodeCard.put(17, context.getString(R.string.card_error_17))
        listErrorCodeCard.put(18, context.getString(R.string.card_error_18))
        listErrorCodeCard.put(19, context.getString(R.string.card_error_19))
        listErrorCodeCard.put(20, context.getString(R.string.card_error_20))


        listErrorCodeBank.put(0, context.getString(R.string.bank_error_00))
        listErrorCodeBank.put(1, context.getString(R.string.bank_error_01))
        listErrorCodeBank.put(2, context.getString(R.string.bank_error_02))
        listErrorCodeBank.put(4, context.getString(R.string.bank_error_04))
        listErrorCodeBank.put(5, context.getString(R.string.bank_error_05))
        listErrorCodeBank.put(6, context.getString(R.string.bank_error_06))
        listErrorCodeBank.put(7, context.getString(R.string.bank_error_07))
        listErrorCodeBank.put(9, context.getString(R.string.bank_error_09))
        listErrorCodeBank.put(11, context.getString(R.string.bank_error_11))
        listErrorCodeBank.put(20, context.getString(R.string.bank_error_20))
        listErrorCodeBank.put(21, context.getString(R.string.bank_error_21))
        listErrorCodeBank.put(22, context.getString(R.string.bank_error_22))
        listErrorCodeBank.put(23, context.getString(R.string.bank_error_23))
        listErrorCodeBank.put(24, context.getString(R.string.bank_error_24))
        listErrorCodeBank.put(25, context.getString(R.string.bank_error_25))
        listErrorCodeBank.put(26, context.getString(R.string.bank_error_26))
        listErrorCodeBank.put(27, context.getString(R.string.bank_error_27))
        listErrorCodeBank.put(28, context.getString(R.string.bank_error_28))
        listErrorCodeBank.put(29, context.getString(R.string.bank_error_29))
        listErrorCodeBank.put(30, context.getString(R.string.bank_error_30))
        listErrorCodeBank.put(31, context.getString(R.string.bank_error_31))
        listErrorCodeBank.put(32, context.getString(R.string.bank_error_32))
        listErrorCodeBank.put(33, context.getString(R.string.bank_error_33))
    }

    companion object {
        private var errorCodePayment: ErrorCodePayment? = null

        fun getInstance(context: Context): ErrorCodePayment {
            if (errorCodePayment == null) {
                errorCodePayment = ErrorCodePayment(context = context)
            }
            return errorCodePayment!!
        }

    }
}
