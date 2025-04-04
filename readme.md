
## Testes de API com RestAssured
Este projeto tem como objetivo realizar testes automatizados de API utilizando RestAssured. Foram implementados testes para validar as funcionalidades de criação, atualização, login, listagem e deleção de usuários, além de testes de carga para verificar a performance da aplicação.

## Autor

Este projeto foi desenvolvido por [Vinicius Zaize](https://github.com/viniciuszaize) 

## Testes Implementados

Os testes cobrem os seguintes cenários:

- **Criar Usuário**: Testes para validar a criação de novos usuários na API, verificando os diferentes status code possíveis.
- **Atualizar Usuário**: Testes para modificar informações de um usuário existente e validar as respostas da API.
- **Login de Usuário**: Testes para validar o processo de autenticação do usuário.
- **Listar Usuários**: Testes para verificar a resposta da API ao listar usuários cadastrados.
- **Deletar Usuário**: Testes para garantir que a remoção de usuários está funcionando corretamente.
- **Testes de Carga**: Simulação de múltiplas requisições para medir o desempenho e estabilidade da API.

Todos os testes foram implementados para validar as possíveis respostas da API, incluindo status codes como **200 (OK), 201 (Criado), 400 (Bad Request), 401 (Não Autorizado)**.
## O projeto está configurado com uma Pipeline CI/CD no GitLab
É possivel rodar os teste diretamente do repo https://gitlab.com/automation-tests8370041/testesdecoberturaapi.git

## Como Executar os Testes
1. Vá até opção Builds
2.  clique em pipeline
3.  clique em New pipeline
4. escolha branch "Master" e clique em new pipeline
5. após finalizar a run é possivel baixar os resultados clicando na opção "Actions"
6. na opção jobs dentro da Builds é possivel visualizar os resultados 


### Pré-requisitos

Para executar os testes, é necessário ter instalado:

- **Java 11+**
- **Maven**
- **RestAssured**

### Passos para execução

1. Clone o repositório:
   ```sh
   git clone https://github.com/viniciuszaize/testesCoberturaAPI.git
   ```
   ou gitlab
     ```sh
   git clone https://gitlab.com/automation-tests8370041/testesdecoberturaapi.git
   ```

2. Acesse a pasta do projeto:
   ```sh
   cd nome-do-projeto
   ```

3. Execute os testes com Maven:
   ```sh
   mvn test
   ```

Os relatórios de execução estarão disponíveis no diretório **target/surefire-reports**.

---

