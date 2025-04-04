package tests;
import org.junit.jupiter.api.Test;
import commands.UserCommands;
import helpers.ApiHelper;

public class UserNegativeTest extends ApiHelper {

    @Test
    public void CriarUsuarioComEmailJaCadastrado() {
    UserCommands.CriarUsuarioEmailJaUsado();
    }
    @Test
    public void loginComErro() {
    UserCommands.LoginInvalido();
    }

    @Test
    public void BuscaUsuarioErro() {
    UserCommands.CriarUsuario();
    UserCommands.LoginUsuario();
    UserCommands.BuscarUsuarioInvalido();
    }

    @Test
    public void EditarUsuarioComEmailEmUso() {
    UserCommands.CriarUsuario();
    UserCommands.LoginUsuario();
    UserCommands.EditarUsuarioComEmailJaExistente();
    }

    @Test
    public void ListaUsuarioErro() {
    UserCommands.CriarUsuario();
    UserCommands.LoginUsuario();
    UserCommands.ListaDeUsuariosNullo();
    }

    @Test
    public void DeletarUsuarioComCarrinhoAtrelado() {
    UserCommands.CriarUsuario();
    UserCommands.LoginUsuario();
    UserCommands.DeletarUsuarioErro();
    
    }
}
