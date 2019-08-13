<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="add_brand"/>
        <input type="text" name="name" pattern="^[\w\-)( ]{2,45}$" placeholder="<fmt:message key="name"/>" required><br/>
        <input type="text" name="country" pattern="^[\w\-)( ]{2,45}$" placeholder="<fmt:message key="country"/>" required><br/>
        <input type="text" name="info" pattern=".{0,300}" placeholder="<fmt:message key="info"/>"><br/>
        <input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/>
        <button type="submit"><fmt:message key="add"/></button>
    </form>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>