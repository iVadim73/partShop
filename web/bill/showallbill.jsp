<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="1">
    <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${bills}" var="bill">
        <tr>
            <td>${bill.sum}</td>
            <td>${bill.billInfo.info}</td>
            <td>${bill.date}</td>
        </tr>
    </c:forEach>
</table>
<lt:pageList pageCount="${pageCount}" elementCount="${bills.size()}" command="show_all_bill"/>
</body>
<jsp:include page="/common/footer.jsp"/>
