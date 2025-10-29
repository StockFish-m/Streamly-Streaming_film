# 🎬 Streamly — Movie Streaming Web Application

**Streamly** là website xem phim trực tuyến được phát triển bằng **Java Web (JSP/Servlet, Tomcat)** theo mô hình **MVC**.  
Người dùng có thể đăng ký tài khoản, xem phim, mua gói subscription qua **VNPAY**, lưu lịch sử xem và quản lý hồ sơ cá nhân.


## Video demo:

https://youtu.be/7XKL2nH0eWI

## 🚀 Tính năng chính

### 👤 Người xem (Viewer)
- Đăng ký, đăng nhập, đăng xuất tài khoản.  
- Quên mật khẩu qua email OTP / link reset.  
- Xem danh sách phim theo thể loại, tìm kiếm, xem chi tiết phim.  
- Lưu lịch sử xem và tiếp tục phim đang xem dở.  
- Đánh giá (review), xếp hạng phim.  
- Mua và gia hạn gói **Subscription (qua VNPAY)**.  
- Cập nhật hồ sơ cá nhân (avatar, thông tin,...).

### 🧑‍💼 Quản trị viên (Admin)
- Quản lý người dùng, phim, thể loại, nội dung.  
- Thêm / sửa / xóa phim.  
- Quản lý gói Subscription, doanh thu và lịch sử thanh toán.  
- Xem thống kê lượt xem, giao dịch, doanh thu.



## ⚙️ Công nghệ & Kiến trúc

| Thành phần | Công nghệ |
|-------------|------------|
| **Ngôn ngữ** | Java (JDK 17+) |
| **Framework / Server** | Jakarta EE (JSP / Servlet), Apache Tomcat 10 |
| **Frontend** | JSP, HTML, CSS, Bootstrap 5, JSTL |
| **Database** | Microsoft SQL Server |
| **DAO Pattern** | JDBC DAO Implementation |
| **Thanh toán** | Tích hợp VNPAY Sandbox |
| **Gửi email OTP** | Jakarta Mail API (Angus Mail) |
| **IDE** | NetBeans |
| **Kiến trúc** | MVC (Model - View - Controller) |
| **Testing** | JUnit 5 |



## 🧩 Cấu trúc thư mục

```

Streamly/
├── src/java/
│ ├── model/ # Các class entity (Content, User, Subscription,...)
│ ├── dao/ # DAO interfaces & implementations
│ ├── service/ # Business logic (ViewerService, PaymentService,...)
│ ├── servlet/ # Controller (LoginServlet, SearchServlet,...)
│ └── com/vnpay/common # Config & HMAC utils cho VNPAY
├── web/
│ ├── WEB-INF/ # web.xml
│ ├── includes/ # header.jsp, footer.jsp,...
│ ├── manager/ # Trang admin
│ └── ... # Giao diện người xem
├── nbproject/ # File cấu hình NetBeans
├── build.xml # Ant build script
├── .gitignore
└── README.md


```

## 💳 Tích hợp thanh toán VNPAY

- Hỗ trợ môi trường **Sandbox**.  
- Hash chữ ký bằng **HmacSHA512**.  
- Callback URL: `/vnpay-return`.  
- Lưu giao dịch vào bảng `PaymentTransaction`.  
- Tự động cập nhật thời hạn gói Subscription theo `SubscriptionPlan.duration`.



## 🧠 Luồng xử lý MVC

1. Người dùng gửi request (ví dụ `/search`, `/pay`).  
2. Servlet xử lý request → gọi Service → DAO → Database.  
3. Service trả model → Servlet forward sang JSP để hiển thị.  
4. JSP render kết quả ra giao diện.


