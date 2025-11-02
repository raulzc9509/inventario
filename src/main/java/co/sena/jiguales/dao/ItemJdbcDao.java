package co.sena.jiguales.dao;

import co.sena.jiguales.config.ConnectionFactory;
import co.sena.jiguales.model.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemJdbcDao implements ItemDao {

    @Override
    public Item insert(Item i) {
        final String sql = "INSERT INTO items (sku,name,unit,min_stock,qty_on_hand,avg_cost,active) VALUES (?,?,?,?,?,?,?)";
        try (Connection cn = ConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, i.getSku());
            ps.setString(2, i.getName());
            ps.setString(3, i.getUnit());
            ps.setBigDecimal(4, n(i.getMinStock()));
            ps.setBigDecimal(5, n(i.getQtyOnHand()));
            ps.setBigDecimal(6, n(i.getAvgCost()));
            ps.setBoolean(7, i.isActive());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) i.setId(rs.getLong(1));
            }
            return i;
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando Item", e);
        }
    }

    @Override
    public boolean update(Item i) {
        final String sql = "UPDATE items SET sku=?, name=?, unit=?, min_stock=?, qty_on_hand=?, avg_cost=?, active=? WHERE id=?";
        try (Connection cn = ConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, i.getSku());
            ps.setString(2, i.getName());
            ps.setString(3, i.getUnit());
            ps.setBigDecimal(4, n(i.getMinStock()));
            ps.setBigDecimal(5, n(i.getQtyOnHand()));
            ps.setBigDecimal(6, n(i.getAvgCost()));
            ps.setBoolean(7, i.isActive());
            ps.setLong(8, i.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando Item", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        final String sql = "DELETE FROM items WHERE id=?";
        try (Connection cn = ConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando Item", e);
        }
    }

    @Override
    public Optional<Item> findById(Long id) {
        final String sql = "SELECT * FROM items WHERE id=?";
        try (Connection cn = ConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error consultando por id", e);
        }
    }

    @Override
    public Optional<Item> findBySku(String sku) {
        final String sql = "SELECT * FROM items WHERE sku=?";
        try (Connection cn = ConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, sku);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error consultando por SKU", e);
        }
    }

    @Override
    public List<Item> findAll() {
        final String sql = "SELECT * FROM items ORDER BY name";
        try (Connection cn = ConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Item> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException e) {
            throw new RuntimeException("Error listando Items", e);
        }
    }

    private Item map(ResultSet rs) throws SQLException {
        Item i = new Item();
        i.setId(rs.getLong("id"));
        i.setSku(rs.getString("sku"));
        i.setName(rs.getString("name"));
        i.setUnit(rs.getString("unit"));
        i.setMinStock(rs.getBigDecimal("min_stock"));
        i.setQtyOnHand(rs.getBigDecimal("qty_on_hand"));
        i.setAvgCost(rs.getBigDecimal("avg_cost"));
        i.setActive(rs.getBoolean("active"));
        return i;
    }

    private BigDecimal n(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }
}
