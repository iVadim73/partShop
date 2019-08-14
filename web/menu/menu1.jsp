<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/common/header.jsp"/>
<body>
    <ftm:setBundle basename="text"/>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
    <h1 class="display-4 text-warning" align="center" ><fmt:message key="menu1"/></h1>
    <h1 class="display-4 text-warning" align="center" ><fmt:message key="menu11"/></h1>
    </div>
</body>
<jsp:include page="/common/footer.jsp"/>
