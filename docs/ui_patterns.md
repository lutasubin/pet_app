# UI Patterns

## Design language

- **Tone**: vui, hồng/tím pastel, bo tròn, card trắng trên nền `#F7F2FA`
- **Font**: Nunito (`res/font/`)
- **Icons**: SVG trong `app/assets/svg/` — load qua Coil SVG hoặc `SvgAssetIcon`

## Tokens (bắt buộc)

| Màn | File token |
|-----|------------|
| Home, bottom nav | `ui/theme/HomeTokens.kt` |
| Splash | `ui/theme/SplashTokens.kt` |
| Onboarding | `OnboardingTokens.kt` (trong feature) |
| Settings | tokens trong block composable / theme chung |

Không hardcode màu hex trong screen trừ prototype tạm (phải đưa về token trước merge).

## Spacing & shape

- Padding card nội dung: ~16dp
- Bottom nav: `HomeBottomNav` — active `#EE4096`, inactive `#A6A6A6`
- Switch: track off `#E1DFEE`, on `#EF4094`
- Card activate: gradient `#EF4094` → `#8152FF`
- Slot pet: vòng tròn trống `#FFF0F7`, viền đứt `#FFB3D9`

## Components pattern

- Tách block nhỏ: `HomeSwarmCard`, `SettingsGeneralBlock`, `HomeShimejiSlot`
- `*Route` bọc ViewModel + collect state; `*Screen` nhận state + callbacks (testable hơn)
- Sheet picker: `SettingsPickerSheets.kt`

## Compose rules

- Stateless khi có thể; `rememberSaveable` cho tab (`MainScaffold`)
- Loading locale: `LocaleTransitionOverlay` + `AppLocaleTransitionGate` (tránh flash splash)
- Prefetch sprite: `PetSpritePrefetcher()` ở root play field / overlay content

## Overlay UI (pet)

- Window = kích thước sprite 128×128 logical
- `PetShimeSprite`: `flipHorizontal = lookRight` (asset gốc nhìn trái)
- Không placeholder xám khi đổi frame — giữ bitmap cũ đến khi load xong

## Accessibility / i18n

- String qua `stringResource(R.string.*)`
- Locale tags: `system`, `en`, `vi`, ... — đổi locale recreate Activity có gate

## Anti-patterns

- Giant `build()` một file
- Logic “tap thì happy” trong Composable (phải qua engine/VM)
- `128.dp` cho sprite engine (sai density)
