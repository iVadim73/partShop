<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<footer class="footer navbar-fixed-bottom position-fixed btn-block">
    <div class="row" style="background-color: #3B3B3B">
        <div class="col-10">
            <p><fmt:message key="footerInfo"/></p>
        </div>
        <div class="col-2">
            <form  method="post" action="/controller">
                <input type="hidden" name="command" value="set_language"/>
                <div align="left" class="btn-group" role="group" aria-label="Basic example">
                    <button type="submit" class="btn btn-outline-secondary" name="language" value="ru">ru</button>
                    <button type="submit" class="btn btn-outline-secondary" name="language" value="de">de</button>
                    <button type="submit" class="btn btn-outline-secondary" name="language" value="en">en</button>
                </div>
            </form>
        </div>
    </div>
</footer>
<%--<footer class="py-4 bg-dark navbar-fixed-bottom position-fixed text-white-50">--%>
    <%--&lt;%&ndash;<div class="container-fluid position-fixed">&ndash;%&gt;--%>
        <%--<div class="row bg-dark" style="height: 40px; ">--%>
        <%--<ftm:setBundle basename="text"/>--%>
            <%--<div style="height: 40px; width: 90%" align="left">--%>
                <%--<fmt:message key="footerInfo"/>--%>
            <%--</div>--%>
            <%--<form style="height: 40px; width: 10%; align-items: center" method="post" action="/controller">--%>
                <%--<input type="hidden" name="command" value="set_language"/>--%>
                <%--<div align="left" class="btn-group" role="group" aria-label="Basic example">--%>
                    <%--<button type="submit" class="btn btn-outline-secondary" name="language" value="ru">ru</button>--%>
                    <%--<button type="submit" class="btn btn-outline-secondary" name="language" value="de">de</button>--%>
                    <%--<button type="submit" class="btn btn-outline-secondary" name="language" value="en">en</button>--%>
                <%--</div>--%>
            <%--</form>--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--</div>--%>
<%--</footer>--%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</html>