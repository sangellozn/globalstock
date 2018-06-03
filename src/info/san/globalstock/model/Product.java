package info.san.globalstock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private ObjectProperty<BigDecimal> pcb;
    private ObjectProperty<LocalDateTime> createdAt;
    private ObjectProperty<LocalDateTime> updatedAt;

    public StringProperty getEan13() {
        return this.ean13;
    }

    private void setEan13(StringProperty ean13) {
        this.ean13 = ean13;
    }

    public StringProperty getName() {
        return this.name;
    }

    private void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty getDescription() {
        return this.description;
    }

    private void setDescription(StringProperty description) {
        this.description = description;
    }

    public ObjectProperty<BigDecimal> getMinStock() {
        return this.minStock;
    }

    private void setMinStock(ObjectProperty<BigDecimal> minStock) {
        this.minStock = minStock;
    }

    public ObjectProperty<BigDecimal> getTargetedStock() {
        return this.targetedStock;
    }

    private void setTargetedStock(ObjectProperty<BigDecimal> targetedStock) {
        this.targetedStock = targetedStock;
    }

    public ObjectProperty<BigDecimal> getCurrentStock() {
        return this.currentStock;
    }

    private void setCurrentStock(ObjectProperty<BigDecimal> currentStock) {
        this.currentStock = currentStock;
    }

    public ObjectProperty<LocalDateTime> getCreatedAt() {
        return this.createdAt;
    }

    private void setCreatedAt(ObjectProperty<LocalDateTime> createdAt) {
        this.createdAt = createdAt;
    }

    public ObjectProperty<LocalDateTime> getUpdatedAt() {
        return this.updatedAt;
    }

    private void setUpdatedAt(ObjectProperty<LocalDateTime> updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ObjectProperty<BigDecimal> getPcb() {
        return this.pcb;
    }

    private void setPcb(ObjectProperty<BigDecimal> pcb) {
        this.pcb = pcb;
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
        private BigDecimal pcb;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


        public ProductBuilder() {
            this.minStock = BigDecimal.ONE;
            this.targetedStock = BigDecimal.ONE;
            this.currentStock = BigDecimal.ZERO;
            this.pcb = BigDecimal.ONE;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }

        public ProductBuilder withEan13(String ean13) {
            this.ean13 = ean13;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withMinStock(BigDecimal minStock) {
            this.minStock = minStock;
            return this;
        }

        public ProductBuilder withTargetedStock(BigDecimal targetedStock) {
            this.targetedStock = targetedStock;
            return this;
        }

        public ProductBuilder withCurrentStock(BigDecimal currentStock) {
            this.currentStock = currentStock;
            return this;
        }

        public ProductBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductBuilder withUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductBuilder withPcb(BigDecimal pcb) {
            this.pcb = pcb;
            return this;
        }

        public Product build() {
            Product result = new Product();

            result.setCreatedAt(new SimpleObjectProperty<>(this.createdAt));
            result.setUpdatedAt(new SimpleObjectProperty<>(this.updatedAt));
            result.setEan13(new SimpleStringProperty(this.ean13));
            result.setName(new SimpleStringProperty(this.name));
            result.setDescription(new SimpleStringProperty(this.description));
            result.setMinStock(new SimpleObjectProperty<>(this.minStock));
            result.setCurrentStock(new SimpleObjectProperty<>(this.currentStock));
            result.setTargetedStock(new SimpleObjectProperty<>(this.targetedStock));
            result.setPcb(new SimpleObjectProperty<>(this.pcb));

            return result;
        }
    }

}
