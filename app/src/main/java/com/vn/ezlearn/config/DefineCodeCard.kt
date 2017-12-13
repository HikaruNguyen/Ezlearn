package com.vn.ezlearn.config

/**
 * Created by Windows 10 Version 2 on 12/13/2017.
 */

interface DefineCodeCard {
    companion object {
        val VIETTEL = "VIETTEL"
        val MOBIFONE = "VMS"
        val VINAPHONE = "VNP"

        val FUNC = "Func"
        val VERSION = "2.0"
        val RECEIVER = "tranthuha280385@gmail.com"

        val CODE_00 = "Nạp thẻ thành công"
        val CODE_99 = "Lỗi chưa xác định"
        val CODE_01 = "Lỗi, địa chỉ IP để truy cập API của NganLuong.vn bị từ chối"
        val CODE_02 = "Lỗi, thông số được gửi từ người bán đến NganLuong.vn không chính xác (tên tham số sai hoặc tham số còn thiếu)"

        val CODE_04 = "Lỗi, Mã checksum không chính xác"
        val CODE_05 = "Lỗi, Mã thẻ cào không chính xác hoặc đã được sử dụng"
        val CODE_06 = "Lỗi, Không kết nối tới hệ thống xác thực thẻ của Telco"
        val CODE_07 = "Lỗi, Tài khoản NgânLượng.vn của merchant nhận tiền nạp không tồn tại"
        val CODE_08 = "Lỗi, Tài khoản NgânLượng.vn của merchant nhận tiền nạp đang bị khóa hoặc bị phong tỏa"
        val CODE_09 = "Lỗi, khách hàng tương ứng với tham số ref_code bị khóa"
        val CODE_10 = "Nạp thẻ thành công"
    }
}
