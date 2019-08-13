<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<br/>
<div class="row mr-auto">
    <div class="col-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="show_all_cart" />
            <button type="submit" class="btn btn-light btn-sm btn-block"><fmt:message key="cart"/></button>
        </form>
    </div>
    <div class="col-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="show_user" />
            <button type="submit" class="btn btn-light btn-sm btn-block">${currentLogin}</button>
        </form>
    </div>
</div>
<div class="row mr-auto">
    <div class="col-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="show_all_wish_list" />
            <button type="submit" class="btn btn-light btn-sm btn-block"><fmt:message key="wishList"/></button>
        </form>
    </div>
    <div class="col-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="signout" />
            <button type="submit" class="btn btn-light btn-sm btn-block"><fmt:message key="signOut"/></button>
        </form>
    </div>
</div>
<div class="row mr-auto">
    <div class="col-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="show_all_order" />
            <input type="hidden" name="login" value="${currentLogin}">
            <button type="submit" class="btn btn-light btn-sm btn-block"><fmt:message key="orders"/></button>
        </form>
    </div>
    <div class="col-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="show_all_bill" />
            <input type="hidden" name="login" value="${currentLogin}">
            <button type="submit" class="btn btn-light btn-sm btn-block"><fmt:message key="bill"/></button>
        </form>
    </div>
</div>