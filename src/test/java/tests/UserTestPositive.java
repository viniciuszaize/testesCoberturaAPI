package tests;
import org.junit.jupiter.api.Test;
import commands.UserCommands;
import helpers.ApiHelper;


public class UserTestPositive extends ApiHelper  {

    @Test
    public void CriarUsuario() {
        UserCommands.CriarUsuario();
    }

    @Test
    public void LoginUsuario() {
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
    }

    @Test
    public void BuscarUsuario() {
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.BuscarUsuarioId();
    }

    @Test
    public void EditarUsuarioExistente() {
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.EditarUsuarioCriado();
    }

    @Test
    public void CriarUsuarioUsandoEditarUsuario() {
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.CriarUsuarioComIdDiferente();
    }

    @Test
    public void deletarUsuario(){
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.DeletarUsuario();
    }

    @Test
    public void deletarUsuarioComIdNaoCadastrado(){
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.DeletarUsuarioIdAleatorio();
    }

    @Test
    public void listaDeUsuarios(){
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.ListaDeUsuarios();
    }

    @Test
    public void listaDeUsuarioCriado(){
        UserCommands.CriarUsuario();
        UserCommands.LoginUsuario();
        UserCommands.ListaDeUsuarioCriado();
    }
}
