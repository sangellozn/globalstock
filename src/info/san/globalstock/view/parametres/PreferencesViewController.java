package info.san.globalstock.view.parametres;

import java.io.File;
import java.util.prefs.Preferences;

import info.san.globalstock.Constants;
import info.san.globalstock.MainApp;
import info.san.globalstock.view.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PreferencesViewController extends AbstractController {

    @FXML
    private Label dataFilePathLabel;

    private DirectoryChooser directoryChooser;

    private Stage stage;

    public PreferencesViewController() {
        this.directoryChooser = new DirectoryChooser();
        String dataFilePath = this.getDataFilePath();

        if (dataFilePath != null) {
            this.directoryChooser.setInitialDirectory(new File(dataFilePath));
        }
    }

    @FXML
    public void onChooseDirectoryButtonAction() {
        File dataFile = this.directoryChooser.showDialog(this.stage);

        if (dataFile != null && dataFile.exists()) {
            Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
            prefs.put(Constants.DATA_FILE_PATH_KEY, dataFile.getAbsolutePath());
            this.dataFilePathLabel.setText(dataFile.getAbsolutePath());
            this.mainApp.loadData();
        }
    }

    @FXML
    public void onValiderButtonAction() {
        this.stage.close();
    }

    @FXML
    private void initialize() {
        String dataFilePath = this.getDataFilePath();

        if (dataFilePath == null) {
            this.dataFilePathLabel.setText("Non choisi");
        } else {
            this.dataFilePathLabel.setText(dataFilePath);
        }
    }

    private String getDataFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        return prefs.get(Constants.DATA_FILE_PATH_KEY, null);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
