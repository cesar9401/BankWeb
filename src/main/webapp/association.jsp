
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
        <jsp:include page="WEB-INF/navClient.jsp"></jsp:include>

            <!--Formulario para buscar cuenta-->
            <section id="form-account-association">
                <div class="container">
                    <div class="row">
                        <div class="col text-center">
                            <h1 id="title-for-account" class="text-danger display-4 my-2">Nueva Asociaci&oacute;n</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col my-4">
                            <form action="ClientController" method="post" id="form-request-association">
                                <div class="form-group row">
                                    <label for="account" class="col-lg-2 col-form-label font-weight-bold text-center">N&uacute;mero de Cuenta</label>
                                    <div class="col-lg-8">
                                        <input type="number" min="0" class="form-control text-center" id="account" name="account" required>
                                    </div>
                                    <div class="col-lg-2">
                                        <button id="btn-search-account" class="btn btn-outline-danger btn-block" type="submit" name="action" value="search-account">Buscar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <!--Formulario para solicitar asociacion-->
            <section id="form-data-association" hidden>
                <div class="container">
                    <hr>
                    <div class="row">
                        <div class="col text-center">
                            <h4 class="font-weight-bold text-danger" id="title-association">Solicitud de Asociaci&oacute;n</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <form action="ClientController" method="post">
                                <div id="associated-information" class="form-row" hidden>
                                    <div class="form-group col-md-6">
                                        <label for="try-number">Intento actual</label>
                                        <input type="number" min="0" value="1" class="form-control" id="try-number" name="try-number" readonly required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="status">Estado</label>
                                        <input type="text" class="form-control" value="EN ESPERA" id="status" name="status" readonly required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col text-center">
                                        <h5 class="font-weight-bold">Datos de la cuenta</h5>
                                        <hr class="my-0">
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="name">Propietario</label>
                                        <input type="text" class="form-control" id="name" name="name" readonly required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="account-id">N&uacute;mero de cuenta</label>
                                        <input type="number" min="0" class="form-control" id="account-id" name="accountId" readonly required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col text-center">
                                        <h5 class="font-weight-bold">Datos de qui&eacute;n solicita</h5>
                                        <hr class="my-0">
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label for="client-client-id">Cliente: </label>
                                        <input type="number" min="0" class="form-control" id="client-client-id" name="client-client-id" readonly required>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="client-name">Nombre</label>
                                        <input type="text" class="form-control" id="client-name" name="client-name" readonly required>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="client-dpi">DPI</label>
                                        <input type="number" min="0" class="form-control" id="client-dpi" name="client-dpi" readonly required>
                                        <input type="hidden" value="0" id="associated-id" name="associatedId">
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-2 offset-md-5">
                                        <button type="submit" class="btn btn-danger btn-block" id="button-association" name="action" value="newAssociaction">Solicitar Asociacion</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <!--Vista para asociacion enviada-->
            <section id="association-view" hidden>
                <div class="container">
                    <hr class="my-2">
                    <div class="row">
                        <div class="col">
                            <div class="card p-4">
                                <div class="card-header text-center">
                                    <h2 class="text-danger">Solicitud de Asociaci&oacute;n</h2>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">N&uacute;mero de asociaci&oacute;n</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-id"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Propietario</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-account-name"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">N&uacute;mero de cuenta</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-account-id"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">N&uacute;mero de intento</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-try-number"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Estado</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-status"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <hr class="my-0">
                                        <div class="col text-center">
                                            <h5 class="font-weight-bold">Datos del solicitante</h5>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Nombre</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-name"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Usuario</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="asso-client-id"></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Modal informacion-->
            <div class="modal fade" id="modal-association" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
            $('#info').text("La cuenta ${noAccount} no existe");
            $('#modal-association').modal('show');
                </c:when>
                <c:when test="${notAllowed != null}">
            $('#info').text("No se puede asociar cuentas propias");
            $('#modal-association').modal('show');
                </c:when>
                <c:when test="${account != null}">
            $('#account').val("${account.accountId}");
            $("#form-data-association").prop("hidden", false);
            $('#name').val("${account.name}");
            $('#account-id').val("${account.accountId}");
            $('#client-client-id').val("${client.clientId}");
            $('#client-name').val("${client.name}");
            $('#client-dpi').val("${client.dpi}");
                </c:when>
                <c:when test="${accepted != null}">
            $('#account').val("${accepted.accountId}");
            $('#info').text("Usted ya he encuentra asociado a la cuenta ${accepted.accountId}");
            $('#modal-association').modal('show');
            //Vista de asociacion
            $('#association-view').prop("hidden", false);
            $('#asso-id').text("#${accepted.associatedId}");
            $('#asso-account-name').text("${accepted.accountName}");
            $('#asso-account-id').text("${accepted.accountId}");
            $('#asso-try-number').text("${accepted.tryNumber}");
            $('#asso-status').text("${accepted.status}");
            $('#asso-name').text("${accepted.associatedName}");
            $('#asso-client-id').text("${accepted.clientId}");
                </c:when>
                <c:when test="${noAttempts != null}">
            $('#account').val("${noAttempts.accountId}");
            //Modal
            $('#info').text("Se ha sobrepasado el numero de intentos de asociacion con la cuenta ${noAttempts.accountId}");
            $('#modal-association').modal('show');
            //Vista de asociacion
            $('#association-view').prop("hidden", false);
            $('#asso-id').text("#${noAttempts.associatedId}");
            $('#asso-account-name').text("${noAttempts.accountName}");
            $('#asso-account-id').text("${noAttempts.accountId}");
            $('#asso-try-number').text("${noAttempts.tryNumber}");
            $('#asso-status').text("${noAttempts.status}");
            $('#asso-name').text("${noAttempts.associatedName}");
            $('#asso-client-id').text("${noAttempts.clientId}");
                </c:when>
                <c:when test="${waiting != null}">
            $('#account').val("${account.accountId}");
            //Form
            $("#form-data-association").prop("hidden", false);
            $("#associated-information").prop("hidden", false);
            $('#name').val("${waiting.accountName}");
            $('#account-id').val("${waiting.accountId}");
            $('#client-client-id').val("${waiting.clientId}");
            $('#client-name').val("${waiting.associatedName}");
            $('#client-dpi').val("${waiting.associatedDpi}");
            $('#associated-id').val("${waiting.associatedId}");
            $('#status').val("${waiting.status}");
            $('#try-number').val("${waiting.tryNumber}");
                </c:when>
                <c:when test="${associated != null}">
            $('#info').text("Se ha enviado correctamente la solicitud de asociacion a la cuenta ${associated.accountId}");
            $('#modal-association').modal('show');
            $('#association-view').prop("hidden", false);
            $('#asso-id').text("#${associated.associatedId}");
            $('#asso-account-name').text("${associated.accountName}");
            $('#asso-account-id').text("${associated.accountId}");
            $('#asso-try-number').text("${associated.tryNumber}");
            $('#asso-status').text("${associated.status}");
            $('#asso-name').text("${associated.associatedName}");
            $('#asso-client-id').text("${associated.clientId}");
                </c:when>
            </c:choose>
        </script>
    </body>
</html>
