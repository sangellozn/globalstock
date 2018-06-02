package info.san.globalstock.view.products.edit;

import info.san.globalstock.model.Product;
import info.san.globalstock.view.AbstractController;
import javafx.fxml.FXML;

public class ProductEditViewController extends AbstractController {

    private Product product;

    @FXML
    private void initialize() {
        // Nothing.
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void bindProduct() {
        // TODO
    }

}
