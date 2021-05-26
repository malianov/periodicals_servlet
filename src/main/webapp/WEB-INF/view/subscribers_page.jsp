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
        <h4 class="h5 text-muted mt-1 mb-2"><fmt:message key="subscribers.our-subscribers"/></h4>


        <form class="col-12 col-lg-4 mb-3 mt-mb-lg-0">
            <input type="search" class="form-control" placeholder="<fmt:message key="subscribers.search-in-any-place"/>"
                   aria-label="Search" name="search_input">
        </form>
    </div>


    <table class="table table-hover table-bordered border-primary border border-2 text-muted">
        <thead class="border-primary border border-2 table-primary">
        <tr>
            <th class="text-center"><fmt:message key="subscribers.id"/></th>
            <th class="text-center"><fmt:message key="subscribers.login"/></th>
            <th class="text-center"><fmt:message key="subscribers.name"/></th>
            <th class="text-center"><fmt:message key="subscribers.surname"/></th>
            <th class="text-center"><fmt:message key="subscribers.email"/></th>
            <th class="text-center"><fmt:message key="subscribers.personal-account"/></th>
            <th class="text-center"><fmt:message key="subscribers.registration-date"/></th>
            <th class="text-center"><fmt:message key="subscribers.action"/></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${subscribers}" var="all_subscribers_list">
            <c:choose>
                <c:when test="${all_subscribers_list.getUserStatus() eq 'BLOCKED'}">
                    <tr class="<%--text-decoration-line-through--%> text-danger zero-five-padding">
                </c:when>
                <c:otherwise>
                    <tr>
                </c:otherwise>
            </c:choose>
            <td class="align-middle text-center"><c:out value="${all_subscribers_list.getId()}"/></td>
            <td class="align-middle"><c:out value="${all_subscribers_list.getLogin()}"/></td>
            <td class="align-middle"><c:out value="${all_subscribers_list.getPerson().getName()}"/></td>
            <td class="align-middle"><c:out value="${all_subscribers_list.getPerson().getSurname()}"/></td>
            <td class="align-middle"><c:out value="${all_subscribers_list.getPerson().getEmail()}"/></td>
            <td class="align-middle text-center"><c:out value="${all_subscribers_list.getId()}"/></td>
            <td class="align-middle text-center">22.06.2019</td>
            <td class="text-center">
                <c:choose>
                    <c:when test="${all_subscribers_list.getUserStatus() eq 'BLOCKED'}">
                        <form method="get" action="/app/app/to_block_unblock_user">
                            <input name="user_login" type="hidden" value="${all_subscribers_list.getLogin()}">
                            <input name="status" type="hidden" value="unblock">
                            <button class="btn btn-sm btn-primary" type="submit">Unblock</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="get" action="/app/app/to_block_unblock_user">
                            <input name="user_login" type="hidden" value="${all_subscribers_list.getLogin()}">
                            <input name="status" type="hidden" value="block">
                            <button class="btn btn-sm btn-primary" type="submit">Block</button>
                        </form>
                    </c:otherwise>
                </c:choose>

            </td>

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
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${searchInput}&current_page=${currentPage - 1}"
                        class="page-link border-primary"><fmt:message key="pagination.previous"/></a></li>
            </c:if>
            <c:if test="${currentPage == 1}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${searchInput}&current_page=${currentPage}"
                        class="page-link border-secondary text-secondary"><fmt:message key="pagination.previous"/></a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${nuOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${searchInput}&current_page=${i}"
                                class="page-link border-primary bg-primary text-white">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${searchInput}&current_page=${i}"
                                class="page-link border-primary">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt nuOfPages}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${searchInput}&current_page=${currentPage + 1}"
                        class="page-link border-primary"><fmt:message key="pagination.next"/></a></li>
            </c:if>
            <c:if test="${currentPage == nuOfPages}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${searchInput}&current_page=${currentPage}"
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