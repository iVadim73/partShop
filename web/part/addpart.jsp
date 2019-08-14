<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>

<ftm:setBundle basename="text"/>
<%--<div style="height: 76%; background-color: #b9bbbe">--%>
<div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
    <div class="container col-4 rounded" style="background-color: #3B3B3B">
        <form method="post" action="/controller">
            <br/>
            <input type="hidden" name="command" value="add_part" />
            <div class="form-group">
                <input class="form-control" style="background-color: #BABABA" type="text" name="catalogNo" pattern="^([\w\-)( ]{2,45})$" placeholder="<fmt:message key="catalogNo"/>" value="${catalogNo}" required>
                <small class="form-text text-muted"><fmt:message key="catalogNo"/></small>
            </div>
            <div class="form-group">
                <input class="form-control" style="background-color: #BABABA" type="text" name="originalCatalogNo" pattern="^([\w\-)( ]{2,45}$)" placeholder="<fmt:message key="originalCatalogNo"/>" value="${originalCatalogNo}" required>
                <small class="form-text text-muted"><fmt:message key="originalCatalogNo"/></small>
            </div>
            <div class="form-group">
                <input class="form-control" style="background-color: #BABABA" type="text" name="info" pattern="^.{0,300}$" placeholder="<fmt:message key="info"/>" value="${info}" required>
                <small class="form-text text-muted"><fmt:message key="info"/></small>
            </div>
            <div class="form-group">
                <input class="form-control" style="background-color: #BABABA" type="text" name="wait" pattern="^(\d{1,3})$" placeholder="<fmt:message key="wait"/>" value="${wait}">
                <small class="form-text text-muted"><fmt:message key="wait"/></small>
            </div>
            <div class="form-group">
                <input class="form-control" style="background-color: #BABABA" type="text" name="stockCount" pattern="^(\d{1,3})$" placeholder="<fmt:message key="stockCount"/>" value="${stockCount}">
                <small class="form-text text-muted"><fmt:message key="stockCount"/></small>
            </div>
            <div class="form-group">
                <input class="form-control" style="background-color: #BABABA" type="text" name="price" pattern="^([\d]{1,19}([.,]\d{1,4})?)$" placeholder="<fmt:message key="price"/>" value="${price}">
                <small class="form-text text-muted"><fmt:message key="price"/></small>
            </div>
            <p style="color: #c69500">
                <fmt:message key="brand"/>: <select name="brandId">
                    <c:forEach items="${brands}" var="brand">
                        <option value="${brand.brandId}">${brand.name}</option>
                    </c:forEach>
                </select>
            <br/>
            <input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/></p>
            <button class="btn btn-primary btn-block" type="submit"><fmt:message key="add"/></button>
            <br/>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


    <%--<form method="post" action="/controller">--%>
        <%--<input type="hidden" name="command" value="add_part"/>--%>
        <%--<fmt:message key="catalogNo"/>: <input type="text" name="catalogNo" pattern="^([\w\-)( ]{2,45})$" placeholder="<fmt:message key="catalogNo"/>" required><br/>--%>
        <%--<fmt:message key="originalCatalogNo"/>: <input type="text" name="originalCatalogNo" pattern="^([\w\-)( ]{2,45}$)" placeholder="<fmt:message key="originalCatalogNo"/>"><br/>--%>
        <%--<fmt:message key="info"/>: <input type="text" name="info" pattern="^.{0,300}$" placeholder="<fmt:message key="info"/>"><br/>--%>
        <%--<fmt:message key="brand"/>: <select name="brandId">--%>
            <%--<c:forEach items="${brands}" var="brand">--%>
                <%--<option value="${brand.brandId}">${brand.name}</option>--%>
            <%--</c:forEach>--%>
        <%--</select><br/>--%>
        <%--<fmt:message key="wait"/>: <input type="text" name="wait" pattern="^(\d{1,3})$" placeholder="<fmt:message key="wait"/>" required><br/>--%>
        <%--<fmt:message key="stockCount"/>:<input type="text" name="stockCount" pattern="^(\d{1,3})$" placeholder="<fmt:message key="stockCount"/>" required><br/>--%>
        <%--<fmt:message key="price"/>: <input type="text" name="price" pattern="^([\d]{1,19}([.,]\d{1,4})?)$" placeholder="<fmt:message key="price"/>" required><br/>--%>
        <%--<input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/>--%>
        <%--<button type="submit"><fmt:message key="add"/></button>--%>
    <%--</form>--%>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>
