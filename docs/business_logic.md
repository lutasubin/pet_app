# Business Logic

## Activate overlay

1. User bật switch Activate trên Home
2. `ToggleActivateUseCase` persist + `HomeViewModel` gọi `PetOverlayController`
3. Nếu chưa có `SYSTEM_ALERT_WINDOW` → UI xin quyền (`HomeActivatePermissionHandler`)
4. Service start → cập nhật play area → attach window(s) → tick arena/simulation

Tắt activate → stop service, giải phóng overlay.

## Chọn pet (slot)

- Tối đa **6 slot** trên Home
- Slot có thể: trống, đã gán pet, hoặc **locked** (unlock bằng flow riêng `UnlockNextSlotUseCase`)
- Chọn pet từ catalog đã unlock + đã download asset
- Shop: `AssignShimejiFromShopUseCase` → pending id → Home tab consume

## Swarm

- Bật swarm → nhiều `PetId` trên overlay (`PetArenaOverlayHostManager.sync`)
- Một tick / chu kỳ cho cả arena (`TickArenaUseCase`)
- Mỗi pet: window ComposeView riêng, sync vị trí theo anchor engine

## Mix (random)

- `ApplyRandomShimejiMixUseCase`: gán ngẫu nhiên pet đã unlock vào các slot trống/đang dùng (theo rule trong use case)

## Settings ảnh hưởng runtime

| Setting | Hiệu ứng |
|---------|----------|
| Ghost mode | Touch pass-through / alpha (overlay flags) |
| Animation size scale | Scale sprite overlay |
| Speed multiplier | Tick / playback speed |
| Session duration | Tự tắt overlay sau N phút (nếu đã wire) |
| Locale | Recreate activity; gate tránh splash lại |

## Onboarding

- `ObserveOnboardingSeenUseCase` → `AppEntryViewModel.onboardingSeen`
- `null` = chưa load; `false` = hiện onboarding; `true` = vào main
- `MarkOnboardingSeenUseCase` khi hoàn thành

## Pet simulation (engine)

Không duplicate rule file — tóm tắt:

- Mặc định spawn: `GroundMoving` + clip `Walk` (đứng im nếu `GroundIdle` + `Stand` 1 frame)
- Drag → `Dragged`; thả → `Airborne` + gravity; chạm đất → `Bouncing` → `GroundMoving`
- `PreviewHold`: xem clip catalog, không physics đầy đủ

## Dev menu

- Tab Setting → mở dev → `MainPetTestContent`: test engine trong Activity (không overlay)
- Ẩn hoặc flag build type trước production release

## Data integrity

- Mọi ghi settings/home qua repository + DataStore
- Engine update luôn qua mutex + `replace()` trên repository
