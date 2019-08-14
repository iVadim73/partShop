<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="container col-12 rounded" style="background-color: white">
            <div class="row">
                <div class="col">
                    <br/>
                    <fmt:message key="catalogNo"/>: ${part.catalogNo}<br/>
                    <fmt:message key="originalCatalogNo"/>: ${part.originalCatalogNo}<br/>
                    <fmt:message key="info"/>: ${part.info}<br/>
                    <fmt:message key="brand"/>: ${part.brand.name}<br/>
                    <fmt:message key="country"/>: ${part.brand.country}<br/>
                    <fmt:message key="brandInfo"/>: ${part.brand.info}<br/>
                    <fmt:message key="stockCount"/>: ${part.stockCount}<br/>
                    <fmt:message key="wait"/>: ${part.wait}<br/>
                    <fmt:message key="price"/>: <lt:fractionalNumber number="${part.price}"/><br/>
                    <br/>
                    <br/>
                    <fmt:message key="feedback"/>:<br/>
                    <c:choose>
                        <c:when test="${feedbacks.isEmpty()}">
                            <fmt:message key="noFeedback"/>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${feedbacks}" var="feedback">
                                <fmt:message key="login"/>:${feedback.user.login} <fmt:message key="date"/>:${feedback.date} <fmt:message key="star"/>:${feedback.star}<br/>
                                ${feedback.comment}<br/>
                                <br/>
                            </c:forEach>
                            <lt:pageList pageCount="${pageCount}" elementCount="${feedbacks.size()}" command="show_part"/>
                        </c:otherwise>
                    </c:choose><br/>
                </div>
                <div class="col">
                    <img src="${part.pictureUrl}">
                    <br/>
                    <br/>
                    <c:if test="${userType != 'guest'}">
                        <form method="post" action="/controller">
                            <input type="hidden" name="command" value="add_feedback"/>
                            <input type="hidden" name="partId" value="${part.partId}">
                            <input type="text" name="star" pattern="^(10)|\d$" placeholder="<fmt:message key="star"/>" required><br/>
                            <input type="text" name="comment" pattern="^[\p{L}\s\.\-]{0,300}$" placeholder="<fmt:message key="feedback"/>" required><br/>
                            <button type="submit"><fmt:message key="addFeedback"/></button>
                        </form><br/>
                    </c:if>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="add_to_cart"/>
                        <input type="hidden" name="partId" value="${part.partId}">
                        <input type="text" name="count" pattern="^\d{1,3}$" placeholder="<fmt:message key="count"/>" value="1" required><br/>
                        <button type="submit"><fmt:message key="addToCart"/></button>
                    </form><br/>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="add_to_wish_list"/>
                        <input type="hidden" name="partId" value="${part.partId}">
                        <button type="submit"><fmt:message key="addToWishList"/></button>
                    </form><br/>
                </div>
            </div>
        </div>
    </div>
</body>
<jsp:include page="/common/footer.jsp"/>
