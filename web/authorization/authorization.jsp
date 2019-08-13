<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
    <div class="container">
        <br/>
        <form method="post" action="/signin">
            <input type="hidden" name="command" value="to_signin"/>
            <button type="submit" class="btn btn-outline-warning btn btn-block"><fmt:message key="signIn"/></button>
        </form>

        <form method="post" action="/registration">
            <input type="hidden" name="command" value="to_registration"/>
                <button type="submit" class="btn btn-outline-warning btn btn-block"><fmt:message key="registration"/></button>
        </form>
    </div>
