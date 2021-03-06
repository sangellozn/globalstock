package info.san.globalstock.store;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import info.san.globalstock.Constants;
import info.san.globalstock.model.Product;

@XmlRootElement
public class AppData {

    private final String version = "1.0";

    public static final class ListeCourse {

        private Collection<ListeCourseItem> items = new ArrayList<>();

        public static final class ListeCourseItem {

            private String ean13;

            private String name;

            private BigDecimal qty;

            private BigDecimal totalUnit;

            @XmlAttribute
            public String getEan13() {
                return this.ean13;
            }

            public void setEan13(String ean13) {
                this.ean13 = ean13;
            }

            @XmlAttribute
            public BigDecimal getQty() {
                return this.qty;
            }

            public void setQty(BigDecimal qty) {
                this.qty = qty;
            }

            @XmlAttribute
            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @XmlAttribute
            public BigDecimal getTotalUnit() {
                return this.totalUnit;
            }

            public void setTotalUnit(BigDecimal totalUnit) {
                this.totalUnit = totalUnit;
            }

        }

        @XmlElement(name = "item")
        @XmlElementWrapper(name = "items")
        public Collection<ListeCourseItem> getItems() {
            return this.items;
        }

        public void setItems(Collection<ListeCourseItem> items) {
            this.items = items;
        }

    }

    public static final class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

        @Override
        public String marshal(LocalDateTime v) throws Exception {
            return v.format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
        }

        @Override
        public LocalDateTime unmarshal(String v) throws Exception {
            return LocalDateTime.parse(v, DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
        }
    }

    private List<SimpleProduct> simpleProductList = new ArrayList<>();

    private ListeCourse listeCourse;

    public AppData() {
        // For JAXB.
    }

    public AppData(Collection<Product> productsToSave, ListeCourse listeCourse) {
        for (Product p : productsToSave) {
            SimpleProduct sp = new SimpleProduct();
            sp.setCreatedAt(p.getCreatedAt().get());
            sp.setCurrentStock(p.getCurrentStock().get());
            sp.setDescription(p.getDescription().get());
            sp.setEan13(p.getEan13().get());
            sp.setMinStock(p.getMinStock().get());
            sp.setName(p.getName().get());
            sp.setTargetedStock(p.getTargetedStock().get());
            sp.setUpdatedAt(p.getUpdatedAt().get());
            sp.setPcb(p.getPcb().get());

            this.simpleProductList.add(sp);
        }

        this.listeCourse = listeCourse;
    }

    public static class SimpleProduct {

        private String ean13;

        private String name;

        private String description;

        private BigDecimal minStock;

        private BigDecimal currentStock;

        private BigDecimal targetedStock;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        private BigDecimal pcb;

        @XmlAttribute
        public String getEan13() {
            return this.ean13;
        }

        public void setEan13(String ean13) {
            this.ean13 = ean13;
        }

        @XmlAttribute
        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlAttribute
        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlAttribute
        public BigDecimal getMinStock() {
            return this.minStock;
        }

        public void setMinStock(BigDecimal minStock) {
            this.minStock = minStock;
        }

        @XmlAttribute
        public BigDecimal getCurrentStock() {
            return this.currentStock;
        }

        public void setCurrentStock(BigDecimal currentStock) {
            this.currentStock = currentStock;
        }

        @XmlAttribute
        public BigDecimal getTargetedStock() {
            return this.targetedStock;
        }

        public void setTargetedStock(BigDecimal targetedStock) {
            this.targetedStock = targetedStock;
        }

        @XmlAttribute
        @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
        public LocalDateTime getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        @XmlAttribute
        @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
        public LocalDateTime getUpdatedAt() {
            return this.updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }

        @XmlAttribute
        public BigDecimal getPcb() {
            return this.pcb;
        }

        public void setPcb(BigDecimal pcb) {
            this.pcb = pcb;
        }

    }

    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    public List<SimpleProduct> getSimpleProductList() {
        return this.simpleProductList;
    }

    @XmlAttribute
    public String getVersion() {
        return this.version;
    }

    public Collection<Product> getProductList() {
        List<Product> result = new ArrayList<>();

        for (SimpleProduct sp : this.simpleProductList) {
            result.add(new Product.ProductBuilder()
                    .withCurrentStock(sp.getCurrentStock())
                    .withDescription(sp.getDescription())
                    .withEan13(sp.getEan13())
                    .withMinStock(sp.getMinStock())
                    .withName(sp.getName())
                    .withTargetedStock(sp.getTargetedStock())
                    .withCreatedAt(sp.getCreatedAt())
                    .withUpdatedAt(sp.getUpdatedAt())
                    .withPcb(sp.getPcb())
                    .build());
        }

        return result;
    }

    @XmlElement
    public ListeCourse getListeCourse() {
        return this.listeCourse;
    }

    public void setListeCourse(ListeCourse listeCourse) {
        this.listeCourse = listeCourse;
    }

}
