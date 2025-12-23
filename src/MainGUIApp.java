import com.cityelectro.service.AuthenticationService;
import com.cityelectro.service.StoreService;
import com.cityelectro.ui.LandingPage;
import com.cityelectro.ui.MainGUI;

/**
 * Main GUI Application - Entry point for CityElectro GUI version 
 */
public class MainGUIApp {
    public static void main(String[] args) {
        // Initialize services
        AuthenticationService authService = new AuthenticationService();
        StoreService storeService = new StoreService();

        // Create main GUI (but don't show it yet)
        MainGUI mainGUI = new MainGUI(authService, storeService);
        mainGUI.setVisible(false); // Hide initially

        // Show landing page first
        new LandingPage(mainGUI);
    }
}
