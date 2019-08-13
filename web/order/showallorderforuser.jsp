<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>



<div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
    ${login}</br>
    <c:choose>
        <c:when test="${orders.isEmpty()}">
            <fmt:message key="noOrder"/>
        </c:when>
        <c:otherwise>
            <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                <thead>
                <tr class="table-info">
                    <th scope="col"><fmt:message key="id"/></th>
                    <th scope="col"><fmt:message key="dateOrder"/></th>
                    <th scope="col"><fmt:message key="catalogNo"/></th>
                    <th scope="col"><fmt:message key="brandName"/></th>
                    <th scope="col"><fmt:message key="price"/></th>
                    <th scope="col"><fmt:message key="partCount"/></th>
                    <th scope="col"><fmt:message key="cost"/></th>
                    <th scope="col"><fmt:message key="dateCondition"/></th>
                    <th scope="col"><fmt:message key="conditionInfo"/></th>
                    <th scope="col" colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${orders}" var="order">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.dateOrder}</td>
                        <td>${order.part.catalogNo}</td>
                        <td>${order.part.brand.name}</td>
                        <td><lt:fractionalNumber number="${order.part.price}"/></td>
                        <td>${order.partCount}</td>
                        <td><lt:fractionalNumber number="${order.cost}"/></td>
                        <td>${order.dateCondition}</td>
                        <td>${order.condition.info}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="mx-auto" style="width: 200px;">
                <lt:pageList pageCount="${pageCount}" elementCount="${orders.size()}" command="show_all_order" login="${login}"/>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


<%--${login}</br>--%>
    <%--<c:choose>--%>
        <%--<c:when test="${orders.isEmpty()}">--%>
            <%--<fmt:message key="noOrder"/>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
        <%--<table border="1">--%>
            <%--<tr>--%>
                <%--<td><fmt:message key="id"/></td>--%>
                <%--<td><fmt:message key="dateOrder"/></td>--%>
                <%--<td><fmt:message key="catalogNo"/></td>--%>
                <%--<td><fmt:message key="brandName"/></td>--%>
                <%--<td><fmt:message key="price"/></td>--%>
                <%--<td><fmt:message key="partCount"/></td>--%>
                <%--<td><fmt:message key="cost"/></td>--%>
                <%--<td><fmt:message key="dateCondition"/></td>--%>
                <%--<td><fmt:message key="conditionInfo"/></td>--%>
            <%--</tr>--%>
            <%--<c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${orders}" var="order">--%>
                <%--<tr>--%>
                    <%--<td>${order.orderId}</td>--%>
                    <%--<td>${order.dateOrder}</td>--%>
                    <%--<td>${order.part.catalogNo}</td>--%>
                    <%--<td>${order.part.brand.name}</td>--%>
                    <%--<td><lt:fractionalNumber number="${order.part.price}"/></td>--%>
                    <%--<td>${order.partCount}</td>--%>
                    <%--<td><lt:fractionalNumber number="${order.cost}"/></td>--%>
                    <%--<td>${order.dateCondition}</td>--%>
                    <%--<td>${order.condition.info}</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
        <%--</table>--%>
        <%--<lt:pageList pageCount="${pageCount}" elementCount="${orders.size()}" command="show_all_order" login="${login}"/>--%>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>

</body>
<jsp:include page="/common/footer.jsp"/>
