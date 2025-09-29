
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Poppins", sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background:url('https://pixeldrain.com/api/file/9PGEhuki');


            background-size: 97% auto;
            background-attachment: fixed;
        }

        .wrapper {
            width: 430px;
            background: rgba(212, 208, 212, 0.5);
            color: #fff;
            border-radius: 10px;
            padding: 30px 40px;
            backdrop-filter: blur(10px);
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.4);
        }

        .wrapper h2 {
            text-align: center;
        }

        .wrapper .input-box {
            width: 100%;
            height: 50px;
            position: relative;
            margin: 30px 0;
        }

        .input-box input {
            width: 100%;
            height: 100%;
            border: none;
            background: transparent;
            outline: none;
            border: 2px solid rgba(255, 255, 255, 0.8);
            border-radius: 40px;
            font-size: 16px;
            color: #fff;
            padding: 20px 45px 20px 20px;
        }

        .input-box input::placeholder {
            color: #fff;
        }

        .input-box i {
            position: absolute;
            right: 20px;
            top: 50%;
            transform: translateY(-50%);
            font-size: 20px;
        }

        .wrapper .options {
            display: flex;
            justify-content: space-between;
            font-size: 14.5px;
            margin: -15px 0 15px;
        }

        .options label input {
            accent-color: #fff;
            margin-right: 3px;
        }

        .options a {
            color: #fff;
            text-decoration: none;
        }

        .options a:hover {
            text-decoration: underline;
        }

        .wrapper .login-btn {
            width: 100%;
            height: 45px;
            background: #fff;
            border: none;
            outline: none;
            border-radius: 40px;
            box-shadow: 0 0 10px rgba(0, 0, 0, .1);
            cursor: pointer;
            font-size: 16px;
            color: #333;
            font-weight: 600;
        }

        .wrapper .register-text {
            font-size: 14.5px;
            text-align: center;
            margin: 20px 0 15px;
        }

        .register-text a {
            color: #fff;
            text-decoration: none;
            font-weight: 600;
        }

        .register-text a:hover {
            text-decoration: underline;
        }
    </style>

    <!-- Boxicons CDN -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper">
        <h2>Login</h2>

        <form action="userlogin" method="post">
            <div class="input-box">
                <input type="text" name="username" placeholder="Username" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="password" name="password" placeholder="Password" required>
                <i class='bx bx-lock-alt'></i>
            </div>
            <div class="options">
                <label><input type="checkbox" name="remember"> Remember me</label>
                <a href="request_password">Forgot password?</a>
            </div>

            <input type="submit" value="Login" class="login-btn">
        </form>

        <p class="register-text">
            Don’t have an account? <a href="signup.jsp">Register</a>
        </p>
    </div>
</body>
</html>
