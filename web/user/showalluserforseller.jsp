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
            <div class="col-4">
                <form action="/controller">
                    <div class="input-group input-group-md">
                        <input type="hidden" name="command" value="search_user"/>
                        <input class="form-control" type="text" name="partOfUserLogin" pattern="^[\w\-]{1,20}$" placeholder="<fmt:message key="login"/>">
                        <button class="btn btn-warning" type="submit"><fmt:message key="search"/></button>
                    </div>
                </form>
            </div>
        </div>
        <c:choose>
            <c:when test="${users.isEmpty()}">
                <div class="container-fluid" style="background-color: #80bdff" align="center">
                    <fmt:message key="noUser"/>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                    <caption style="background-color: #0f6674">
                        <div class="row" style="height: 20px">
                            <div class="col"><fmt:message key="userList"/></div>
                            <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${users.size()}" command="show_all_user"/></div>
                        </div>
                    </caption>
                    <thead>
                    <tr class="table-info">
                        <th scope="col"><fmt:message key="login"/></th>
                        <th scope="col"><fmt:message key="email"/></th>
                        <th scope="col"><fmt:message key="name"/></th>
                        <th scope="col"><fmt:message key="discount"/></th>
                        <th scope="col"><fmt:message key="star"/></th>
                        <th scope="col"><fmt:message key="bill"/></th>
                        <th scope="col"><fmt:message key="isActive"/></th>
                        <th scope="col" colspan="2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach begin="0" end="9" items="${users}" var="user">
                        <tr>
                            <td>${user.login}</td>
                            <td>${user.email}</td>
                            <td>${user.name}</td>
                            <td>${user.discount}</td>
                            <td>${user.star}</td>
                            <td><lt:fractionalNumber number="${user.bill}"/></td>
                            <td>${user.isActive}</td>
                            <td>
                                <form method="post" action="/controller">
                                    <input type="hidden" name="command" value="show_user_for_seller_and_admin"/>
                                    <input type="hidden" name="userId" value="${user.userId}"/>
                                    <button type="submit"><fmt:message key="more"/></button>
                                </form>
                            </td>
                            <td>
                                <form method="post" action="/controller">
                                    <input type="hidden" name="command" value="activate_deactivate_user"/>
                                    <input type="hidden" name="userId" value="${user.userId}">
                                    <button type="submit"><fmt:message key="activate/deactivate"/></button>
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