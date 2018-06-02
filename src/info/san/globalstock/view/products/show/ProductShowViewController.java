package info.san.globalstock.view.products.show;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import info.san.globalstock.model.Product;
import info.san.globalstock.view.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProductShowViewController extends AbstractController {

    private Product selectedProduct;

    @FXML
    private Label ean13Label;

    @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label minStockLabel;

    @FXML
    private Label targetedStockLabel;

    @FXML
    private Label currentStockLabel;

    @FXML
    private Label creeLeLabel;

    @FXML
    private Label majLeLabel;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private Consumer<Product> editButtonCallBack;

    public void setEditButtonCallBack(Consumer<Product> editButtonCallBack) {
        this.editButtonCallBack = editButtonCallBack;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        this.bindProduct();
    }

    private void bindProduct() {
        this.currentStockLabel.setText(this.selectedProduct.getCurrentStock().get().toPlainString());
        this.creeLeLabel.setText(this.selectedProduct.getCreatedAt().get().format(this.df));
        this.descriptionLabel.setText(this.selectedProduct.getDescription().get());
        this.ean13Label.setText(this.selectedProduct.getEan13().get());
        this.majLeLabel.setText(this.selectedProduct.getUpdatedAt().get().format(this.df));
        this.minStockLabel.setText(this.selectedProduct.getMinStock().get().toPlainString());
        this.nomLabel.setText(this.selectedProduct.getName().get());
        this.targetedStockLabel.setText(this.selectedProduct.getTargetedStock().get().toPlainString());
    }

    @FXML
    private void editProduct() {
        this.editButtonCallBack.accept(this.selectedProduct);
    }

}
