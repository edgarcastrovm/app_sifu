package com.sifu.core.constants;

public interface QueryConstants {
    String SQL_VENTAS = """
        SELECT 
            f.id AS factura_id,
            f.fecha_creacion AS fecha_venta,
            f.metodo_pago,
            f.total AS total_factura,
            CONCAT(p.nombre, ' ', p.apellido) AS cliente,
            p.cedula AS cedula_cliente,
            p.correo AS email_cliente,
            df.cantidad,
            pr.nombre AS producto,
            cp.nombre AS categoria,
            pr.precio AS precio_unitario,
            df.subtotal AS subtotal_item,
            CONCAT(pa.nombre, ' ', pa.apellido) AS agricultor,
            s.cantidad AS stock_disponible,
            s.uni_medida AS unidad_medida
        FROM 
            tbl_factura f
        JOIN tbl_cliente c ON f.cliente_id = c.id
        JOIN tbl_persona p ON c.persona_id = p.id
        JOIN tbl_det_fact df ON f.id = df.factura_id
        JOIN tbl_agri_prod ap ON df.producto_id = ap.id
        JOIN tbl_producto pr ON ap.producto_id = pr.id
        JOIN tbl_cat_prod cp ON pr.categoria_id = cp.id
        JOIN tbl_agricultor a ON ap.agricultor_id = a.id
        JOIN tbl_persona pa ON a.persona_id = pa.id
        LEFT JOIN tbl_stock s ON ap.id = s.agri_prod_id
        ORDER BY f.fecha_creacion DESC, f.id, pr.nombre
    """;

}
