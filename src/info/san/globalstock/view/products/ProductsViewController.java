package info.san.globalstock.view.products;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.san.globalstock.MainApp;
import info.san.globalstock.model.Product;
import info.san.globalstock.view.AbstractController;
import info.san.globalstock.view.products.edit.ProductEditViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ProductsViewController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsViewController.class);

    @FXML
    private BorderPane productGestionBorderPane;

    @FXML
    public void onAccueilButtonAction() {
        this.mainApp.showShoppingView();
    }

    public void onNouveauProduitAction() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/products/edit/ProductEditView.fxml"));
            Pane form = loader.load();
            ProductEditViewController ctrl = loader.getController();
            ctrl.setMainApp(this.mainApp);
            ctrl.setProduct(new Product.ProductBuilder().build());

            this.productGestionBorderPane.setCenter(form);
        } catch (IOException e) {
            ProductsViewController.LOGGER.error("Error while loading view for new product...", e);
        }

    }

}
