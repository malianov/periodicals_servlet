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

    <div class="container my-5 text-center border border-danger bg-light">
        <div class="row p-8 pb-0 pe-lg-0 pt-lg-5 align-items-center rounded-3 border shadow-lg">
            <div class="px-4 py-5 my-5 text-center">
                <h1 class="display-5 fs-3 text-danger"><fmt:message key="error_message.there-is-a-problem-with-your-information"/></h1>
                <div class="container"><br><br>
                    <c:forEach var="error" items="${errorMessages}">
                        <div class="fs-4 text-danger"><p>✔  <fmt:message key="${error.getValue()}"/></p></div>
                    </c:forEach>
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