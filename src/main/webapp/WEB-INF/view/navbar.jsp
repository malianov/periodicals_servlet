<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : \"en\"}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom border-primary">
    <nav class="navbar navbar-light border border-primary border-2 rounded-pill">
        <div class="container-fluid">
            <a class="navbar-brand fs-3 fw-bold" href="${pageContext.request.contextPath}/app/to_home_page">
                <img src="${pageContext.request.contextPath}/resources/images/logo.svg" alt="" width="50"
                     height="40" class="d-inline-block align-text">
                <c:choose>
                    <c:when test="${not empty sessionScope.login}">
                        <fmt:message key="good-day-user"/>${sessionScope.login}
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="your-lovely-periodic"/>
                    </c:otherwise>
                </c:choose>
            </a>
        </div>
    </nav>
    <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0 fs-5 text-muted">
        <c:choose>
            <c:when test="${page eq 'home'}">
                <li><a href="${pageContext.request.contextPath}/app/to_home_page" class="nav-link px-2 link-primary"><fmt:message key="home"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/app/to_home_page" class="nav-link px-2 link-dark"><fmt:message key="home"/></a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${page eq 'catalog'}">
                <li><a href="${pageContext.request.contextPath}/app/to_catalog_page?current_page=1" class="nav-link px-2 link-primary"><fmt:message key="catalog"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/app/to_catalog_page?current_page=1" class="nav-link px-2 link-dark"><fmt:message key="catalog"/></a></li>
            </c:otherwise>
        </c:choose>
        <c:if test="${sessionScope.role eq 'ADMIN'}">
            <c:choose>
                <c:when test="${page eq 'subscriptions'}">
                    <li><a href="${pageContext.request.contextPath}/app/to_subscriptions_page?current_page=1" class="nav-link px-2 link-primary"><fmt:message key="catalog.subscriptions"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/app/to_subscriptions_page?current_page=1" class="nav-link px-2 link-dark"><fmt:message key="catalog.subscriptions"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${sessionScope.role eq 'SUBSCRIBER'}">
            <c:choose>
                <c:when test="${page eq 'my_subscriptions'}">
                    <li><a href="${pageContext.request.contextPath}/app/to_my_subscriptions_page" class="nav-link px-2 link-primary"><fmt:message key="catalog.my-subscriptions"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/app/to_my_subscriptions_page" class="nav-link px-2 link-dark"><fmt:message key="catalog.my-subscriptions"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:choose>
            <c:when test="${sessionScope.role eq 'ADMIN'}">
                <c:choose>
                    <c:when test="${page eq 'subscribers'}">
                        <li><a href="${pageContext.request.contextPath}/app/to_subscribers_page?current_page=1" class="nav-link px-2 link-primary"><fmt:message key="subscribers"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/app/to_subscribers_page?current_page=1" class="nav-link px-2 link-dark"><fmt:message key="subscribers"/></a></li>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${page eq 'support'}">
                        <li><a href="${pageContext.request.contextPath}/app/to_support_page" class="nav-link px-2 link-primary"><fmt:message key="support"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/app/to_support_page" class="nav-link px-2 link-dark"><fmt:message key="support"/></a></li>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
















<%--        <c:choose>
            <c:when test="${page eq 'support'}">
                <li><a href="${pageContext.request.contextPath}/app/to_support_page" class="nav-link px-2 link-primary"><fmt:message key="support"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/app/to_support_page" class="nav-link px-2 link-dark"><fmt:message key="support"/></a></li>
            </c:otherwise>
        </c:choose>--%>





    </ul>
    <div class="col-md-4 text-end">


        <button class="btn btn-primary me-3" type="button" id="dropdownMenuBtn1" data-bs-toggle="dropdown">
            <c:choose>
                <c:when test="${language eq 'ua'}">
                    <fmt:message key="language.ukrainian"/>
                </c:when>
                <c:when test="${language eq 'ru'}">
                    <fmt:message key="language.russian"/>
                </c:when>
                <c:when test="${language eq 'en'}">
                    <fmt:message key="language.english"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="language.english"/>
                </c:otherwise>
            </c:choose>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuBtn1">
            <li><a class="dropdown-item" href="?language=en&search_input=${searchInput}&current_page=${currentPage}"><fmt:message key="language.english"/></a></li>
            <li><a class="dropdown-item" href="?language=ua&search_input=${searchInput}&current_page=${currentPage}"><fmt:message key="language.ukrainian"/></a></li>
            <li><a class="dropdown-item" href="?language=ru&search_input=${searchInput}&current_page=${currentPage}"><fmt:message key="language.russian"/></a></li>
        </ul>

        <c:choose>
            <c:when test="${not empty sessionScope.login}">
                <a href="${pageContext.request.contextPath}/app/logout" class="btn btn-outline-primary me-3"><fmt:message key="logout"/></a>
            </c:when>
            <c:otherwise>
                <button type="button" class="btn btn-outline-primary me-3" data-bs-toggle="modal" data-bs-target="#modalLogin">
                    <fmt:message key="login"/>
                </button>
            </c:otherwise>
        </c:choose>

