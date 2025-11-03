<%@ include file="header.jsp" %>

<h2>Register</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required>
    
    <label for="email">Email</label>
    <input type="email" id="email" name="email" required>
    
    <label for="password">Password</label>
    <input type="password" id="password" name="password" required>
    
    <button type="submit">Register</button>
</form>

<%@ include file="footer.jsp" %>