<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>
    <p>--------------------------------------------------</p>
    we are here
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="set_language"/>
        <button type="submit" name="language" value="ru">ru</button>
        <button type="submit" name="language" value="de">de</button>
        <button type="submit" name="language" value="en">en</button>
    </form>
</footer>
