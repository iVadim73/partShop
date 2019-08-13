<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
    <ftm:setBundle basename="text"/>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="container col-4 rounded" style="background-color: #3B3B3B">
            <form method="post" action="/controller">
                <br/>
                <input type="hidden" name="command" value="registration" />
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="text" name="login" pattern="^[\w\-]{3,20}$" placeholder="<fmt:message key="login"/>" value="${login}" required>
                    <small class="form-text text-muted">You can use only....</small>
                </div>
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="password" name="password" pattern="^[\w\-]{6,18}$" placeholder="<fmt:message key="password"/>" value="${password}" required>
                    <small class="form-text text-muted">You can use only....</small>
                </div>
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="email" name="email" pattern="^(?=.{5,254}$).{1,64}@.{3,255}$" placeholder="<fmt:message key="email"/>" value="${email}" required>
                    <small class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="<fmt:message key="phone"/>" value="${phone}">
                    <small class="form-text text-muted"><fmt:message key="optional"/></small>
                </div>
                <div class="form-group">
                    <input class="form-control" style="background-color: #BABABA" type="text" name="name" pattern="^[\w\- ]{2,35}$" placeholder="<fmt:message key="name"/>" value="${name}">
                    <small class="form-text text-muted"><fmt:message key="optional"/></small>
                </div>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="register"/></button>
                <br/>
            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
<jsp:include page="/common/footer.jsp"/>
