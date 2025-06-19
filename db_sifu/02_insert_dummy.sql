INSERT INTO tbl_persona (per_nombres, per_apellidos, per_identificacion, per_telefono, per_email, per_direccion, per_fecha_nacimiento, per_genero, per_estado) VALUES
('Juan', 'Pérez', '1700000001', '0991234567', 'juan.perez.user@example.com', 'Calle Ficticia 123, Machachi', '1985-05-10', 'MASCULINO', 'activo'),
('María', 'García', '1700000002', '0987654321', 'maria.g.user@example.com', 'Avenida Imaginaria 456, Tabacundo', '1990-11-22', 'FEMENINO', 'activo'),
('Carlos', 'Rodríguez', '1700000003', '0971122334', 'carlos.r.user@example.com', 'Sector Rural s/n, Ayora', '1978-03-15', 'MASCULINO', 'activo'),
('Ana', 'López', '1700000004', '0968877665', 'ana.l.user@example.com', 'Barrio Central s/n, Sangolquí', '1992-07-01', 'FEMENINO', 'activo'),
('Luis', 'Vargas', '1700000005', '0954433221', 'luis.v.user@example.com', 'Vía a Mindo Km 5, Mindo', '1980-09-28', 'MASCULINO', 'activo'),
('Sofía', 'Castro', '1700000006', '0943344556', 'sofia.c.user@example.com', 'Recinto La Cascada, Pedro Vicente Maldonado', '1995-01-05', 'FEMENINO', 'activo'),
('Roberto', 'Morales', '1700000007', '0932233445', 'roberto.m.user@example.com', 'Av. principal s/n, Calderón', '1975-04-18', 'MASCULINO', 'activo'),
('Elena', 'Torres', '1700000008', '0921122330', 'elena.t.user@example.com', 'Camino Vecinal 10, Aloasí', '1988-12-03', 'FEMENINO', 'activo'),
('Miguel', 'Salazar', '1700000009', '0910011223', 'miguel.s.user@example.com', 'Hacienda El Campo, Tocachi', '1982-06-20', 'MASCULINO', 'activo'),
('Laura', 'Garcés', '1700000010', '0909988776', 'laura.g.user@example.com', 'Barrio Nuevo s/n, Juan Montalvo', '1993-02-14', 'FEMENINO', 'activo');

INSERT INTO tbl_usuario (per_ref_id, usu_nombre_usuario, usu_password_hash, usu_rol, usu_estado) VALUES
(1, 'juan.perez', MD5('1700000001'), 'productor', 'activo'),
(2, 'maria.garcia', MD5('1700000002'), 'productor', 'activo'),
(3, 'carlos.rodriguez', MD5('1700000003'), 'productor', 'activo'),
(4, 'ana.lopez', MD5('1700000004'), 'productor', 'activo'),
(5, 'luis.vargas', MD5('1700000005'), 'productor', 'activo'),
(6, 'sofia.castro', MD5('1700000006'), 'productor', 'activo'),
(7, 'roberto.morales', MD5('1700000007'), 'productor', 'activo'),
(8, 'elena.torres', MD5('1700000008'), 'productor', 'activo'),
(9, 'miguel.salazar', MD5('1700000009'), 'productor', 'activo'),
(10, 'laura.garces', MD5('17000000010'), 'productor', 'activo');


INSERT INTO tbl_productor (pro_nombre_contacto, per_ref_id, pro_municipio, pro_parroquia, pro_tipo_produccion) VALUES
('Juan Pérez',1, 'Mejía', 'Machachi', 'Agrícola'),
('María García', 2, 'Pedro Moncayo', 'Tabacundo', 'Agrícola'),
('Carlos Rodríguez', 3, 'Cayambe', 'Ayora', 'Pecuaria'),
('Ana López', 4, 'Rumiñahui', 'Sangolquí', 'Agrícola'),
('Luis Vargas', 5, 'San Miguel de los Bancos', 'Mindo', 'Mixta'),
('Sofía Castro',6, 'Puerto Quito', 'Pedro Vicente Maldonado', 'Artesanal'),
('Roberto Morales', 7, 'Quito', 'Calderón', 'Pecuaria'),
('Elena Torres',8, 'Mejía', 'Aloasí', 'Agrícola'),
('Miguel Salazar', 9, 'Pedro Moncayo', 'Tocachi', 'Agrícola'),
('Laura Garcés', 10, 'Cayambe', 'Juan Montalvo', 'Mixta');

