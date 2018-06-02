package info.san.globalstock.view.shopping;

import info.san.globalstock.view.AbstractController;
import javafx.fxml.FXML;

public class ShoppingViewController extends AbstractController {

    @FXML
    public void onGererProduitButtonListClick() {
        this.mainApp.showProductsView();
    }

}
