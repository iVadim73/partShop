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
            <c:when test="${bills.isEmpty()}">
                <div class="container-fluid" style="background-color: #80bdff" align="center">
                    <fmt:message key="noBill"/>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover" style="background-color: #b9bbbe">
                    <caption style="background-color: #0f6674">
                        <div class="row" style="height: 20px">
                            <div class="col"><fmt:message key="billOperations"/></div>
                            <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${bills.size()}" command="show_all_bill" login="${bills[0].user.login}"/></div>
                        </div>
                    </caption>
                    <thead>
                        <tr class="table-info">
                            <th scope="col"><fmt:message key="sum"/></th>
                            <th scope="col"><fmt:message key="info"/></th>
                            <th scope="col"><fmt:message key="date"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${bills}" var="bill">
                            <tr>
                                <td><lt:fractionalNumber number="${bill.sum}"/></td>
                                <td>${bill.billInfo.info}</td>
                                <td>${bill.date}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
<jsp:include page="/common/footer.jsp"/>
