package info.san.globalstock.view;

import javafx.fxml.FXML;

public class RootLayoutController extends AbstractController {

    @FXML
    public void goToHome() {
        this.mainApp.showShoppingView();
    }

    @FXML
    public void close() {
        this.mainApp.close();
    }

    public void showPrefrences() {
        this.mainApp.showPrefrencesView();
    }

}
