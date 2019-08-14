<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/common/header.jsp"/>
<body>
    <ftm:setBundle basename="text"/>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
    <h1 class="display-4 text-warning" align="center" ><fmt:message key="menu2"/></h1>
    <p align="center" style="color: #c69500"><fmt:message key="menu22"/><br/>
    <fmt:message key="menu222"/></p>
    </div>
</body>
<jsp:include page="/common/footer.jsp"/>
