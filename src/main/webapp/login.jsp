
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="css.html" %>
        <title>Banco "El Billeton"</title>
    </head>
    <body>
        <section id="login">
            <div class="container">
                <div class="row align-items-center vh-100">
                    <div class="col-12 col-lg-6 offset-lg-3">
                        <form class="form-signin" action="ManagerController" method="post">
                            <div class="row">
                                <div class="col text-center">
                                    <img class="text-center" src="resources/user.jpg" width="80%">
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
        <%@include file="js.html" %>
    </body>
</html>
