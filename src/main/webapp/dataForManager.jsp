
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="resources/css.html"%>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Banco "El Billeton"</title>
    </head>
    <body>

        <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>

            <section id="update">
                <div class="container">
                    <div class="row mb-4">
                        <div class="col text-center">
                            <h1 class="display-4 text-danger" id="title-data" >Actualizar Datos</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <!--Formulario-->
                            <form action="ManagerController" method="post" enctype="multipart/form-data">
                                <div class="form-row">
                                    <div class="form-group col-md-4" id="for-code">
                                        <label for="code">C&oacute;digo</label>
                                        <input type="number" min="0" class="form-control" id="code" name="code" value="0" readonly required>
                                    </div>
                                    <div class="form-group col-md-8" id="for-name">
                                        <label for="name">Nombre</label>
                                        <input type="text" maxlength="50" class="form-control" id="name" name="name" required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label for="dpi">DPI</label>
                                        <input type="number" min="0" maxlength="15" class="form-control" id="dpi" name="dpi" required>
                                    </div>
                                    <div class="form-group col-md-4" id="fecha" hidden>
                                        <label for="birth">Fecha de Nacimiento</label>
                                        <input type="date" class="form-control" id="birth" name="birth" required>
                                    </div>
                                    <div class="form-group col-md-4" id="jornada" hidden>
                                        <label for="birth">Jornada Laboral</label>
                                        <select class="form-control" name="workday" id="workday" required>
                                            <option value="" selected>Choose...</option>
                                            <option value="1">MATUTINO</option>
                                            <option value="2">VESPERTINO</option>
                                            <option value="3">24 HORAS</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="gender">Genero</label>
                                        <select class="form-control" name="gender" id="gender" required>
                                            <option value="" selected>Choose...</option>
                                            <option value="true">Masculino</option>
                                            <option value="false">Femenino</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="address">Direcci&oacute;n</label>
                                        <input type="text" maxlength="50" class="form-control" id="address" name="address" placeholder="1234 Main St" required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="password">Password</label>
                                        <input type="password" minlength="3" maxlength="35" class="form-control" id="password" name="password" required>
                                    </div>
                                </div>
                                <div class="form-row" id="title-account" hidden>
                                    <div class="col">
                                        <hr class="my-2">
                                        <h5 class="text-center">Informacion de la Cuenta:</h5>
                                    </div>
                                </div>
                                <div class="form-row" id="info-account" hidden>
                                    <div class="form-group col-md-6">
                                        <label for="created-on">Fecha de Creacion</label>
                                        <input type="date" class="form-control" id="created-on" name="created-on" required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="pdf">PDF(DPI)</label>
                                        <input type="file" class="form-control" id="pdf" name="pdf" required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-2 offset-md-5 my-4">
                                        <button type="submit" class="btn btn-danger btn-lg btn-block" id="button-data" name="action" value="updateManager">Actualizar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

        <%@include file="resources/js.html" %>

        <c:choose>
            <c:when test="${manager != null}">
                <script>
                    $('#code').val("${manager.managerId}");
                    $('#name').val("${manager.name}");
                    $('#dpi').val("${manager.dpi}");
                    $('#fecha').prop('hidden', true);
                    $('#birth').prop('required', false);
                    $('#jornada').prop('hidden', false);
                    $('#gender').val('${manager.gender}');
                    $('#workday').val('${manager.workDay}');
                    $('#address').val('${manager.address}');
                    $('#password').val('${manager.password}');
                    $('#title-account').prop("hidden", true);
                    $('#info-account').prop("hidden", true);
                    $('#created-on').prop("required", false);
                    $('#pdf').prop("required", false);
                </script>
            </c:when>
            <c:when test="${addClient != null}">
                <script>
                    $('#title-data').text("Agregar Cliente");
                    $('#for-code').prop("hidden", true);
                    //$('#code').prop("readonly", false);
                    $('#for-name').removeClass("col-md-8");
                    $('#for-name').addClass("col-md-12");
                    $('#code').prop("required", false);
                    $('#fecha').prop("hidden", false);
                    $('#jornada').prop("hidden", true);
                    $('#workday').prop("required", false);
                    $('#title-account').prop("hidden", false);
                    $('#info-account').prop("hidden", false);
                    $('#pdf').prop("required", true);
                    $('#button-data').prop("value", "insertClient");
                    $('#button-data').text("Agregar");
                </script>
            </c:when>
        </c:choose>
    </body>
</html>
