# Known Bugs / Gotchas

> Ghi bug chưa fix hoặc cạm bẫn để AI không lặp lại sai lầm.

## Overlay / gesture

- **Drag absolute pointer** trên overlay → pet nhảy / mất gesture. Phải dùng cumulative `dragAmount` + anchor tại `onDragStart` (rule `screen-pet-touch-drag.mdc`).
- **`pointerInput(snap)`** → restart gesture mỗi tick → drag cancel.

## Sprite / UI

- Dùng `128.dp` thay vì `SPRITE_WIDTH_PX.toDp()` → pet scale sai trên màn 3x.
- Decode bitmap trực tiếp mỗi frame trong Composable → giật; dùng `PetSpriteCache` + prefetch.

## Lifecycle Service

- `ComposeView` trong Service: thứ tự `performRestore` → `ON_CREATE` → set 3 view-tree owners → `setContent` → `addView` (rule overlay-window).
- Sai thứ tự → crash `consumeRestoredStateForKey`.

## Engine

- Đổi default phase sang `GroundIdle` + clip `Stand` → pet đứng im (1 frame).
- Tick song song Activity + Service không mutex → race state.

## Build / config

- `key.properties` thiếu → release không sign (OK local).
- minSdk 36 — emulator/device phải đủ API.

## (Thêm bug mới bên dưới)

- 
