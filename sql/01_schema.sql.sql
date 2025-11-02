
-- 1) Base de datos
CREATE DATABASE IF NOT EXISTS jiguales_java
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 2) Usuario local con permisos SOLO sobre esta BD
CREATE USER IF NOT EXISTS 'jiguales_user'@'localhost' IDENTIFIED BY 'Jiguales_1234';
GRANT ALL PRIVILEGES ON jiguales_java.* TO 'jiguales_user'@'localhost';
FLUSH PRIVILEGES;

-- 3) Tabla principal 
USE jiguales_java;

CREATE TABLE IF NOT EXISTS items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sku VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(160) NOT NULL,
  unit VARCHAR(16) NOT NULL,
  min_stock DECIMAL(14,3) NOT NULL DEFAULT 0,
  qty_on_hand DECIMAL(14,3) NOT NULL DEFAULT 0,
  avg_cost DECIMAL(14,4) NOT NULL DEFAULT 0,
  active TINYINT(1) NOT NULL DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 4) Inserción de ejemplo (puedes borrar estas líneas si no quieres datos)
INSERT INTO items (sku, name, unit, min_stock, qty_on_hand, avg_cost, active)
VALUES ('HAR-0001', 'Harina de trigo', 'kg', 10, 50, 2500.0000, 1)
ON DUPLICATE KEY UPDATE name=VALUES(name);
