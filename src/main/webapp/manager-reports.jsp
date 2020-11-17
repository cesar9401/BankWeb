
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <title>Reportes</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>

            <section id="report">
                <div class="row">
                    <div class="col text-center mt-4">
                        <h1 class="text-danger" id="title-report-manager">Titulo</h1>
                    </div>
                </div>
            </section>

            <section>
                <div class="container">
                    <div class="row" id="form-report-manager">
                        <div class="col">
                            <form action="ReportController" method="post">
                                <div class="row" id="for-manager-1" hidden>
                                    <div class="col col-md-8 offset-md-2">
                                        <label for="entity">Entidad</label>
                                        <select id="entity" name="entity" class="form-control">
                                            <option value="" selected>Choose...</option>
                                            <option value="MANAGERS">Gerente</option>
                                            <option value="CASHIERS">Cajeros</option>
                                            <option value="CLIENTS">Clientes</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="row" id="for-manager-2" hidden>
                                    <div class="col col-md-8 offset-md-2">
                                        <label for="limit">Limite</label>
                                        <input type="number" min="0" step="any" class="form-control text-center" id="limit" name="limit">
                                    </div>
                                </div>

                                <div class="row" id="for-manager-5" hidden>
                                    <div class="col col-md-4 offset-md-2">
                                        <label for="date1">Fecha 1</label>
                                        <input type="date" class="form-control" id="date1" name="date1">
                                    </div>
                                    <div class="col col-md-4">
                                        <label for="date2">Fecha 2</label>
                                        <input type="date" class="form-control" id="date2" name="date2">
                                    </div>
                                </div>

                                <div class="row" id="for-manager-6" hidden>
                                    <div class="col col-md-2 offset-md-2">
                                        <label for="type">Tipo de Busqueda</label>
                                        <select id="type6" name="type6" class="form-control">
                                            <option value="Codigo">Codigo</option>
                                            <option value="Nombre">Nombre</option>
                                            <option value="Limite">Cuenta entre Q</option>
                                        </select>
                                    </div>

                                    <div class="col col-md-6" id="for-code-client">
                                        <label for="client-code">Codigo</label>
                                        <input type="number" min="0" class="form-control" id="client-code" name="client-code">
                                    </div>

                                    <div class="col col-md-6" id="for-name-client" hidden>
                                        <label for="client-name">Nombre</label>
                                        <input type="text" class="form-control" id="client-name" name="client-name">
                                    </div>

                                    <div class="col col-md-3" id="for-limit1" hidden>
                                        <label for="limit1">Limite 1</label>
                                        <input type="number" min="0" step="any" class="form-control" id="limit1" name="limit1">
                                    </div>

                                    <div class="col col-md-3" id="for-limit2" hidden>
                                        <label for="limit2">Limite 2</label>
                                        <input type="number" min="0" step="any" class="form-control" id="limit2" name="limit2">
                                    </div>
                                </div>

                                <div class="row my-2">
                                    <div class="col col-md-8 offset-md-2 text-center">
                                        <button type="submit" id="btn-report-manager" name="action" value="#" class="btn btn-outline-secondary btn-block">Generar Reporte</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!--Reporte 1-->
                    <div id="manager1" hidden>
                        <div class="row">
                            <div class="col my-4">
                                <table class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">N&uacute;mero</th>
                                            <th scope="col">Codigo</th>
                                            <th scope="col">Nombre</th>
                                            <th scope="col">Descripcion</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="h" items="${history}">
                                        <tr>
                                            <th scope="row" class="align-middle">${h.changeId}</th>
                                            <td class="align-middle">${h.changedId}</td>
                                            <td class="align-middle">${h.changedName}</td>
                                            <td class="align-middle">${h.description}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a href="ControllerPdf?action=manager1&entity=${entity}" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div>                        
                </div>


                <!--Reporte 2-->
                <div id="manager2" hidden>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">N&uacute;mero</th>
                                        <th scope="col">Id Cliente</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Cuenta</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Monto</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${transactions}">
                                        <tr>
                                            <th scope="row" class="align-middle">${t.transactionId}</th>
                                            <td class="align-middle">${t.clientId}</td>
                                            <td class="align-middle">${t.clientName}</td>
                                            <td class="align-middle">${t.accountId}</td>
                                            <td class="align-middle">${t.type}</td>
                                            <td class="align-middle">Q. ${t.amount}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a href="ControllerPdf?action=manager2&limit=${limit}" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div>    
                </div>


                <!--Reporte 3-->
                <div id="manager3" hidden>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Cliente</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">DPI</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="s" items="${summed}">
                                        <tr>
                                            <th scope="row" class="align-middle">${s.clientId}</th>
                                            <td class="align-middle">${s.clientName}</td>
                                            <td class="align-middle">${s.dpi}</td>
                                            <td class="align-middle">${s.type}</td>
                                            <td class="align-middle">${s.total}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a href="ControllerPdf?action=manager3&limit=${limit}" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div> 
                </div>

                <!--Reporte 4-->
                <div id="manager4" hidden>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Cliente</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">DPI</th>
                                        <th scope="col">Cuenta</th>
                                        <th scope="col">Saldo</th>
                                        <th scope="col">Fecha de Creacion</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="a" items="${accounts}">
                                        <tr>
                                            <th scope="row" class="align-middle">${a.clientId}</th>
                                            <td class="align-middle">${a.name}</td>
                                            <td class="align-middle">${a.dpi}</td>
                                            <td class="align-middle">${a.accountId}</td>
                                            <td class="align-middle">Q. ${a.credit}</td>
                                            <td class="align-middle">${a.createdOn}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a href="ControllerPdf?action=manager4" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div>
                </div>

                <!--Reporte 5-->
                <div id="manager5" hidden>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Cliente</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">DPI</th>
                                        <th scope="col">Genero</th>
                                        <th scope="col">Fecha de Nacimiento</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="c" items="${clients}">
                                        <tr>
                                            <th scope="row" class="align-middle">${c.clientId}</th>
                                            <td class="align-middle">${c.name}</td>
                                            <td class="align-middle">${c.dpi}</td>
                                            <td class="align-middle">${c.sex}</td>
                                            <td class="align-middle">${c.birth}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a href="ControllerPdf?action=manager5&date1=${date1}&date2=${date2}" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div>
                </div>

                <!--Reporte 6-->
                <div id="manager6" hidden>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Transaction</th>
                                        <th scope="col">Id Cliente</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Cuenta</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Monto</th>
                                        <th scope="col">Credito Actual</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${transactions}">
                                        <tr>
                                            <th scope="row" class="align-middle">${t.transactionId}</th>
                                            <td class="align-middle">${t.clientId}</td>
                                            <td class="align-middle">${t.clientName}</td>
                                            <td class="align-middle">${t.accountId}</td>
                                            <td class="align-middle">${t.type}</td>
                                            <td class="align-middle">Q. ${t.amount}</td>
                                            <td class="align-middle">Q. ${t.credit}</td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a id="link-6" href="#" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div>
                </div>


                <!--Reporte 7-->
                <div id="manager7" hidden>
                    <div class="row">
                        <div class="col my-4">
                            <table class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Id Cajero</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">DPI</th>
                                        <th scope="col">Genero</th>
                                        <th scope="col">Jornada</th>
                                        <th scope="col">Total transacciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row" class="align-middle">${cashier.cashierId}</th>
                                        <td class="align-middle">${cashier.name}</td>
                                        <td class="align-middle">${cashier.dpi}</td>
                                        <td class="align-middle">${cashier.sex}</td>
                                        <td class="align-middle">${cashier.workDayName}</td>
                                        <td class="align-middle">${cashier.quantity}</td>
                                    </tr> 
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col col-md-8 offset-md-2">
                            <a href="ControllerPdf?action=manager7&date1=${date1}&date2=${date2}" class="btn btn-outline-dark btn-block" target="_blank">Generar PDF</a>
                        </div>
                    </div>        
                </div>


            </div>
        </section>

        <%@include file="resources/js.html" %>
        <c:choose>
            <c:when test="${manager1 != null}">
                <script>
                    $('#title-report-manager').text("Historial de Actualizaciones");
                    $('#for-manager-1').prop("hidden", false);
                    $('#btn-report-manager').prop("value", "getManager1");
                    $('#entity').prop("required", true);
                    <c:if test="${history != null}">
                    $('#manager1').prop("hidden", false);
                    $('#entity').val("${entity}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${manager2 != null}">
                <script>
                    $('#title-report-manager').text("Clientes con transacciones mayor a un limite");
                    $('#for-manager-2').prop("hidden", false);
                    $('#btn-report-manager').prop("value", "getManager2");
                    $('#limit').prop("required", true);
                    <c:if test="${transactions != null}">
                    $('#manager2').prop("hidden", false);
                    $('#limit').val("${limit}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${manager3 != null}">
                <script>
                    $('#title-report-manager').text("Clientes con transacciones monetarias sumadas mayores a un límite");
                    $('#for-manager-2').prop("hidden", false);
                    $('#btn-report-manager').prop("value", "getManager3");
                    $('#limit').prop("required", true);
                    <c:if test="${summed != null}">
                    $('#manager3').prop("hidden", false);
                    $('#limit').val("${limit}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${manager4 != null}">
                <script>
                    $('#title-report-manager').text("Los 10 clientes con más dinero en sus cuentas");
                    $("#form-report-manager").prop("hidden", true);
                    $('#manager4').prop("hidden", false);
                </script>
            </c:when>

            <c:when test="${manager5 != null}">
                <script>
                    $('#title-report-manager').text("Clientes inactivos durante un periodo de tiempo");
                    $('#for-manager-5').prop("hidden", false);
                    $('#date1').prop("required", true);
                    $('#date2').prop("required", true);
                    $('#btn-report-manager').prop("value", "getManager5");
                    <c:if test="${clients != null}">
                    $('#manager5').prop("hidden", false);
                    $('#date1').val("${date1}");
                    $('#date2').val("${date2}");
                    </c:if>
                </script>
            </c:when>

            <c:when test="${manager6 != null}">
                <script>
                    $('#title-report-manager').text("Historial de transacciones por cliente");
                    $('#for-manager-6').prop("hidden", false);
                    $('#client-code').prop("required", true);
                    $('#type6').change(function cambios() {
                        $("#for-code-client").prop("hidden", true);
                        $("#for-name-client").prop("hidden", true);
                        $("#for-limit1").prop("hidden", true);
                        $("#for-limit2").prop("hidden", true);
                        $('#client-code').prop("required", false);
                        $('#client-name').prop("required", false);
                        $('#limit1').prop("required", false);
                        $('#limit2').prop("required", false);
                        let selected = $('#type6').val();
                        if (selected === "Codigo") {
                            $("#for-code-client").prop("hidden", false);
                            $('#client-code').prop("required", true);
                        } else if (selected === "Nombre") {
                            $("#for-name-client").prop("hidden", false);
                            $('#client-name').prop("required", true);
                        } else if (selected === "Limite") {
                            $("#for-limit1").prop("hidden", false);
                            $("#for-limit2").prop("hidden", false);
                            $('#limit1').prop("required", true);
                            $('#limit2').prop("required", true);
                        }
                    });
                    $('#btn-report-manager').prop("value", "getManager6");
                    <c:if test="${transactions != null}">
                    $('#manager6').prop("hidden", false);
                    let type = "${type}";
                    if (type === "Codigo") {
                        $("#link-6").prop("href", "ControllerPdf?action=manager6&type=${type}&code=${code}");
                    } else if (type === "Nombre") {
                        $("#link-6").prop("href", "ControllerPdf?action=manager6&type=${type}&name=${name}");
                    } else {
                        $("#link-6").prop("href", "ControllerPdf?action=manager6&type=${type}&limit1=${limit1}&limit2=${limit2}");
                    }
                    </c:if>
                </script>
            </c:when>

            <c:when test="${manager7 != null}">
                <script>
                    $('#title-report-manager').text("Cajero que más transacciones ha realizado en un intervalo de tiempo");
                    $('#for-manager-5').prop("hidden", false);
                    $('#date1').prop("required", true);
                    $('#date2').prop("required", true);
                    $('#btn-report-manager').prop("value", "getManager7");
                    <c:if test="${cashier != null}">
                    $('#manager7').prop("hidden", false);
                    $('#date1').val("${date1}");
                    $('#date2').val("${date2}");
                    </c:if>
                </script>
            </c:when>
        </c:choose>
    </body>
</html>
