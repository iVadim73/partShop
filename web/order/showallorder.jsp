<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <c:choose>
            <c:when test="${orders.isEmpty()}">
                <div class="container-fluid" style="background-color: #80bdff" align="center">
                    <fmt:message key="noOrder"/>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                    <caption style="background-color: #0f6674">
                        <div class="row" style="height: 20px">
                            <div class="col"><fmt:message key="orderList"/> ${login}</div>
                            <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${orders.size()}" command="show_all_order" login="${login}"/></div>
                        </div>
                    </caption>
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
                        <th scope="col"><fmt:message key="isActive"/></th>
                        <th scope="col" colspan="2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach begin="0" end="9" items="${orders}" var="order">
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
                            <td>${order.isActive}</td>
                            <td>
                                <form method="post" action="/controller">
                                    <input type="hidden" name="command" value="to_update_order_form"/>
                                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                                    <button type="submit"><fmt:message key="more"/></button>
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
