import static org.junit.Assert.*;
import org.junit.Test;

public class LoginTest {

    @Test
    public void testValidAdminLogin() {
        assertTrue(MainApp.isValidLogin("admin", "admin", "admin"));
    }

    @Test
    public void testValidBuyerLogin() {
        assertTrue(MainApp.isValidLogin("buyer", "buyerpassword", "buyer"));
    }

    @Test
    public void testInvalidLogin() {
        assertFalse(MainApp.isValidLogin("unknown", "wrongpass", "admin"));
    }

    @Test
    public void testEmptyUsername() {
        assertFalse(MainApp.isValidLogin("", "admin", "admin"));
    }

    @Test
    public void testEmptyPassword() {
        assertFalse(MainApp.isValidLogin("admin", "", "admin"));
    }
}
