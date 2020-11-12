
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Nuevo Deposito</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/navCashier.jsp"></jsp:include>

            <section id="form-account">
                <div class="container">
                    <div class="row">
                        <div class="col text-center">
                            <h1 class="text-danger display-4 my-2">Nuevo Deposito</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col my-4">
                            <form action="ManagerCashController" method="post" id="form-deposit">
                                <div class="form-group row">
                                    <label for="account" class="col-lg-2 col-form-label font-weight-bold text-center">N&uacute;mero de Cuenta</label>
                                    <div class="col-lg-8">
                                        <input type="number" min="0" class="form-control text-center" id="account" name="account" required>
                                    </div>
                                    <div class="col-lg-2">
                                        <button class="btn btn-outline-danger btn-block" type="submit" name="action" value="search-account">Buscar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <section id="form-data-deposit" hidden>
                <div class="container">
                    <hr>
                    <hr>
                    <div class="row">
                        <div class="col text-center">
                            <h4 class="font-weight-bold text-danger">Datos Deposito</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <form action="ManagerCashController" method="post">
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="account-id">Cuenta</label>
                                        <input type="number" min="0" class="form-control" id="account-id" name="accountId" readonly required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="name">Propietario</label>
                                        <input type="text" class="form-control" id="name" name="name" readonly required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label for="account-id">Fecha</label>
                                        <input type="date" class="form-control" id="created-on" name="created-on" required>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="name">Hora</label>
                                        <input type="time" class="form-control" id="created-at" name="created-at" required>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="name">Monto Q.</label>
                                        <input type="number" min="0" step="any" class="form-control text-right" id="amount" name="amount" placeholder="Q. 0.00" required>
                                        <input type="hidden" value="${code}" name="cashierId">
                                        <input type="hidden" value="CREDITO" name="type">
                                    <input type="hidden" value="0" name="transactionId">

                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-2 offset-md-5">
                                    <button type="submit" class="btn btn-danger btn-block" name="action" value="newDeposit">Depositar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <!-- Modal informacion-->
        <div class="modal fade" id="modal-deposit" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
        <script>
            <c:choose>
                <c:when test="${noAccount != null}">
            $('#info').text("No se ha encontrado la cuenta:  ${noAccount}");
            $('#modal-deposit').modal('show');
                </c:when>
                <c:when test="${account != null}">
            $('#account').val("${account.accountId}");
            $('#form-data-deposit').prop("hidden", false);
            $('#account-id').val("${account.accountId}");
            $('#name').val("${account.name}");
                </c:when>
            </c:choose>
        </script>
    </body>
</html>
