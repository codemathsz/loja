<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="#page-top"><img src="assets/img/logos/logo.png" alt="Logo" /></a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ml-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav text-uppercase ml-auto">
                    <c:if test="${usuarioLogado != null }">
                    	<li class="nav-item"><a class="nav-link js-scroll-trigger" href="categorias">Categorias</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="produtos">Produtos</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="logout
                        ">Sair</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger">${usuarioLogado.getNome() }</a></li> 
                    </c:if> 
                    <c:if test="${usuarioLogado == null }">
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="login">Login</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="cadastro">Cadastrar-se</a></li> 
                    </c:if> 
                    </ul>
                </div>
            </div>
        </nav>