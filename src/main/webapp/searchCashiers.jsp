
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Cajeros</title>
    </head>
    <body>

        <!--Navbar-->
        <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>

            <!--Formulario para busqueda de cajeros-->
            <section id="search-cashiers">
                <div class="container">
                    <div class="row">
                        <div class="col text-center my-2">
                            <h1 class="text-danger display-4">Cajeros</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <form action="ManagerCashController" method="post">
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
                                        <button class="btn btn-danger btn-block my-4" type="submit" name="action" value="findCashiers">Buscar</button>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <a href="ManagerCashController?action=requestAddCashier" class="btn btn-danger btn-block my-4">Agregar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <!--Vista para cajeros resultado de la busqueda-->
            <section id="view-cashiers" hidden>
                <div class="container">
                    <div class="row">
                    <c:forEach var="c" items="${cashiers}">
                        <div class="col-12 my-2">
                            <div class="card">
                                <h3 class="card-header text-center text-danger">Cajero #${c.cashierId}</h3>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col col-md-3 text-right">
                                            <p class="card-title font-weight-bold my-1">Codigo</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.cashierId}</p>
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
                                            <p class="card-title font-weight-bold my-1">Jornada</p>
                                        </div>
                                        <div class="col col-md-9">
                                            <p class="card-title my-1">${c.workDayName}</p>
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
                                        <div class="col-12 text-center">
                                            <a href="ManagerCashController?action=requestUpdateCashier&cashierId=${c.cashierId}" class="btn btn-light">Editar</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>

        <!--Vista para informacion de nuevos cajeros y cajeros editados-->
        <section id="for-cashier" hidden>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="card p-4">
                            <div class="card-header text-center">
                                <h2 id="title-for-cashier" class="text-danger">Nuevo Cajero</h2>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col col-md-3 text-right">
                                        <p class="card-title font-weight-bold my-1">Codigo</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="cashier-id" ></p>
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
                                        <p class="card-title font-weight-bold my-1">Jornada</p>
                                    </div>
                                    <div class="col col-md-9">
                                        <p class="card-title my-1" id="workday"></p>
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
                                <hr class="my-2">
                                <div class="row">
                                    <div class="col text-center">
                                        <a href="#" id="link-edit" class="btn btn-danger">Editar</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

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

            <c:choose>
                <c:when test="${newCashier != null}">
                $('#info').text("Se ha agregado correctamente al cajero ${newCashier.name}");
                $('#modalManager').modal('show');
                $('#for-cashier').attr("hidden", false);
                $('#title-for-cashier').text("Nuevo Cajero");
                //Setear datos
                $('#cashier-id').text("${newCashier.cashierId}");
                $('#name').text("${newCashier.name}");
                $('#workday').text("${newCashier.workDayName}");
                $('#dpi').text("${newCashier.dpi}");
                $('#address').text("${newCashier.address}");
                $('#gender').text("${newCashier.sex}");
                $('#link-edit').attr("href", "ManagerCashController?action=requestUpdateCashier&cashierId=${newCashier.cashierId}");
                </c:when>

                <c:when test="${cashiers != null}">
                //Visibilidad para resultado de busqueda clientes
                $('#view-cashiers').attr("hidden", false);
                </c:when>

                <c:when test="${updateCashier != null}">
                $('#info').text("Se ha actualizado correctamente al cajero ${updateCashier.name}");
                $('#modalManager').modal('show');
                $('#for-cashier').attr("hidden", false);
                $('#title-for-cashier').text("Cajero Actualizado");
                //Setear datos
                $('#cashier-id').text("${updateCashier.cashierId}");
                $('#name').text("${updateCashier.name}");
                $('#workday').text("${updateCashier.workDayName}");
                $('#dpi').text("${updateCashier.dpi}");
                $('#address').text("${updateCashier.address}");
                $('#gender').text("${updateCashier.sex}");
                $('#link-edit').attr("href", "ManagerCashController?action=requestUpdateCashier&cashierId=${updateCashier.cashierId}");
                </c:when>
            </c:choose>

            };
        </script>
    </body>
</html>
