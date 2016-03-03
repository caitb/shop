<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>


sessionid
<%
    out.print(request.getSession().getId());
%>
<br/>
redis key admin
<%
    out.print(session.getAttribute("admin"));
%>

</body>
</html>
