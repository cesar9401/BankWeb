
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
        <jsp:include page="WEB-INF/navCashier.jsp"></jsp:include>

            <section id="report">
                <div class="row">
                    <div class="col text-center mt-4">
                        <h1 class="text-danger" id="title-report-cashier">Titulo</h1>
                    </div>
                </div>
            </section>

            <section>
                <div class="container">
                    <div class ="row" id="form-report-cashier">
                        <div class="col">
                            <form action="ReportController" method="post">
                                <div class="row">
                                    <div class="col col-md-8 offset-md-2" id="for-cashier-1">
                                        <label if="for-date1" for="date1">Fecha</label>
                                        <input type="date" class="form-control text-center" id="date1" name="date1" required>
                                    </div>
                                    <div class="col col-md-4" id="for-cashier-2" hidden>
                                        <label for="date2">Fecha 2</label>
                                        <input type="date" class="form-control text-center" id="date2" name="date2">
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col col-md-8 offset-md-2 text-center">
                                        <button type="submit" id="btn-report-cashier" name="action" value="#" class="btn btn-outline-secondary btn-block">Generar Reporte</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!--Reporte 1-->
                    <div class="row" id="cashier1" hidden>
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Transacci&oacute;n</th>
                                        <th scope="col">Cuenta</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Monto</th>
                                        <th scope="col">Id Cliente</th>
                                        <th scope="col">Nombre</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${turn}">
                                    <tr>
                                        <th scope="row" class="align-middle">${t.transactionId}</th>
                                        <td class="align-middle">${t.accountId}</td>
                                        <td class="align-middle">${t.type}</td>
                                        <td class="align-middle">${t.amount}</td>
                                        <td class="align-middle">${t.clientId}</td>
                                        <td class="align-middle">${t.clientName}</td>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                    
                <!--Reporte 2-->
                <div class="row" id="cashier2" hidden>
                    <div class="col my-4">
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">Id Transacci&oacute;n</th>
                                    <th scope="col">Cuenta</th>
                                    <th scope="col">Tipo</th>
                                    <th scope="col">Monto</th>
                                    <th scope="col">Id Cliente</th>
                                    <th scope="col">Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="t" items="${transactions}">
                                    <tr>
                                        <th scope="row" class="align-middle">${t.transactionId}</th>
                                        <td class="align-middle">${t.accountId}</td>
                                        <td class="align-middle">${t.type}</td>
                                        <td class="align-middle">${t.amount}</td>
                                        <td class="align-middle">${t.clientId}</td>
                                        <td class="align-middle">${t.createdOn}</td>
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
            <c:when test="${cashier1 != null}">
                <script>
                    $('#title-report-cashier').text("Transacciones durante el dia de trabajo");
                    $("#btn-report-cashier").prop("value", "getCashier1");
                    <c:if test="${turn != null}">
                    $('#date1').val("${date}");
                    $("#cashier1").prop("hidden", false);
                    </c:if>
                </script>
            </c:when>

            <c:when test="${cashier2 != null}">
                <script>
                    $('#title-report-cashier').text("Transacciones por dia en un intervalo de tiempo");
                    $("#for-cashier-1").removeClass("col-md-8");
                    $("#for-cashier-1").addClass("col-md-4");
                    $("#for-cashier-2").prop("hidden", false);
                    $('#date2').prop("required", true);
                    $("#btn-report-cashier").prop("value", "getCashier2");
                    <c:if test="${transactions != null}">
                    $('#date1').val("${date1}");
                    $('#date2').val("${date2}");
                    $('#cashier2').prop("hidden", false);
                    </c:if>
                </script>
            </c:when>
        </c:choose>
    </body>
</html>
