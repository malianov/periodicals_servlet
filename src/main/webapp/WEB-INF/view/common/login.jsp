<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css"/>
</head>
<body>
<div>
    <p>User Login</p>
</div>
<div>
    <form method="get" action="/app/login">

        <c:if test="${param.dataInvalid == true}">
            <p style="color: orange">invalid input</p>
        </c:if>

        <c:if test="${param.userExist == false}">
            <p style="color: darkred">user not found</p>
        </c:if>

        <input name="login" type="text" placeholder="Login" autofocus>
        <input name="password" type="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>
</div>

</body>
</html>
