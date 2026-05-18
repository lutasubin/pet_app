# Ads Strategy (planned)

## Trạng thái hiện tại

**Chưa tích hợp** Google Mobile Ads, UMP consent, hay mediation.

## Nguyên tắc khi thêm

1. **Tách layer**: `feature/ads/` hoặc `core/ads/` — `AdsCoordinator`, không gọi SDK trong Composable/ViewModel trực tiếp
2. **Không gián đoạn overlay**: không full-screen ad khi pet đang chạy trừ khi user chủ động (ví dụ mở shop)
3. **Tần suất**: tránh spam; ưu tiên rewarded cho unlock cosmetic (nếu product yêu cầu)
4. **Consent**: UMP trước personalized ads (EU/US theo policy)

## Gợi ý placement (draft)

| Placement | Loại | Ghi chú |
|-----------|------|---------|
| Sau onboarding lần đầu | Interstitial | Tối đa 1 lần / session |
| Unlock slot / pet premium | Rewarded | User chọn xem |
| Shop scroll idle | Native/Banner | Nhẹ, không che CTA |
| Settings | Không ad | Giữ tin cậy |

## Kiến trúc đề xuất

```
UI event → ViewModel → ShowInterstitialUseCase → AdsRepository → AdMobDataSource
```

- ViewModel chỉ biết `Result` / sealed outcome
- Preload interstitial ở background; show khi `canShow()` và cooldown đủ

## Billing (tương lai)

- Remove ads / premium pets: Play Billing Library — skill tại `.cursor/skill_androi/.../play-billing-library-version-upgrade/`
- Không trộn billing logic vào `HomeViewModel` — facade riêng

## Metrics (khi có)

- Firebase Analytics hoặc tương đương — event: `ad_impression`, `ad_failed`, `reward_earned`
- Không log PII

## Checklist trước ship ads

- [ ] `AD_ID` permission / manifest meta
- [ ] Test ads unit debug vs production
- [ ] ProGuard keep rules cho AdMob
- [ ] Không crash khi không có network
