package helpers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
public class ApiHelper {
    
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://serverest.dev";
    }

}
