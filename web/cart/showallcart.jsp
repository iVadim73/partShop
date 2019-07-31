<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="1">
    <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${carts}" var="cart">
        <tr>
            <td>${cart.part.catalogNo}</td>
            <td>${cart.part.info}</td>
            <td>${cart.part.price}</td>
            <td>${cart.part.wait}</td>
            <td>${cart.part.brand.name}</td>
            <td>${cart.part.brand.country}</td>
            <td>${cart.part.stockCount}</td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="update_cart"/>
                    <input type="hidden" name="cartId" value="${cart.cartId}">
                    <input type="text" name="count" pattern="^\d{1,3}$" placeholder="count" value="${cart.count}">
                    <button type="submit">update</button>
                </form>
            </td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="delete_from_cart"/>
                    <input type="hidden" name="cartId" value="${cart.cartId}">
                    <button type="submit">delete</button>
                </form>
            </td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="add_to_order"/>
                    <input type="hidden" name="partId" value="${cart.part.partId}">
                    <input type="hidden" name="count" value="${cart.count}">
                    <input type="hidden" name="cartId" value="${cart.cartId}">
                    <button type="submit">buy</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<lt:pageList pageCount="${pageCount}" elementCount="${carts.size()}" command="show_all_cart"/>
</body>
<jsp:include page="/common/footer.jsp"/>