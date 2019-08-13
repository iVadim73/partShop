<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <c:choose>
        <c:when test="${carts.isEmpty()}">
            <fmt:message key="noCart"/>
        </c:when>
        <c:otherwise>
            <table border="1">
                <tr>
                    <td><fmt:message key="catalogNo"/></td>
                    <td><fmt:message key="info"/></td>
                    <td><fmt:message key="price"/></td>
                    <td><fmt:message key="wait"/></td>
                    <td><fmt:message key="brandName"/></td>
                    <td><fmt:message key="country"/></td>
                    <td><fmt:message key="stockCount"/></td>
                    <td><fmt:message key="discountCost"/></td>
                </tr>
                <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${carts}" var="cart">
                    <tr>
                        <td>${cart.part.catalogNo}</td>
                        <td>${cart.part.info}</td>
                        <td>${cart.part.price}</td>
                        <td>${cart.part.wait}</td>
                        <td>${cart.part.brand.name}</td>
                        <td>${cart.part.brand.country}</td>
                        <td>${cart.part.stockCount}</td>
                        <td><lt:fractionalNumber number="${cart.count * cart.part.price * (100 - cart.user.discount) / 100}"/></td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="update_cart"/>
                                <input type="hidden" name="cartId" value="${cart.cartId}">
                                <input type="text" name="count" pattern="^\d{1,3}$" placeholder="<fmt:message key="count"/>" value="${cart.count}">
                                <button type="submit"><fmt:message key="update"/></button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="delete_from_cart"/>
                                <input type="hidden" name="cartId" value="${cart.cartId}">
                                <button type="submit"><fmt:message key="delete"/></button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="add_to_order"/>
                                <input type="hidden" name="partId" value="${cart.part.partId}">
                                <input type="hidden" name="count" value="${cart.count}">
                                <input type="hidden" name="cartId" value="${cart.cartId}">
                                <button type="submit"><fmt:message key="buy"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <form method="post" action="/controller">
                <input type="hidden" name="command" value="add_all_to_order"/>
                <button type="submit"><fmt:message key="buyAll"/></button>
            </form>
            <lt:pageList pageCount="${pageCount}" elementCount="${carts.size()}" command="show_all_cart"/>
        </c:otherwise>
    </c:choose>
</body>
<jsp:include page="/common/footer.jsp"/>