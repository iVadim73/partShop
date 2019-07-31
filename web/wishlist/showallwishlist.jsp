<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lt" uri="customTags" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="1">
    <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${wishLists}" var="wishList">
        <tr>
            <td>${wishList.part.catalogNo}</td>
            <td>${wishList.part.originalCatalogNo}</td>
            <td>${wishList.part.info}</td>
            <td>${wishList.part.price}</td>
                <%--<td>${wishList.part.pictureUrl}</td>--%>
            <td>${wishList.part.wait}</td>
            <td>${wishList.part.brand.name}</td>
            <td>${wishList.part.brand.country}</td>
            <td>${wishList.part.stockCount}</td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="delete_from_wish_list"/>
                    <input type="hidden" name="wishListId" value="${wishList.wishListId}">
                    <button type="submit">delete</button>
                </form>
            </td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="add_to_cart"/>
                    <input type="hidden" name="partId" value="${wishList.part.partId}">
                    <input type="hidden" name="wishListId" value="${wishList.wishListId}">
                    <input type="text" name="count" pattern="^\d{1,3}$" placeholder="count" value="1">
                    <button type="submit">add to cart</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<lt:pageList pageCount="${pageCount}" elementCount="${wishLists.size()}" command="show_all_wishl_ist"/>
</body>
<jsp:include page="/common/footer.jsp"/>