package co.sena.jiguales.model;

import java.math.BigDecimal;

public class Item {
    private Long id;
    private String sku;
    private String name;
    private String unit;
    private BigDecimal minStock;
    private BigDecimal qtyOnHand;
    private BigDecimal avgCost;
    private boolean active;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getMinStock() { return minStock; }
    public void setMinStock(BigDecimal minStock) { this.minStock = minStock; }
    public BigDecimal getQtyOnHand() { return qtyOnHand; }
    public void setQtyOnHand(BigDecimal qtyOnHand) { this.qtyOnHand = qtyOnHand; }
    public BigDecimal getAvgCost() { return avgCost; }
    public void setAvgCost(BigDecimal avgCost) { this.avgCost = avgCost; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", minStock=" + minStock +
                ", qtyOnHand=" + qtyOnHand +
                ", avgCost=" + avgCost +
                ", active=" + active +
                '}';
    }
}
