<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Study Material Platform</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css">
    <style>
        body { padding: 20px; }
        nav { display: flex; justify-content: space-between; align-items: center; }
        nav ul { margin: 0; }
    </style>
</head>
<body>
    <main class="container">
        <nav>
            <ul>
                <li><strong>Study Platform</strong></li>
            </ul>
            <ul>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/upload">Upload</a></li>
                </c:if>
            </ul>
            <ul>
                <c:if test="${not empty sessionScope.user}">
                    <li>Welcome, ${sessionScope.user.username}</li>
                    <li><a href="${pageContext.request.contextPath}/logout" role="button">Logout</a></li>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/login" role="button" class="outline">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register" role="button">Register</a></li>
                </c:if>
            </ul>
        </nav>
        <hr>