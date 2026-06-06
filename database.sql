/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Elizabeth
 * Created: 6 jun. 2026

 */

-- Crear base de datos
CREATE DATABASE RestauranteMerlin;
GO

-- Usar la base de datos
USE RestauranteMerlin;
GO

-- Tabla Usuario
CREATE TABLE Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    estado VARCHAR(20) DEFAULT 'activo'
);
GO

-- Tabla Empleado
CREATE TABLE Empleado (
    id_empleado INT IDENTITY(1,1) PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    horario VARCHAR(50),
    id_usuario INT UNIQUE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);
GO

-- Tabla Cliente
CREATE TABLE Cliente (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100)
);
GO

-- Tabla Mesa
CREATE TABLE Mesa (
    id_mesa INT IDENTITY(1,1) PRIMARY KEY,
    numero_mesa INT NOT NULL,
    capacidad INT NOT NULL,
    estado VARCHAR(20) NOT NULL
);
GO

-- Tabla Categoria
CREATE TABLE Categoria (
    id_categoria INT IDENTITY(1,1) PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200)
);
GO

-- Tabla Producto
CREATE TABLE Producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);
GO

-- Tabla Pedido
CREATE TABLE Pedido (
    id_pedido INT IDENTITY(1,1) PRIMARY KEY,
    fecha_pedido DATETIME DEFAULT GETDATE(),
    estado VARCHAR(30),
    total DECIMAL(10,2),
    id_cliente INT,
    id_mesa INT,
    id_empleado INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_mesa) REFERENCES Mesa(id_mesa),
    FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado)
);
GO

-- Tabla DetallePedido
CREATE TABLE DetallePedido (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    id_pedido INT,
    id_producto INT,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);
GO

-- Tabla Pago
CREATE TABLE Pago (
    id_pago INT IDENTITY(1,1) PRIMARY KEY,
    fecha_pago DATETIME DEFAULT GETDATE(),
    monto DECIMAL(10,2),
    metodo_pago VARCHAR(30),
    id_pedido INT UNIQUE,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
);
GO

-- Tabla Reserva
CREATE TABLE Reserva (
    id_reserva INT IDENTITY(1,1) PRIMARY KEY,
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL,
    cantidad_personas INT NOT NULL,
    estado VARCHAR(20),
    id_cliente INT,
    id_mesa INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_mesa) REFERENCES Mesa(id_mesa)
);
GO

-- Datos de prueba
INSERT INTO Usuario (usuario, contraseña, rol, estado) VALUES
('admin', 'admin123', 'administrador', 'activo'),
('mesero1', 'mesero123', 'mesero', 'activo'),
('cocinero1', 'cocina123', 'cocinero', 'activo');
GO

INSERT INTO Mesa (numero_mesa, capacidad, estado) VALUES
(1, 2, 'disponible'),
(2, 4, 'disponible'),
(3, 6, 'disponible'),
(4, 4, 'ocupada'),
(5, 2, 'reservada');
GO

INSERT INTO Categoria (nombre_categoria, descripcion) VALUES
('Entradas', 'Platos para compartir'),
('Platos marinos', 'Ceviches y mariscos'),
('Bebidas', 'Gaseosas y cervezas'),
('Postres', 'Dulces');
GO

INSERT INTO Producto (nombre_producto, descripcion, precio, stock, id_categoria) VALUES
('Ceviche Norteño', 'Pescado fresco con limón', 28.50, 20, 2),
('Chicha Morada', 'Bebida tradicional', 7.00, 50, 3),
('Suspiro a la limeña', 'Postre de manjar', 12.00, 25, 4);
GO

INSERT INTO Cliente (nombres, telefono, correo) VALUES
('Carlos Pérez', '999111222', 'carlos@email.com'),
('María López', '999333444', 'maria@email.com');
GO

INSERT INTO Empleado (nombres, apellidos, cargo, telefono, horario, id_usuario) VALUES
('Jean Carlos', 'Huanca Borja', 'Administrador', '987654321', 'Lun-Vie 9am-6pm', 1),
('Soledad', 'Santiago Chavez', 'Mesera', '987654322', 'Lun-Dom 11am-5pm', 2),
('Leonel', 'Vargas Curisinche', 'Cocinero', '987654323', 'Mar-Dom 2pm-10pm', 3);
GO

PRINT '✅ Script completado';
GO