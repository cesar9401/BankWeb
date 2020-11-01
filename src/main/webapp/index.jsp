
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="css.html"%>
        <title>Banco "El Billeton"</title>
    </head>
    <body>
        <a id="link" href="ManagerController?action=takeChoice" class="btn btn-success" hidden=""></a>
        
        <%@include file="js.html"%>
        <script>
            this.document.getElementById("link").click();
        </script>
    </body>
</html>
