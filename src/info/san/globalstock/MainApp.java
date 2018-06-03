package info.san.globalstock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.san.globalstock.model.Product;
import info.san.globalstock.store.AppData;
import info.san.globalstock.store.AppData.ListeCourse;
import info.san.globalstock.view.RootLayoutController;
import info.san.globalstock.view.parametres.PreferencesViewController;
import info.san.globalstock.view.products.ProductsViewController;
import info.san.globalstock.view.shopping.ShoppingViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * MIT License
 *
 * Copyright (c) 2016 sangellozn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**
 * Main entry app point.
 *
 * @author ANGELLOZ-NICOUD Sébastien
 */
public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private SimpleObjectProperty<ListeCourse> listeCourse;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Global Stock");
        this.primaryStage.setMinWidth(1024);
        this.primaryStage.setMinHeight(800);

        this.loadData();
        this.initRootLayout();
    }

    @Override
    public void stop() throws Exception {
        this.saveData();
    }

    public void loadData() {
        this.products.clear();
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String dataFilePath = prefs.get(Constants.DATA_FILE_PATH_KEY, null);
        File dataFile = null;
        if (dataFilePath == null) {
            dataFile = Paths.get("./globalStock.xml").toFile();
            prefs.put(Constants.DATA_FILE_PATH_KEY, Paths.get(".").toFile().getAbsolutePath());
        } else {
            dataFile = Paths.get(dataFilePath + "/globalStock.xml").toFile();
        }

        if (dataFile.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(AppData.class);
                Unmarshaller um = context.createUnmarshaller();
                AppData appData = (AppData) um.unmarshal(dataFile);
                this.products.addAll(appData.getProductList());
                this.listeCourse = new SimpleObjectProperty<>(appData.getListeCourse());
            } catch (JAXBException e) {
                MainApp.LOGGER.error("Error while loading data...", e);
            }
        }
    }

    /**
     * Initialisation du layout principal.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            this.rootLayout = loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            this.showShoppingView();

            // Show the scene containing the root layout.
            Scene scene = new Scene(this.rootLayout);
            scene.getStylesheets()
                    .add(MainApp.class.getResource("application.css").toExternalForm());
            scene.getStylesheets()
                    .add(MainApp.class.getResource("bootstrap3.css").toExternalForm());
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            MainApp.LOGGER.error("Error in MainApp...", e);
        }
    }

    public void showPrefrencesView() {
        try {
            Stage dialogStage = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/parametres/PreferencesView.fxml"));
            AnchorPane form = (AnchorPane) loader.load();
            PreferencesViewController ctrl = loader.getController();
            ctrl.setStage(dialogStage);
            ctrl.setMainApp(this);

            dialogStage.setTitle("Préférences");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(form);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            MainApp.LOGGER.error("Error while opening preferences window...", e);
        }
    }

    public void showShoppingView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/shopping/ShoppingView.fxml"));
            AnchorPane shoppingPane = loader.load();
            ShoppingViewController ctrl = loader.getController();
            ctrl.setMainApp(this);
            ctrl.setListeCourse(this.getListeCourse());

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
            ctrl.setProductList(this.products);

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
        Platform.exit();
    }

    private void saveData() throws JAXBException {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String dataFilePath = prefs.get(Constants.DATA_FILE_PATH_KEY, Paths.get(".").toFile().getAbsolutePath());
        try {
            JAXBContext context = JAXBContext.newInstance(AppData.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(new AppData(this.products, this.listeCourse.get()), new File(dataFilePath + "/globalStock.xml"));
        } catch (PropertyException e) {
            MainApp.LOGGER.error("Error while saving data...", e);
        } catch (JAXBException e) {
            MainApp.LOGGER.error("Error while saving data...", e);
            throw e;
        }
    }

    public SimpleObjectProperty<ListeCourse> getListeCourse() {
        return this.listeCourse;
    }

    public void setListeCourse(SimpleObjectProperty<ListeCourse> listeCourse) {
        this.listeCourse = listeCourse;
    }

    public ObservableList<Product> getProducts() {
        return this.products;
    }

}
