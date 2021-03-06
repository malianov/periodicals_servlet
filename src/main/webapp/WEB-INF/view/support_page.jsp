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
    <div class="container my-5">
        <div class="row p-8 pb-0 pe-lg-0 pt-lg-5 align-items-center rounded-3 border shadow-lg">
            <div class="col-10 col-sm-8 col-lg-6">
                <img src="${pageContext.request.contextPath}\resources\images\support.jpg"
                     class="d-block mx-lg-auto img-fluid" alt="Bootstrap Themes" width="700"
                     height="500" loading="lazy">
            </div>
            <div class="col-lg-6">
                <h1 class="display-6 fw-bold lh-1 mb-3"><fmt:message key="support.we-support-you-in-difficult-times"/></h1>
                <p class="lead"><fmt:message key="support.something-does-not-work"/><br><br>
                    <fmt:message key="support.would-you-like-to-unsubscribe-and-return-money"/>
                    <br><br><fmt:message key="support.nothing-is-suitable-for-you"/><br><br>
                    <fmt:message key="support.write-us-and-we-help-you"/><br><br>
                    <fmt:message key="support.we-need-a-job"/><br><br>
                    <fmt:message key="support.we-dont-like-relax"/><br><br>
                    <fmt:message key="support.we-want-to-work"/><br><br>
                    <fmt:message key="support.call-us"/><br>
                    <fmt:message key="support.write-us"/><br><br>
                    <br>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="container my-0 fixed-bottom">
    <footer>
        <div class="text-center text-white p-3 bg-primary">
            <fmt:message key="support.copyright"/>
            <a class="text-white" href="ma_igor@ukr.net"><fmt:message key="support.copyright"/></a>
        </div>
    </footer>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>