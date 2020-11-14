
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Banca en L&iacute;nea</title>
    </head>
    <body>
        <!--NavBar-->
        <jsp:include page="WEB-INF/navClient.jsp">
            <jsp:param name="requests" value="${requests}"/>
        </jsp:include>

        <section id="profile">
            <div class="jumbotron py-2">
                <div class="container text-center">
                    <h1 class="display-4 text-danger">Banco "El Billeton"</h1>
                    <h1><span class="font-weight-bold">Usuario:</span> ${client.name}</h1>
                    <p class="lead">DPI <span class="badge badge-light">${client.dpi}</span></p>
                    <p class="lead">Direccion <span class="badge badge-light">${client.address}</span></p>
                </div>
            </div>
        </section>

        <!--Cuentas bancarias-->
        <section id="accounts">
            <div class="container">
                <div class="row">
                    <div class="col text-center my-0">
                        <h3 class="text-danger my-0">Cuentas bancarias</h3>
                        <small class="my-0">${client.name}</small>
                    </div>
                </div>

                <div class="row my-0">
                    <div class="col my-0">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Cuenta</th>
                                    <th scope="col">Fecha de Creacion</th>
                                    <th scope="col">Credito</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="a" items="${client.accounts}">
                                    <tr>
                                        <td>${a.accountId}</td>
                                        <td>${a.createdOn}</td>
                                        <td>Q. ${a.credit}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/js.html" %>
    </body>
</html>