INSERT INTO tbl_producto (prd_nombre, prd_descripcion, cat_ref_id) VALUES
('tomate riñón', 'tomate común de mesa, ideal para ensaladas', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('papa chola', 'papa andina, de cáscara morada y carne amarilla', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('plátano maduro', 'plátano para cocinar, dulce al madurar', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'FRUTAS')),
('fréjol tierno', 'vainas de fréjol verde, fresco', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'LEGUMBRES')),
('lechuga crespa', 'lechuga verde de hojas rizadas', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('manzana red delicious', 'manzana roja, dulce y crujiente', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'FRUTAS')),
('zanahoria', 'raíz naranja, rica en vitaminas', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('brócoli', 'vegetal verde de flores compactas', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('carne de res (lomo)', 'corte magro de res, ideal para asar', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'CARNES')),
('leche fresca', 'leche entera de vaca, sin procesar', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'LACTEOS')),
('queso fresco', 'queso blanco suave, artesanal', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'LACTEOS')),
('yuca', 'tubérculo harinoso, versátil', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('mandarina', 'cítrico dulce y fácil de pelar', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'FRUTAS')),
('cebolla paiteña', 'cebolla roja, de sabor intenso', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('limón', 'cítrico ácido, esencial en la cocina', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'FRUTAS')),
('carne de cerdo (pernil)', 'pierna de cerdo, ideal para hornear', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'CARNES')),
('mora', 'baya oscura, ideal para jugos y postres', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'FRUTAS')),
('choclo', 'maíz tierno, popular en la gastronomía andina', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('remolacha', 'raíz morada, dulce y nutritiva', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'HORTALIZAS')),
('huevo de campo', 'huevos de gallinas criadas en libertad', (SELECT cat_id FROM tbl_catalogo WHERE cat_nombre = 'LACTEOS'));



INSERT INTO tbl_oferta_producto (pro_ref_id, prd_ref_id, ofe_unidad_medida, ofe_precio, ofe_cantidad_disponible, ofe_url_foto) VALUES
-- Productor 1 (Juan Pérez)
(1, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'tomate riñón'), 'kg', 0.85, 50.00, 'https://picsum.photos/id/10/200/300'),
(1, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'papa chola'), 'saco (50lb)', 18.00, 5.00, 'https://picsum.photos/id/11/200/300'),
(1, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'fréjol tierno'), 'atado', 1.20, 20.00, 'https://picsum.photos/id/12/200/300'),

-- Productor 2 (María García)
(2, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'plátano maduro'), 'unidad', 0.25, 100.00, 'https://picsum.photos/id/13/200/300'),
(2, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'lechuga crespa'), 'unidad', 0.75, 30.00, 'https://picsum.photos/id/14/200/300'),
(2, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'manzana red delicious'), 'kg', 1.50, 40.00, 'https://picsum.photos/id/15/200/300'),

-- Productor 3 (Carlos Rodríguez)
(3, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'carne de res (lomo)'), 'kg', 8.50, 15.00, 'https://picsum.photos/id/16/200/300'),
(3, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'leche fresca'), 'litro', 0.90, 200.00, 'https://picsum.photos/id/17/200/300'),
(3, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'queso fresco'), 'libra', 3.00, 10.00, 'https://picsum.photos/id/18/200/300'),

-- Productor 4 (Ana López)
(4, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'zanahoria'), 'kg', 0.60, 60.00, 'https://picsum.photos/id/19/200/300'),
(4, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'brócoli'), 'unidad', 1.00, 25.00, 'https://picsum.photos/id/20/200/300'),
(4, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'yuca'), 'kg', 0.70, 35.00, 'https://picsum.photos/id/21/200/300'),

-- Productor 5 (Luis Vargas)
(5, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'mandarina'), 'unidad', 0.30, 80.00, 'https://picsum.photos/id/22/200/300'),
(5, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'cebolla paiteña'), 'kg', 0.50, 45.00, 'https://picsum.photos/id/23/200/300'),
(5, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'limón'), 'unidad', 0.10, 150.00, 'https://picsum.photos/id/24/200/300'),

-- Productor 6 (Sofía Castro)
(6, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'mora'), 'libra', 2.50, 12.00, 'https://picsum.photos/id/25/200/300'),
(6, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'choclo'), 'unidad', 0.40, 60.00, 'https://picsum.photos/id/26/200/300'),
(6, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'remolacha'), 'kg', 0.70, 28.00, 'https://picsum.photos/id/27/200/300'),

-- Productor 7 (Roberto Morales)
(7, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'carne de res (lomo)'), 'kg', 8.00, 20.00, 'https://picsum.photos/id/28/200/300'),
(7, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'carne de cerdo (pernil)'), 'kg', 6.00, 18.00, 'https://picsum.photos/id/29/200/300'),
(7, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'leche fresca'), 'litro', 0.85, 180.00, 'https://picsum.photos/id/30/200/300'),

-- Productor 8 (Elena Torres)
(8, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'tomate riñón'), 'kg', 0.90, 45.00, 'https://picsum.photos/id/31/200/300'),
(8, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'papa chola'), 'libra', 0.30, 100.00, 'https://picsum.photos/id/32/200/300'),
(8, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'zanahoria'), 'atado', 0.50, 30.00, 'https://picsum.photos/id/33/200/300'),

-- Productor 9 (Miguel Salazar)
(9, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'plátano maduro'), 'unidad', 0.20, 120.00, 'https://picsum.photos/id/34/200/300'),
(9, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'fréjol tierno'), 'kg', 1.30, 15.00, 'https://picsum.photos/id/35/200/300'),
(9, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'lechuga crespa'), 'unidad', 0.80, 25.00, 'https://picsum.photos/id/36/200/300'),

-- Productor 10 (Laura Garcés)
(10, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'manzana red delicious'), 'libra', 0.70, 50.00, 'https://picsum.photos/id/37/200/300'),
(10, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'queso fresco'), 'kg', 6.50, 8.00, 'https://picsum.photos/id/38/200/300'),
(10, (SELECT prd_id FROM tbl_producto WHERE prd_nombre = 'huevo de campo'), 'cubeta (30 huevos)', 4.00, 10.00, 'https://picsum.photos/id/39/200/300');