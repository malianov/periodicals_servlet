<header
        class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom border-primary">

    <nav class="navbar navbar-light border border-primary border-2 rounded-pill">
        <div class="container-fluid">
            <a class="navbar-brand fs-3 fw-bold" href="${pageContext.request.contextPath}/app/to_home_page">
                <img src="${pageContext.request.contextPath}/resources/images/logo.svg" alt="" width="50"
                     height="40" class="d-inline-block align-text">your lovely periodics
            </a>
        </div>
    </nav>
    <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0 fs-5 text-muted">
        <li><a href="${pageContext.request.contextPath}/app/to_home_page" class="nav-link px-2 link-primary">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/app/to_catalog_page" class="nav-link px-2 link-dark">Catalog</a></li>
        <li><a href="${pageContext.request.contextPath}/app/to_support_page" class="nav-link px-2 link-dark">Support</a></li>
    </ul>
    <div class="col-md-3 text-end">
        <button class="btn btn-primary me-3" type="button" id="dropdownMenuBtn1" data-bs-toggle="dropdown">
            English
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuBtn1">
            <li><a class="dropdown-item" href="#">English</a></li>
            <li><a class="dropdown-item" href="#">Українська</a></li>
            <li><a class="dropdown-item" href="#">Русский</a></li>
        </ul>
        <button type="button" class="btn btn-outline-primary me-3" data-bs-toggle="modal"
                data-bs-target="#modalLogin">Login
        </button>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                data-bs-target="#modalSignUp">Sign-up
        </button>
    </div>
</header>
<%--</div>--%>
<%--<div class="container">--%>
<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="modalHeaderFooterTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalHeaderFooterTitle">Login</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Please, enter your login information</p>
                <form method="GET" action="${pageContext.request.contextPath}/app/app/login">
                    <div class="row mb-3">
                        <label for="horizontalLogin" class="col-md-2 col-form-label">login</label>
                        <div class="col-md-10">
                            <input name="login" type="text" autofocus class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalPassword" class="col-md-2 col-form-label">Password</label>
                        <div class="col-md-10">
                            <input name="password" type="password" class="form-control">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">enter</button>
                        <button type="button" class="btn btn-outline-primary"
                                data-bs-dismiss="modal">cancel
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
                <h5 class="modal-title" id="modalHeaderFooterTitle">Registration</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Please, enter your registration information</p>
                <form method="GET" action="${pageContext.request.contextPath}/to_registration_page">
                    <div class="row mb-3">
                        <label for="horizontalLogin" class="col-md-2 col-form-label">login</label>
                        <div class="col-md-10">
                            <input name="login" type="text" autofocus type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalName" class="col-md-2 col-form-label">name</label>
                        <div class="col-md-10">
                            <input name="name" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalSurname" class="col-md-2 col-form-label">surname</label>
                        <div class="col-md-10">
                            <input name="surname" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalEmail" class="col-md-2 col-form-label">email</label>
                        <div class="col-md-10">
                            <input name="email" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalPassword" class="col-md-2 col-form-label">password</label>
                        <div class="col-md-10">
                            <input name="password" type="password" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="horizontalConfirmedPassword" class="col-md-2 col-form-label">confirm</label>
                        <div class="col-md-10">
                            <input name="confirmedPassword" type="password" class="form-control">
                        </div>
                    </div>
                    <input name="role" type="hidden" class="form-control" value="subscriber">
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">register</button>
                        <button type="button" class="btn btn-outline-primary"
                                data-bs-dismiss="modal">cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>