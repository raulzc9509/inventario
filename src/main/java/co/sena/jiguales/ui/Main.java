package co.sena.jiguales.ui;

import co.sena.jiguales.dao.ItemDao;
import co.sena.jiguales.dao.ItemJdbcDao;
import co.sena.jiguales.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final ItemDao itemDao = new ItemJdbcDao();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("""
                ==== Inventario Panaderia Jiguales ====
                1. Crear ítem
                2. Listar ítems
                3. Actualizar ítem
                4. Eliminar ítem
                5. Buscar por SKU
                0. Salir
                """);
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> crear(sc);
                case "2" -> listar();
                case "3" -> actualizar(sc);
                case "4" -> eliminar(sc);
                case "5" -> buscarSku(sc);
                case "0" -> { System.out.println("¡Listo, chao!"); return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void crear(Scanner sc) {
        Item i = new Item();
        System.out.print("SKU: "); i.setSku(sc.nextLine());
        System.out.print("Nombre: "); i.setName(sc.nextLine());
        System.out.print("Unidad (kg,u,L): "); i.setUnit(sc.nextLine());
        i.setMinStock(new BigDecimal("0"));
        i.setQtyOnHand(new BigDecimal("0"));
        i.setAvgCost(new BigDecimal("0"));
        i.setActive(true);
        i = itemDao.insert(i);
        System.out.println("Creado con id=" + i.getId());
    }

    private static void listar() {
        List<Item> items = itemDao.findAll();
        if (items.isEmpty()) System.out.println("No hay ítems");
        items.forEach(System.out::println);
    }

    private static void actualizar(Scanner sc) {
        System.out.print("ID a actualizar: ");
        Long id = Long.valueOf(sc.nextLine());
        Optional<Item> opt = itemDao.findById(id);
        if (opt.isEmpty()) { System.out.println("No existe"); return; }
        Item i = opt.get();
        System.out.print("Nuevo nombre (" + i.getName() + "): ");
        String n = sc.nextLine(); if (!n.isBlank()) i.setName(n);
        System.out.print("Nueva unidad (" + i.getUnit() + "): ");
        String u = sc.nextLine(); if (!u.isBlank()) i.setUnit(u);
        System.out.print("Activo (true/false) [" + i.isActive() + "]: ");
        String act = sc.nextLine(); if (!act.isBlank()) i.setActive(Boolean.parseBoolean(act));
        boolean ok = itemDao.update(i);
        System.out.println(ok ? "Actualizado" : "No se actualizó");
    }

    private static void eliminar(Scanner sc) {
        System.out.print("ID a eliminar: ");
        Long id = Long.valueOf(sc.nextLine());
        boolean ok = itemDao.delete(id);
        System.out.println(ok ? "Eliminado" : "No se eliminó");
    }

    private static void buscarSku(Scanner sc) {
        System.out.print("SKU a buscar: ");
        String sku = sc.nextLine();
        itemDao.findBySku(sku).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No encontrado")
        );
    }
}
