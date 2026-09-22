package main.java.api_teste.ds.controllers;


import java.net.URI; // Importa a classe URI para construir e manipular HTTP de novos recursos
import java.util.ServiceLoader;

import org.springframework.beans.factory.annotation.Autowired; // Injeção automática do Spring
import org.springframework.http.ResponseEntity; // Importa a classe para montar a resposta HTTP completa (Status headers, corpo da mensagem)
import org.springframework.validation.annotation.Validated; // Importa anotação para habilitar suporte a validação no controller
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia requisições do tipo delete
import org.springframework.web.bind.annotation.GetMapping; // Mapeia requisições do tipo GET
import org.springframework.web.bind.annotation.PathVariable; // Mapeia valiáveis passadas diretamente via caminho da URL
import org.springframework.web.bind.annotation.PostMapping; // Mapeia requisições do tipo POST
import org.springframework.web.bind.annotation.PutMapping; // Mapeia requisições do tipo PUT
import org.springframework.web.bind.annotation.RequestBody; // Converte objetos JSON em objetos JAVA
import org.springframework.web.bind.annotation.RequestMapping; // Importa anotação para deginir o caminho/rota base do controlador
import org.springframework.web.bind.annotation.RestController; // Importa anotação que define esta classe como um controller REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Importa utilitário para gerar a URI da requisição atual dinamicamente

import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;

@RestController // Define a classe como um controlador REST que retorna respostas em JSON
@RequestMapping ("/user") // Define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated // Ativa a verificação de validações nos parâmetros recebidos no controller

public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping ("/{id}") // Mapeia requisições HTTP GET na rota "/user/{id}"
    public ResponseEntity<User> findById(@PathVariable Long Id) { // Método para buscar usuário por id capturado da URL
        User obj = this.userService.findById(Id); // Invoca a buscar do usuário através do ID recebido
        return ResponseEntity.ok().body(obj); // Retorna código HTTP 200(pk) com o objeto User no corpo da resposta
    } // Fim do método findById

    @PostMapping // 
    public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj){

        this.userService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();

    }


}
