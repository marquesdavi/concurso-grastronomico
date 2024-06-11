# Concurso Gastronômico

Painel de controle feito com Jhipser. Abaixo, você encontrará uma descrição do projeto, as tecnologias utilizadas, as rotas disponíveis e as instruções para rodar o projeto.

## Descrição do Projeto

O Concurso Gastronômico é uma aplicação web desenvolvida com as seguintes tecnologias:

- **Java 17**: Linguagem de programação utilizada no backend.
- **JHipster**: Ferramenta para gerar, desenvolver e manter aplicações Java e Spring Boot.
- **VueJS**: Framework JavaScript utilizado no frontend para construção da interface do usuário.
- **TypeScript**: Superset do JavaScript que adiciona tipagem estática ao código.

## Autores

- [@marquesdavi](https://www.github.com/marquesdavi) - Desenvolvedor FullStack(Backend Heavy) e Gerente de Projetos
- [@EdsKelvin](https://github.com/EdsKelvin) - Desenvolvedor FullStack
- [@DevPedrosacul](https://github.com/DevPedrosacul) - Desenvolvedor Frontend
- [@VagnerNCarvalho](https://github.com/VagnerNCarvalho) - Requisitos e Prototipagem
- [@samirfsantos](https://github.com/samirfsantos) - Requisitos

## Tecnologias Utilizadas

- **Java 17**: Backend.
- **Spring Boot**: Framework Java para criação de aplicações standalone.
- **JHipster 8.4.0**: Ferramenta de geração de código.
- **VueJS**: Framework JavaScript para construção do frontend.
- **TypeScript**: Linguagem de programação para desenvolvimento do frontend.
- **Node.js**: Ambiente de execução JavaScript utilizado para gerenciar dependências do frontend e rodar o servidor de desenvolvimento.
- **Webpack**: Utilizado para empacotamento dos módulos JavaScript.
- **Docker**: Utilizado para containerização da aplicação e seus serviços.

## Estrutura do Projeto

- **/src**: Contém o código fonte da aplicação.
- **/src/main**: Contém o código fonte principal.
- **/src/main/docker**: Configurações Docker para a aplicação e serviços dependentes.

## Rotas

- **/**: Painel de Controle JHipster
- **/contest**: Página de destino (Landing Page)
- **/login**: Página de login
- **/account/register**: Página de registro de novos usuários
- **/account/settings**: Página de configurações de conta
- **/admin/user-management**: Gerenciamento de usuários (acesso restrito a administradores)
- **/admin/metrics**: Métricas de desempenho da aplicação (acesso restrito a administradores)
- **/admin/health**: Verificação de saúde da aplicação (acesso restrito a administradores)
- **/admin/configuration**: Configurações da aplicação (acesso restrito a administradores)
- **/admin/audits**: Auditorias da aplicação (acesso restrito a administradores)
- **/admin/logs**: Logs da aplicação (acesso restrito a administradores)

## Como Rodar o Projeto

### Pré-requisitos

Antes de rodar o projeto, você deve instalar e configurar as seguintes dependências na sua máquina:

1. **Node.js**: Baixe e instale em [Node.js](https://nodejs.org/).
2. **Maven**: Certifique-se de que o Maven esteja instalado para construir o projeto Java.

### Passos para Rodar

1. **Instalar Dependências**

   Navegue até o diretório do projeto e instale as dependências do Node.js:

   ```sh
   npm install
   ```

2. **Iniciar o Backend e o Frontend**

   Em dois terminais separados, rode os seguintes comandos:

   **Terminal 1:**

   ```sh
   mvnw
   ```

   **Terminal 2:**

   ```sh
   npm start
   ```

   Isso iniciará o servidor de desenvolvimento e abrirá a aplicação no navegador. O backend estará rodando em [http://localhost:8080](http://localhost:8080) e o frontend estará disponível em [http://localhost:9000](http://localhost:9000).

### Rodar Testes

- **Testes do Cliente**

  Para rodar os testes do frontend, execute:

  ```sh
  npm test
  ```

- **Testes do Backend**

  Para rodar os testes do backend, execute:

  ```sh
  mvnw verify
  ```

### Build para Produção

Para construir o jar final e otimizar a aplicação para produção, rode:

```sh
mvnw -Pprod clean verify
```

Isso concatenará e minificará os arquivos CSS e JavaScript do cliente e modificará `index.html` para referenciar esses novos arquivos. Para garantir que tudo funcionou, rode:

```sh
java -jar target/*.jar
```

Então navegue até [http://localhost:8080](http://localhost:8080) no seu navegador.

---

## Considerações Finais

Para mais informações sobre o desenvolvimento com JHipster, consulte a [documentação oficial](https://www.jhipster.tech/documentation-archive/v8.4.0).
