<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
    <ftm:setBundle basename="text"/>
    <div style="height: 76%; background-color: #b9bbbe">
        </br>
        <fmt:message key="error"/>
    </div>
</body>
<jsp:include page="/common/footer.jsp"/>
