<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="container col-8 rounded" style="background-color: #3B3B3B">
            <div class="row">
                <div class="col">
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="update_user_data_for_admin"/>
                        <input type="hidden" name="userId" value="${userId}"/>
                        <p style="color: #c69500">
                            <br/>
                            <fmt:message key="login"/>: ${login}<br/>
                            <fmt:message key="email"/>: ${email}<br/>
                            <fmt:message key="bill"/>: ${bill}<br/>
                            <fmt:message key="registrationDate"/>: ${registrationDate}
                        </p>
                        <div class="form-group">
                            <input class="form-control" style="background-color: #BABABA" type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="<fmt:message key="phone"/>" value="${phone}" required>
                            <small class="form-text text-muted"><fmt:message key="phone"/></small>
                        </div>

                        <div class="form-group">
                            <input class="form-control" style="background-color: #BABABA" type="text" name="name" pattern="^[\p{L}\s\.\-]{2,35}$" placeholder="<fmt:message key="name"/>" value="${name}" required>
                            <small class="form-text text-muted"><fmt:message key="name"/></small>
                        </div>
                        <div class="form-group">
                            <input class="form-control" style="background-color: #BABABA" type="text" name="discount" pattern="^\d{1,2}(\.\d{1,2})?$" placeholder="<fmt:message key="discount"/>" value="${discount}" required>
                            <small class="form-text text-muted"><fmt:message key="discount"/></small>
                        </div>
                        <div class="form-group">
                            <input class="form-control" style="background-color: #BABABA" type="text" name="star" pattern="^(10)|\d$" placeholder="<fmt:message key="star"/>" value="${star}" required>
                            <small class="form-text text-muted"><fmt:message key="star"/></small>
                        </div>
                        <div class="form-group">
                            <input class="form-control" style="background-color: #BABABA" type="text" name="comment" pattern="^[\p{L}\s\.\-]{0,300}$"  placeholder="<fmt:message key="comment"/>" value="${comment}" required>
                            <small class="form-text text-muted"><fmt:message key="comment"/></small>
                        </div>
                        <p style="color: #c69500">
                            <fmt:message key="role"/>: <select name="roleId">
                            <c:forEach items="${roles}" var="foreachRole">
                                <c:choose>
                                    <c:when test="${foreachRole.roleId == role.roleId}"><option selected value="${foreachRole.roleId}">${foreachRole.type}</option></c:when>
                                    <c:otherwise><option value="${foreachRole.roleId}">${foreachRole.type}</option></c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select><br/>
                            <c:choose>
                                <c:when test="${isActive}"><input type="checkbox" name="active" value="is active" checked> <fmt:message key="isActive"/><br/></c:when>
                                <c:otherwise><input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/></c:otherwise>
                            </c:choose>
                        </p>
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message key="change"/></button>
                        <br/>
                    </form>
                </div>
                <div class="col">
                    <br/>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="add_bill" />
                        <input type="hidden" name="userId" value="${userId}">
                        <input type="text" name="sum" pattern="^\-?\d{1,5}([.,]\d{1,2})?$" placeholder="<fmt:message key="sum"/>" required>
                        <select name="billInfoId">
                            <c:forEach items="${billInfoList}" var="billInfo">
                                <option value="${billInfo.billInfoId}">${billInfo.info}</option>
                            </c:forEach>
                        </select><br/>
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message key="updateBill"/></button>
                        <br/>
                    </form>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_all_bill"/>
                        <input type="hidden" name="login" value="${login}">
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message key="showUserBills"/></button>
                        <br/>
                    </form>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_all_order" />
                        <input type="hidden" name="login" value="${login}">
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message key="showUserOrders"/></button>
                        <br/>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
<jsp:include page="/common/footer.jsp"/>