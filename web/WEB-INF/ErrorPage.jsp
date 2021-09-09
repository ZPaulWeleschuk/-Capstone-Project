<%-- 
    Document   : ErrorPage
    Created on : Jul. 3, 2021, 4:00:15 p.m.
    Author     : Rylan Cook
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
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
        <link href="${pageContext.request.contextPath}/CSS/error.css" type="text/css" rel="stylesheet" />
        <title>Error</title>
    </head>
    <body>
        <div class="container">
    
            <h1 class="message">
                <!-- Remove this before end, this is so that we can still process the error -->
                <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

                <jsp:scriptlet>
            if (exception != null) {
                exception.printStackTrace(new java.io.PrintWriter(out));
            } else {
                out.print("404 Error, page doesn't exsist");
            }
                </jsp:scriptlet>
            </h1>
            <a style="text-decoration: none" class="submitBtn" href="/">Go back to the main page</a>
        </div>
    </body>
</html>
