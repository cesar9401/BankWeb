
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Reportes</title>
    </head>
    <body>
        <!--NavBar-->
        <jsp:include page="WEB-INF/navClient.jsp"></jsp:include>

            <section id="report">
                <div class="row">
                    <div class="col text-center mt-4">
                        <h1 class="text-danger" id="title-report-client">Titulo</h1>
                    </div>
                </div>
            </section>

            <section>
                <div class="container">
                    <div class="row" id="form-report-client">
                        <div class="col">
                            <form action="ReportController" method="post">

                                <div class="row" id="for-client-1" hidden>
                                    <div class="col col-md-4 offset-md-2">
                                        <label for="client-account">Cuenta</label>
                                        <select class="form-control" id="client-account" name="client-account">
                                            <option value="" selected>Choose...</option>
                                        <c:forEach var="a" items="${accounts}">
                                            <option value="${a.accountId}">GT - ${a.accountId} - ${a.name} </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col col-md-4">
                                    <label for="client-year">Year</label>
                                    <input type="number" min="1900" max="2200" class="form-control" id="client-year" name="client-year">
                                </div>
                            </div>

                            <div class="row" id="for-client-2" hidden>
                                <div class="col col-md-4 offset-md-2">
                                    <label for="date1">Fecha 1</label>
                                    <input type="date" class="form-control" id="date1" name="date1">
                                </div>
                                <div class="col col-md-4">
                                    <label for="date2">Fecha 2</label>
                                    <input type="date" class="form-control" id="date2" name="date2">
                                </div>
                            </div>

                            <div class="row my-2">
                                <div class="col col-md-8 offset-md-2 text-center">
                                    <button type="submit" id="btn-report-client" name="action" value="#" class="btn btn-outline-secondary btn-block">Generar Reporte</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!--Reporte 1-->
                <div class="row" id="client1" hidden>
                    <div class="col my-4">
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">Id Transacci&oacute;n</th>
                                    <th scope="col">Cuenta</th>
                                    <th scope="col">Tipo</th>
                                    <th scope="col">Monto</th>
                                    <th scope="col">Cajero</th>
                                    <th scope="col">Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="t" items="${top15}">
                                    <tr>
                                        <th scope="row" class="align-middle">${t.transactionId}</th>
                                        <td class="align-middle">${t.accountId}</td>
                                        <td class="align-middle">${t.type}</td>
                                        <td class="align-middle">Q. ${t.amount}</td>
                                        <td class="align-middle">${t.cashierId}</td>
                                        <td class="align-middle">${t.createdOn}</td>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!--Reporte 2-->
                <div class="row" id="client2" hidden>
                    <div class="col my-4">
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">Id Transacci&oacute;n</th>
                                    <th scope="col">Cuenta</th>
                                    <th scope="col">Tipo</th>
                                    <th scope="col">Monto</th>
                                    <th scope="col">Saldo por Transacción</th>
                                    <th scope="col">Cajero</th>
                                    <th scope="col">Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="t" items="${transactions}">
                                    <tr>
                                        <th scope="row" class="align-middle">${t.transactionId}</th>
                                        <td class="align-middle">${t.accountId}</td>
                                        <td class="align-middle">${t.type}</td>
                                        <td class="align-middle">Q. ${t.amount}</td>
                                        <td class="align-middle">Q. ${t.balance}</td>
                                        <td class="align-middle">${t.cashierId}</td>
                                        <td class="align-middle">${t.createdOn}</td>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!--Reporte 3-->
                <div id="client3" hidden>
                    <div class="row">
                        <div class="col ny-4">
                            <table class="table">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Cuenta</th>
                                        <th scope="col">Fecha de Creacion</th>
                                        <th scope="col">Credito</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>${top1.get(0).accountId}</td>
                                        <td>${top1.get(0).dateAccount}</td>
                                        <td>Q. ${top1.get(0).credit}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Transacci&oacute;n</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Monto</th>
                                        <th scope="col">Saldo por Transacción</th>
                                        <th scope="col">Cajero</th>
                                        <th scope="col">Fecha</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${top1}">
                                        <tr>
                                            <th scope="row" class="align-middle">${t.transactionId}</th>
                                            <td class="align-middle">${t.type}</td>
                                            <td class="align-middle">Q. ${t.amount}</td>
                                            <td class="align-middle">Q. ${t.balance}</td>
                                            <td class="align-middle">${t.cashierId}</td>
                                            <td class="align-middle">${t.createdOn}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>                    
                </div>

                <!--Reporte 4-->
                <div class="row" id="client4" hidden>
                    <div class="col my-4">
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">Id Asociaci&oacute;n</th>
                                    <th scope="col">Id Cliente(Solicita)</th>
                                    <th scope="col">Nombre(Solicita)</th>
                                    <th scope="col">Mi Cuenta</th>
                                    <th scope="col">Estado</th>
                                    <th scope="col">Intento</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="r" items="${received}">
                                    <tr>
                                        <th scope="row" class="align-middle">${r.associatedId}</th>
                                        <td class="align-middle">${r.clientId}</td>
                                        <td class="align-middle">${r.associatedName}</td>
                                        <td class="align-middle">${r.accountId}</td>
                                        <td class="align-middle">${r.status}</td>
                                        <td class="align-middle">${r.tryNumber}</td>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!--Reporte 5-->
                <div class="row" id="client5" hidden>
                    <div class="col my-4">
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">Id Asociaci&oacute;n</th>
                                    <th scope="col">Id Cliente(Propietario)</th>
                                    <th scope="col">Nombre(Propietario)</th>
                                    <th scope="col">Cuenta</th>
                                    <th scope="col">Estado</th>
                                    <th scope="col">Intento</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${sent}">
                                    <tr>
                                        <th scope="row" class="align-middle">${s.associatedId}</th>
                                        <td class="align-middle">${s.accountClientId}</td>
                                        <td class="align-middle">${s.accountName}</td>
                                        <td class="align-middle">${s.accountId}</td>
                                        <td class="align-middle">${s.status}</td>
                                        <td class="align-middle">${s.tryNumber}</td>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>
        </section>

        <%@include file="resources/js.html" %>
        <c:choose>
            <c:when test="${client1 != null}">
                <script>
                    $("#title-report-client").text("Transacciones Top15 del año por cuenta");
                    $('#for-client-1').prop("hidden", false);
                    $("#client-account").prop("required", true);
                    $("#client-year").prop("required", true);
                    $('#btn-report-client').prop("value", "getClient1");
                    <c:if test="${top15 != null}">
                    $("#client1").prop("hidden", false);
                    $("#client-account").val("${account}");
                    $("#client-year").val("${year}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${client2 != null}">
                <script>
                    $("#title-report-client").text("Transacciones dentro de un intervalo de tiempo");
                    $('#for-client-2').prop("hidden", false);
                    $('#date1').prop("required", true);
                    $('#date2').prop("required", true);
                    $('#btn-report-client').prop("value", "getClient2");
                    <c:if test="${transactions != null}">
                    $("#client2").prop("hidden", false);
                    $('#date1').val("${date1}");
                    $('#date2').val("${date2}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${client3 != null}">
                <script>
                    $("#title-report-client").text("Transacciones cuenta Top1");
                    $('#for-client-2').prop("hidden", false);
                    $('#date1').prop("required", true);
                    $('#date2').prop("required", true);
                    $('#btn-report-client').prop("value", "getClient3");
                    <c:if test="${top1 != null}">
                    $("#client3").prop("hidden", false);
                    $('#date1').val("${date1}");
                    $('#date2').val("${date2}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${client4 != null}">
                <script>
                    $("#title-report-client").text("Solicitudes de Asociaciones Recibidas");
                    $('#form-report-client').prop("hidden", true);
                    $("#client4").prop("hidden", false);
                </script>
            </c:when>

            <c:when test="${client5 != null}">
                <script>
                    $("#title-report-client").text("Solicitudes de Asociaciones Enviadas");
                    $('#form-report-client').prop("hidden", true);
                    $("#client5").prop("hidden", false);
                </script>
            </c:when>
        </c:choose>
    </body>
</html>
