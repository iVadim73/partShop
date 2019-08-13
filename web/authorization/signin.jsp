<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
    <ftm:setBundle basename="text"/>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="container col-4 rounded" style="background-color: #3B3B3B">
            <form method="post" action="/controller">
                <input type="hidden" name="command" value="signin"/>
                <br/>
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="text" name="login" pattern="^[\w\-]{3,20}$" placeholder="<fmt:message key="login"/>" value="${login}" required>
                    <small class="form-text text-muted">You can use only....</small>
                </div>
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="password" name="password" pattern="^[\w\-]{6,18}$" placeholder="<fmt:message key="password"/>" value="${password}" required>
                    <small class="form-text text-muted">You can use only....</small>
                </div>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="signIn"/></button>
            </form>
            <br/>
            <br/>
            <p class="text-white" align="center"><fmt:message key="notRegister"/></p>
            <form method="post" action="/registration">
                <input type="hidden" name="command" value="to_registration"/>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="registration"/></button>
                <br/>
            </form>
        </div>
    </div>
</body>
<jsp:include page="/common/footer.jsp"/>
