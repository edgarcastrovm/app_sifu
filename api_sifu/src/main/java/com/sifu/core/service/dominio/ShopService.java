package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.repo.*;
import com.sifu.core.utils.dto.*;
import com.sifu.core.utils.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService implements IShopService {

    private static final Logger log = LogManager.getLogger("com.sifu.core.service.dominio.IProductoService");

    private final ProductoRepository productoRepository;
    private final CategoriaProdRepository categoriaProdRepository;
    private final AgricultorRepository agricultorRepository;
    private final AgriProdRepository agriProdRepository;
    private final ClienteRepository clienteRepository;
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;

    public ShopService(ProductoRepository productoRepository, CategoriaProdRepository categoriaProdRepository, AgricultorRepository agricultorRepository, AgriProdRepository agriProdRepository, ClienteRepository clienteRepository, CarritoRepository carritoRepository, DetalleCarritoRepository detalleCarritoRepository) {
        this.productoRepository = productoRepository;
        this.categoriaProdRepository = categoriaProdRepository;
        this.agricultorRepository = agricultorRepository;
        this.agriProdRepository = agriProdRepository;
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
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

    public ApiResponse<List<AgriProdDto>> listarProductosAgricultorByUser(Usuario usuario) {
        Integer id = usuario.getPersona().getAgricultor().getId();
        log.info(String.format("Agricultor id: %d", id));

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

    public ApiResponse<List<AgriProdDto>> agregarProductoAlCarrito(Usuario usuario, ProductoDto item) {
        Integer id = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", id));
        //validamos cliente
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            log.error("No se encontro un cliente ligado a esta cuenta");
            return ApiResponse.error(RC.NOT_FOUND,"No se encontro un cliente ligado a esta cuenta");
        }
        //Validamos si tiene un carrito
        Optional<Carrito> _carrito = carritoRepository.findByCliente_Id(cliente.getId());
        Carrito carrito = _carrito.isPresent() ? _carrito.get() : null;
        if (!_carrito.isPresent()) {
            log.info("No se encontro un carrito se crea uno");
            carrito = new Carrito();
            carrito.setCliente(cliente);
            carrito.setFechaCreacion(LocalDateTime.now());
            carritoRepository.save(carrito);
        }

        //Validamos el producto
        Optional<Producto> _producto = productoRepository.findById(item.getId());
        Producto producto = _producto.isPresent() ? _producto.get() : null;
        if (producto == null) {
            log.error("No se encontro un producto");
            return ApiResponse.error(RC.NOT_FOUND,"No se encontro un producto");
        }

        //Agregamos el item al carrito

        DetalleCarrito detalleCarrito = new DetalleCarrito();
        detalleCarrito.setProducto(producto);
        detalleCarrito.setCarrito(carrito);
        detalleCarritoRepository.save(detalleCarrito);

        return ApiResponse.success(RC.OK,"Item Agregado correctamente",null);
    }

    public ApiResponse<List<DetalleCarritoDto>> verCarrito(Usuario usuario) {
        Integer id = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", id));
        //validamos cliente
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            log.error("No se encontro un cliente ligado a esta cuenta");
            return ApiResponse.error(RC.NOT_FOUND,"No se encontro un cliente ligado a esta cuenta");
        }

        //Validamos si tiene un carrito
        Optional<Carrito> _carrito = carritoRepository.findByCliente_Id(cliente.getId());
        Carrito carrito = _carrito.isPresent() ? _carrito.get() : null;
        if (!_carrito.isPresent()) {
            log.info("No se encontro un carrito se crea uno");
            carrito = new Carrito();
            carrito.setCliente(cliente);
            carrito.setFechaCreacion(LocalDateTime.now());
            carritoRepository.save(carrito);
        }

        //Obtenemos los items del carrito
        List<DetalleCarrito> _items = detalleCarritoRepository.getDetalleCarritoByCarrito(carrito);
        List<DetalleCarritoDto> items;
        if(_items.size()>0){
            items = _items.stream().map(DetalleCarritoDto::new).collect(Collectors.toList());
        }else {
            items = new ArrayList<>();
        }
        return ApiResponse.success(RC.OK,items);
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
