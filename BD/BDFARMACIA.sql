CREATE DATABASE PeruFarma;
USE PeruFarma;

-- =======================================================
--        BASE DE DATOS - GESTOR DE VENTAS E INVENTARIO
-- =======================================================

-- ===================== ROL =====================
CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL,
    descripcion VARCHAR(150) NOT NULL
);

-- ===================== USUARIO =====================
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(100),
    apellido VARCHAR(50),
    dni CHAR(8),
    telefono VARCHAR(15),
    estado BOOLEAN DEFAULT 1,
    nombre VARCHAR(50),
    contrasena VARCHAR(100),
    nombre_usuario VARCHAR(50),
    direccion VARCHAR(150),
    fecha_creacion DATE,
    id_rol INT,
    FOREIGN KEY (id_rol) REFERENCES Rol(id_rol)
);

-- ===================== CLIENTE =====================
CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(8),
    nombre VARCHAR(100),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion VARCHAR(150),
    fecha_registro DATE
);

-- ===================== CATEGORIA PRODUCTO =====================
CREATE TABLE Categoria_Producto (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100),
    descripcion VARCHAR(150)
);

-- ===================== PROVEEDOR =====================
CREATE TABLE Proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    ruc CHAR(11),
    empresa VARCHAR(100),
    representante VARCHAR(100),
    fecha_registro DATE,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion VARCHAR(150)
);

-- ===================== PRODUCTO =====================
CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    presentacion VARCHAR(100),
    contenido VARCHAR(100),
    laboratorio VARCHAR(100),
    tipo VARCHAR(100), -- analgésico, antibiótico, etc.
    precio_compra DECIMAL(10,2),
    precio_venta DECIMAL(10,2),
    precio_con_igv DECIMAL(10,2),
    fecha_vencimiento DATE,
    llevarreceta BOOLEAN,
    registro_sanitario VARCHAR(100),
    lote VARCHAR(50),
    estado BOOLEAN DEFAULT 1,
    id_categoria INT,
    id_proveedor INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria_Producto(id_categoria),
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor)
);

-- ===================== INVENTARIO =====================
CREATE TABLE Inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT UNIQUE,
    stock INT,
    stock_minimo INT,
    dias_antesvencimiento INT,
    estado_stock VARCHAR(50),
    fecha_actualizacion DATE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

-- ===================== METODO DE PAGO =====================
CREATE TABLE Metodo_Pago (
    id_metodo_pago INT AUTO_INCREMENT PRIMARY KEY,
    nombre_metodo VARCHAR(50),
    descripcion VARCHAR(150)
);

-- ===================== RECETA MEDICA =====================
CREATE TABLE Receta_Medica (
    id_receta_medica INT AUTO_INCREMENT PRIMARY KEY,
    nombre_paciente VARCHAR(100),
    dni_paciente CHAR(8),
    nombre_medico VARCHAR(100),
    cmp_medico VARCHAR(20),
    fecha_emision DATE,
    observaciones TEXT,
    estado VARCHAR(50)
);


-- ===================== COMPROBANTE =====================
CREATE TABLE Comprobante (
    id_comprobante INT AUTO_INCREMENT PRIMARY KEY,
    tipo_comprobante VARCHAR(50),
    nro_comprobante VARCHAR(50),
    fecha_emision DATE,
    id_receta_medica INT NULL,
    id_venta INT NULL,
    FOREIGN KEY (id_receta_medica) REFERENCES Receta_Medica(id_receta_medica)
);

-- ===================== VENTA =====================
CREATE TABLE Venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATE,
    hora_venta TIME,
    id_usuario INT,
    id_comprobante INT NULL,
    id_metodo_pago INT,
    id_cliente INT NULL,
    total DECIMAL(10,2),
    estado VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_metodo_pago) REFERENCES Metodo_Pago(id_metodo_pago),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

ALTER TABLE Venta
ADD CONSTRAINT fk_venta_comprobante
FOREIGN KEY (id_comprobante) REFERENCES Comprobante(id_comprobante);

ALTER TABLE Comprobante
ADD CONSTRAINT fk_comprobante_venta
FOREIGN KEY (id_venta) REFERENCES Venta(id_venta);



-- ===================== DETALLE VENTA =====================
CREATE TABLE Detalle_Venta (
    id_detalle_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    id_venta INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    precio_igv DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto),
    FOREIGN KEY (id_venta) REFERENCES Venta(id_venta)
);

-- ===================== RECETA VENTA =====================
CREATE TABLE Receta_Venta (
    id_receta_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_venta INT,
    id_receta INT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_venta) REFERENCES Venta(id_venta),
    FOREIGN KEY (id_receta) REFERENCES Receta_Medica(id_receta_medica)
);

