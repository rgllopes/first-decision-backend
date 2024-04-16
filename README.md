# Firts-Decision
Desafio técnico fullstack

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
(https://docs.docker.com/get-docker/)
2) No projeto, na pasta `docker`, revise o arquivo `docker-compose.yml`. Nele é possível editar as variáveis de acesso ao banco de dados que estará disponível através do container como por exempo o usuário e a sennha de acesoo, a porta de comunicação com o baco de dados, a porta de comunicação com o pgAdmin, o nome da rede, etc...
3) Após revisar as informações do container, através do terminal, acessar a pasta docker e rode o comando para inicializar o container do Postgres:
`docker-compose up -d`
Com este container você terá acesso ao Postgres e ao Open Source Administrador `pgadmin`.
4) Abra um navegador de sua preferencia com a url: `localhost:8081`, caso não tenha alterado no arquivo docker-compose.yml.
5) Digite o usuário e a senha contidas nas variáveis `POSTGRES_USER` e `POSTGRES_PASSWORD` do arquivo `docker-compose.yml`.
6) O projeto possui o arquivo `src/resources/application.properties`, na variável `spring.datasource.url`, esta o nome da base da dados que o sistema irá utilizar.
Por default esta sendo utilizado `firstdecision`.
7) Criar um novo banco de dados de acordo com o nome da variável do item 6.
8) Atualize o projeto maven para baixar as dependências.

##### Agora ja estamos prontos para rodar o projeto pela primeira vez. O sistema irá criar as tabelas e 1 usuário padrão com as informações:
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
login -> /api/login
<br>
criar usuário -> /api/create-user
<br>
listar usuários -> /api/users<br>

#### O link do projeto frontend é:
(https://github.com/rgllopes/first-decision-frontend/tree/main)
