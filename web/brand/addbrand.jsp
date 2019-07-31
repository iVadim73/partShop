<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/header.jsp"/>
<body>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="add_brand"/>
        <input type="text" name="name" pattern="^[\w\-)( ]{2,45}$" placeholder="name"><br/>
        <input type="text" name="country" pattern="^[\w\-)( ]{2,45}$" placeholder="country"><br/>
        <input type="text" name="info" pattern=".{0,300}" placeholder="info"><br/>
        <input type="checkbox" name="active" value="is active"> is active?<br/>
        <button type="submit">add</button>
    </form>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>