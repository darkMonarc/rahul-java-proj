<%@ include file="header.jsp" %>

<h2>Upload New Material</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <label for="title">Title</label>
    <input type="text" id="title" name="title" required>
    
    <label for="description">Description</label>
    <textarea id="description" name="description"></textarea>
    
    <label for="file">File</label>
    <input type="file" id="file" name="file" required>
    
    <button type="submit">Upload</button>
</form>

<%@ include file="footer.jsp" %>