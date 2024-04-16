# Firts-Decision
Desafio técnico fullstack Spring Boot

## Tecnologias utilizadas
Java 17
<br>
Spring Boot 3.2.4
<br>
JPA 
<br>
Banco de dados Postgres 
<br>
Docker
<br>
## Antes de começar
1) Certifique-se de que possui o docker instalado em sua máquina.
`https://docs.docker.com/get-docker/`
2) Na pasta `docker`, revise o arquivo `docker-compose.yml`. Nele é possível editar as variáveis de acesso ao banco de dados que estará disponível através do container.
3) Após revisar as informações do container, através do terminal, entrar na pasta docker e rode o comando para inicilizar o container do Postgres:
`docker-compose up -d`
```
Com este container você terá acesso ao banco de dados Postgres e ao Open Source Administrador pgadmin.
```
<br>
4) Abra um navegador de sua preferencia com a url:
<br>
`localhost:8081`
<br>
5) Digite o usuário e a senha contidas nas variáveis `POSTGRES_USER` e `POSTGRES_PASSWORD` do arquivo `docker-compose.yml`.
<br>
6) O proje possui o arquivo `src/resources/application.properties`, na variável `spring.datasource.url`, esta o nome da base da dados que o sistema irá utilizar.
<br>
```
Por default esta sendo utilizado `firstdecision`.
```
<br>
8) Criar um novo banco de dados.

#### Agora ja estamos prontos para rodar o projeto pela primeira vez. O sistema irá criar as tabelas e 1 usuário padrão com as informações:
<br>
userName = Admin
<br>
email = admin@admin.com
<br>
password = 123456
<br>
role = ADMIN
<br>
As informações deste usuário estão no arquivo `src/main/java/br/com/api/config/AdminUserConfig na função `run`
<br>


## endpoints
criar usuário -> /api/create-user
<br>
listar usuários -> /api/users<br>
