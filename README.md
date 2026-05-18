# Screen Pet (Shimeji Live)

Android app — pet sprite Shimeji chạy trên màn hình qua system overlay. Kotlin, Jetpack Compose, Hilt, Clean MVVM.

## Quick start

```bash
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

Yêu cầu: **API 36+** (minSdk 36). Cấp quyền *Hiển thị trên các ứng dụng khác* để bật overlay từ Home.

## Cấu trúc

```
app/src/main/java/com/weappsinc/screenpet/
├── core/           # constants, util
├── ui/theme/       # design tokens
└── feature/        # splash, onboarding, home, settings, pet
```

## AI / Cursor

| File | Mục đích |
|------|----------|
| [AGENTS.md](./AGENTS.md) | System prompt dự án — đọc đầu tiên |
| [docs/](./docs/) | Architecture, business logic, UI, ads plan |
| [.cursor/rules/](./.cursor/rules/) | Rules ngắn theo domain (pet, overlay, MVVM) |
| [.cursor/memory/](./.cursor/memory/) | Focus hiện tại, bugs, feature status |
| [.cursor/prompts/](./.cursor/prompts/) | Template prompt tái sử dụng |

Workflow đề xuất: `AGENTS.md` → `docs/architecture.md` → `.cursor/memory/current_focus.md` → code.

## Features (tóm tắt)

- Chọn pet, unlock slot, bật overlay, swarm nhiều pet
- Shop UI, settings (ghost, size, speed, locale)
- Pet engine: FSM + physics + sprite catalog Shimeji

## Tests

```bash
./gradlew :app:testDebugUnitTest
```

## Release

Tạo `key.properties` (không commit):

```properties
storeFile=...
storePassword=...
keyAlias=...
keyPassword=...
```

```bash
./gradlew :app:bundleRelease
```

## License

Proprietary — WeApps Inc.
