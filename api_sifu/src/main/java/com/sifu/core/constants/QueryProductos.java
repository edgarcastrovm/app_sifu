package com.sifu.core.constants;

public interface QueryProductos {

	String SQL_PRODUCTOS = """
			SELECT
				SUM(st.cantidad) AS cantidad,
				pr.nombre AS producto,
				cp.nombre AS categoria,
				pr.precio AS precioUnitario
			FROM
				tbl_producto pr
				INNER JOIN tbl_agri_prod ap ON ap.producto_id = pr.id
				INNER JOIN tbl_cat_prod cp ON cp.id = pr.categoria_id
				LEFT JOIN tbl_stock st ON st.agri_prod_id  = ap.id
			GROUP BY pr.id, pr.nombre, cp.nombre, pr.precio;
			
			""";

}
