<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>

<div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
    <c:choose>
        <c:when test="${wishLists.isEmpty()}">
            <fmt:message key="noWishList"/>
        </c:when>
        <c:otherwise>
            <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                <caption style="background-color: #0f6674">
                    <div class="row" style="height: 20px">
                        <div class="col">Избранное</div>
                        <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${wishLists.size()}" command="show_all_wish_list"/></div>
                    </div>
                </caption>
                <thead>
                    <tr class="table-info">
                        <th scope="col"><fmt:message key="catalogNo"/></th>
                        <th scope="col"><fmt:message key="originalCatalogNo"/></th>
                        <th scope="col"><fmt:message key="info"/></th>
                        <th scope="col"><fmt:message key="price"/></th>
                        <th scope="col"><fmt:message key="wait"/></th>
                        <th scope="col"><fmt:message key="brandName"/></th>
                        <th scope="col"><fmt:message key="country"/></th>
                        <th scope="col"><fmt:message key="stockCount"/></th>
                        <th scope="col" colspan="3"></th>
                    </tr>
                </thead>
                <tbody>
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
                </tbody>
            </table>
            <br/>
            <%--<div class="mx-auto" style="width: 200px;">--%>
            <%--<lt:pageList pageCount="${pageCount}" elementCount="${brands.size()}" command="show_all_brand"/>--%>
            <%--</div>--%>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <%--<c:choose>--%>
        <%--<c:when test="${wishLists.isEmpty()}">--%>
            <%--<fmt:message key="noWishList"/>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--<table border="1">--%>
                <%--<tr>--%>
                    <%--<td><fmt:message key="catalogNo"/></td>--%>
                    <%--<td><fmt:message key="originalCatalogNo"/></td>--%>
                    <%--<td><fmt:message key="info"/></td>--%>
                    <%--<td><fmt:message key="price"/></td>--%>
                    <%--<td><fmt:message key="wait"/></td>--%>
                    <%--<td><fmt:message key="brandName"/></td>--%>
                    <%--<td><fmt:message key="country"/></td>--%>
                    <%--<td><fmt:message key="stockCount"/></td>--%>
                <%--</tr>--%>
                <%--<c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${wishLists}" var="wishList">--%>
                    <%--<tr>--%>
                        <%--<td>${wishList.part.catalogNo}</td>--%>
                        <%--<td>${wishList.part.originalCatalogNo}</td>--%>
                        <%--<td>${wishList.part.info}</td>--%>
                        <%--<td><lt:fractionalNumber number="${wishList.part.price}"/></td>--%>
                        <%--<td>${wishList.part.wait}</td>--%>
                        <%--<td>${wishList.part.brand.name}</td>--%>
                        <%--<td>${wishList.part.brand.country}</td>--%>
                        <%--<td>${wishList.part.stockCount}</td>--%>
                        <%--<td>--%>
                            <%--<form method="post" action="/controller">--%>
                                <%--<input type="hidden" name="command" value="delete_from_wish_list"/>--%>
                                <%--<input type="hidden" name="wishListId" value="${wishList.wishListId}">--%>
                                <%--<button type="submit"><fmt:message key="delete"/></button>--%>
                            <%--</form>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--<form method="post" action="/controller">--%>
                                <%--<input type="hidden" name="command" value="add_to_cart"/>--%>
                                <%--<input type="hidden" name="partId" value="${wishList.part.partId}">--%>
                                <%--<input type="hidden" name="wishListId" value="${wishList.wishListId}">--%>
                                <%--<input type="text" name="count" pattern="^\d{1,3}$" placeholder="<fmt:message key="count"/>" value="1">--%>
                                <%--<button type="submit"><fmt:message key="addToCart"/></button>--%>
                            <%--</form>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</c:forEach>--%>
            <%--</table>--%>
            <%--<lt:pageList pageCount="${pageCount}" elementCount="${wishLists.size()}" command="show_all_wish_list"/>--%>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>
</body>
<jsp:include page="/common/footer.jsp"/>