<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/header.jsp"/>
<body>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="add_part"/>
    <input type="text" name="catalogNo" pattern="^[\w\-)( ]{2,45}$" placeholder="catalogNo"><br/>
    <input type="text" name="originalCatalogNo" pattern="^[\w\-)( ]{2,45}$" placeholder="originalCatalogNo"><br/>
    <input type="text" name="info" pattern="^.{0,300}$" placeholder="info"><br/>
    <input type="text" name="price" pattern="^[\d]{1,19}(,[\d]{1,4})?$" placeholder="price"><br/>
    <input type="text" name="pictureUrl" pattern="" placeholder="pictureUrl"><br/>
    <input type="text" name="wait" pattern="^\d{1,3}$" placeholder="wait"><br/>
    <select name="brandId">
        <c:forEach items="${brands}" var="brand">
            <option value="${brand.brandId}">${brand.name}</option>
        </c:forEach>
    </select><br/>
    <input type="text" name="stockCount" pattern="^\d{1,3}$" placeholder="stockCount"><br/>
    <input type="checkbox" name="active" value="is active"> is active?<br/>
    <button type="submit">add</button>
</form>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>
