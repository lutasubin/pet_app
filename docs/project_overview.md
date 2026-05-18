# App Purpose

**Screen Pet / Shimeji Live**: cho phép người dùng bật pet sprite (Shimeji) chạy trên màn hình Android như overlay — tương tác kéo thả, đi bộ, rơi, bật tường, nhiều pet (swarm).

# Main Features

| Tính năng | Mô tả |
|-----------|--------|
| Home | Chọn tối đa 6 slot pet, unlock, download asset, Mix ngẫu nhiên |
| Activate | Bật/tắt overlay (cần quyền vẽ trên app khác) |
| Swarm | Nhiều pet cùng lúc trên overlay (`PetArenaRepository`) |
| Shop (UI) | Duyệt catalog, preview, gán pet về Home |
| Settings | Ghost mode, kích thước/tốc độ animation, thời lượng phiên, đa ngôn ngữ |
| Onboarding | Hướng dẫn lần đầu (DataStore flag) |
| Dev menu | Trong tab Setting — test pet/engine trong app (ẩn production sau) |

# Users

Người dùng Android thích wallpaper/pet vui trên màn hình; không cần kiến thức kỹ thuật.

# Goal

- Trải nghiệm mượt, pet “sống” trên màn hình
- Ship Play Store (AAB), kiến trúc bảo trì được
- Monetization (ads/IAP) **sau** khi core overlay ổn định

# Không phải mục tiêu

- Backend phức tạp, social, chat
- Game 3D / Unity embed

# Package & ID

- `com.weappsinc.screenpet`
- Assets pet: `app/assets/` (shimeji frames, flags, svg icons)
