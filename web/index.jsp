<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="/common/header.jsp"/>
    <body>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="show_all_brand" />
        <button type="submit">showallbrand</button>
    </form>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="show_all_part" />
        <button type="submit">showallpart</button>
    </form>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="show_all_user" />
        <button type="submit">showalluser</button>
    </form>
    </body>
    <jsp:include page="/common/footer.jsp"/>
</html>
