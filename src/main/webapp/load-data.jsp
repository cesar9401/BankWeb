
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="css.html" %>
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
                            <div class="row">
                                <div class="col text-right">
                                    <input type="file" name="xml" accept=".xml" required>
                                </div>
                                <div class="col">
                                    <button type="submit" class="btn btn-success" name="action" value="data">Cargar Archivos</button>
                                </div>
                            </div>
                        </form>                        
                    </div>
                </div>
            </div>
        </section>

        <%@include file="js.html" %>
    </body>
</html>
