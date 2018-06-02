package info.san.globalstock;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.san.globalstock.view.RootLayoutController;
import info.san.globalstock.view.products.ProductsViewController;
import info.san.globalstock.view.shopping.ShoppingViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Global Stock");

        this.initRootLayout();
    }

    /**
     * Initialisation du layout principal.
     */
    public void initRootLayout() {
        try {
            MainApp.LOGGER.info("Yop");
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            this.rootLayout = loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            this.showShoppingView();

            // Show the scene containing the root layout.
            Scene scene = new Scene(this.rootLayout);
            scene.getStylesheets().add(MainApp.class.getResource("application.css").toExternalForm());
            scene.getStylesheets().add(MainApp.class.getResource("bootstrap3.css").toExternalForm());
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            MainApp.LOGGER.error("Error in MainApp...", e);
        }
    }

    public void showShoppingView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/shopping/ShoppingView.fxml"));
            AnchorPane shoppingPane = loader.load();
            ShoppingViewController ctrl = loader.getController();
            ctrl.setMainApp(this);

            this.rootLayout.setCenter(shoppingPane);
        } catch (IOException e) {
            MainApp.LOGGER.error("Error while loading shopping view...", e);
        }
    }

    public void showProductsView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/products/ProductsView.fxml"));
            AnchorPane productsPane = loader.load();
            ProductsViewController ctrl = loader.getController();
            ctrl.setMainApp(this);

            this.rootLayout.setCenter(productsPane);
        } catch (IOException e) {
            MainApp.LOGGER.error("Error while loading products view...", e);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void close() {
        MainApp.LOGGER.info("Exiting application...");
        // TODO Enregistrement automatique avant de quitter.
        System.exit(0);
    }

}
