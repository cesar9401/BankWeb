
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Banco "El Billeton"</title>
    </head>
    <body>
        <!--NavBar-->
        <jsp:include page="WEB-INF/navClient.jsp"></jsp:include>

            <section id="profile">
                <div class="jumbotron py-4">
                    <div class="container text-center">
                        <h1 class="display-4 text-danger">Banco "El Billeton"</h1>
                        <h1><span class="font-weight-bold">Usuario:</span> ${client.name}</h1>
                    <p class="lead">DPI <span class="badge badge-light">${client.dpi}</span></p>
                    <p class="lead">Direccion <span class="badge badge-light">${client.address}</span></p>
                </div>
            </div>
        </section>

        <%@include file="resources/js.html" %>
    </body>
</html>
