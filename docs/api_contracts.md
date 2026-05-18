# API Contracts

## Tổng quan

Ứng dụng **offline-first**, không có REST API backend hiện tại. “Contract” ở đây = **repository interfaces** và **domain models** — SSOT cho AI khi mở rộng.

## Repository contracts

### `AppSettingsRepository`

- `observe(): Flow<AppSettings>`
- `setGhostMode`, `setAnimationSizeScale`, `setAnimationSpeedMultiplier`, `setSessionDurationMinutes`, `setLocaleTag`
- Model: `AppSettings` — ghost, scale 0.75–1.5, speed 0.5–1.5, session minutes, `localeTag`

### `HomeSettingsRepository` / `ShimejiUnlockRepository`

- Trạng thái activate overlay, swarm, slot assignments, unlocked/downloaded ids
- Consume qua `ObserveHomeStateUseCase`, mutate qua `ToggleActivateUseCase`, `SelectShimejiAtSlotUseCase`, ...

### `ShimejiCatalogRepository`

- `loadCatalog(): Result<List<ShimejiCatalogEntry>>` (hoặc tương đương)
- Nguồn: assets + metadata trong app

### `PetSimulationRepository`

```kotlin
val world: StateFlow<PetWorldState>
suspend fun replace(newWorld: PetWorldState)
```

### `PetArenaRepository`

```kotlin
val arena: StateFlow<PetArenaState>
suspend fun replace(newArena: PetArenaState)
```

## Input / engine contract

Pet nhận input qua domain, không qua UI trực tiếp:

- `PetInput.Tick(dtMs)`
- `PetInput.PointerDown/Move/Up(x, y)` — tọa độ **pixel logical engine**

Use cases:

- `TickPetUseCase` / `TickArenaUseCase`
- `DispatchPetInputUseCase` / `DispatchArenaInputUseCase`
- `UpdateArenaPlayAreaUseCase(PetPlayArea)` — bounds màn hình

## Overlay ↔ app contract

- `PetOverlayController` (home): start/stop service theo settings + unlocked pets
- Intent/action constants: `core/constants/PetOverlayContract.kt`
- Permission: `Settings.canDrawOverlays` — UI phải dẫn user tới settings nếu thiếu

## Tương lai (khi có backend)

Khi thêm API:

1. Tạo `feature/<name>/data/remote/` + DTO
2. Map DTO → domain model trong repository impl
3. Document endpoint tại đây (method, path, auth, error codes)
4. Không expose DTO lên UI/ViewModel

## Tương lai (Ads / Billing)

Xem `docs/ads_strategy.md` — facade interface trước, impl sau.
