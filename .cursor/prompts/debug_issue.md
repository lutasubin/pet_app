# Debug Issue

Debug có hệ thống cho Screen Pet (Android).

## Bước 1 — Phân loại

- [ ] UI Compose only
- [ ] ViewModel / UseCase / DataStore
- [ ] Pet engine (pure) — reproduce bằng unit test
- [ ] Overlay Service / WindowManager / permission
- [ ] Gesture / drag

## Bước 2 — Đọc context

- `AGENTS.md` + `docs/architecture.md`
- Rule: `.cursor/rules/screen-pet-*.mdc` (chọn đúng file)
- `.cursor/memory/known_bugs.md`

## Bước 3 — Thu thập bằng chứng

- Log tag `PetOverlay` cho window
- Repro steps: device API, overlay on/off, swarm on/off
- Nếu engine: viết test nhỏ trong `app/src/test/.../engine/`

## Bước 4 — Sửa

- Fix gốc, không workaround UI che lỗi
- Một concern mỗi commit
- Cập nhật `known_bugs.md` nếu là gotcha lặp lại

## Mô tả bug (điền)

- Triệu chứng: 
- Expected: 
- Actual: 
- File nghi ngờ: 
