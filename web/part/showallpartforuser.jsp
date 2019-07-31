<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="0">
    <tr>
        <td>
            <form action="/controller">
                <input type="hidden" name="command" value="search_part"/>
                <input type="text" name="partOfPartCatalogNo" pattern="^[\w\-)( ]{1,45}$" placeholder="part">
                <button type="submit">search</button>
            </form>
        </td>
    </tr>
</table>
<table border="1">
    <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${parts}" var="part">
        <tr>
            <td>${part.catalogNo}</td>
            <td>${part.originalCatalogNo}</td>
            <td>${part.info}</td>
            <td>${part.price}</td>
                <%--<td>${part.pictureUrl}</td>--%>
            <td>${part.wait}</td>
            <td>${part.brand.name}</td>
            <td>${part.brand.country}</td>
            <td>${part.stockCount}</td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="add_to_cart"/>
                    <input type="hidden" name="partId" value="${part.partId}">
                    <input type="text" name="count" pattern="^\d{1,3}$" placeholder="count" value="1">
                    <button type="submit">add to cart</button>
                </form>
            </td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="add_to_wish_list"/>
                    <input type="hidden" name="partId" value="${part.partId}">
                    <button type="submit">add to wish list</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<lt:pageList pageCount="${pageCount}" elementCount="${parts.size()}" command="show_all_part"/>
</body>
<jsp:include page="/common/footer.jsp"/>