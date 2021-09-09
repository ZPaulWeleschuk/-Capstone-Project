<%-- 
    Document   : login
    Created on : 20-Feb-2021, 12:21:47 PM
    Author     : Rylan Cook, Jaehan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
        <link rel="manifest" href="/site.webmanifest">
        <link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
        <meta name="msapplication-TileColor" content="#603cba">
        <meta name="theme-color" content="#ffffff">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/CSS/commonStyles.css" type="text/css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/CSS/login.css" type="text/css" rel="stylesheet" />
        <title>Login</title>
    </head>
    <body>
        <div class="header">
            <a href="/"><img src="./Images/WOM_fullLogo.png"></a>
        </div>
        <div class="container">
            <h1>WorkOrders and Jobs</h1>
            <form action="login" method="post">
                <input type="text" name="userId" value="${userId}" placeholder="User ID"><br>
                <input type="password" name="password" placeholder="Password" value=""><br>
                <input class="submitBtn" type="submit" value="Log On">
            </form>
            <p class="message">${message}</p>
        </div>
    </body>
</html>
