<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="1">
    <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${orders}" var="order">
        <tr>
            <td>${order.dateOrder}</td>
            <td>${order.part.catalogNo}</td>
            <td>${order.part.brand.name}</td>
            <td>${order.part.price}</td>
            <td>${order.partCount}</td>
            <td>${order.cost}</td>
            <td>${order.dateCondition}</td>
            <td>${order.condition.info}</td>
        </tr>
    </c:forEach>
</table>
<lt:pageList pageCount="${pageCount}" elementCount="${orders.size()}" command="show_all_order"/>
</body>
<jsp:include page="/common/footer.jsp"/>
