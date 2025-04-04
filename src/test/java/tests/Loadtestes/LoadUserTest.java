package tests.Loadtestes;


import org.junit.jupiter.api.Test;

import commands.LoadCommands;
import commands.UserCommands;
import helpers.ApiHelper;

public class LoadUserTest extends ApiHelper{
    
   @Test
   public void testeCargaCriarUsuario() {
       LoadCommands.executarTesteCarga(UserCommands::CriarUsuarioCarga, 101);
   }
    
}
