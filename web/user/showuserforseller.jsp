<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="update_user_data"/>
    ${login}<br/>
    ${email}<br/>
    <input type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="phone" value="${phone}"><br/>
    <input type="text" name="name" pattern="^[\w\- ]{2,35}$" placeholder="name" value="${name}"><br/>
    ${registrationDate}<br/>
    <input type="text" name="discount" pattern="" placeholder="discount" value="${discount}"><br/>
    <input type="text" name="star" pattern="" placeholder="star" value="${star}"><br/>
    ${bill}<br/>
    <input type="text" name="comment" pattern="" placeholder="comment" value="${comment}"><br/>
    ${isActive}<br/>
    <button type="submit">change</button>
</form>

</body>
<jsp:include page="/common/footer.jsp"/>