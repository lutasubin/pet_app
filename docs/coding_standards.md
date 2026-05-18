# Coding Standards

## Naming

| Loại | Hậu tố / pattern | Ví dụ |
|------|------------------|--------|
| ViewModel | `ViewModel` | `HomeViewModel` |
| UI state | `UiState` | `HomeUiState` |
| Use case | `UseCase` | `ToggleActivateUseCase` |
| Repository (interface) | `Repository` | `AppSettingsRepository` |
| Implementation | `DataStore*Repository`, `Asset*Repository` | `DataStoreAppSettingsRepository` |
| Route (Compose entry) | `Route` | `SettingsRoute` |
| Hilt module | `Module`, `BindModule`, `ProvideModule` | `PetModule` |
| Engine pure | không suffix Android | `PetEngine`, `PetClipPlayback` |

## Layer rules

```
✅ Composable → gọi callback / ViewModel event
✅ ViewModel → UseCase only
✅ UseCase → Repository
❌ Composable → Repository / DataStore
❌ ViewModel → Context
❌ Navigate trong ViewModel
```

## Kotlin / Compose

- Ưu tiên Composable stateless; state từ `StateFlow` + `collectAsStateWithLifecycle`
- Một màn hình = một `*UiState` data class immutable
- `Result` cho domain/data khi có thể fail
- File ngắn (~100 dòng); tách file khi phình
- Comment logic khó: **tiếng Việt**

## Constants & assets

- Path asset: `core/constants/*AssetPaths.kt`, `ShimejiFrameCatalog`
- Không hardcode `"shime12.png"` trong Composable
- String user-facing: `res/values/strings.xml` (+ locale `values-vi`, `values-en`, ...)

## Pet / overlay (bắt buộc)

- Drag overlay: cumulative `dragAmount` + `rememberUpdatedState` — xem rule touch-drag
- Không `pointerInput(snap)` — restart gesture
- Sprite size: pixel logical → `toDp()` với density
- Input pet: `PetPlayEventSink` → use case → engine

## Testing

- Mọi UseCase mới: unit test trong `app/src/test/`
- Engine behavior: test pure trong `feature/pet/domain/engine/`
- ViewModel: test StateFlow khi logic đáng kể

## Git / secrets

- Không commit `key.properties`, keystore
- Không log debug trong release build
