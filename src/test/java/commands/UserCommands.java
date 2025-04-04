package commands;

import helpers.Utils;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCommands {

    private static String emailUsuario;
    private static String idUsuario;
    private static String tokenUsuario;
    private static String idAleatorio;

    public static String getEmailUsuario() {
        return emailUsuario;
    }

    public static String getIdUsuario() {
        return idUsuario;
    }

    public static String getTokenUser() {
        return tokenUsuario;
    }

    public static void CriarUsuario() {
        emailUsuario = Utils.gerarEmailFake();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", "Fulano da Silva");
        requestBody.put("email", emailUsuario);
        requestBody.put("password", "teste");
        requestBody.put("administrador", "true");

        Response response = 
            given()
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("/usuarios")
            .then()
                .statusCode(201)
                .extract().response();

        idUsuario = response.jsonPath().getString("_id");
        System.out.println("Usuário criado: " + emailUsuario + " | ID: " + idUsuario);

    }

    public static Integer CriarUsuarioCarga() {
        Response response = 
            given()
                .contentType("application/json")
                .body("{ \"nome\": \"Fulano da Silva\", \"email\": \"beltrano@qa.com.br\", \"password\": \"teste\", \"administrador\": \"true\" }")
            .when()
                .post("/usuarios")
            .then()
                .extract()
                .response();

        return response.getStatusCode();
    }

    public static void LoginUsuario() {
        if (emailUsuario == null) {
            throw new IllegalStateException("Erro: Nenhum usuário foi criado para login!");
        }
    
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", emailUsuario);
        requestBody.put("password", "teste");
    
        Response response = 
            given()
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("/login")
            .then()
                .statusCode(200)
                .extract().response();
    
        tokenUsuario = response.jsonPath().getString("authorization");
    
        if (tokenUsuario == null || tokenUsuario.isEmpty()) {
            throw new IllegalStateException("Erro: Token JWT não foi gerado");
        }
        String message = response.jsonPath().getString("message");
            assertEquals("Login realizado com sucesso", message);

        System.out.println("Response Body: " + response.getBody().asString());
    }
    
    public static void BuscarUsuarioId() {
        if (tokenUsuario == null || tokenUsuario.isEmpty()) {
            throw new IllegalStateException("Erro: token não disponivel. Faça login primeiro.");
        }
    
        Response response = 
            given()
                .header("Authorization", tokenUsuario)
                .pathParam("id", idUsuario)
            .when()
                .get("/usuarios/{id}")
            .then()
                .statusCode(200)
                .extract().response();
    
        System.out.println("Response da busca: " + response.body().asString());
    }

    public static void EditarUsuarioCriado() {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", "Beltrano de Oliveira");
        requestBody.put("email", emailUsuario);
        requestBody.put("password", "teste");
        requestBody.put("administrador", "true");

        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .pathParam("id", idUsuario)
                .body(requestBody)
            .when()
                .put("/usuarios/{id}")
            .then()
                .statusCode(200)
                .extract().response();
                String message = response.jsonPath().getString("message");
                 assertEquals("Registro alterado com sucesso", message);
                 System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void CriarUsuarioComIdDiferente() {
        emailUsuario = Utils.gerarEmailFake();
        idAleatorio = Utils.gerarIdAleatorio();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", "Beltrano de Oliveira");
        requestBody.put("email", emailUsuario);
        requestBody.put("password", "teste");
        requestBody.put("administrador", "true");

        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .pathParam("id", idAleatorio)
                .body(requestBody)
            .when()
                .put("/usuarios/{id}")
            .then()
                .statusCode(201)
                .extract().response();

                String message = response.jsonPath().getString("message");
                String newIdString = response.jsonPath().getString("_id");

                assertEquals("Cadastro realizado com sucesso", message);
                System.out.println("Novo ID" + newIdString);
                System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void DeletarUsuario(){

        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .pathParam("id", idUsuario)
            .when()
                .delete("/usuarios/{id}")
            .then()
                .statusCode(200)
                .extract().response();
                String message = response.jsonPath().getString("message");
                 assertEquals("Registro excluído com sucesso", message);
                 System.out.println("Response Body: " + response.getBody().asString());

    }

    public static void DeletarUsuarioIdAleatorio(){
        idAleatorio = Utils.gerarIdAleatorio();
        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .pathParam("id", idAleatorio)
            .when()
                .delete("/usuarios/{id}")
            .then()
                .statusCode(200)
                .extract().response();
                String message = response.jsonPath().getString("message");
                 assertEquals("Nenhum registro excluído", message);
                 System.out.println("Response Body: " + response.getBody().asString());

    }

    public static void ListaDeUsuarios(){
        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
            .when()
                .get("/usuarios")
            .then()
                .statusCode(200)
                .extract().response();

                String quantidade = response.jsonPath().getString("quantidade");
                String usuarios = response.jsonPath().getString("usuarios");
                assertNotNull(quantidade);
                assertNotNull(usuarios);
                System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void ListaDeUsuarioCriado(){
        Utils.waitForSeconds(10);
        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .queryParam("_id", idUsuario)
            .when()
                .get("/usuarios/")
            .then()
                .statusCode(200)
                .extract().response();
                List<Map<String, Object>> usuarios = response.jsonPath().getList("usuarios");
                Map<String, Object> usuario = usuarios.get(0);
                String nome = (String) usuario.get("nome");
                String idRetornado = (String) usuario.get("_id");
                String email = (String) usuario.get("email");
                assertEquals("Fulano da Silva", nome);
                assertEquals(idUsuario, idRetornado);
                assertEquals(emailUsuario, email);
                System.out.println("Response Body: " + response.getBody().asString());
    }
//// testes negativos 
    public static void CriarUsuarioEmailJaUsado(){
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("nome", "Fulano da Silva");
        requestBody.put("email", "beltrano@qa.com.br");
        requestBody.put("password", "teste");
        requestBody.put("administrador", "true");

        Response response = 
            given()
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("/usuarios")
            .then()
                .statusCode(400)
                .extract().response();

                String message = response.jsonPath().getString("message");
                assertEquals("Este email já está sendo usado", message);
                System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void LoginInvalido(){
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", "teste@teste.com");
            requestBody.put("password", "1223332442");
        
            Response response = 
                given()
                    .contentType("application/json")
                    .body(requestBody)
                .when()
                    .post("/login")
                .then()
                    .statusCode(401)
                    .extract().response();
    
                    String message = response.jsonPath().getString("message");
                    assertEquals("Email e/ou senha inválidos", message);
                    System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void BuscarUsuarioInvalido() {
        if (tokenUsuario == null || tokenUsuario.isEmpty()) {
            throw new IllegalStateException("Erro: token não disponivel. Faça login primeiro.");
        }
    
        Response response = 
            given()
                .header("Authorization", tokenUsuario)
                .pathParam("id", "reruefuefyuyufeyfeu")
            .when()
                .get("/usuarios/{id}")
            .then()
                .statusCode(400)
                .extract().response();
    
                String message = response.jsonPath().getString("message");
                assertEquals("Usuário não encontrado", message);
                System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void EditarUsuarioComEmailJaExistente() {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", "Beltrano de Oliveira");
        requestBody.put("email", emailUsuario);
        requestBody.put("password", "teste");
        requestBody.put("administrador", "true");

        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .pathParam("id", "rq9nVpldT6K1Bfap")
                .body(requestBody)
            .when()
                .put("/usuarios/{id}")
            .then()
                .statusCode(400)
                .extract().response();
                String message = response.jsonPath().getString("message");
                 assertEquals("Este email já está sendo usado", message);
                 System.out.println("Response Body: " + response.getBody().asString());
    }

    public static void ListaDeUsuariosNullo(){
        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .queryParam("_id", "erro")
            .when()
                .get("/usuarios/")
            .then()
                .statusCode(200)
                .extract().response();
    
                int quantidade = response.jsonPath().getInt("quantidade");
                assertEquals(0, quantidade);

                List<Map<String, Object>> usuarios = response.jsonPath().getList("usuarios");
                assertTrue(usuarios.isEmpty());

    }
    
    public static void DeletarUsuarioErro(){

        Response response = 
            given()
                .contentType("application/json")
                .header("Authorization", tokenUsuario)
                .pathParam("id", "0uxuPY0cbmQhpEz1")
            .when()
                .delete("/usuarios/{id}")
            .then()
                .statusCode(400)
                .extract().response();
                String message = response.jsonPath().getString("message");
                assertEquals("Não é permitido excluir usuário com carrinho cadastrado", message);
                System.out.println("Response Body: " + response.getBody().asString());
    }
}
