# Lọc pet bản quyền (data1 / data2)

Trước khi đăng Play Store, đã xóa pet trùng IP nổi tiếng (anime, game, VTuber, phim, v.v.).

## Cách chạy lại

```bash
python3 scripts/filter_copyrighted_pets.py          # xem trước
python3 scripts/filter_copyrighted_pets.py --delete
```

Chỉnh danh sách **GIỮ** trong `scripts/filter_copyrighted_pets.py` → `KEEP_NAMES`.

## Kết quả (lần lọc gần nhất)

- **Giữ:** 68 pet (56 `data1`, 12 `data2`)
- **Đã xóa:** 299 pet

Catalog app đọc tự động từ thư mục còn lại (`AssetShimejiCatalogRepository`).

## Lưu ý

- Ảnh onboarding / `app/assets/images/` chưa lọc — kiểm tra riêng nếu có nhân vật IP.
- Nếu thêm pet mới: chỉ thêm tên **generic / bạn có license**, không thêm nhân vật thương hiệu.
