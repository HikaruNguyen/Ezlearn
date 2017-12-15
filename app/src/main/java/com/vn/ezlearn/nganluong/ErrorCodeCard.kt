package com.vn.ezlearn.nganluong

import android.annotation.SuppressLint
import java.util.*

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/12/2017.
 */

class ErrorCodeCard @SuppressLint("UseSparseArrays")
constructor() {
    var errorCode: MutableMap<Int, String> = HashMap()

    init {
        errorCode.put(0, DefineCodeCard.CODE_CARD_00)
        errorCode.put(99, DefineCodeCard.CODE_CARD_99)
        errorCode.put(1, DefineCodeCard.CODE_CARD_01)
        errorCode.put(2, DefineCodeCard.CODE_CARD_02)
        errorCode.put(3, DefineCodeCard.CODE_CARD_03)
        errorCode.put(4, DefineCodeCard.CODE_CARD_04)
        errorCode.put(5, DefineCodeCard.CODE_CARD_05)
        errorCode.put(6, DefineCodeCard.CODE_CARD_06)
        errorCode.put(7, DefineCodeCard.CODE_CARD_07)
        errorCode.put(8, DefineCodeCard.CODE_CARD_08)
        errorCode.put(9, DefineCodeCard.CODE_CARD_09)
        errorCode.put(10, DefineCodeCard.CODE_CARD_10)
        errorCode.put(11, DefineCodeCard.CODE_CARD_11)
        errorCode.put(12, DefineCodeCard.CODE_CARD_12)
        errorCode.put(13, DefineCodeCard.CODE_CARD_13)
        errorCode.put(14, DefineCodeCard.CODE_CARD_14)
        errorCode.put(15, DefineCodeCard.CODE_CARD_15)
        errorCode.put(16, DefineCodeCard.CODE_CARD_16)
        errorCode.put(17, DefineCodeCard.CODE_CARD_17)
        errorCode.put(18, DefineCodeCard.CODE_CARD_17)
        errorCode.put(19, DefineCodeCard.CODE_CARD_17)
        errorCode.put(20, DefineCodeCard.CODE_CARD_17)
    }

    companion object {
        private var errorCodeCard: ErrorCodeCard? = null

        val instance: ErrorCodeCard
            get() {
                if (errorCodeCard == null) {
                    errorCodeCard = ErrorCodeCard()
                }
                return errorCodeCard!!
            }
    }
}
