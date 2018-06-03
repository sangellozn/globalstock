package info.san.globalstock.view.products.show;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import info.san.globalstock.Constants;
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
    private Label pcbLabel;

    @FXML
    private Label creeLeLabel;

    @FXML
    private Label majLeLabel;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT);

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
        this.pcbLabel.setText(this.selectedProduct.getPcb().get().toPlainString());

        this.selectedProduct.getCurrentStock().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                this.currentStockLabel.setText(newValue.toPlainString());
            }
        });
    }

    @FXML
    private void onEditButtonAction() {
        this.editButtonCallBack.accept(this.selectedProduct);
    }

    @FXML
    private void onConsommerButtonAction() {
        BigDecimal currentStock = this.selectedProduct.getCurrentStock().get();

        if (currentStock.compareTo(BigDecimal.ZERO) > 0) {
            this.selectedProduct.getCurrentStock().set(currentStock.subtract(BigDecimal.ONE));
        }
    }

}
