# Gym App

Este é um projeto pessoal de estudos, que consiste em uma aplicação para uma academia. O backend foi desenvolvido em Java, utilizando o framework Spring, e segue a arquitetura de microserviços. O frontend foi construído com Angular.

## Funcionalidades

- **Cadastro de Usuários**: Usuários administradores podem cadastrar novos usuários na plataforma.
- **Controle de Usuários**: Usuários administradores podem verificar o status dos usuários, além de poder ativar, desativar e excluir usuários.
- **Gerenciamento de Treinos**: Usuários treinadores podem visualizar, criar, editar, ativar e desativar treinos, além de associar usuários clientes ao seus treinos.
- **Painel de Controle**: Interface para treinador gerenciar os treinos e usuários associados.
- **Alteração de senha**: Todos os usuários ao serem cadastrados tem como senha padrão o seu CPF, podendo alterar essa senha na plataforma.
- **Visualização de treinos**: Usuários clientes podem acessar e visualizar os treinos associados pelo seu treinador.

## Tecnologias Utilizadas

- **Backend**: 
  - Java
  - Spring Boot
  - Spring Security (Autenticação e Autorização com JWT e roles)
  - Spring Cloud Config Server e Client (Configuração Global)
  - RabbitMQ (Comunicação Assíncrona entre microserviços por mensageria)
  - API Gateway
  - Maven

- **Frontend**:
  - Angular (Standalone components)
  - TypeScript
  - HTML/CSS
  - Bootstrap

