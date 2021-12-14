# Projeto API de estudo em Java de Raphael Mendes Stopa

Olá Jedi. Esta é uma API de estudo. Se trata de um cadastro de usuários da força. A ideia de fazer esta API veio do filme Star Wars, a vingança dos Sith. Neste filme, uma das coisas que o recém formado império galáctico consegue ao invadir a sede do conselho Jedi, e os dados deles sobre usuários da força catalogados por todo a galáxia.

As entidades deste estudo possuem comentários, do porque fiz como fiz de acordo com a história do SW.

Este estudo contem, todo o tema abordado de alguma forma, mas uma features estas. P.s; Não contem testes porque este tema ainda será abordado.

![alt_text](src/main/resources/static/star wars.jpg)

## Rodando o projeto

Este projeto foi desenvolvido, usando uma técnica para desenvolvimento ágil que usa um Docker de Mysql com migrations do liquibase. Para rodar o Docker use o comando abaixo:

```
docker-compose -p “project_raphael” -f src/main/docker/mysql.yml up -d
```

Na primeira inicialização, pode demorar um pouco. Por favor, seja paciente. Dependendo da IDE, pode ser necessário um pequeno ajuste para melhor conexão com o banco de dados. String de conexão: jdbc:mysql://localhost:3306/gft?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true


## Atenção

Projeto com o Spring Security e JWT. Para pegar seu token, use no endpoint /api/authenticate os seguintes Jsons:

```
Para o gerenciador use:
   {
   "password": "admin",
   "email": "admin@localhost"
   }


Para o usuário comum use:
   {
   "password": "user",
   "email": "user@localhost"
   }
  
```
Ainda é possível enviar o campo "rememberMe": true.

Os dados estarão automaticamente no banco de dados graças ao liquibase.

## OpenAPI/Swagger
Este projeto possui uma documentação feita com OpenAPI. Para vê-la acesse: http://localhost:8080/swagger-ui/index.html

## Outras features
* Implantação do QueryDsl.
* Mappers com mapstruct.
* Erros expostos com zalando.