-- ===================== REPORTE =====================
CREATE TABLE Reporte (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    tipo_reporte VARCHAR(100),
    fecha_generacion DATE,
    id_usuario INT,
    ruta_pdf VARCHAR(200),
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- =======================================================
--        FIN DEL SCRIPT
-- =======================================================
DROP DATABASE IF EXISTS PeruFarma;



---------------------------------------------------------------------------------


INSERT INTO Rol (nombre_rol, descripcion)
VALUES 
('Administrador', 'Acceso total al sistema'),
('Empleado', 'Gestión de ventas y clientes');

INSERT INTO Usuario (correo, apellido, dni, telefono, estado, nombre, contrasena, nombre_usuario, direccion, fecha_creacion, id_rol)
VALUES
('admin@perufarma.com', 'Pérez', '12345678', '987654321', 1, 'Juan', '12345', 'admin', 'Av. Lima 101', CURDATE(), 1);

INSERT INTO Cliente (dni, nombre, telefono, correo, direccion, fecha_registro)
VALUES
('87654321', 'Carlos Ramírez', '999888777', 'carlos@gmail.com', 'Av. Arequipa 500', CURDATE()),
('45678912', 'María Torres', '955444222', 'maria@gmail.com', 'Jr. Lima 123', CURDATE());

INSERT INTO Categoria_Producto (nombre_categoria, descripcion)
VALUES 
('Analgésico', 'Medicamentos para el dolor'),
('Antibiótico', 'Medicamentos contra infecciones');

INSERT INTO Proveedor (ruc, empresa, representante, fecha_registro, telefono, correo, direccion)
VALUES
('12345678901', 'Farmacorp', 'Luis Gómez', CURDATE(), '987123456', 'contacto@farmacorp.com', 'Av. Perú 1001');

INSERT INTO Categoria_Producto (nombre_categoria, descripcion)
VALUES 
('Analgésico', 'Medicamentos para el dolor'),
('Antibiótico', 'Medicamentos contra infecciones');

INSERT INTO Proveedor (ruc, empresa, representante, fecha_registro, telefono, correo, direccion)
VALUES
('12345678901', 'Farmacorp', 'Luis Gómez', CURDATE(), '987123456', 'contacto@farmacorp.com', 'Av. Perú 1001');



INSERT INTO Inventario (id_producto, stock, stock_minimo, dias_antesvencimiento, estado_stock, fecha_actualizacion)
VALUES
(1, 30, 10, 180, 'Normal', CURDATE()),
(2, 3, 5, 90, 'Bajo', CURDATE());

INSERT INTO Metodo_Pago (nombre_metodo, descripcion)
VALUES 
('Efectivo', 'Pago directo en efectivo'),
('Tarjeta', 'Pago con tarjeta de crédito o débito');

INSERT INTO Comprobante (tipo_comprobante, nro_comprobante, fecha_emision)
VALUES
('Boleta', 'B001-0001', CURDATE()),
('Factura', 'F001-0002', CURDATE());

INSERT INTO Venta (fecha_venta, hora_venta, id_usuario, id_comprobante, id_metodo_pago, id_cliente, total, estado)
VALUES
(CURDATE(), '10:30:00', 1, 1, 1, 1, 20.00, 'Completado'),
(CURDATE(), '11:45:00', 1, 2, 2, 2, 35.00, 'Completado');

INSERT INTO Detalle_Venta (id_producto, id_venta, cantidad, precio_unitario, precio_igv, subtotal)
VALUES
(1, 1, 5, 2.00, 0.36, 10.00),
(2, 1, 2, 5.00, 0.90, 10.00),
(1, 2, 3, 2.00, 0.36, 6.00),
(2, 2, 5, 5.00, 0.90, 29.00);



INSERT INTO Categoria_Producto (nombre_categoria, descripcion)
VALUES 
('Analgésico', 'Medicamentos para el dolor'),
('Antibiótico', 'Medicamentos contra infecciones');

INSERT INTO Proveedor (ruc, empresa, representante, fecha_registro, telefono, correo, direccion)
VALUES
('12345678901', 'Farmacorp', 'Luis Gómez', CURDATE(), '987123456', 'contacto@farmacorp.com', 'Av. Perú 1001');

INSERT INTO Producto (nombre, presentacion, contenido, laboratorio, tipo, precio_compra, precio_venta, precio_con_igv,
                      fecha_vencimiento, llevarreceta, registro_sanitario, lote, estado, id_categoria, id_proveedor)
VALUES
('Paracetamol 500mg', 'Caja', '10 tabletas', 'Roche', 'Analgésico', 1.50, 2.00, 2.36, DATE_ADD(CURDATE(), INTERVAL 180 DAY), 0, 'RS123', 'L001', 1, 1, 1),
('Amoxicilina 500mg', 'Caja', '12 cápsulas', 'Bayer', 'Antibiótico', 3.50, 5.00, 5.90, DATE_ADD(CURDATE(), INTERVAL 90 DAY), 1, 'RS456', 'L002', 1, 2, 1);


----------------------------------

INSERT INTO Categoria_Producto (nombre_categoria, descripcion)
VALUES 
('Analgésico', 'Medicamentos para el dolor'),
('Antibiótico', 'Medicamentos contra infecciones');

INSERT INTO Proveedor (ruc, empresa, representante, fecha_registro, telefono, correo, direccion)
VALUES
('12345678901', 'Farmacorp', 'Luis Gómez', CURDATE(), '987123456', 'contacto@farmacorp.com', 'Av. Perú 1001');

INSERT INTO Producto (nombre, presentacion, contenido, laboratorio, tipo, precio_compra, precio_venta, precio_con_igv,
                      fecha_vencimiento, llevarreceta, registro_sanitario, lote, estado, id_categoria, id_proveedor)
VALUES
('Paracetamol 500mg', 'Caja', '10 tabletas', 'Roche', 'Analgésico', 1.50, 2.00, 2.36, DATE_ADD(CURDATE(), INTERVAL 180 DAY), 0, 'RS123', 'L001', 1, 1, 1),
('Amoxicilina 500mg', 'Caja', '12 cápsulas', 'Bayer', 'Antibiótico', 3.50, 5.00, 5.90, DATE_ADD(CURDATE(), INTERVAL 90 DAY), 1, 'RS456', 'L002', 1, 2, 1),
('Ibuprofeno 400mg', 'Frasco', '100ml', 'Pfizer', 'Analgésico', 2.50, 3.50, 4.13, DATE_ADD(CURDATE(), INTERVAL 300 DAY), 0, 'RS789', 'L003', 1, 1, 1),
('Cefalexina 500mg', 'Caja', '10 cápsulas', 'Sanofi', 'Antibiótico', 4.00, 6.00, 7.08, DATE_ADD(CURDATE(), INTERVAL 20 DAY), 1, 'RS999', 'L004', 1, 2, 1);

INSERT INTO Inventario (id_producto, stock, stock_minimo, dias_antesvencimiento, estado_stock, fecha_actualizacion)
VALUES 
(1, 30, 10, 180, 'Normal', CURDATE()),  -- Stock suficiente
(2, 3, 5, 90, 'Bajo', CURDATE()),       -- Stock bajo
(3, 2, 5, 300, 'Crítico', CURDATE()),   -- Muy bajo
(4, 15, 10, 20, 'Normal', CURDATE());   -- Suficiente



SELECT * FROM PRODUCTO


INSERT INTO Producto (
    nombre, presentacion, contenido, laboratorio, tipo, 
    precio_compra, precio_venta, precio_con_igv, 
    fecha_vencimiento, llevarreceta, registro_sanitario, 
    lote, estado, id_categoria, id_proveedor
)
VALUES (
    'Jarabe para la tos 100ml',
    'Frasco',
    '100ml',
    'MK',
    'Expectorante',
    8.50,
    12.00,
    14.16,
    DATE_SUB(CURDATE(), INTERVAL 10 DAY), -- 🔴 VENCIDO hace 10 días
    0,
    'RSV-001',
    'L009',
    1,
    1,
    1
);
SELECT * FROM PRODUCTO

INSERT INTO Inventario (id_producto, stock, stock_minimo, dias_antesvencimiento, estado_stock, fecha_actualizacion)
VALUES 
(7, 15, 10, 10, 'Normal', CURDATE());


SELECT * FROM INVENTARIO

---------------------


DELIMITER //

CREATE TRIGGER trg_actualizar_estado_stock
BEFORE UPDATE ON Inventario
FOR EACH ROW
BEGIN
    IF NEW.stock < 10 THEN
        SET NEW.estado_stock = 'Crítico';
    ELSEIF NEW.stock < 30 THEN
        SET NEW.estado_stock = 'Bajo';
    ELSE
        SET NEW.estado_stock = 'Normal';
    END IF;
END;
//

DELIMITER ;

----------------------

DELIMITER //

CREATE TRIGGER trg_insertar_estado_stock
BEFORE INSERT ON Inventario
FOR EACH ROW
BEGIN
    IF NEW.stock < 10 THEN
        SET NEW.estado_stock = 'Crítico';
    ELSEIF NEW.stock < 30 THEN
        SET NEW.estado_stock = 'Bajo';
    ELSE
        SET NEW.estado_stock = 'Normal';
    END IF;
END;
//

DELIMITER ;

SELECT *fROM inventario;
SELECT *fROM producto
select *from detalle_venta
SELECT *fROM cliente
SELECT *fROM comprobante