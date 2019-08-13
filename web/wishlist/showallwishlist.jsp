<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <c:choose>
        <c:when test="${wishLists.isEmpty()}">
            <fmt:message key="noWishList"/>
        </c:when>
        <c:otherwise>
            <table border="1">
                <tr>
                    <td><fmt:message key="catalogNo"/></td>
                    <td><fmt:message key="originalCatalogNo"/></td>
                    <td><fmt:message key="info"/></td>
                    <td><fmt:message key="price"/></td>
                    <td><fmt:message key="wait"/></td>
                    <td><fmt:message key="brandName"/></td>
                    <td><fmt:message key="country"/></td>
                    <td><fmt:message key="stockCount"/></td>
                </tr>
                <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${wishLists}" var="wishList">
                    <tr>
                        <td>${wishList.part.catalogNo}</td>
                        <td>${wishList.part.originalCatalogNo}</td>
                        <td>${wishList.part.info}</td>
                        <td><lt:fractionalNumber number="${wishList.part.price}"/></td>
                        <td>${wishList.part.wait}</td>
                        <td>${wishList.part.brand.name}</td>
                        <td>${wishList.part.brand.country}</td>
                        <td>${wishList.part.stockCount}</td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="delete_from_wish_list"/>
                                <input type="hidden" name="wishListId" value="${wishList.wishListId}">
                                <button type="submit"><fmt:message key="delete"/></button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="add_to_cart"/>
                                <input type="hidden" name="partId" value="${wishList.part.partId}">
                                <input type="hidden" name="wishListId" value="${wishList.wishListId}">
                                <input type="text" name="count" pattern="^\d{1,3}$" placeholder="<fmt:message key="count"/>" value="1">
                                <button type="submit"><fmt:message key="addToCart"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <lt:pageList pageCount="${pageCount}" elementCount="${wishLists.size()}" command="show_all_wish_list"/>
        </c:otherwise>
    </c:choose>
</body>
<jsp:include page="/common/footer.jsp"/>