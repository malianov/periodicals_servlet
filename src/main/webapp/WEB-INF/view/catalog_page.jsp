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
            <c:choose>
                <c:when test="${sessionScope.role ne 'GUEST'}">
                    <th scope="col"><fmt:message key="catalog.action"/></th>
                </c:when>
            </c:choose>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${periodicals}" var="all_periodics_list">
            <c:choose>
                <c:when test="${all_periodics_list.getPeriodicalStatus() eq 'NONORDERABLE'}">
                    <tr class="table-active">
                </c:when>
                <c:otherwise>
                    <tr>
                </c:otherwise>
            </c:choose>
            <td><c:out value="${all_periodics_list.getId()}"/></td>
            <td><c:out value="${all_periodics_list.getTitle()}"/></td>
            <td><c:out value="${all_periodics_list.getTheme()}"/></td>
            <td><c:out value="${all_periodics_list.getPeriodicalType()}"/></td>
            <td><c:out value="${all_periodics_list.getPricePerItem()}"/></td>
            <c:choose>
                <c:when test="${sessionScope.role ne 'GUEST'}">
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.role eq 'ADMIN'}">
                                <div class="d-flex">
                                    <button type="button" class="btn btn-outline-primary me-3 btn-sm flex-fill"
                                            data-bs-toggle="modal"
                                            data-bs-target="#modalInformation_${all_periodics_list.getId()}"><fmt:message key="catalog.edit"/>
                                    </button>
                                    <c:choose>
                                        <c:when test="${all_periodics_list.getPeriodicalStatus() eq 'NONORDERABLE'}">
                                            <form method="get" action="/app/app/to_make_order_nonorder_periodic">
                                                <input name="periodic_id" type="hidden"
                                                       value="${all_periodics_list.getId()}">
                                                <input name="periodic_status" type="hidden" value="make_orderable">
                                                <button class="btn btn-sm btn-outline-success me-3 btn-sm flex-fill"
                                                        type="submit"><fmt:message key="catalog.make-orderable"/>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${all_periodics_list.getPeriodicalStatus() eq 'ORDERABLE'}">
                                            <form method="get" action="/app/app/to_make_order_nonorder_periodic">
                                                <input name="periodic_id" type="hidden"
                                                       value="${all_periodics_list.getId()}">
                                                <input name="periodic_status" type="hidden" value="make_nonorderable">
                                                <button class="btn btn-sm btn-outline-danger me-3" type="submit"><fmt:message key="catalog.make-non-orderable"/>
                                                </button>
                                            </form>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="modal fade" id="modalInformation_${all_periodics_list.getId()}"
                                     tabindex="-1" role="dialog"
                                     aria-hidden="true" data-bs-backdrop="static">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content border border-primary border-3">
                                            <div class="modal-header">
                                                <h5 class="modal-title fs-5 fw-bold text-center">
                                                    <fmt:message key="catalog.periodic-information"/></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form method="GET"
                                                      action="${pageContext.request.contextPath}/app/to_edit_periodic">
                                                    <div class="mb-3">
                                                        <label class="form-label"><fmt:message key="catalog.title-per"/></label>
                                                        <input type="text" class="form-control btn-outline-primary"
                                                               name="new_title">
                                                        <input name="id" type="hidden"
                                                               value="${all_periodics_list.getId()}">
                                                        <div class="form-text"><c:out
                                                                value="${all_periodics_list.getTitle()}"/></div>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="message-text"
                                                               class="col-form-label"><fmt:message key="catalog.form-descriprion"/></label>
                                                        <textarea class="form-control btn-outline-primary"
                                                                  id="message-text" name="new_description"></textarea>
                                                        <div class="form-text">"<c:out
                                                                value="${all_periodics_list.getDescription()}"></c:out></div>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label"><fmt:message key="catalog.price-per-item"/></label>
                                                        <input type="text" class="form-control btn-outline-primary"
                                                               name="new_price">
                                                        <div class="form-text">
                                                            <c:out value="${all_periodics_list.getPricePerItem()}"></c:out>
                                                        </div>
                                                    </div>
                                                    <div class="d-grid gap-2">
                                                        <button class="btn btn-primary" type="submit"><fmt:message key="confirm"/></button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.role eq 'SUBSCRIBER'}">
                                <form method="post" action="/app/app/to_order_periodic">
                                    <input name="periodic_id" type="hidden" value="${all_periodics_list.getId()}">
                                    <c:choose>
                                        <c:when test="${all_periodics_list.getPeriodicalStatus() eq 'NONORDERABLE'}">
                                            <button type="button" class="btn btn-outline-danger btn-sm me-3"
                                                    data-bs-toggle="modal" data-bs-target="#modalNonOrder"><fmt:message key="catalog.nonorderable"/>
                                            </button>
                                        </c:when>
                                        <c:when test="${all_periodics_list.getPeriodicalStatus() eq 'ORDERABLE'}">
                                            <button type="button" class="btn btn-outline-primary btn-sm me-3"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#modalOrder_${all_periodics_list.getId()}"><fmt:message key="catalog.order"/>
                                            </button>
                                            <div class="container">
                                                <div class="modal fade" id="modalOrder_${all_periodics_list.getId()}"
                                                     tabindex="-1" role="dialog"
                                                     aria-hidden="true"
                                                     data-bs-backdrop="static">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content border border-primary border-3">
                                                            <div class="modal-header text-center">
                                                                <p class="modal-title fs-6 fw-bold fst-italic"><fmt:message key="catalog.who-reads-a-lot-that-knows-a-lot"/>
                                                                    </p>
                                                                <button type="button" class="btn-close"
                                                                        data-bs-dismiss="modal"
                                                                        aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form method="post" action="/app/app/to_order_periodic">
                                                                    <div class="mb-3">
                                                                        <article class="blog-post">
                                                                            <h2 class="blog-post-title"><fmt:message key="catalog.purchase-order"/></h2>
                                                                            <p class="blog-post-meta">
                                                                                    <%--Date: <span id="date"></span>
                                                                                    Time: <span id="time"></span>--%>
                                                                                        <fmt:message key="catalog.created-by"/> ${sessionScope.login}
                                                                            </p>
                                                                            <hr>
                                                                            <h5 class="fst-italic">
                                                                                <strong><fmt:message key="catalog.title-per"/></strong></h5>
                                                                            <p>${all_periodics_list.getTitle()}</p>
                                                                            <hr>
                                                                            <h5 class="fst-italic">
                                                                                <strong><fmt:message key="catalog.description"/></strong></h5>
                                                                            <p>${all_periodics_list.getDescription()}</p>
                                                                            <hr>
                                                                            <h5 class="fst-italic"><strong><fmt:message key="catalog.price-per-item"/></strong>
                                                                            </h5>
                                                                            <p>${all_periodics_list.getPricePerItem()}</p>
                                                                            <hr>
                                                                            <h5 class="fst-italic"><strong><fmt:message key="catalog.months-of-subscription"/></strong>
                                                                            </h5>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox1"
                                                                                       value="item_1" disabled>
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox1"><fmt:message key="catalog.jan"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox2"
                                                                                       value="item_2" disabled>
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox2"><fmt:message key="catalog.feb"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox3"
                                                                                       value="item_3" disabled>
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox3"><fmt:message key="catalog.mar"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox1"
                                                                                       value="item_4" disabled>
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox1"><fmt:message key="catalog.apr"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox2"
                                                                                       value="item_5" disabled>
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox2"><fmt:message key="catalog.may"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox3"
                                                                                       value="item_6" disabled>
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox3"><fmt:message key="catalog.jun"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input btn-outline-primary"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox1"
                                                                                       value="item_7">
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox1"><fmt:message key="catalog.jul"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input btn-outline-primary"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox2"
                                                                                       value="item_8">
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox2"><fmt:message key="catalog.aug"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input btn-outline-primary"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox3"
                                                                                       value="item_9">
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox3"><fmt:message key="catalog.sep"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input btn-outline-primary"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox1"
                                                                                       value="item_10">
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox1"><fmt:message key="catalog.oct"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input btn-outline-primary"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox2"
                                                                                       value="item_11">
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox2"><fmt:message key="catalog.nov"/></label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input btn-outline-primary"
                                                                                       type="checkbox" name="selected"
                                                                                       id="inlineCheckbox3"
                                                                                       value="item_12">
                                                                                <label class="form-check-label"
                                                                                       for="inlineCheckbox3"><fmt:message key="catalog.dec"/></label>
                                                                            </div>
                                                                            <hr>
                                                                            <h5 class="fst-italic">
                                                                                <strong><fmt:message key="catalog.address"/></strong></h5>
                                                                            <p><textarea
                                                                                    class="form-control btn-outline-primary"
                                                                                    id="message-text"
                                                                                    name="address"></textarea>
                                                                            </p>
                                                                            <input name="subscription_year"
                                                                                   type="hidden" value="2021">
                                                                        </article>
                                                                        <hr>
                                                                        <div class="d-grid gap-2">
                                                                            <button class="btn btn-primary"
                                                                                    type="submit"><fmt:message key="catalog.confirm-order"/>
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </form>
                            </c:when>
                        </c:choose>
                    </td>
                </c:when>
            </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="container my-0 fixed-bottom">
    <nav>
        <ul class="pagination justify-content-center ">
            <c:if test="${current_page != 1}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${search_input}&current_page=${current_page - 1}"
                        class="page-link border-primary"><fmt:message key="pagination.previous"/></a></li>
            </c:if>
            <c:if test="${current_page == 1}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${search_input}&current_page=${current_page}"
                        class="page-link border-secondary text-secondary"><fmt:message key="pagination.previous"/></a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${nu_of_pages}" var="i">
                <c:choose>
                    <c:when test="${current_page eq i}">
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${search_input}&current_page=${i}"
                                class="page-link border-primary bg-primary text-white">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${search_input}&current_page=${i}"
                                class="page-link border-primary">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${current_page lt nu_of_pages}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${search_input}&current_page=${current_page + 1}"
                        class="page-link border-primary"><fmt:message key="pagination.next"/></a></li>
            </c:if>
            <c:if test="${current_page == nu_of_pages}">
                <li class="page-item"><a
                        href="${pageContext.request.contextPath}/app/to_catalog_page?search_input=${search_input}&current_page=${current_page}"
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
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>