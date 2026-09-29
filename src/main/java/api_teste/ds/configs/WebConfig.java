package api_teste.ds.configs;

import org.springframework.context.annotation.Configuration; // Importa a anotação de configuração do Spring Container
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Importa a classe responsável por registrar as regras do CORS
import org.springframework.web.servlet.config.annotation.EnableWebMvc; // Importa a anotaçaõ que habilita os recursos do spring Web MVC
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Importa a interface de customização do Spring MVC

@Configuration // Indica que essa classe possui configurações de Beans que deve ser inicializados com o Spring Ioc
@EnableWebMvc // Importa e ativa o suporte básico as requisições e controladores Web MVC do Spring

public class WebConfig implements WebMvcConfigurer { // Classe de configuração que implementa o contrato de customização do Spring
    
    @Override // Sobreescreve o método de mapeamento Cors padrão da interface WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry) { // Método indicado pelo Spring para registrar as regras do CORS
     
        registry.addMapping("/**"); // Libera qualquer rota da API(coringa "/**") para aceitar chamadas externas

    }


}
