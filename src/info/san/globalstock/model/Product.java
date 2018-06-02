package info.san.globalstock.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private StringProperty ean13;
    private StringProperty name;
    private StringProperty description;
    private ObjectProperty<BigDecimal> minStock;
    private ObjectProperty<BigDecimal> targetedStock;
    private ObjectProperty<BigDecimal> currentStock;
    private ObjectProperty<LocalDate> createdAt;
    private ObjectProperty<LocalDate> updatedAt;

    private StringProperty getEan13() {
        return this.ean13;
    }

    private void setEan13(StringProperty ean13) {
        this.ean13 = ean13;
    }

    private StringProperty getName() {
        return this.name;
    }

    private void setName(StringProperty name) {
        this.name = name;
    }

    private StringProperty getDescription() {
        return this.description;
    }

    private void setDescription(StringProperty description) {
        this.description = description;
    }

    private ObjectProperty<BigDecimal> getMinStock() {
        return this.minStock;
    }

    private void setMinStock(ObjectProperty<BigDecimal> minStock) {
        this.minStock = minStock;
    }

    private ObjectProperty<BigDecimal> getTargetedStock() {
        return this.targetedStock;
    }

    private void setTargetedStock(ObjectProperty<BigDecimal> targetedStock) {
        this.targetedStock = targetedStock;
    }

    private ObjectProperty<BigDecimal> getCurrentStock() {
        return this.currentStock;
    }

    private void setCurrentStock(ObjectProperty<BigDecimal> currentStock) {
        this.currentStock = currentStock;
    }

    private ObjectProperty<LocalDate> getCreatedAt() {
        return this.createdAt;
    }

    private void setCreatedAt(ObjectProperty<LocalDate> createdAt) {
        this.createdAt = createdAt;
    }

    private ObjectProperty<LocalDate> getUpdatedAt() {
        return this.updatedAt;
    }

    private void setUpdatedAt(ObjectProperty<LocalDate> updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Product() {
        // Nothing.
    }

    public static class ProductBuilder {
        private String ean13;
        private String name;
        private String description;
        private BigDecimal minStock;
        private BigDecimal targetedStock;
        private BigDecimal currentStock;


        public ProductBuilder() {
            this.minStock = BigDecimal.ZERO;
            this.currentStock = BigDecimal.ZERO;
        }

        public ProductBuilder withEan13(String ean13) {
            this.ean13 = ean13;
            return this;
        }

        public Product build() {
            Product result = new Product();

            result.setCreatedAt(new SimpleObjectProperty<>(LocalDate.now()));
            result.setUpdatedAt(new SimpleObjectProperty<>(LocalDate.now()));
            result.setEan13(new SimpleStringProperty(this.ean13));

            // FIXME finir autres propriétés.
            /*result.set
            result.set
            result.set
            result.set
            result.set
            result.set
            result.set*/

            return result;
        }
    }

}
