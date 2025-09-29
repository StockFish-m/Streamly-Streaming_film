
<!--
<!DOCTYPE html>
<html>
<head>
  <title>Streamly</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
   External styles 
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/5/w3.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

   Custom styles 
  <style>
    .w3-sidebar a {font-family: "Roboto", sans-serif}
    body,h1,h2,h3,h4,h5,h6,.w3-wide {font-family: "Montserrat", sans-serif;}
  </style>
</head>


<body class="w3-content" style="max-width:1400px; padding-top:68px" >

<div class="w3-top">
  <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
    <div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">☰</div>
    <div class="w3-right w3-padding-16">Mail</div>
    <a href="${pageContext.request.contextPath}/main"  style="text-decoration: none;">
        <div class="w3-center w3-padding-16">Streamly</div>
    </a>
  </div>
</div>
    
     Sidebar (hidden by default) 
<nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left" style="display:none;z-index:2;width:40%;min-width:300px" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()"
  class="w3-bar-item w3-button">Close Menu</a>
  <a href="/search" onclick="w3_close()" class="w3-bar-item w3-button">Search</a>
  <a href="/profile" onclick="w3_close()" class="w3-bar-item w3-button">Profile</a>
  <a href="/library" onclick="w3_close()" class="w3-bar-item w3-button">Library</a>
  <a href="/subscription" onclick="w3_close()" class="w3-bar-item w3-button">Subscription</a>
  <a href="/logout" class="w3-bar-item w3-button" onclick="w3_close()">Log out</a>
</nav>

<script>
// Script to open and close sidebar
function w3_open() {
  document.getElementById("mySidebar").style.display = "block";
}
 
function w3_close() {
  document.getElementById("mySidebar").style.display = "none";
}
</script>-->

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Streamly</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Boxicons + Google Fonts -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Roboto', sans-serif; }
        .topbar {
            position: fixed;
            top: 0; left: 0;
            width: 100%;
            height: 60px;
            background-color: #111;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
            z-index: 999;
        }

        .topbar .logo {
            font-size: 24px;
            font-weight: bold;
            color: #4dbf00;
            text-decoration: none;
        }

        .topbar .menu-toggle {
            font-size: 26px;
            cursor: pointer;
        }

        .sidebar {
            position: fixed;
            top: 60px;
            left: 0;
            width: 250px;
            background-color: #1e1e1e;
            height: 100%;
            padding-top: 20px;
            display: none;
            flex-direction: column;
            z-index: 998;
        }

        .sidebar a {
            padding: 15px 25px;
            color: #fff;
            text-decoration: none;
            font-size: 18px;
            border-bottom: 1px solid #333;
        }

        .sidebar a:hover {
            background-color: #333;
            color: #4dbf00;
        }
    </style>
</head>

<body style="padding-top:60px;">

<div class="topbar">
    <div class="menu-toggle" onclick="toggleSidebar()"><i class='bx bx-menu'></i></div>
    <a class="logo" href="${pageContext.request.contextPath}/main">Streamly</a>
    <div></div> <!-- placeholder for alignment -->
</div>

<div class="sidebar" id="sidebar">
    <a href="${pageContext.request.contextPath}/main">🏠 Home</a>
    <a href="${pageContext.request.contextPath}/search">🔍 Search</a>
    <a href="${pageContext.request.contextPath}/profile">👤 Profile</a>
    <a href="${pageContext.request.contextPath}/library">📚 Library</a>
    <a href="${pageContext.request.contextPath}/subscription">💳 Subscription</a>
<!--    <a href="#">⚙️ Setting</a>-->
    <a href="${pageContext.request.contextPath}/logout">🚪 Log out</a>
</div>

<script>
    function toggleSidebar() {
        const sidebar = document.getElementById("sidebar");
        sidebar.style.display = (sidebar.style.display === "flex") ? "none" : "flex";
    }
</script>
