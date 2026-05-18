# Fix Overlay / Pet Engine

Dùng prompt này khi sửa **overlay, drag, physics, animation sprite**.

## Bắt buộc đọc (theo thứ tự)

1. `.cursor/rules/screen-pet-mental-model.mdc`
2. `.cursor/rules/screen-pet-overlay-window.mdc`
3. `.cursor/rules/screen-pet-touch-drag.mdc`
4. `.cursor/rules/screen-pet-game-loop.mdc`
5. `.cursor/rules/screen-pet-sprite-animation.mdc`

## Checklist trước PR

- [ ] Một nguồn tick khi overlay active
- [ ] `PetSimulationUpdateMutex` trên mọi replace
- [ ] Drag: cumulative delta, không pointer absolute
- [ ] Sprite `toDp()` từ pixel logical
- [ ] Unit test engine nếu đổi FSM/physics

## Mô tả

- Bug: 
- Phase/clip liên quan: 
- Single pet hay swarm: 