<%--        <c:if test="${empty sessionScope.login}">--%>
<%--            <button type="button" class="btn btn-primary" data-bs-toggle="modal"--%>
<%--                    data-bs-target="#modalSignUp"><fmt:message key="sign-up"/>--%>
<%--            </button>--%>
<%--        </c:if>--%>

        <c:choose>
            <c:when test="${empty sessionScope.login}">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#modalSignUp"><fmt:message key="sign-up"/>
                </button>
            </c:when>
            <c:when test="${!empty sessionScope.login && sessionScope.role eq 'SUBSCRIBER'}">
                <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                        data-bs-target="#modalPersonalAccount">Ваш баланс: ${sessionScope.subscriberBalance} ₴
                </button>
            </c:when>
        </c:choose>
    </div>
</header>



<div class="modal fade" id="modalPersonalAccount" tabindex="-1" role="dialog" aria-hidden="true"
     data-bs-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content border border-warning border-3">
            <div class="modal-header">
                <p class="modal-title fs-5 fw-bold text-center">
                    "В газетах нет ни слова правды. Потому-то их и читают."</p>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form  method="get" action="/app/app/to_increase_balance">
                    <div class="mb-3">
                        <article class="blog-post">
                            <h2 class="blog-post-title">Private account</h2>
                            <p class="blog-post-meta">
                                For ${sessionScope.login},
                                Date: <span id="date"></span>
                                Time: <span id="time"></span>
                            </p>
                            <hr>

                            <h5 class="fst-italic"><strong>You have already:</strong></h5>
                            <p>${sessionScope.subscriberBalance} ₴</p>
                            <h5 class="fst-italic"><strong>How much money you would like to add?</strong></h5>
                            <div class="mb-3">
                                <input name="addition_to_balance" type="text" class="form-control" placeholder="Enter your money here">
                            </div>
                        </article>
                        <hr>
                        <div class="d-grid gap-2">
                            <button class="btn btn-warning" type="submit">Confirm addition</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>



<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="modalHeaderFooterTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalHeaderFooterTitle"><fmt:message key="login"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><fmt:message key="please-enter-your-login-information"/></p>
                <form method="GET" action="${pageContext.request.contextPath}/app/app/login">
                    <div class="row mb-3">
                        <label for="horizontalLogin" class="col-md-2 col-form-label"><fmt:message key="login"/></label>
                        <div class="col-md-10">
                            <input name="login" type="text" autofocus class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalPassword" class="col-md-2 col-form-label"><fmt:message
                                key="password"/></label>
                        <div class="col-md-10">
                            <input name="password" type="password" class="form-control">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="enter"/></button>
                        <button type="button" class="btn btn-outline-primary"
                                data-bs-dismiss="modal"><fmt:message key="cancel"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modalSignUp" tabindex="-1" role="dialog" aria-labelledby="modalHeaderFooterTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalHeaderFooterTitle"><fmt:message key="registration"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><fmt:message key="please-enter-your-registration-information"/></p>
                <form method="GET" action="${pageContext.request.contextPath}/app/app/registration">
                    <div class="row mb-3">
                        <label for="horizontalLogin" class="col-md-2 col-form-label"><fmt:message key="login"/></label>
                        <div class="col-md-10">
                            <input name="login" type="text" autofocus type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalName" class="col-md-2 col-form-label"><fmt:message key="name"/></label>
                        <div class="col-md-10">
                            <input name="name" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalSurname" class="col-md-2 col-form-label"><fmt:message
                                key="surname"/></label>
                        <div class="col-md-10">
                            <input name="surname" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalEmail" class="col-md-2 col-form-label"><fmt:message key="email"/></label>
                        <div class="col-md-10">
                            <input name="email" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalPassword" class="col-md-2 col-form-label"><fmt:message
                                key="password"/></label>
                        <div class="col-md-10">
                            <input name="password" type="password" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalConfirmedPassword" class="col-md-2 col-form-label"><fmt:message
                                key="confirm"/></label>
                        <div class="col-md-10">
                            <input name="confirmedPassword" type="password" class="form-control">
                        </div>
                    </div>
                    <input name="role" type="hidden" class="form-control" value="subscriber">
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="register"/></button>
                        <button type="button" class="btn btn-outline-primary"
                                data-bs-dismiss="modal"><fmt:message key="cancel"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>