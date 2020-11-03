
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
        <title>Actualizar Datos</title>
    </head>
    <body>

        <c:if test="${manager != null}">
            <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>
        </c:if>

        <section id="update">
            <div class="container">
                <div class="row mb-4">
                    <div class="col text-center">
                        <h1 class="display-4 text-danger">Actualizar Datos</h1>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <!--Formulario-->
                        <form action="#" method="post">
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="code">Codigo</label>
                                    <input type="number" min="0" class="form-control" id="code" name="code" readonly required>
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="name">Nombre</label>
                                    <input type="text" maxlength="50" class="form-control" id="name" name="name" required>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="dpi">DPI</label>
                                    <input type="number" min="0" maxlength="15" class="form-control" id="dpi" name="dpi" placeholder="2379731380801" required>
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
                                    <label for="address">Address</label>
                                    <input type="text" maxlength="50" class="form-control" id="address" name="address" placeholder="1234 Main St" required>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="password">Password</label>
                                    <input type="password" minlength="3" maxlength="35" class="form-control" id="password" name="password" required>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-2 offset-md-5 my-4">
                                    <button type="submit" class="btn btn-danger btn-lg btn-block">Actualizar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/js.html" %>
        <c:if test="${manager != null}">
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
            </script>
        </c:if>
    </body>
</html>
