package info.san.globalstock.view;

import javafx.fxml.FXML;

public class RootLayoutController extends AbstractController {

    @FXML
    public void goToHome() {
        this.mainApp.showShoppingView();
    }

    public void close() {
        this.mainApp.close();
    }

}
