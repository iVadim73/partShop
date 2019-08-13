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
                    <input type="hidden" name="command" value="to_add_part_form"/>
                    <button class="btn btn-warning" type="submit"><fmt:message key="add"/></button>
                </form>
            </div>
            <div class="col-4">
                <form action="/controller">
                    <div class="input-group input-group-md">
                        <input type="hidden" name="command" value="search_part"/>
                        <input class="form-control" type="text" name="partOfPartCatalogNo" pattern="^[\w\-)( ]{1,45}$" placeholder="<fmt:message key="catalogNo"/>" value="${partOfPartCatalogNo}"/>
                        <button class="btn btn-warning" type="submit"><fmt:message key="search"/></button>
                    </div>
                </form>
            </div>
        </div>
        <c:choose>
            <c:when test="${parts.isEmpty()}">
                <fmt:message key="noPart"/>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                    <caption style="background-color: #0f6674">
                        <div class="row" style="height: 20px">
                            <div class="col">Список запастей</div>
                            <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${parts.size()}" command="show_all_part"/></div>
                        </div>
                    </caption>
                    <thead>
                        <tr class="table-info">
                            <th scope="col"><fmt:message key="catalogNo"/></th>
                            <th scope="col"><fmt:message key="originalCatalogNo"/></th>
                            <th scope="col"><fmt:message key="info"/></th>
                            <th scope="col"><fmt:message key="price"/></th>
                            <th scope="col"><fmt:message key="waitForTable"/></th>
                            <th scope="col"><fmt:message key="brandName"/></th>
                            <th scope="col"><fmt:message key="stockCount"/></th>
                            <th scope="col"><fmt:message key="isActive"/></th>
                            <th scope="col" colspan="4"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${parts}" var="part">
                            <tr>
                                <td>${part.catalogNo}</td>
                                <td>${part.originalCatalogNo}</td>
                                <td>${part.info}</td>
                                <td><lt:fractionalNumber number="${part.price}"/></td>
                                <td>${part.wait}</td>
                                <td>${part.brand.name}</td>
                                <td>${part.stockCount}</td>
                                <td>${part.isActive}</td>
                                <td>
                                    <form method="post" action="/controller">
                                        <input type="hidden" name="command" value="show_part"/>
                                        <input type="hidden" name="partId" value="${part.partId}"/>
                                        <button type="submit"><fmt:message key="more"/></button>
                                    </form>
                                </td>
                                <td>
                                    <form method="post" action="/controller">
                                        <input type="hidden" name="command" value="activate_deactivate_part"/>
                                        <input type="hidden" name="partId" value="${part.partId}">
                                        <button type="submit"><fmt:message key="activate/deactivate"/></button>
                                    </form>
                                </td>
                                <td>
                                    <form method="post" action="/controller">
                                        <input type="hidden" name="command" value="add_to_cart"/>
                                        <input type="hidden" name="partId" value="${part.partId}">
                                        <input type="text" name="count" pattern="^\d{1,3}$" placeholder="<fmt:message key="count"/>" value="1">
                                        <button type="submit"><fmt:message key="addToCart"/></button>
                                    </form>
                                </td>
                                <td>
                                    <form method="post" action="/controller">
                                        <input type="hidden" name="command" value="add_to_wish_list"/>
                                        <input type="hidden" name="partId" value="${part.partId}">
                                        <button type="submit"><fmt:message key="addToWishList"/></button>
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