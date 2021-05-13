<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<%--<!DOCTYPE html>--%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>periodics</title>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/all.min.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
</head>

<body>
<div class="container">

    <jsp:include page="navbar.jsp"/>

    <div class="nav justify-content-between fs-5 text-muted">
        <h4 class="h5 text-muted mt-1 mb-2">Our periodics catalog</h4>
        <form class="col-12 col-lg-4 mb-3 mt-mb-lg-0">
            <input type="search" class="form-control" placeholder="Search by periodics name..." aria-label="Search">
        </form>
    </div>
    <table class="table table-hover table-bordered border-primary border border-2 text-muted">
        <thead class="border-primary border border-2 table-primary">
        <tr>
            <th class="scope=" scope="col">id</th>
            <th scope="col"><a href=# class="text-decoration-none">Periodics ▴▾</a></th>
            <%--                    <i class="fas fa-sort"></i></a></th>--%>
            <th class="bg-able" scope="col">
                <div class="drop-items">
                    <a class="text-decoration-none dropdown-toggle" id="dropdownMenuBtn10"
                       data-bs-toggle="dropdown">theme</a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuBtn10">
                        <div class="total">
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">all items</label>
                            </div>
                        </div>
                        <div class="all-items">
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">healthy</label>
                            </div>
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">healthy1</label>
                            </div>
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">healthy2</label>
                            </div>
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">healthy3</label>
                            </div>
                        </div>
                        <div class="btn-menu d-grid gap-2 pe-2">
                            <button type="button" class="btn btn-primary">применить</button>
                        </div>
                    </div>
                </div>
                </ul>
            </th>
            <th class="bg-able" scope="col">
                <div class="drop-items">
                    <a class="text-decoration-none dropdown-toggle" id="dropdownMenuBtn10"
                       data-bs-toggle="dropdown">type
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuBtn10">
                        <div class="total">
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">all items</label>
                            </div>
                        </div>
                        <div class="all-items">
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">magazines</label>
                            </div>
                            <div class="form-check form-check">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                                       value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">newspapers</label>
                            </div>
                        </div>
                        <div class="btn-menu d-grid gap-2 pe-2">
                            <button type="button" class="btn btn-primary">применить</button>
                        </div>
                    </div>
                </div>
                </ul>
            </th>
            <th scope="col">date added</th>
            <th scope="col"><a href=# class="text-decoration-none">price, ₴ ▴▾</a></th>
            <%--                    <i class="fas fa-hryvnia"></i> <i class="fas fa-sort"></i></a></th>--%>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>England</td>
            <td>English</td>
            <td>London</td>
            <td>England</td>
            <td>123.23</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>France</td>
            <td>French</td>
            <td>Paris</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>France</td>
            <td>French</td>
            <td>Paris</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>France</td>
            <td>French</td>
            <td>Paris</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>France</td>
            <td>French</td>
            <td>Paris</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>France</td>
            <td>French</td>
            <td>Paris</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Germany</td>
            <td>German</td>
            <td>Berlin</td>
            <td>England</td>
            <td>English</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="container my-0 fixed-bottom">
    <nav>
        <ul class="pagination justify-content-center ">
            <li class="page-item"><a href="#" class="page-link border-primary">Previous</a></li>
            <li class="page-item"><a href="#" class="page-link border-primary">1</a></li>
            <li class="page-item"><a href="#" class="page-link border-primary">2</a></li>
            <li class="page-item"><a href="#" class="page-link border-primary">3</a></li>
            <li class="page-item"><a href="#" class="page-link border-primary">Next</a></li>
        </ul>
    </nav>
    <footer>
        <div class="text-center text-white p-3 bg-primary">
            © 2021 Copyright:
            <a class="text-white" href="ma_igor@ukr.net">Malianov Igor</a>
        </div>
    </footer>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>