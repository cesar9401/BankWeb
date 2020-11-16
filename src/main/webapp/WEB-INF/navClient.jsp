<nav class="navbar navbar-expand-lg navbar-light sticky-top">
    <div class="container">
        <a class="navbar-brand" href="ClientController?action=profile">Inicio</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Reportes
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Transacciones Top15 del año</a>
                        <a class="dropdown-item" href="#">Transacciones por periodo</a>
                        <a class="dropdown-item" href="#">Transacciones Cuenta Top1</a>
                        <a class="dropdown-item" href="#">Asociaciones recibidas</a>
                        <a class="dropdown-item" href="#">Asociaciones enviadas</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a href="ClientController?action=respondToRequests" class="nav-link">Solicitudes <span class="badge badge-light">${requests.size()}</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ClientController?action=transferRequest">Transacciones</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ClientController?action=requestAssociation">Solicitar Asociaci&oacute;n</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Cuenta
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Ver Reportes</a>
                        <a class="dropdown-item" href="ManagerController?action=signOff">Cerrar Sesion</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
