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
        <h4 class="h5 text-muted mt-1 mb-2"><fmt:message key="catalog.our-periodic-catalog"/></h4>


        <form class="col-12 col-lg-4 mb-3 mt-mb-lg-0">
            <input type="search" class="form-control" placeholder="<fmt:message key="catalog.search-by-periodic-name"/>"
                   aria-label="Search" name="search_input">
        </form>
    </div>

    <table class="table table-hover table-bordered border-primary border border-2 text-muted">
        <thead class="border-primary border border-2 table-primary">
        <tr>
            <th class="scope=" scope="col"><fmt:message key="catalog.id"/></th>
            <th scope="col"><a href="#" class="text-decoration-none"><fmt:message key="catalog.periodic-name"/></a>
            </th>
            <th class="bg-able" scope="col">
                <div class="drop-items">
                    <a class="text-decoration-none dropdown-toggle" data-bs-toggle="dropdown"><fmt:message
                            key="catalog.theme"/></a>
                    <div class="dropdown-menu">
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.auto-moto-yachts"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message
                                    key="catalog.for-children-and-parents"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.leisure-relaxation"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.women"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.film-and-television"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.economy-business-money"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.cooking"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.medicine-health"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.society-politics"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.sport"/></label>
                        </div>
                        <div class="btn-menu d-grid gap-2 pe-2">
                            <button type="button" class="btn btn-primary"><fmt:message key="catalog.apply"/></button>
                        </div>
                    </div>
                </div>
            </th>
            <th class="bg-able" scope="col">
                <div class="drop-items">
                    <a class="text-decoration-none dropdown-toggle" data-bs-toggle="dropdown"><fmt:message
                            key="catalog.type"/></a>
                    <div class="dropdown-menu">
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.magazine"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.newspaper"/></label>
                        </div>
                        <div class="form-check form-check">
                            <input class="form-check-input" type="checkbox" value="option1">
                            <label class="form-check-label"><fmt:message key="catalog.electronic"/></label>
                        </div>
                        <div class="btn-menu d-grid gap-2 pe-2">
                            <button type="button" class="btn btn-primary"><fmt:message key="catalog.apply"/></button>
                        </div>
                    </div>
                </div>
            </th>

            <th scope="col"><a href=# class="text-decoration-none"><fmt:message key="catalog.price"/></a></th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${periodicals}" var="all_periodics_list">

            <c:choose>
                <c:when test="${all_periodics_list.getPeriodicalStatus() eq 'NONORDERABLE'}">
                    <tr class="table-active">
                </c:when>
                <c:otherwise>
                    <tr onclick="input" data-toggle="modal" href="#the name for my modal windows" >
                </c:otherwise>
            </c:choose>

            <td><c:out value="${all_periodics_list.getId()}"/></td>
            <td><c:out value="${all_periodics_list.getTitle()}"/></td>
            <td><c:out value="${all_periodics_list.getTheme()}"/></td>
            <td><c:out value="${all_periodics_list.getPeriodicalType()}"/></td>
            <td><c:out value="${all_periodics_list.getPricePerItem()}"/></td>

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