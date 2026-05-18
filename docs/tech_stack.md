# Tech Stack

## Platform

- **Language**: Kotlin 2.2.x
- **UI**: Jetpack Compose (BOM 2026.02), Material 3
- **Min/Target SDK**: 36
- **Build**: AGP 9.2, Gradle Kotlin DSL, KSP

## Libraries (production)

| Library | Dùng cho |
|---------|----------|
| Hilt | DI, `@HiltViewModel` |
| Lifecycle + `collectAsStateWithLifecycle` | UI state |
| DataStore Preferences | Settings, home, onboarding |
| Coroutines | Flow, game tick, IO prefetch |
| Coil + coil-svg | Ảnh onboarding, SVG settings icons |

## Không có (hiện tại)

- Navigation Compose (NavHost) — tab state thủ công
- Room / SQLite
- Retrofit / OkHttp
- Firebase
- Google Mobile Ads / UMP
- Play Billing

## Tooling

- Unit test: JUnit 4, `kotlinx-coroutines-test`
- Android test: Espresso, Compose UI test (ít)
- Cursor: `.cursor/rules/*.mdc`, `.cursor/skill_androi/` (Android skills tham khảo)

## Release

- `key.properties` + `signingConfigs.release` (local, gitignore)
- `versionCode` / `versionName` trong `app/build.gradle.kts`
- ProGuard: `isMinifyEnabled = false` (hiện tại)
