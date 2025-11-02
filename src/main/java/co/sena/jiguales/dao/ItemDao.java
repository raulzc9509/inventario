package co.sena.jiguales.dao;

import co.sena.jiguales.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDao {
    Item insert(Item i);
    boolean update(Item i);
    boolean delete(Long id);
    Optional<Item> findById(Long id);
    Optional<Item> findBySku(String sku);
    List<Item> findAll();
}
