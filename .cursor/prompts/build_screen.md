# Build Screen (Compose + MVVM)

Build một màn Compose production-ready cho **Screen Pet**.

## Đọc trước

- `AGENTS.md`
- `docs/ui_patterns.md`, `docs/coding_standards.md`
- `ui/theme/*Tokens.kt` tương ứng

## Yêu cầu

- Feature-first: `feature/<name>/presentation/`
- `*Route.kt` (Hilt ViewModel) + `*Screen.kt` (stateless UI) + `*UiState.kt`
- Logic → UseCase → Repository; **không** business logic trong Composable
- String/màu qua `strings.xml` / tokens
- File ~≤100 dòng; tách composable nhỏ

## Output mong đợi

1. UiState immutable
2. ViewModel: 1 StateFlow, event handlers gọi use case
3. Preview hoặc test ViewModel nếu có logic
4. Không hardcode asset path

## Tham số (điền khi dùng)

- Feature: 
- Màn hình: 
- Figma/mockup: 
- Use cases cần: 
