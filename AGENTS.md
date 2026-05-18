# Screen Pet (Shimeji Live)

Android app: pet sprite chạy trên màn hình qua **system overlay** (Shimeji-style). Không phải CRUD — là **mini game engine + overlay window**.

# Stack

- Kotlin, Jetpack Compose, Material 3
- Hilt (DI), KSP
- DataStore Preferences (settings, unlock, onboarding)
- Coroutines + StateFlow
- Coil (ảnh/SVG)
- minSdk 26/targetSdk 36, AGP 9.x
- **Chưa có**: AdMob, Room, Retrofit, Billing

# Architecture

```
UI (Route/Screen/Composable)
  → ViewModel (1 UiState, StateFlow)
    → UseCase
      → Repository (SSOT)
        → DataSource (DataStore / assets)
```

**Feature-first** (`app/src/main/java/com/weappsinc/screenpet/feature/`):

| Feature | Trách nhiệm |
|---------|-------------|
| `splash` | Splash → vào app |
| `onboarding` | Lần đầu cài |
| `home` | Chọn pet, activate overlay, swarm, shop UI |
| `settings` | Ghost mode, size/speed, locale, session |
| `pet` | Engine FSM, physics, overlay Service, arena/swarm |

**Core** (`core/`, `ui/theme/`): constants asset, util, design tokens — không business logic.

**Pet engine** (quan trọng): `PetEngine` + `PetSimulationRepository` / `PetArenaRepository` — pure domain, unit test JUnit. Chi tiết: `docs/architecture.md`, `.cursor/rules/screen-pet-*.mdc`.

# Rules (tóm tắt — chi tiết trong `.cursor/rules/`)

- MVVM + Clean; **không** logic nghiệp vụ trong Composable
- ViewModel **không** dùng `Context`
- Navigation chỉ từ UI (`*Route.kt`), không navigate trong ViewModel
- Asset path → `core/constants/*`, không hardcode trong UI
- Overlay: `PetOverlayService`, quyền `SYSTEM_ALERT_WINDOW`, tick mutex, drag cumulative — **đọc rule overlay + touch-drag trước khi sửa**
- File ≤ ~100 dòng; comment phức tạp bằng tiếng Việt
- Assistant trả lời user bằng tiếng Việt

# UI

- Token: `ui/theme/*Tokens.kt`, string: `res/values*/strings.xml`
- Home: hồng/tím (#EF4094, #8152FF), bo tròn, card mềm
- Sprite overlay: **128px logical** (`SPRITE_WIDTH_PX.toDp()`), không `128.dp` thuần

# Ads / monetization

Chưa tích hợp. Khi thêm: tách module/feature riêng, không spam; xem `docs/ads_strategy.md`.

# Coding style

- Đặt tên tiếng Anh: `HomeViewModel`, `ToggleActivateUseCase`, `SettingsRoute`
- 1 screen = 1 `*UiState` immutable
- UseCase có unit test; engine pure có test trong `app/src/test/`

# Workflow AI (đọc trước khi code)

1. `AGENTS.md` (file này)
2. `docs/architecture.md` + `docs/project_overview.md`
3. `.cursor/memory/current_focus.md`
4. Rule liên quan trong `.cursor/rules/` (pet overlay → đọc cả 5 file `screen-pet-*`)

# Build

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

Signing release: `key.properties` (không commit secret).
