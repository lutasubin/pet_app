# Refactor Feature

Refactor an toàn một feature trong Screen Pet.

## Đọc trước

- `docs/architecture.md`
- `docs/coding_standards.md`
- `.cursor/memory/known_bugs.md`

## Nguyên tắc

- Giữ hành vi người dùng; đổi cấu trúc, không đổi product silently
- Không gộp layer (presentation không gọi DataStore)
- Pet/overlay: chạy unit test engine/use case sau refactor
- Diff nhỏ, từng bước; không "cleanup" ngoài phạm vi

## Checklist

- [ ] Repository interface không đổi contract public (hoặc migrate có chủ đích)
- [ ] Hilt module cập nhật binding
- [ ] `./gradlew :app:testDebugUnitTest` pass
- [ ] `./gradlew :app:assembleDebug` pass

## Tham số

- Feature: 
- Mục tiêu refactor: 
- Không được phá: 
