
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Cajero - ${cashier.name}</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/navCashier.jsp"></jsp:include>

            <section id="profile">
                <div class="jumbotron py-4">
                    <div class="container text-center">
                        <h1 class="display-4 text-danger">Banco "El Billeton"</h1>
                        <h1><span class="font-weight-bold">Cajero:</span> ${cashier.name}</h1>
                    <p class="lead">Jornada: <span class="badge badge-info">${cashier.workDayName}</span></p>
                    <p class="lead">Horario: <span class="badge badge-light">${cashier.startTime}</span> - <span class="badge badge-light">${cashier.endTime}</span></p>
                </div>
            </div>
        </section>
        <%@include file="resources/js.html" %>
    </body>
</html>
