package info.san.globalstock.view.products.edit;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import info.san.globalstock.model.Product;
import info.san.globalstock.view.AbstractController;
import info.san.globalstock.view.products.ProductsViewController.ProductsViewCancelCallback;
import info.san.globalstock.view.products.ProductsViewController.ProductsViewEditCallback;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProductEditViewController extends AbstractController {

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private Product product;

    @FXML
    private Label productEditViewTitle;

    @FXML
    private TextField ean13Field;

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Spinner<Integer> stockMinimalSpinner;

    @FXML
    private Spinner<Integer> stockCibleSpinner;

    @FXML
    private Spinner<Integer> stockActuelSpinner;

    @FXML
    private Label creeLeLabel;

    @FXML
    private Label majLeLabel;

    private ProductsViewEditCallback productsViewEditCallback;

    private ProductsViewCancelCallback productsViewCancelCallback;

    @FXML
    private void initialize() {
        this.stockMinimalSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 0));
        this.stockCibleSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 0));
        this.stockActuelSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
    }

    public void setProductEdit(Product product) {
        this.product = product;
        this.bindProductEdit();
    }

    public void setProductsViewEditCallback(ProductsViewEditCallback productsViewEditCallback) {
        this.productsViewEditCallback = productsViewEditCallback;
    }

    public void setProductsViewCancelCallback(
            ProductsViewCancelCallback productsViewCancelCallback) {
        this.productsViewCancelCallback = productsViewCancelCallback;
    }

    private void bindProductEdit() {
        if (this.product.getEan13().get() != null && !this.product.getEan13().get().isEmpty()) {
            this.productEditViewTitle.setText("Edition d'un produit");
        } else {
            this.productEditViewTitle.setText("Ajout d'un produit");
        }

        this.ean13Field.setText(this.product.getEan13().get());
        this.nomField.setText(this.product.getName().get());
        this.descriptionField.setText(this.product.getDescription().get());
        this.stockActuelSpinner.getValueFactory().setValue(this.product.getCurrentStock().get().intValue());
        this.stockCibleSpinner.getValueFactory().setValue(this.product.getTargetedStock().get().intValue());
        this.stockMinimalSpinner.getValueFactory().setValue(this.product.getMinStock().get().intValue());
        this.creeLeLabel.setText(this.product.getCreatedAt().get().format(this.df));
        this.majLeLabel.setText(this.product.getUpdatedAt().get().format(this.df));
    }

    @FXML
    private void save() {
        String previousEan13 = this.product.getEan13().get();

        // TODO Faire les vérifications nécessaire.
        this.product.getCurrentStock().set(BigDecimal.valueOf(this.stockActuelSpinner.getValue()));
        this.product.getDescription().set(this.descriptionField.getText());
        this.product.getEan13().set(this.ean13Field.getText());
        this.product.getMinStock().set(BigDecimal.valueOf(this.stockMinimalSpinner.getValue()));
        this.product.getName().set(this.nomField.getText());
        this.product.getTargetedStock().set(BigDecimal.valueOf(this.stockCibleSpinner.getValue()));
        this.product.getUpdatedAt().set(LocalDateTime.now());

        this.productsViewEditCallback.apply(this.product, previousEan13 == null || previousEan13.isEmpty());
    }

    @FXML
    private void cancel() {
        this.productsViewCancelCallback.apply();
    }

}
