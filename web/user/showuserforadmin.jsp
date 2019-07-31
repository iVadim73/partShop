<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="update_user_data_for_admin"/>
    <input type="hidden" name="userId" value="${userId}"/>

    ${login}<br/>
    ${email}<br/>
    <input type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="phone" value="${phone}"><br/>
    <input type="text" name="name" pattern="^[\w\- ]{2,35}$" placeholder="name" value="${name}"><br/>
    ${registrationDate}<br/>
    <input type="text" name="discount" pattern="^\d{1,2}(\.\d{1,2})?$" placeholder="discount" value="${discount}"><br/>
    <input type="text" name="star" pattern="^(10)|\d$" placeholder="star" value="${star}"><br/>
    ${bill}<br/>
    <select name="roleId">
        <c:forEach items="${roles}" var="foreachrole">
            <c:choose>
                <c:when test="${foreachrole.roleId == role.roleId}"><option selected value="${foreachrole.roleId}">${foreachrole.type}</option></c:when>
                <c:otherwise><option value="${foreachrole.roleId}">${foreachrole.type}</option></c:otherwise>
            </c:choose>
        </c:forEach>
    </select><br/>
    <input type="text" name="comment" pattern=".{0,300}" placeholder="comment" value="${comment}"><br/>
    <c:choose>
        <c:when test="${isActive}"><input type="checkbox" name="active" value="is active" checked> is active?<br/></c:when>
        <c:otherwise><input type="checkbox" name="active" value="is active"> is active?<br/></c:otherwise>
    </c:choose>
    <button type="submit">change</button>
</form>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="add_bill" />
    <input type="hidden" name="userId" value="${userId}">
    <input type="text" name="sum" pattern="^\d{1,5}(\.\d{1,2})?$" placeholder="sum"><br/>
    <select name="billInfoId">
        <c:forEach items="${billInfoList}" var="billInfo">
            <option value="${billInfo.billInfoId}">${billInfo.info}</option>
        </c:forEach>
    </select><br/>
    <button type="submit">bill plus</button>
</form>
</body>
<jsp:include page="/common/footer.jsp"/>