<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="update_user_data_for_admin"/>
        <input type="hidden" name="userId" value="${userId}"/>
        <fmt:message key="login"/>: ${login}<br/>
        <fmt:message key="email"/>: ${email}<br/>
        <fmt:message key="phone"/>: <input type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="<fmt:message key="phone"/>" value="${phone}"><br/>
        <fmt:message key="name"/>: <input type="text" name="name" pattern="^[\w\- ]{2,35}$" placeholder="<fmt:message key="name"/>" value="${name}"><br/>
        <fmt:message key="registrationDate"/>: ${registrationDate}<br/>
        <fmt:message key="discount"/>: <input type="text" name="discount" pattern="^\d{1,2}(\.\d{1,2})?$" placeholder="<fmt:message key="discount"/>" value="${discount}" required><br/>
        <fmt:message key="star"/>: <input type="text" name="star" pattern="^(10)|\d$" placeholder="<fmt:message key="star"/>" value="${star}"><br/>
        <fmt:message key="bill"/>: <lt:fractionalNumber number="${bill}"/><br/>
        <fmt:message key="role"/>: <select name="roleId">
            <c:forEach items="${roles}" var="foreachRole">
                <c:choose>
                    <c:when test="${foreachRole.roleId == role.roleId}"><option selected value="${foreachRole.roleId}">${foreachRole.type}</option></c:when>
                    <c:otherwise><option value="${foreachRole.roleId}">${foreachRole.type}</option></c:otherwise>
                </c:choose>
            </c:forEach>
        </select><br/>
        <fmt:message key="comment"/>: <input type="text" name="comment" pattern=".{0,300}" placeholder="<fmt:message key="comment"/>" value="${comment}"><br/>
        <c:choose>
            <c:when test="${isActive}"><input type="checkbox" name="active" value="is active" checked> <fmt:message key="isActive"/><br/></c:when>
            <c:otherwise><input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/></c:otherwise>
        </c:choose>
        <button type="submit"><fmt:message key="change"/></button>
    </form>

    <form method="post" action="/controller">
        <input type="hidden" name="command" value="add_bill" />
        <input type="hidden" name="userId" value="${userId}">
        <input type="text" name="sum" pattern="^\-?\d{1,5}([.,]\d{1,2})?$" placeholder="<fmt:message key="sum"/>" required><br/>
        <select name="billInfoId">
            <c:forEach items="${billInfoList}" var="billInfo">
                <option value="${billInfo.billInfoId}">${billInfo.info}</option>
            </c:forEach>
        </select><br/>
        <button type="submit"><fmt:message key="updateBill"/></button>
    </form>

    <form method="post" action="/controller">
        <input type="hidden" name="command" value="show_all_bill"/>
        <input type="hidden" name="login" value="${login}">
        <button type="submit"><fmt:message key="showUserBills"/></button>
    </form>

    <form method="post" action="/controller">
        <input type="hidden" name="command" value="show_all_order" />
        <input type="hidden" name="login" value="${login}">
        <button type="submit"><fmt:message key="showUserOrders"/></button>
    </form>
</body>
<jsp:include page="/common/footer.jsp"/>