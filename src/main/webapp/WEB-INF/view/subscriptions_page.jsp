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

    <div class="nav justify-content-between fs-5 text-muted">

        <c:choose>
            <c:when test="${sessionScope.role eq 'SUBSCRIBER'}">
                <h4 class="h5 text-muted mt-1 mb-2"><fmt:message key="subscriptions.my-subscriptions"/></h4>
            </c:when>
            <c:otherwise>
                <h4 class="h5 text-muted mt-1 mb-2"><fmt:message key="subscriptions.current-subscriptions"/></h4>
            </c:otherwise>
        </c:choose>

        <form class="col-12 col-lg-4 mb-3 mt-mb-lg-0">
            <input type="search" class="form-control" placeholder="<fmt:message key="subscriptions.search-in-users"/>"
                   aria-label="Search" name="search_input">
        </form>
    </div>


    <table class="table table-hover table-bordered border-primary border border-2 text-muted">
        <thead class="border-primary border border-2 table-primary">
        <tr>
            <th class="text-center"><fmt:message key="subscriptions.id"/></th>
            <th class="text-center"><fmt:message key="subscriptions.subscription-date"/></th>
            <th class="text-center"><fmt:message key="subscriptions.subscriber-login"/></th>
            <th class="text-center"><fmt:message key="subscriptions.periodic-title"/></th>
            <th class="text-center"><fmt:message key="subscriptions.periodic-year"/></th>
            <th class="text-center"><fmt:message key="subscriptions.periodic-item"/></th>
            <th class="text-center"><fmt:message key="subscriptions.paid"/></th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${subscriptions}" var="all_subscriptions_list">
            <tr>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getId()}"/></td>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getDate()}"/></td>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getUser().getLogin()}"/></td>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getPeriodical().getTitle()}"/></td>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getPeriodicYear()}"/></td>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getPeriodicItem()}"/></td>
                    <td class="align-middle"><c:out value="${all_subscriptions_list.getItemPrice()}"/></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>

<div class="container my-0 fixed-bottom">
    <nav>
        <ul class="pagination justify-content-center ">

            <c:if test="${currentPage != 1}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_subscriptions_page?search_input=${searchInput}&current_page=${currentPage - 1}"
                        class="page-link border-primary"><fmt:message key="pagination.previous"/></a></li>
            </c:if>
            <c:if test="${currentPage == 1}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_subscriptions_page?search_input=${searchInput}&current_page=${currentPage}"
                        class="page-link border-secondary text-secondary"><fmt:message key="pagination.previous"/></a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${nuOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/app/to_subscriptions_page?search_input=${searchInput}&current_page=${i}"
                                class="page-link border-primary bg-primary text-white">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/app/to_subscriptions_page?search_input=${searchInput}&current_page=${i}"
                                class="page-link border-primary">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt nuOfPages}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_subscriptions_page?search_input=${searchInput}&current_page=${currentPage + 1}"
                        class="page-link border-primary"><fmt:message key="pagination.next"/></a></li>
            </c:if>
            <c:if test="${currentPage == nuOfPages}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_subscriptions_page?search_input=${searchInput}&current_page=${currentPage}"
                        class="page-link border-secondary text-secondary"><fmt:message key="pagination.next"/></a></li>
            </c:if>


        </ul>
    </nav>

    <footer>
        <div class="text-center text-white p-3 bg-primary">
            <fmt:message key="footer.copyright"/>
            <a class="text-white" href="ma_igor@ukr.net"><fmt:message key="footer.malianov-igor"/></a>
        </div>
    </footer>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>