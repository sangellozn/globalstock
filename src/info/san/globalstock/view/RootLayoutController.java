package info.san.globalstock.view;

import info.san.globalstock.MainApp;

public class RootLayoutController {

    private MainApp mainApp;

    public void close() {
        this.mainApp.close();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
