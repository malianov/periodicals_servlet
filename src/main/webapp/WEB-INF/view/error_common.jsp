<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : \"en\"}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<jsp:include page="header.jsp"/>

<body>
<div class="container">

    <jsp:include page="navbar.jsp"/>

    <div class="container my-5 text-center">
        <div class="row p-8 pb-0 pe-lg-0 pt-lg-5 align-items-center rounded-3 border shadow-lg">
            <div class="px-4 py-5 my-5 text-center">
                <h1 class="display-5 fw-bold">The page is not find...</h1>
                <div class="col-lg-6 mx-auto">
                    <p class="lead mb-4">You are here, because the required page does not exists or was removed to another address or you have no enought rights to read it.</p>
                    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/app/to_home_page"><fmt:message key="home"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="container my-0 fixed-bottom">
    <footer>
        <div class="text-center text-white p-3 bg-primary">
            <fmt:message key="support.copyright"/>
            <a class="text-white" href="ma_igor@ukr.net"><fmt:message key="support.malianov-igor"/></a>
        </div>
    </footer>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>