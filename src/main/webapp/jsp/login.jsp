<%@ include file="header.jsp" %>

<h2>Login</h2>

<c:if test="${not empty param.success}">
    <p style="color:green;">Registration successful! Please log in.</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required>
    
    <label for="password">Password</label>
    <input type="password" id="password" name="password" required>
    
    <button type="submit">Login</button>
</form>

<%@ include file="footer.jsp" %>