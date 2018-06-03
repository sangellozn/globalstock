package info.san.globalstock.view.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import info.san.globalstock.MainApp;
import info.san.globalstock.model.Product;
import info.san.globalstock.store.AppData.ListeCourse;
import info.san.globalstock.store.AppData.ListeCourse.ListeCourseItem;
import info.san.globalstock.view.AbstractController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ShoppingViewController extends AbstractController {

    @FXML
    private Button creerListButton;

    @FXML
    private Button cloturerListButton;

    @FXML
    private Button annulerListeButton;

    @FXML
    private TableView<ListeCourseItem> listeCourseTableView;

    @FXML
    private TableColumn<ListeCourseItem, String> ean13TableColumn;

    @FXML
    private TableColumn<ListeCourseItem, String> nameTableColumn;

    @FXML
    private TableColumn<ListeCourseItem, BigDecimal> qtyTableColumn;

    @FXML
    private TableColumn<ListeCourseItem, BigDecimal> totalQtyTableColumn;

    private SimpleObjectProperty<ListeCourse> listeCourse;

    @FXML
    private void initialize() {
        this.ean13TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEan13()));
        this.nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        this.qtyTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQty()));
        this.totalQtyTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTotalUnit()));
    }

    @FXML
    public void onGererProduitButtonListClick() {
        this.mainApp.showProductsView();
    }

    @FXML
    public void onCloseButtonClick() {
        this.mainApp.close();
    }

    @FXML
    public void onCreerListButtonAction() {
        Collection<Product> productList = this.mainApp.getProducts();
        Collection<ListeCourseItem> items = new ArrayList<>();

        for (Product p : productList) {
            BigDecimal currentStock = p.getCurrentStock().get();
            BigDecimal minStock = p.getMinStock().get();

            // Need buy, stock has depleat beyond minimal.
            if (currentStock.compareTo(minStock) < 0) {
                ListeCourseItem toBuy = new ListeCourseItem();
                BigDecimal pcb = p.getPcb().get();
                BigDecimal targetedStock = p.getTargetedStock().get();

                toBuy.setEan13(p.getEan13().get());
                toBuy.setName(p.getName().get());

                BigDecimal qty = targetedStock.subtract(currentStock).divide(pcb).setScale(0, RoundingMode.FLOOR);

                if (qty.multiply(pcb).add(currentStock).compareTo(targetedStock) < 0) {
                    qty = qty.add(BigDecimal.ONE);
                }

                toBuy.setQty(qty);
                toBuy.setTotalUnit(qty.multiply(pcb));
                items.add(toBuy);
            }
        }

        if (!items.isEmpty()) {
            ListeCourse newListeCourse = new ListeCourse();
            newListeCourse.setItems(items);
            this.listeCourse.set(newListeCourse);
        }
    }

    @FXML
    public void onCloturerListButtonAction() {
        Map<String, Product> productByEan13 = this.mainApp.getProducts().stream().collect(Collectors.toMap(p -> p.getEan13().get(), Function.identity()));
        for (ListeCourseItem item : this.listeCourse.get().getItems()) {
            Product product = productByEan13.get(item.getEan13());
            product.getCurrentStock().set(product.getCurrentStock().get().add(item.getTotalUnit()));
        }

        this.mainApp.getListeCourse().set(null);
    }

    @FXML
    public void onAnnulerListeButtonAction() {
        this.mainApp.getListeCourse().set(null);
    }

    @Override
    public void setMainApp(MainApp mainApp) {
        super.setMainApp(mainApp);
        this.defineButtonState();
    }

    private void defineButtonState() {
        if (this.mainApp.getListeCourse().get() == null) {
            this.cloturerListButton.setVisible(false);
            this.annulerListeButton.setVisible(false);
            this.creerListButton.setVisible(true);
        } else {
            this.creerListButton.setVisible(false);
            this.cloturerListButton.setVisible(true);
            this.annulerListeButton.setVisible(true);
        }
    }

    public void setListeCourse(SimpleObjectProperty<ListeCourse> listeCourse) {
        this.listeCourse = listeCourse;
        if (listeCourse.get() != null) {
            this.listeCourseTableView.setItems(FXCollections.observableArrayList(listeCourse.get().getItems()));
        }

        this.listeCourse.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.listeCourseTableView.setItems(FXCollections.observableArrayList(newValue.getItems()));
            } else {
                this.listeCourseTableView.setItems(FXCollections.observableArrayList());
            }
            this.defineButtonState();
        });
    }

}
