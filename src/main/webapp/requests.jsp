
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

        <section id="my-requests">
            <div class="container">
                <div class="row my-4">
                    <div class="col text-center">
                        <h1 class="text-danger">Solicitudes de asociacion</h1>
                    </div>
                </div>

                <div class="row my-2">
                    <c:forEach var="r" items="${requests}">
                        <div class="col-12 col-lg-6 my-1">
                            <div class="card px-4 py-0">

                                <div class="card-body">
                                    <div class="row">
                                        <div class="col text-right">
                                            <p class="card-title font-weight-bold my-0">N&uacute;mero de cuenta</p>
                                        </div>
                                        <div class="col">
                                            <p class="card-title my-0" id="asso-account-id">${r.accountId}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col text-right">
                                            <p class="card-title font-weight-bold my-0">N&uacute;mero de intento</p>
                                        </div>
                                        <div class="col">
                                            <p class="card-title my-0" id="asso-try-number">${r.tryNumber}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <hr class="my-0">
                                        <div class="col text-center">
                                            <h5 class="text-secondary my-2">Datos del solicitante</h5>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col text-right">
                                            <p class="card-title font-weight-bold my-0">Nombre</p>
                                        </div>
                                        <div class="col">
                                            <p class="card-title my-0" id="asso-name">${r.associatedName}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col text-right">
                                            <p class="card-title font-weight-bold my-0">Usuario</p>
                                        </div>
                                        <div class="col">
                                            <p class="card-title my-0" id="asso-client-id">${r.clientId}</p>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col col-md-6 text-right">
                                            <a href="ClientController?action=answerRequest&answer=ACEPTADA&associatedId=${r.associatedId}" class="btn btn-outline-danger" role="button">Aceptar</a>
                                        </div>
                                        <div class="col col-md-6 text-left">
                                            <a href="ClientController?action=answerRequest&answer=RECHAZADA&associatedId=${r.associatedId}" class="btn btn-outline-danger" role="button">Rechazar</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>


        <%@include file="resources/js.html" %>
    </body>
</html>
