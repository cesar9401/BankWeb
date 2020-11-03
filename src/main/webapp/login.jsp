
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Banco "El Billeton"</title>
    </head>
    <body>
        <!--Formulario para login-->
        <section id="login">
            <div class="container">
                <div class="row align-items-center vh-100">
                    <div class="col-12 col-lg-6 offset-lg-3">
                        <form class="form-signin" action="ManagerController" method="post">
                            <div class="row">
                                <div class="col text-center">
                                    <img class="text-center" src="resources/img/user.jpg" width="80%">
                                </div>
                            </div>
                            <h1 class="h3 mb-3 font-weight-normal text-center">Iniciar Sesi&oacute;n</h1>
                            <input type="number" min="0" id="inputId" class="form-control form-control-lg my-1" name="code" placeholder="Codigo" required autofocus>
                            <input type="password" id="inputPassword" class="form-control form-control-lg my-1" name="password" placeholder="Contraseña" required>
                            <select class="form-control my-1" name="type" required>
                                <option value="" selected>Choose...</option>
                                <option value="manager">Gerente</option>
                                <option value="cashier">Cajero</option>
                                <option value="client">Cliente</option>
                            </select>
                            <button class="btn btn-lg btn-danger btn-block my-1" type="submit" name="action" value="signIn">Sign in</button>
                        </form>
                    </div>
                </div>
            </div>            
        </section>

        <!-- Modal -->
        <div class="modal fade" id="modalLogin" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><span class="badge badge-danger">Error</span></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="info">Su usuario y/o contraseña son incorrectos</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="resources/js.html" %>
        <c:if test="${error != null}">
            <script type="text/javascript">
                $(document).ready(function () {
                    $('#modalLogin').modal('show');
                });
            </script> 
        </c:if>
    </body>
</html>
