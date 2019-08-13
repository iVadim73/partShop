<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <c:choose>
        <c:when test="${parts.isEmpty()}">
            <fmt:message key="noPart"/>
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
                    <td><fmt:message key="brandInfo"/></td>
                    <td><fmt:message key="stockCount"/></td>
                </tr>
                <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${parts}" var="part">
                    <tr>
                        <td>${part.catalogNo}</td>
                        <td>${part.originalCatalogNo}</td>
                        <td>${part.info}</td>
                        <td><lt:fractionalNumber number="${part.price}"/></td>
                        <td>${part.wait}</td>
                        <td>${part.brand.name}</td>
                        <td>${part.brand.country}</td>
                        <td>${part.brand.info}</td>
                        <td>${part.stockCount}</td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="show_part"/>
                                <input type="hidden" name="partId" value="${part.partId}"/>
                                <button type="submit"><fmt:message key="more"/></button>
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
            </table>
            <lt:pageList pageCount="${pageCount}" elementCount="${parts.size()}" command="show_all_part"/>
        </c:otherwise>
    </c:choose>
</body>
<jsp:include page="/common/footer.jsp"/>