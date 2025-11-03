<%@ include file="header.jsp" %>

<h2>Available Study Materials</h2>

<c:if test="${empty materials}">
    <p>No materials have been uploaded yet.</p>
</c:if>

<c:if test="${not empty materials}">
    <table>
        <thead>
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Uploaded By</th>
                <th>Download</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="material" items="${materials}">
                <tr>
                    <td><c:out value="${material.title}" /></td>
                    <td><c:out value="${material.description}" /></td>
                    <td><c:out value="${material.uploaderUsername}" /></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/download?id=${material.materialId}" role="button" class="outline">
                            Download (<c:out value="${material.fileName}" />)
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<%@ include file="footer.jsp" %>