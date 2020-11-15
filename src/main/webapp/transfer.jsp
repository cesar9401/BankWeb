
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
        <jsp:include page="WEB-INF/navClient.jsp"></jsp:include>

            <section id="transef">
                <div class="container">
                    <div class="row">
                        <div class="col text-center my-3">
                            <h4 class="text-danger">Nueva Transferencia</h4>
                        </div>
                    </div>

                    <!--Formulario para transferencias-->
                    <div class="row">
                        <div class="col">
                            <form action="ClientController" method="post">
                                <div class="row">
                                    <div class="col col-md-8 offset-md-2">
                                        <label for="origin-account">Cuenta Origen</label>
                                        <select id="origin-account" name="origin-account" class="form-control" required>
                                            <option value="" selected>Choose...</option>
                                        <c:forEach var="a" items="${accounts}">
                                            <option value="${a.accountId}">GT - ${a.accountId} - ${a.name} </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-8 offset-md-2">
                                    <label for="destination-account">Cuenta Destino</label>
                                    <select id="destination-account" name="destination-account" class="form-control" required>
                                        <option value="" selected>Choose...</option>
                                        <c:forEach var="d" items="${destinations}">
                                            <option value="${d.accountId}">GT - ${d.accountId} - ${d.name} </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-4 offset-md-2">
                                    <label for="transfer-date">Fecha</label>
                                    <input type="date" class="form-control" id="transfer-date" name="transfer-date" required>
                                </div>
                                <div class="col col-md-4">
                                    <label for="transfer-time">Hora</label>
                                    <input type="time" class="form-control" id="transfer-time" name="transfer-time" required>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-8 offset-md-2">
                                    <label for="transfer-amount">Monto Q.</label>
                                    <input type="number" min="25" step="any" class="form-control" id="transfer-amount" name="transfer-amount" required>
                                    <small class="form-text text-muted">
                                        El monto minimo para una transferencia es de Q. 25.00
                                    </small>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-8 offset-md-2 text-center">
                                    <button type="submit" class="btn btn-outline-danger btn-block" name="action" value="newTransaction">Realizar Transacci&oacute;n</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <!--Modal transaccion-->
        <div class="modal fade" id="modal-transfer-succesful" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><span class="badge badge-info">Informaci&oacute;n</span></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row mt-4">
                            <div class="col text-center">
                                <h3 class="text-danger">Resultado de la transaccion</h3>
                            </div>
                        </div>
                        <div class="row my-2">
                            <c:forEach var="t" items="${transactions}">
                                <div class="col-12 col-md-6 my-1">
                                    <div class="card px-4 py-2">
                                        <div class="card-header text-center">
                                            <h3 class="text-danger">Transaccion ${t.transactionId}</h3>
                                        </div>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col text-right">
                                                    <p class="card-title font-weight-bold my-0">N&uacute;mero de cuenta</p>
                                                </div>
                                                <div class="col">
                                                    <p class="card-title my-0">${t.accountId}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col text-right">
                                                    <p class="card-title font-weight-bold my-0">Tipo</p>
                                                </div>
                                                <div class="col">
                                                    <p class="card-title my-0">${t.type}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col text-right">
                                                    <p class="card-title font-weight-bold my-0">Monto</p>
                                                </div>
                                                <div class="col">
                                                    <p class="card-title my-0">Q. ${t.amount}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card-footer text-muted text-center">
                                            ${t.createdOn} - ${t.createdAt}
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div> 

        <!-- Modal informacion-->
        <div class="modal fade" id="modal-transfer" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><span class="badge badge-info">Informaci&oacute;n</span></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="info"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div> 

        <%@include file="resources/js.html" %>
        <c:choose>
            <c:when test="${sameAccounts != null}">
                <script>
                    $('#info').text("No se puede hacer una transferencia entre una misma cuenta.");
                    $('#modal-transfer').modal('show');
                    $('#origin-account').val("${origin.accountId}");
                    $('#transfer-date').val("${origin.createdOn}");
                    $('#transfer-time').val("${origin.createdAt}");
                    $('#transfer-amount').val("${origin.amount}");
                    $('#destination-account').val("");
                    $('#destination-account').focus();
                </script>
            </c:when>
            <c:when test="${outOfMoney != null}">
                <script>
                    $('#info').text("La cuenta ${a.accountId} no tiene los fondos necesarios para la transaccion.");
                    $('#modal-transfer').modal('show');

                    $('#origin-account').val("${origin.accountId}");
                    $('#transfer-date').val("${origin.createdOn}");
                    $('#transfer-time').val("${origin.createdAt}");
                    $('#transfer-amount').val("${origin.amount}");
                    $('#destination-account').val("${destination}");

                    $('#transfer-amount').focus();
                </script>
            </c:when>
            <c:when test="${transactions != null}">
                <script>
                    $('#modal-transfer-succesful').modal('show');
                </script>
            </c:when>
        </c:choose>
    </body>
</html>
