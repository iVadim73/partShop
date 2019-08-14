<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <ftm:setLocale value="${language}" scope="session"/>
    <ftm:setBundle basename="text"/>
    <div class="header-panel bg-dark" style="height: 160px">
        <div class="row">
            <div class="col-2" align="center">
                <a href="/index.jsp"><img src="/picture/logo.jpg"></a>
            </div>
            <div class="col-8">
                <div class="btn-group btn-lg btn-block" role="group" aria-label="Basic example">
                    <a href="/menu/menu1.jsp" class="btn btn-outline-secondary"><fmt:message key="header.menu1"/></a>
                    <a href="/menu/menu2.jsp" class="btn btn-outline-secondary"><fmt:message key="header.menu2"/></a>
                    <a href="/menu/menu3.jsp" class="btn btn-outline-secondary"><fmt:message key="header.menu3"/></a>
                </div>
                <div class="row">
                    <c:choose>
                        <c:when test="${userType eq 'admin' || userType eq 'seller'}">
                            <div class="container">
                                <div class="row mr-auto">
                                    <div class="col-3">
                                        <form method="post" action="/controller">
                                            <input type="hidden" name="command" value="show_all_brand" />
                                            <button type="submit" class="btn btn-outline-warning btn-sm btn-block"><fmt:message key="showAllBrand"/></button>
                                        </form>
                                    </div>
                                    <div class="col-3">
                                        <form method="post" action="/controller">
                                            <input type="hidden" name="command" value="show_all_part" />
                                            <button type="submit" class="btn btn-outline-warning  btn-sm btn-block"><fmt:message key="showAllPart"/></button>
                                        </form>
                                    </div>
                                    <div class="col-3">
                                        <form method="post" action="/controller">
                                            <input type="hidden" name="command" value="show_all_user" />
                                            <button type="submit" class="btn btn-outline-warning btn-sm btn-block"><fmt:message key="showAllUser"/></button>
                                        </form>
                                    </div>
                                    <div class="col-3">
                                        <form method="post" action="/controller">
                                            <input type="hidden" name="command" value="show_all_order" />
                                            <button type="submit" class="btn btn-outline-warning btn-sm btn-block"><fmt:message key="showAllOrder"/></button>
                                        </form>
                                    </div>
                                </div>
                                <div class="row mr-auto">
                                    <div class="col-12">
                                        <form method="post" action="/controller">
                                            <div class="input-group">
                                                <input type="hidden" name="command" value="search_order" />
                                                <input class="form-control btn-sm" type="text" name="orderId" pattern="^\d{1,6}$" placeholder="<fmt:message key="orderId"/>" value="${orderId}"/>
                                                <button type="submit" class="btn btn-outline-warning btn-sm"><fmt:message key="findOrder"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-12">
                                <form action="/controller">
                                    <br/>
                                    <div class="input-group input-group-lg">
                                        <input type="hidden" name="command" value="search_part"/>
                                        <input class="form-control" type="text" name="partOfPartCatalogNo" pattern="^[\w\-)( ]{1,45}$" placeholder="<fmt:message key="catalogNo"/>" value="${partOfPartCatalogNo}"/>
                                        <button type="submit" class="btn btn-warning"><fmt:message key="searchPart"/></button>
                                    </div>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-2">
                <c:choose>
                    <c:when test="${currentLogin != null}">
                        <jsp:include page="/authorization/signout.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="/authorization/authorization.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</head>
<c:if test="${condition != null}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        ${condition}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>