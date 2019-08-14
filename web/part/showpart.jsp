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
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="update_part"/>
                        <input type="hidden" name="partId" value="${part.partId}">
                        <input type="hidden" name="pictureUrl" value="${part.pictureUrl}">
                        <fmt:message key="catalogNo"/>: <input type="text" name="catalogNo" pattern="^[\w\-)( ]{2,45}$" placeholder="<fmt:message key="catalogNo"/>" value="${part.catalogNo}" required><br/>
                        <fmt:message key="originalCatalogNo"/>: <input type="text" name="originalCatalogNo" pattern="^[\w\-)( ]{2,45}$" placeholder="<fmt:message key="originalCatalogNo"/>" value="${part.originalCatalogNo}" required><br/>
                        <fmt:message key="info"/>: <input type="text" name="info" pattern="^[\p{L}\s\.\-]{0,300}$" placeholder="<fmt:message key="info"/>" value="${part.info}"><br/>
                        <fmt:message key="brand"/>:
                        <select name="brandId">
                            <c:forEach items="${brands}" var="foreachBrand">
                                <c:choose>
                                    <c:when test="${foreachBrand.brandId == part.brand.brandId}"><option selected value="${foreachBrand.brandId}">${foreachBrand.name}</option></c:when>
                                    <c:otherwise><option value="${foreachBrand.brandId}">${foreachBrand.name}</option></c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select><br/>
                        <fmt:message key="stockCount"/>: <input type="text" name="stockCount" pattern="^\d{1,3}$" placeholder="<fmt:message key="stockCount"/>" value="${part.stockCount}" required><br/>
                        <fmt:message key="wait"/>: <input type="text" name="wait" pattern="^\d{1,3}$" placeholder="<fmt:message key="wait"/>" value="${part.wait}" required><br/>
                        <fmt:message key="price"/>: <input type="text" name="price" pattern="^[\d]{1,19}([,.]\d{1,2})?$" placeholder="<fmt:message key="price"/>" value="<lt:fractionalNumber number="${part.price}"/>"><br/>
                        <c:choose>
                            <c:when test="${part.isActive}"><input type="checkbox" name="active" value="is active" checked> <fmt:message key="isActive"/><br/></c:when>
                            <c:otherwise><input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/></c:otherwise>
                        </c:choose>
                        <button type="submit"><fmt:message key="update"/></button>
                    </form>
                    <br/>
                    <fmt:message key="feedback"/>:<br/>
                    <c:choose>
                        <c:when test="${feedbacks.isEmpty()}">
                            <fmt:message key="noFeedback"/>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${feedbacks}" var="feedback">
                                <fmt:message key="login"/>:${feedback.user.login} <fmt:message key="date"/>:${feedback.date} <fmt:message key="star"/>:${feedback.star}<br/>
                                ${feedback.comment}
                                <form method="post" action="/controller">
                                    <input type="hidden" name="command" value="delete_feedback"/>
                                    <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">
                                    <input type="hidden" name="partId" value="${part.partId}">
                                    <button type="submit"><fmt:message key="delete"/></button>
                                </form>
                            </c:forEach>
                            <lt:pageList pageCount="${pageCount}" elementCount="${feedbacks.size()}" command="show_part"/>
                        </c:otherwise>
                    </c:choose><br/>
                </div>
                <div class="col">
                    <img src="${part.pictureUrl}"><br/><br/>
                    <form method="post" action="/UploadServlet" enctype="multipart/form-data">
                        <input type="hidden" name="partId" value="${part.partId}">
                        <input type="file" name="file"><br/>
                        <button type="submit"><fmt:message key="uploadFile"/></button>
                    </form>
                    <br/>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="add_feedback"/>
                        <input type="hidden" name="partId" value="${part.partId}">
                        <input type="text" name="star" pattern="^(10)|\d$" placeholder="<fmt:message key="star"/>" required><br/>
                        <input type="text" name="comment" pattern="^[\p{L}\s\.\-]{0,300}$" placeholder="<fmt:message key="feedback"/>" required><br/>
                        <button type="submit"><fmt:message key="addFeedback"/></button>
                    </form><br/>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="add_to_cart"/>
                        <input type="hidden" name="partId" value="${part.partId}">
                        <input type="text" name="count" pattern="^\d{1,3}$" placeholder="<fmt:message key="count"/>" value="1" required><br/>
                        <button type="submit"><fmt:message key="addToCart"/></button>
                    </form>
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