<nav class="navbar navbar-expand-lg navbar-light sticky-top">
    <div class="container">
        <a class="navbar-brand" href="ManagerController?action=profile">Inicio</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Enlace 1<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ManagerCashController?action=searchCashiers">Cajeros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ManagerController?action=searchClients">Clientes</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Cuenta
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Ver Reportes</a>
                        <a class="dropdown-item" href="ManagerController?action=updateData">Actualizar Datos</a>
                        <a class="dropdown-item" href="ManagerController?action=signOff">Cerrar Sesion</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>