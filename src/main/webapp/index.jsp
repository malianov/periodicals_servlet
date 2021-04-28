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
    <input name="login" type="text" placeholder="Login" autofocus>
    <input name="password" type="password" placeholder="Password" required>
    <button type="submit">Login</button>
</form>
</div>

<div>
    <p>User Registration</p>
</div>
<div>
    <form method="get" action="/app/registration">

        <input name="login" type="text" placeholder="Login" autofocus>
        <input name="name" type="text" placeholder="Name">
        <input name="surname" type="text" placeholder="Surname">
        <input name="email" type="text" placeholder="Email">
        <input name="password" type="password" placeholder="Password" required>
        <input name="confirmedPassword" type="password" placeholder="Password" required>
        <button type="submit">Registration</button>
    </form>
</div>
</body>
</html>
