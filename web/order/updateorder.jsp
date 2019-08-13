<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <ftm:setBundle basename="text"/>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="container col-4 rounded" style="background-color: #3B3B3B">
            <br/>
            <p style="color: #c69500">
                <fmt:message key="orderId"/>: ${order.orderId}<br/>
                <fmt:message key="login"/>: ${order.user.login}<br/>
                <fmt:message key="dateOrder"/>: ${order.dateOrder}<br/>
                <fmt:message key="catalogNo"/>: ${order.part.catalogNo}<br/>
                <fmt:message key="brandName"/>: ${order.part.brand.name}<br/>
                <fmt:message key="price"/>: <lt:fractionalNumber number="${order.part.price}"/><br/>
                <fmt:message key="partCount"/>: ${order.partCount}<br/>
                <fmt:message key="cost"/>: <lt:fractionalNumber number="${order.cost}"/><br/>
                <fmt:message key="dateCondition"/>: ${order.dateCondition}<br/>
            </p>
            <form method="post" action="/controller">
                <input type="hidden" name="command" value="update_order"/>
                <input type="hidden" name="orderId" value="${order.orderId}">
                <p style="color: #c69500">
                    <fmt:message key="conditionInfo"/>
                    <select name="conditionId">
                    <c:forEach items="${conditions}" var="condition">
                        <c:choose>
                            <c:when test="${condition.conditionId == order.condition.conditionId}"><option selected value="${condition.conditionId}">${condition.name}</option></c:when>
                            <c:otherwise><option value="${condition.conditionId}">${condition.name}</option></c:otherwise>
                        </c:choose>
                    </c:forEach>
                    </select><br/>
                </p>
                <c:choose>
                    <c:when test="${order.isActive}"><p style="color: #c69500"><input type="checkbox" name="active" value="is active" checked> <fmt:message key="isActive"/></p><br/></c:when>
                    <c:otherwise><p style="color: #c69500"><input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/></p><br/></c:otherwise>
                </c:choose>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="update"/></button>
                <br/>
            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
<jsp:include page="/common/footer.jsp"/>
