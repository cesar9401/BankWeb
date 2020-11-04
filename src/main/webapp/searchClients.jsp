
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

        <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>
        
        <section id="search-clients">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <form action="ManagerController" method="post">
                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <select class="form-control my-4" id="type" name="type" required>
                                        <option value="0">C&oacute;digo</option>
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


        <%@include file="resources/js.html" %>
        <script>
            window.onload = function () {
                const type = document.getElementById("type");
                const search = document.getElementById("search");
                type.addEventListener("change", () => {
                    if(type.value === "0" || type.value === "2") {
                        search.setAttribute("type", "number");
                        search.setAttribute("min", "0");
                    } else if(type.value === "1") {
                        search.setAttribute("type", "text");
                        search.removeAttribute("min");
                    }
                });
            };
        </script>
    </body>
</html>
