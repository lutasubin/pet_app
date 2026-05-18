# Architecture Overview

## Mental model

App = **game loop nhỏ** + **Android overlay window**, không phải form CRUD.

```
PetOverlayService (FGS, WindowManager TYPE_APPLICATION_OVERLAY)
  → PetOverlayWindowHost / PetArenaOverlayHostManager
    → Compose: PetShimeSprite + pointer drag
      → PetViewModel / PetArenaOverlayPlaySink
        → TickArenaUseCase / DispatchPetInputUseCase
          → PetEngine (FSM + physics)
            → PetSimulationRepository / PetArenaRepository (StateFlow SSOT)
```

Activity (`MainActivity`) và Service **cùng** repository Hilt `@Singleton`. Chỉ **một** nguồn tick tại một thời điểm (`PetOverlaySession.active`).

## Thư mục

```
app/src/main/java/com/weappsinc/screenpet/
├── MainActivity.kt, AppEntryContent.kt, AppEntryViewModel.kt
├── core/constants/     # Asset paths, physics, clip catalog
├── core/util/
├── ui/theme/           # Color, Type, *Tokens.kt
└── feature/
    ├── splash/         presentation
    ├── onboarding/     presentation, domain, data, di
    ├── home/           presentation, domain, data, di
    ├── settings/       presentation, domain, data, di
    └── pet/            presentation, domain (engine!), data, di
```

Mỗi feature (trừ splash mỏng):

- `presentation/` — `*Route`, `*Screen`, `*ViewModel`, `*UiState`
- `domain/` — `model`, `repository` (interface), `usecase`
- `data/` — `DataStore*Repository`, asset loaders
- `di/` — Hilt modules

## Luồng app (navigation)

Không dùng Navigation Compose graph lớn — state local:

1. `AppEntryContent`: Splash → (onboarding nếu chưa seen) → `MainScaffold`
2. `MainScaffold`: bottom tab `Home | Shop | Setting`
3. Setting có nhánh dev: `devMenuContent` → `MainPetTestContent`

`*Route.kt` = entry Composable của feature; nhận callback navigate, không gọi ViewModel navigate.

## Pet domain (điểm then chốt)

| Thành phần | Vai trò |
|------------|---------|
| `PetEngine` | Pure: FSM `PetRuntimePhase`, physics, clip |
| `ShimejiFrameCatalog` | SSOT frame/clip |
| `PetClipPlayback` | Advance frame theo tick |
| `PetSimulationRepository` | `world: StateFlow` — 1 pet legacy/demo |
| `PetArenaRepository` | `arena: StateFlow` — swarm multi-pet |
| `PetSimulationUpdateMutex` | Tránh race Activity vs Service |
| `PetOverlayService` | Tick khi overlay active, attach window |

**FSM phases** (không thêm tùy tiện): `GroundIdle`, `GroundMoving`, `Airborne`, `Bouncing`, `Dragged`, `WallLeft`, `WallRight`, `Ceiling`, `PreviewHold`.

## Data

| Dữ liệu | Lưu trữ |
|---------|----------|
| Onboarding seen | DataStore (`onboarding`) |
| Home slots, unlock, activate, swarm | DataStore (`home`) |
| App settings | DataStore (`settings`) |
| Shimeji catalog | Assets + `AssetShimejiCatalogRepository` |

Room: **chưa dùng**.

## Dependency (gradle)

Xem `docs/tech_stack.md`. DI: Hilt `@HiltViewModel`, `@Binds` / `@Provides` trong `feature/*/di/`.

## Sửa code an toàn

| Muốn sửa | Đọc trước |
|----------|-----------|
| Kéo pet overlay | `.cursor/rules/screen-pet-touch-drag.mdc` |
| Service/window | `screen-pet-overlay-window.mdc` |
| Animation frame | `screen-pet-sprite-animation.mdc` |
| Tick/physics/FSM | `screen-pet-game-loop.mdc` |
| MVVM chung | `screen-pet-compose-mvvm-senior.mdc` |

## Tests

- `app/src/test/.../engine/` — PetEngine pure
- `app/src/test/.../usecase/` — Use cases
- Chạy: `./gradlew :app:testDebugUnitTest`
