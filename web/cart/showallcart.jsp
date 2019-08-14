<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="row" style="background-color: #b9bbbe" align="center">
            <div class="col-2">
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="add_all_to_order"/>
                    <button class="btn btn-warning" type="submit"><fmt:message key="buyAll"/></button>
                </form>
            </div>
        </div>
        <c:choose>
            <c:when test="${carts.isEmpty()}">
                <div class="container-fluid" style="background-color: #80bdff" align="center">
                    <fmt:message key="noCart"/>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                    <caption style="background-color: #0f6674">
                        <div class="row" style="height: 20px">
                            <div class="col"><fmt:message key="cartList"/></div>
                            <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${carts.size()}" command="show_all_cart"/></div>
                        </div>
                    </caption>
                    <thead>
                    <tr class="table-info">
                        <th scope="col"><fmt:message key="catalogNo"/></th>
                        <th scope="col"><fmt:message key="info"/></th>
                        <th scope="col"><fmt:message key="price"/></th>
                        <th scope="col"><fmt:message key="wait"/></th>
                        <th scope="col"><fmt:message key="brandName"/></th>
                        <th scope="col"><fmt:message key="country"/></th>
                        <th scope="col"><fmt:message key="stockCount"/></th>
                        <th scope="col"><fmt:message key="discountCost"/></th>
                        <th scope="col" colspan="3"></th>
                    </tr>
                    </thead>
                    <tbody>
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
                    </tbody>
                </table>
                <br/>
            </c:otherwise>
        </c:choose>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
<jsp:include page="/common/footer.jsp"/>