
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Carga de Datos</title>
    </head>
    <body>
        <section id="load">
            <div class="container">
                <div class="row">
                    <div class="col text-center">
                        <h1 class="text-danger display-2">Carga de Archivos</h1>
                    </div>
                </div>

                <div class="row py-4 my-4">
                    <div class="col">
                        <form id="form-load" action="ManagerController" method="post" enctype="multipart/form-data">
                            <div class="row py-2">
                                <div class="col-12 col-lg-2 offset-lg-5 text-center">
                                    <input type="file" name="data" accept=".xml" required>
                                </div>
                            </div>
                            <div class="row py-2">
                                <div class="col-12 col-lg-2 offset-lg-5 text-center">
                                    <button type="submit" class="btn btn-success" name="action" value="data">Cargar Archivos</button>
                                </div>
                            </div>
                        </form>                        
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/js.html" %>
    </body>
</html>
