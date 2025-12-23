import com.cityelectro.service.AuthenticationService;
import com.cityelectro.service.StoreService;

/**
 * Main GUI Application - Entry point for CityElectro GUI version
 */
public class MainGUIApp {
    public static void main(String[] args) {
        // Initialize services
        AuthenticationService authService = new AuthenticationService();
        StoreService storeService = new StoreService();

        // Create and show main GUI
        com.cityelectro.ui.MainGUI mainGUI = new com.cityelectro.ui.MainGUI(authService, storeService);
        mainGUI.setVisible(true);
    }
}
