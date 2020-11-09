
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Clientes</title>
    </head>
    <body>
        <!--Navbar-->
        <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>

            <!--Formulario para busqueda de clientes-->
            <section id="search-clients">
                <div class="container">
                    <div class="row">
                        <div class="col text-center my-2">
                            <h1 class="text-danger display-4">Clientes</h1>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col">
                            <form action="ManagerController" method="post">
                                <div class="form-row">
                                    <div class="form-group col-md-3">
                                        <select class="form-control my-4" id="type" name="type" required>
                                            <option value="0" selected>C&oacute;digo</option>
                                            <option value="1">Nombre</option>
                                            <option value="2">DPI</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-5">
                                        <input type="number" min="0" class="form-control my-4" id="search" name="search" required>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <button class="btn btn-danger btn-block my-4" type="submit" name="action" value="findClients">Buscar</button>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <a href="ManagerController?action=addClient" class="btn btn-danger btn-block my-4">Agregar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <!--Vista para clientes resultado de la busqueda-->
            <section id="view-clients" hidden>
                <div class="container">
                    <div class="row">
                    <c:forEach var="c" items="${clients}">
                        <div class="col-12 my-2">
                            <div class="card">
                                <h3 class="card-header text-center text-danger">Cliente #${c.clientId}</h3>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Codigo</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.clientId}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Nombre</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.name}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">DPI</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.dpi}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Fecha de Nacimiento</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.birth}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Direccion</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.address}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Genero</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.sex}</p>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-4 text-center">
                                            <a href="ManagerController?action=requestUpdateClient&clientId=${c.clientId}" class="btn btn-light">Editar</a>
                                        </div>
                                        <div class="col-4 text-center">
                                            <a href="#" onclick="setClientId('${c.name}', '${c.clientId}')" class="btn btn-light" id="link-account" data-toggle="modal" data-target="#modal-account">Agregar Cuenta</a>
                                        </div>
                                        <div class="col-4 text-center">
                                            <a href="ManagerController?action=getDPI&clientId=${c.clientId}" target="_blank" class="btn btn-light">Ver DPI</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>

        <!--Vista para informacion de nuevos clientes y clientes editados-->
        <section id="new-client" hidden>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="card p-4">
                            <div class="card-header text-center">
                                <h2 id="title-new-client" class="text-danger">Nuevo Cliente</h2>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">Codigo</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="client-id" ></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">Nombre</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="name"></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">DPI</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="dpi"></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">Fecha de Nacimiento</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="birth"></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">Direccion</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="address"></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">Genero</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="gender"></p>
                                    </div>
                                </div>

                                <div id="account-info" hidden>
                                    <hr class="my-1">
                                    <div class="row">
                                        <div class="col">
                                            <h5 class="text-center text-danger">Informacion de la Cuenta</h5>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Numero de Cuenta</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="account-id"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Fecha de Creacion</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="created-on"></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Saldo</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1" id="credit"></p>
                                        </div>
                                    </div> 
                                </div>

                                <hr class="my-2">
                                <div class="row">
                                    <div class="col text-center">
                                        <a href="#" id="link-edit" class="btn btn-danger">Editar</a>
                                    </div>
                                    <div class="col text-center">
                                        <a href="#" class="btn btn-danger" id="link-account" data-toggle="modal" data-target="#modal-account">Agregar Cuenta</a>
                                    </div>
                                    <div class="col text-center">
                                        <a href="#" id="link-dpi" target="_blank" class="btn btn-danger">Ver DPI</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Modal para agregar cuentas-->
        <div class="modal fade" id="modal-account" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Agregar Cuenta</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="ManagerController" method="post">
                            <div class="form-group">
                                <label for="name-account">Nombre</label>
                                <input type="text" class="form-control" id="name-account" name="name-account" placeholder="Nombre" readonly required>
                                <input type="hidden" id="id-account" name="id-account" value="0">
                                <input type="hidden" id="credit-account" name="credit-account" value="0">
                                <input type="hidden" id="client-id-account" name="client-id-account">
                            </div>
                            <div class="form-group">
                                <label for="created-on-account">Fecha de Creaci&oacute;n</label>
                                <input type="date" class="form-control" id="created-on-account" name="created-on-account" required>
                            </div>
                            <button type="submit" class="btn btn-danger" id="btn-submit" name="action" value="insertAccount">Agregar Cuenta</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal informacion-->
        <div class="modal fade" id="modalManager" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><span class="badge badge-info">Informaci&oacute;n</span></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="info">Sus datos se han actualizado correctamente</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>                                        

        <%@include file="resources/js.html" %>
        <script>
            window.onload = function () {
                const type = document.getElementById("type");
                type.addEventListener("change", () => {
                    if (type.value === "0") {
                        $('#search').attr("type", "number");
                        $('#search').attr("min", "0");
                    } else if (type.value === "1") {
                        $('#search').attr("type", "text");
                        $('#search').removeAttr("min");
                    } else if (type.value === "2") {
                        $('#search').attr("type", "number");
                        $('#search').attr("min", "0");
                    }
                });
                $('#new-client').attr("hidden", true);

            <c:choose>
                <c:when test="${newClient != null}">
                $('#new-client').attr("hidden", false);
                    <c:choose>
                        <c:when test="${newAccount != null}">
                $('#title-new-client').text("Nueva Cuenta");
                $('#info').text("Se ha agregado correctamente una cuenta para el cliente ${newClient.name}");
                        </c:when>
                        <c:otherwise>
                $('#title-new-client').text("Nuevo Cliente");
                $('#info').text("Se ha agregado correctamente al cliente ${newClient.name}");
                        </c:otherwise>
                    </c:choose>
                $('#modalManager').modal('show');
                $('#account-info').attr("hidden", false);
                //Setear datos
                $('#client-id').text("${newClient.clientId}");
                $('#name').text("${newClient.name}");
                $('#dpi').text("${newClient.dpi}");
                $('#birth').text("${newClient.birth}");
                $('#address').text("${newClient.address}");
                $('#gender').text("${newClient.sex}");
                $('#account-id').text("${newClient.accounts.get(0).accountId}");
                $('#created-on').text("${newClient.accounts.get(0).createdOn}");
                $('#credit').text("Q. ${newClient.accounts.get(0).credit}");
                //Enlaces
                $('#link-edit').attr("href", "ManagerController?action=requestUpdateClient&clientId=${newClient.clientId}");
                $('#link-dpi').attr("href", "ManagerController?action=getDPI&clientId=${newClient.clientId}");
                $('#link-account').attr("onclick", "setClientId('${newClient.name}', '${newClient.clientId}')");
                </c:when>

                <c:when test="${clients != null}">
                //Visibilidad para resultado de busqueda clientes
                $('#view-clients').attr("hidden", false);
                </c:when>

                <c:when test="${updateClient != null}">
                $('#new-client').attr("hidden", false);
                $('#info').text("Se ha actualizado correctamente al cliente ${updateClient.name}");
                $('#modalManager').modal('show');
                $('#account-info').attr("hidden", true);
                //Setear datos
                $('#client-id').text("${updateClient.clientId}");
                $('#name').text("${updateClient.name}");
                $('#dpi').text("${updateClient.dpi}");
                $('#birth').text("${updateClient.birth}");
                $('#address').text("${updateClient.address}");
                $('#gender').text("${updateClient.sex}");
                //Enlaces
                $('#link-edit').attr("href", "ManagerController?action=requestUpdateClient&clientId=${updateClient.clientId}");
                $('#link-dpi').attr("href", "ManagerController?action=getDPI&clientId=${updateClient.clientId}");
                $('#link-account').attr("onclick", "setClientId('${updateClient.name}', '${updateClient.clientId}')");
                </c:when>
            </c:choose>
            };

            const setClientId = function (nameClient, clientId) {
                $('#name-account').val(nameClient);
                $('#client-id-account').val(clientId);
            };
        </script>
    </body>
</html>
