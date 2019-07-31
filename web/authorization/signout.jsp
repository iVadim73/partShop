<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <table>
            <tr>
                <td>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_all_cart" />
                        <button type="submit">cart</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_user" />
                        <button type="submit">${userLogin}</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_all_wish_list" />
                        <button type="submit">wish list</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="signout" />
                        <button type="submit">sign out</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_all_order" />
                        <button type="submit">orders</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="show_all_bill" />
                        <button type="submit">bill</button>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
