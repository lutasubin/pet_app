# Current Focus

> Cập nhật file này khi đổi sprint / task chính. AI đọc trước khi implement.

## Đang làm

- Settings UI: feature flags, khối animation/general (`SettingsFeatureFlags`, các `Settings*Block`)
- Chuẩn hóa docs AI-first (`AGENTS.md`, `docs/`, `.cursor/memory/`)

## Tiếp theo (gợi ý)

- Hoàn thiện Settings (session timer wire end-to-end nếu chưa)
- Shop: download Shimeji thật, preview overlay
- Ẩn dev menu production (`BuildConfig` hoặc flag)
- Ads/IAP (xem `docs/ads_strategy.md`) — sau khi overlay ổn

## Không đụng trừ khi được yêu cầu

- Refactor lớn Navigation Compose (hiện tab state đủ dùng)
- Đổi `ShimejiFrameCatalog` timing (ảnh hưởng cảm giác Shimeji gốc)
- Hạ minSdk

## Workflow

1. Đọc `AGENTS.md` + `docs/architecture.md` + file này
2. Sửa pet/overlay → đọc `.cursor/rules/screen-pet-*.mdc`
3. Chạy `./gradlew :app:testDebugUnitTest` nếu đụng engine/use case
