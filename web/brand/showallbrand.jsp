<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <div style="height: 76%; background-image: url(/picture/index.jpg); background-size: cover">
        <div class="row" style="background-color: #b9bbbe" align="center">
            <div class="col-2">
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="to_add_brand_form"/>
                    <button class="btn btn-warning" type="submit"><fmt:message key="add"/></button>
                </form>
            </div>
            <div class="col-4">
                <form action="/controller">
                    <div class="input-group input-group-md">
                        <input type="hidden" name="command" value="search_brand"/>
                        <input class="form-control" type="text" name="partOfBrandName" pattern="^[\w\-)( ]{1,45}$" placeholder="<fmt:message key="brand"/>">
                        <button class="btn btn-warning" type="submit"><fmt:message key="search"/></button>
                    </div>
                </form>
            </div>
        </div>
        <c:choose>
            <c:when test="${brands.isEmpty()}">
                <fmt:message key="noBrand"/>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover table-sm" style="background-color: #b9bbbe">
                    <caption style="background-color: #0f6674">
                        <div class="row" style="height: 20px">
                            <div class="col">Список производителей</div>
                            <div class="col"><lt:pageList pageCount="${pageCount}" elementCount="${brands.size()}" command="show_all_brand"/></div>
                        </div>
                    </caption>
                    <thead>
                    <tr class="table-info">
                        <th scope="col"><fmt:message key="name"/></th>
                        <th scope="col"><fmt:message key="country"/></th>
                        <th scope="col"><fmt:message key="info"/></th>
                        <th scope="col"><fmt:message key="isActive"/></th>
                        <th scope="col" colspan="2"></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${brands}" var="brand">
                        <tr>
                            <td>${brand.name}</td>
                            <td>${brand.country}</td>
                            <td>${brand.info}</td>
                            <td>${brand.isActive}</td>
                            <td>
                                <form method="post" action="/controller">
                                    <input type="hidden" name="command" value="to_update_brand_form"/>
                                    <input type="hidden" name="brandId" value="${brand.brandId}"/>
                                    <button type="submit"><fmt:message key="change"/></button>
                                </form>
                            </td>
                            <td>
                                <form method="post" action="/controller">
                                    <input type="hidden" name="command" value="activate_deactivate_brand"/>
                                    <input type="hidden" name="brandId" value="${brand.brandId}">
                                    <button type="submit"><fmt:message key="activate/deactivate"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br/>
            </c:otherwise>
        </c:choose>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
<jsp:include page="/common/footer.jsp"/>