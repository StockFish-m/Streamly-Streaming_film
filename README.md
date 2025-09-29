# Streamly-Streaming_film
# Streamly — Java Web Streaming (Netflix-like)

> Java JSP/Servlets • Tomcat 10 • SQL Server • MVC • DAO • JSTL • Filters • Sessions • VNPAY (sandbox)

## 1. Demo nhanh
- 🎥 Video (90s): <link>
- 🖥️ Live demo (optional): <link> (Viewer: viewer@demo.com / 123456; Admin: admin@demo.com / 123456)

## 2. Tính năng chính
- 👤 Auth (login/register), role Viewer/Admin, session + filter guard
- 🎬 Catalog phim, tìm kiếm, lịch sử xem
- 💳 Thanh toán VNPAY (sandbox), HMAC SHA-512, callback `/vnpay-return`
- 📦 Gói subscription, hạn dùng, chặn mua trùng gói
- 🛡️ Validation, thông báo lỗi UI thân thiện

## 3. Kiến trúc & Kỹ thuật
- **MVC** (Servlet Controller • JSP View • DAO/Service)
- **JDBC + SQL Server** (script tạo DB: `/db/schema.sql`, seed: `/db/seed.sql`)
- **Bảo mật**: hash secret, `.env`/`config.properties` (không commit)
- **Build & Run**: NetBeans (Ant) / Maven (tuỳ chọn), Tomcat 10
- **Tests**: JUnit (services/dao), Postman collection: `/docs/api-postman.json`

## 4. Chạy nhanh (Docker)
```bash
# 1) Clone
git clone https://github.com/<you>/streamly && cd streamly

# 2) Khởi chạy bằng Docker Compose (app + mssql)
docker compose up -d --build

# 3) Mở trình duyệt
# http://localhost:8080/Streamly
# Account demo: viewer@demo.com / 123456

