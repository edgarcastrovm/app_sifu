package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.repo.AgriProdRepository;
import com.sifu.core.repo.AgricultorRepository;
import com.sifu.core.repo.CategoriaProdRepository;
import com.sifu.core.repo.ProductoRepository;
import com.sifu.core.utils.dto.AgriProdDto;
import com.sifu.core.utils.dto.AgricultorDto;
import com.sifu.core.utils.dto.CategoriaProdDto;
import com.sifu.core.utils.dto.ProductoDto;
import com.sifu.core.utils.entity.AgriProd;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.CategoriaProd;
import com.sifu.core.utils.entity.Producto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService implements IShopService {

    private static final Logger log = LogManager.getLogger("com.sifu.core.service.dominio.IProductoService");

    private final ProductoRepository productoRepository;
    private final CategoriaProdRepository categoriaProdRepository;
    private final AgricultorRepository agricultorRepository;
    private final AgriProdRepository agriProdRepository;

    public ShopService(ProductoRepository productoRepository, CategoriaProdRepository categoriaProdRepository, AgricultorRepository agricultorRepository, AgriProdRepository agriProdRepository) {
        this.productoRepository = productoRepository;
        this.categoriaProdRepository = categoriaProdRepository;
        this.agricultorRepository = agricultorRepository;
        this.agriProdRepository = agriProdRepository;
    }

    public ApiResponse<List<CategoriaProdDto>> listarCategorias() {
        List<CategoriaProd> itemsBase = categoriaProdRepository.findAll();
        List<CategoriaProdDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(CategoriaProdDto::new).collect(Collectors.toList());
            log.info("Categorias encontradas: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron categorias");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND,items);
        }
    }

    public ApiResponse<List<ProductoDto>> listarProductos() {
        List<Producto> itemsBase = productoRepository.findAll();
        List<ProductoDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(ProductoDto::new).collect(Collectors.toList());
            log.info("Productos encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron productos");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND,items);
        }
    }


    public ApiResponse<List<AgriProdDto>> listarProductosAgricultor(Integer id) {
        List<AgriProd> itemsBase = agriProdRepository.findByAgricultor_Id(id);
        List<AgriProdDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(AgriProdDto::new).collect(Collectors.toList());
            log.info("Productos encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron productos");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND,items);
        }
    }

    public ApiResponse<List<AgricultorDto>> listarAgricultores() {
        List<Agricultor> itemsBase = agricultorRepository.findAll();
        List<AgricultorDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(AgricultorDto::new).collect(Collectors.toList());
            log.info("Agricultores encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron Agricultores");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND,items);
        }
    }
}
