package com.vn.ezlearn.nganluong

/**
 * Created by Windows 10 Version 2 on 12/13/2017.
 */

interface DefineCodeCard {
    companion object {
        val VIETTEL = "VIETTEL"
        val MOBIFONE = "VMS"
        val VINAPHONE = "VNP"

        val FUNC = "CardCharge"
        val VERSION = "2.0"
        val RECEIVER = "tranthuha280385@gmail.com"

        val CODE_CARD_00 = "Nạp thẻ thành công"
        val CODE_CARD_99 = "Lỗi chưa được định nghĩa hoặc không rõ nguyên nhân"
        val CODE_CARD_01 = "Địa chỉ IP truy cập API của NgânLượng.vn bị từ chối"
        val CODE_CARD_02 = "Tham số gửi từ merchant tới NgânLượng.vn chưa chính xác"
        val CODE_CARD_03 = "Mã merchant không tồn tại hoặc merchant đang bị khóa kết nối tới NgânLượng.vn"
        val CODE_CARD_04 = "Mã checksum không chính xác"
        val CODE_CARD_05 = "Tài khoản nhận tiền nạp của merchant không tồn tại"
        val CODE_CARD_06 = "Tài khoản nhận tiền nạp của merchant đang bị khóa hoặc bị phong tỏa, không thể thực giao dịch nạp tiền"
        val CODE_CARD_07 = "Thẻ đã được sử dụng"
        val CODE_CARD_08 = "Thẻ bị khóa"
        val CODE_CARD_09 = "Thẻ hết hạn sử dụng"
        val CODE_CARD_10 = "Thẻ chưa được kích hoạt hoặc không tồn tại"
        val CODE_CARD_11 = "Mã thẻ sai định dạng"
        val CODE_CARD_12 = "Sai số serial của thẻ"
        val CODE_CARD_13 = "Thẻ chưa được kích hoạt hoặc không tồn tại"
        val CODE_CARD_14 = "Thẻ không tồn tại"
        val CODE_CARD_15 = "Thẻ không sử dụng được"
        val CODE_CARD_16 = "Số lần thử (nhập sai liên tiếp) của thẻ vượt quá giới hạn cho phép"
        val CODE_CARD_17 = "Hệ thống Telco bị lỗi hoặc quá tải, thẻ chưa bị trừ"
        val CODE_CARD_18 = "Hệ thống Telco bị lỗi hoặc quá tải, thẻ có thể bị trừ, cần phối hợp với NgânLượng.vn để tra soát"
        val CODE_CARD_19 = "Kết nối từ NgânLượng.vn tới hệ thống Telco bị lỗi, thẻ chưa bị trừ"
        val CODE_CARD_20 = "Kết nối tới telco thành công, thẻ bị trừ nhưng chưa cộng tiền trên NgânLượng.vn"
    }
}
