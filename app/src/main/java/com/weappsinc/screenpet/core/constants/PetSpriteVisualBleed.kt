package com.weappsinc.screenpet.core.constants

/**
 * Bù padding trong suốt trong khung 128x128 Shimeji: cho neo dịch thêm theo hướng biên
 * để hình vẽ thật sự "dính" mép màn hình (vài px bị cắt ngoài biên là chấp nhận được).
 *
 * Cac gia tri duoc tang manh so voi truoc (6/6/6/8 -> 22/22/16/24) vi sprite shime
 * thuong co padding trong suot rong hai ben (~20-26px moi ben) va vai chuc px duoi chan,
 * khien pet trong nhu cach mep man hinh khoang 1 ngon tay. Tang bleed -> than pet
 * dich sat mep that su.
 */
object PetSpriteVisualBleed {
    const val INTO_EDGE_LEFT_PX: Int = 44
    const val INTO_EDGE_RIGHT_PX: Int = 44
    const val INTO_EDGE_TOP_PX: Int = 24
    /**
     * Đáy: số âm = kéo pet LÊN cao hơn mép dưới (để chân không bị che bởi gesture bar / cong đáy).
     * Dương = đẩy pet xuống sát/vượt mép.
     */
    const val INTO_EDGE_BOTTOM_PX: Int = -16
}
