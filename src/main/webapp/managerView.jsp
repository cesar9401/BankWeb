
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include  file="resources/css.html" %>
        <link href="resources/style.css" rel="stylesheet">
        <link href="resources/img/banking.png" rel="icon" type="image/png">
        <title>Gerente - ${manager.name}</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/navManager.jsp"></jsp:include>

            <section id="profile">
                <div class="jumbotron py-4">
                    <div class="container text-center">
                        <h1 class="display-4 text-danger">Banco "El Billeton"</h1>
                        <h1><span class="font-weight-bold">Gerente</span> ${manager.name}</h1>
                    <p class="lead">Jornada: <span class="badge badge-info">${manager.workDayName}</span></p>
                    <p class="lead">Horario: <span class="badge badge-light">${manager.startTime}</span> - <span class="badge badge-light">${manager.endTime}</span></p>
                </div>
            </div>
        </section>

        <!-- Modal -->
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
        <c:if test="${update != null}">
            <script type="text/javascript">
                $(document).ready(function () {
                    $('#modalManager').modal('show');
                });
            </script> 
        </c:if>
    </body>
</html>
