package info.san.globalstock.view.products;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.san.globalstock.MainApp;
import info.san.globalstock.model.Product;
import info.san.globalstock.view.AbstractController;
import info.san.globalstock.view.products.edit.ProductEditViewController;
import info.san.globalstock.view.products.show.ProductShowViewController;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ProductsViewController extends AbstractController {

    @FunctionalInterface
    public interface ProductsViewEditCallback {
        void apply(Product product, Boolean isNew);
    }

    @FunctionalInterface
    public interface ProductsViewCancelCallback {
        void apply();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsViewController.class);

    private ObservableList<Product> productList;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, String> ean13TableColumn;

    @FXML
    private TableColumn<Product, String> nomTableColumn;

    @FXML
    private BorderPane productGestionBorderPane;

    @FXML
    private void initialize() {
        this.ean13TableColumn.setCellValueFactory(cellData -> cellData.getValue().getEan13());
        this.nomTableColumn.setCellValueFactory(cellData -> cellData.getValue().getName());

        this.productTableView.getSelectionModel().selectedItemProperty()
                .addListener(this::onSelectedItem);

        this.showNoSelectedProductView();
    }

    private void showNoSelectedProductView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("view/products/NoSelectedProductView.fxml"));
            Pane form = loader.load();
            this.productGestionBorderPane.setCenter(form);
        } catch (IOException e) {
            ProductsViewController.LOGGER.error("Error while loading view...", e);
        }

        this.productTableView.getSelectionModel().clearSelection();
    }

    private void productsViewCancelCallback() {
        this.showNoSelectedProductView();
    }

    @FXML
    public void onAccueilButtonAction() {
        this.mainApp.showShoppingView();
    }

    private void productsViewEditCallback(Product product, Boolean isNew) {
        if (isNew) {
            this.productList.add(product);
        }

        this.showNoSelectedProductView();
    }

    public void onNouveauProduitAction() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("view/products/edit/ProductEditView.fxml"));
            Pane form = loader.load();
            ProductEditViewController ctrl = loader.getController();
            ctrl.setMainApp(this.mainApp);
            ctrl.setProductEdit(new Product.ProductBuilder().build());
            ctrl.setProductsViewEditCallback(this::productsViewEditCallback);
            ctrl.setProductsViewCancelCallback(this::productsViewCancelCallback);

            this.productGestionBorderPane.setCenter(form);
        } catch (IOException e) {
            ProductsViewController.LOGGER.error("Error while loading view for new product...", e);
        }
    }

    public void editProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("view/products/edit/ProductEditView.fxml"));
            Pane form = loader.load();
            ProductEditViewController ctrl = loader.getController();
            ctrl.setMainApp(this.mainApp);
            ctrl.setProductEdit(product);
            ctrl.setProductsViewEditCallback(this::productsViewEditCallback);
            ctrl.setProductsViewCancelCallback(this::productsViewCancelCallback);

            this.productGestionBorderPane.setCenter(form);
        } catch (IOException e) {
            ProductsViewController.LOGGER.error("Error while loading view...", e);
        }
    }

    private void onSelectedItem(Observable observable, Product oldValue, Product newValue) {
        if (newValue == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("view/products/show/ProductShowView.fxml"));
            Pane form = loader.load();
            ProductShowViewController ctrl = loader.getController();
            ctrl.setMainApp(this.mainApp);
            ctrl.setSelectedProduct(newValue);
            ctrl.setEditButtonCallBack(this::editProduct);

            this.productGestionBorderPane.setCenter(form);
        } catch (IOException e) {
            ProductsViewController.LOGGER.error("Error while loading view...", e);
        }
    }

    public void setProductList(ObservableList<Product> productList) {
        this.productList = productList;
        this.productTableView.setItems(this.productList);
    }

}
