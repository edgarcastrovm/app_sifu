-- DROP TABLE IF EXISTS tbl_oferta_producto CASCADE;
-- DROP TABLE IF EXISTS tbl_producto CASCADE;
-- DROP TABLE IF EXISTS tbl_catalogo CASCADE;
-- DROP TABLE IF EXISTS tbl_productor CASCADE;
-- DROP TABLE IF EXISTS tbl_usuario CASCADE;
-- DROP TABLE IF EXISTS tbl_persona CASCADE;

-- 1. Creación de la tabla tbl_persona
CREATE TABLE tbl_persona (
    per_id SERIAL PRIMARY KEY,
    per_nombres VARCHAR(100) NOT NULL,
    per_apellidos VARCHAR(100) NOT NULL,
    per_identificacion VARCHAR(20) UNIQUE, -- Cédula o pasaporte
    per_telefono VARCHAR(20),
    per_email VARCHAR(100) UNIQUE,
    per_direccion VARCHAR(255),
    per_fecha_nacimiento DATE,
    per_genero VARCHAR(10), -- 'MASCULINO', 'FEMENINO', 'OTRO'
    per_fecha_registro TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    per_estado VARCHAR(20) DEFAULT 'activo' -- 'activo', 'inactivo'
);

-- 2. Creación de la tabla tbl_usuario
CREATE TABLE tbl_usuario (
    usu_id SERIAL PRIMARY KEY, -- Esto actúa como la PK y FK al mismo tiempo
    per_ref_id INTEGER NOT NULL, -- Clave foránea a tbl_persona
    usu_nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    usu_password_hash VARCHAR(255) NOT NULL,
    usu_rol VARCHAR(50) DEFAULT 'cliente', -- 'cliente', 'admin', 'gestor', etc.
    usu_fecha_ultimo_login TIMESTAMP WITH TIME ZONE,
    usu_estado VARCHAR(20) DEFAULT 'activo', -- 'activo', 'inactivo', 'bloqueado'
    usu_fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_persona
        FOREIGN KEY (per_ref_id)
        REFERENCES tbl_persona (per_id)
        ON DELETE CASCADE -- Si la persona se elimina, su registro de usuario también
);

-- Índices (opcionales, pero buena práctica)
CREATE INDEX idx_persona_identificacion ON tbl_persona (per_identificacion);
CREATE INDEX idx_persona_email ON tbl_persona (per_email);
CREATE INDEX idx_usuario_nombre_usuario ON tbl_usuario (usu_nombre_usuario);
CREATE INDEX idx_usuario_rol ON tbl_usuario (usu_rol);

-- 1. tabla tbl_productor: almacena la información de cada productor.
CREATE TABLE tbl_productor (
    pro_id SERIAL PRIMARY KEY,
    per_ref_id INTEGER NOT NULL, -- Clave foránea a tbl_persona
    pro_nombre_contacto VARCHAR(100) NOT NULL,
    pro_municipio VARCHAR(100) NOT NULL,
    pro_parroquia VARCHAR(100),
    pro_tipo_produccion VARCHAR(50) NOT NULL,
    pro_fecha_registro TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_productor_persona
        FOREIGN KEY (per_ref_id)
        REFERENCES tbl_persona (per_id)
        ON DELETE CASCADE -- Si la persona se elimina, su registro de usuario también
);

-- 2. tabla tbl_catalogo: para las categorías de productos (ej. "legumbres", "frutas").
CREATE TABLE tbl_catalogo (
    cat_id SERIAL PRIMARY KEY,
    cat_nombre VARCHAR(100) NOT NULL UNIQUE,
    
    cat_descripcion TEXT,
    cat_padre_id INTEGER, -- Puede ser NULL si es una categoría de nivel superior
    cat_estado VARCHAR(20) DEFAULT 'activo', -- 'activo', 'inactivo'
    CONSTRAINT fk_catalogo_padre
        FOREIGN KEY (cat_padre_id)
        REFERENCES tbl_catalogo (cat_id)
        ON DELETE RESTRICT
);

-- 3. tabla tbl_producto: define el "tipo" de producto general (ej. "tomate", "leche").
CREATE TABLE tbl_producto (
    prd_id SERIAL PRIMARY KEY,
    prd_nombre VARCHAR(100) NOT NULL UNIQUE,
    prd_descripcion TEXT,
    prd_url_foto VARCHAR(500), -- URL de la foto del producto
    cat_ref_id INTEGER NOT NULL, -- Clave foránea a tbl_catalogo
    prd_fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_catalogo_producto
        FOREIGN KEY (cat_ref_id)
        REFERENCES tbl_catalogo (cat_id)
        ON DELETE RESTRICT
);

-- 4. tabla tbl_oferta_producto: tabla intermedia que vincula productor con producto específico
-- y sus detalles de oferta (precio, cantidad, foto). Esta es la tabla para el marketplace.
CREATE TABLE tbl_oferta_producto (
    ofe_id SERIAL PRIMARY KEY,
    pro_ref_id INTEGER NOT NULL, -- Clave foránea a tbl_productor
    prd_ref_id INTEGER NOT NULL, -- Clave foránea a tbl_producto (tipo de producto)
    ofe_unidad_medida VARCHAR(20) NOT NULL,
    ofe_precio NUMERIC(10, 2) NOT NULL CHECK (ofe_precio > 0),
    ofe_cantidad_disponible NUMERIC(10, 2) NOT NULL CHECK (ofe_cantidad_disponible >= 0),
    ofe_url_foto VARCHAR(255),
    ofe_estado VARCHAR(20) DEFAULT 'activo', -- 'activo', 'inactivo', 'vendido'
    ofe_fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    ofe_ultima_actualizacion TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_oferta_productor
        FOREIGN KEY (pro_ref_id)
        REFERENCES tbl_productor (pro_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_oferta_producto
        FOREIGN KEY (prd_ref_id)
        REFERENCES tbl_producto (prd_id)
        ON DELETE RESTRICT,
    CONSTRAINT uq_oferta_productor_producto_unidad UNIQUE (pro_ref_id, prd_ref_id, ofe_unidad_medida)
);

-- Índices para mejorar el rendimiento de las búsquedas y las uniones (JOINs).
CREATE INDEX idx_productor_municipio ON tbl_productor (pro_municipio);
CREATE INDEX idx_producto_nombre ON tbl_producto (prd_nombre);
CREATE INDEX idx_oferta_productor_ref_id ON tbl_oferta_producto (pro_ref_id);
CREATE INDEX idx_oferta_producto_ref_id ON tbl_oferta_producto (prd_ref_id);
CREATE INDEX idx_catalogo_nombre ON tbl_catalogo (cat_nombre);


-- Inserción de datos por defecto en tbl_catalogo
INSERT INTO tbl_catalogo (cat_nombre, cat_descripcion) VALUES
('LEGUMBRES', 'Verduras en vaina, como guisantes, frijoles y lentejas.'),
('FRUTAS', 'Productos dulces o ácidos obtenidos de plantas, como manzanas, naranjas y plátanos.'),
('HORTALIZAS', 'Plantas cultivadas en huertos, incluyendo hojas, tallos, raíces y flores comestibles.'),
('CARNES', 'Productos cárnicos de origen animal.'),
('LACTEOS', 'Productos derivados de la leche.');