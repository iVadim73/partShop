<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
    <table border="0">
        <tr>
            <td>
                <form method="post" action="/brand/addbrand.jsp">
                    <button type="submit">add</button>
                </form>
            </td>
            <td>
                <form action="/controller">
                    <input type="hidden" name="command" value="search_brand"/>
                    <input type="text" name="partOfBrandName" pattern="^[\w\-)( ]{1,45}$" placeholder="brand">
                    <button type="submit">search</button>
                </form>
            </td>
        </tr>
    </table>
    <table border="1">
        <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${brands}" var="brand">
        <tr>
            <td>${brand.name}</td>
            <td>${brand.country}</td>
            <td>${brand.info}</td>
            <td>${brand.isActive}</td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="to_update_brand_form"/>
                    <input type="hidden" name="brandId" value="${brand.brandId}"/>
                    <button type="submit">update</button>
                </form>
            </td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="activate_deactivate_brand"/>
                    <input type="hidden" name="brandId" value="${brand.brandId}">
                    <button type="submit">activate/deactivate</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
    <lt:pageList pageCount="${pageCount}" elementCount="${brands.size()}" command="show_all_brand"/>
</body>
<jsp:include page="/common/footer.jsp"/>
