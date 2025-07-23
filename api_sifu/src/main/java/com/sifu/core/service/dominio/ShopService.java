package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.constants.StateConstants;
import com.sifu.core.repo.*;
import com.sifu.core.utils.dto.*;
import com.sifu.core.utils.dto.dominio.AgricultorByProductoDto;
import com.sifu.core.utils.dto.dominio.CabeceraFacturaDto;
import com.sifu.core.utils.dto.dominio.ItemCarritoDto;
import com.sifu.core.utils.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final FacturaRepository facturaRepository;
    private final DetalleFactRepository detalleFactRepository;
    private final StockRepository stockRepository;

    public ShopService(ProductoRepository productoRepository, CategoriaProdRepository categoriaProdRepository, AgricultorRepository agricultorRepository, AgriProdRepository agriProdRepository, ClienteRepository clienteRepository, CarritoRepository carritoRepository, DetalleCarritoRepository detalleCarritoRepository, FacturaRepository facturaRepository, DetalleFactRepository detalleFactRepository, StockRepository stockRepository) {
        this.productoRepository = productoRepository;
        this.categoriaProdRepository = categoriaProdRepository;
        this.agricultorRepository = agricultorRepository;
        this.agriProdRepository = agriProdRepository;
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.facturaRepository = facturaRepository;
        this.detalleFactRepository = detalleFactRepository;
        this.stockRepository = stockRepository;
    }

    public ApiResponse<List<CategoriaProdDto>> listarCategorias() {
        log.info("listarCategorias() called");
        List<CategoriaProd> itemsBase = categoriaProdRepository.findAll();
        List<CategoriaProdDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(CategoriaProdDto::new).collect(Collectors.toList());
            log.info("Categorias encontradas: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron categorias");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND, items);
        }
    }

    public ApiResponse<List<ProductoDto>> listarProductos() {
        log.info("listarProductos() called");
        List<Producto> itemsBase = productoRepository.findAll();
        List<ProductoDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(ProductoDto::new).collect(Collectors.toList());
            log.info("Productos encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron productos");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND, items);
        }
    }

    public ApiResponse<ProductoDto> listarProductoById(Integer id ) {
        log.info("listarProductos() called");
        Optional<Producto> itemsBase = productoRepository.findById(id);
        ProductoDto item;
        if (itemsBase.isPresent()) {
            item = new ProductoDto(itemsBase.get());
            log.info("Productos encontrados: {}", item);
            return ApiResponse.success(item);
        } else {
            log.error("No se encontraron productos");
            item = new ProductoDto();
            return ApiResponse.error(RC.NOT_FOUND, item);
        }
    }


    public ApiResponse<List<AgriProdDto>> listarProductosAgricultor(Integer id) {
        log.info("listarProductosAgricultor() called");
        List<AgriProd> itemsBase = agriProdRepository.findByAgricultor_Id(id);
        List<AgriProdDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(AgriProdDto::new).collect(Collectors.toList());
            log.info("Productos encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron productos");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND, items);
        }
    }

    public ApiResponse<List<AgriProdDto>> listarProductosAgricultorByUser(Usuario usuario) {
        log.info("listarProductosAgricultorByUser() called");
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
            return ApiResponse.error(RC.NOT_FOUND, items);
        }
    }

    public ApiResponse<List<AgricultorByProductoDto>> listarAgricultorByProductoId(Integer id) {

        List<AgriProd> itemsBase = agriProdRepository.findByProducto_Id(id);
        List<AgricultorByProductoDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(AgricultorByProductoDto::new).collect(Collectors.toList());
            log.info("Productos encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron productos");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND, items);
        }
    }

    public ApiResponse<List<AgriProdDto>> agregarProductoAlCarrito(Usuario usuario, ProductoDto item) {
        log.info("agregarProductoAlCarrito() called");
        Integer id = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", id));
        //validamos cliente
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            log.error("No se encontro un cliente ligado a esta cuenta");
            return ApiResponse.error(RC.NOT_FOUND, "No se encontro un cliente ligado a esta cuenta");
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
            return ApiResponse.error(RC.NOT_FOUND, "No se encontro un producto");
        }

        //Agregamos el item al carrito

        DetalleCarrito detalleCarrito = new DetalleCarrito();
        detalleCarrito.setEstado(StateConstants.STATE_ACTIVE);
        detalleCarrito.setProducto(producto);
        detalleCarrito.setCarrito(carrito);
        detalleCarritoRepository.save(detalleCarrito);

        return ApiResponse.success(RC.OK, "Item Agregado correctamente", null);
    }

    public ApiResponse<List<DetalleCarritoDto>> verCarrito(Usuario usuario) {
        log.info("verCarrito() called");
        Integer id = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", id));
        //validamos cliente
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            log.error("No se encontro un cliente ligado a esta cuenta");
            return ApiResponse.error(RC.NOT_FOUND, "No se encontro un cliente ligado a esta cuenta");
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
        List<DetalleCarrito> _items = detalleCarritoRepository.getDetalleCarritoByEstadoAndCarrito(StateConstants.STATE_ACTIVE, carrito);
        List<DetalleCarritoDto> items;
        if (_items.size() > 0) {
            items = _items.stream().map(DetalleCarritoDto::new).collect(Collectors.toList());
        } else {
            items = new ArrayList<>();
        }
        return ApiResponse.success(RC.OK, items);
    }

    public ApiResponse<String> eliminarItemCarrito(Usuario usuario, Integer itemId) {
        log.info("eliminarItemCarrito() called");
        Integer idCliente = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", idCliente));

        ApiResponse apiResponse = detalleCarritoRepository.findById(itemId).map(itemDetalle -> {
            if (itemDetalle.getCarrito().getCliente().getId().equals(idCliente)) {
                itemDetalle.setEstado(StateConstants.STATE_INACTIVE);
                itemDetalle.setFechaUpdate(LocalDateTime.now());
                detalleCarritoRepository.save(itemDetalle);
                return ApiResponse.success(RC.OK, "Item eliminado correctamente");
            } else {
                return ApiResponse.error(RC.FORBIDDEN, "El producto no pertence al cliente");
            }
        }).orElse(ApiResponse.error(RC.BAD_REQUEST, "El item no existe"));

        return apiResponse;
    }

    public ApiResponse<List<AgricultorDto>> listarAgricultores() {
        log.info("listarAgricultores() called");
        List<Agricultor> itemsBase = agricultorRepository.findAll();
        List<AgricultorDto> items;
        if (!itemsBase.isEmpty()) {
            items = itemsBase.stream().map(AgricultorDto::new).collect(Collectors.toList());
            log.info("Agricultores encontrados: {}", items.size());
            return ApiResponse.success(items);
        } else {
            log.error("No se encontraron Agricultores");
            items = new ArrayList<>();
            return ApiResponse.error(RC.NOT_FOUND, items);
        }
    }

    public ApiResponse<ItemCarritoDto> actualizarItemCarrito(Usuario usuario,
                                                             Integer itemId,
                                                             ItemCarritoDto itemDetalle) {
        log.info("actualizarItemCarrito() called");
        Integer idCliente = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", idCliente));

        //Validando si el item existe
        Optional<DetalleCarrito> detalle = detalleCarritoRepository.findById(itemId);
        if (!detalle.isPresent()) {
            return ApiResponse.error(RC.BAD_REQUEST, "El item no existe");
        }

        // Validando si el producto pertence al cliente logueado
        DetalleCarrito detalleCarrito = detalle.get();
        if (!detalleCarrito.getCarrito().getCliente().getId().equals(idCliente)) {
            return ApiResponse.error(RC.FORBIDDEN, "El producto no pertence al cliente");
        }

        // Validando si el agricultor existe en caso de venir el id en la petición
        if (itemDetalle.getIdAgricultor() != null && itemDetalle.getIdAgricultor() > 0) {


            Optional<AgriProd> _agricultor = agriProdRepository.findTopByProducto_IdAndAgricultor_Id(
                    detalleCarrito.getProducto().getId(),
                    itemDetalle.getIdAgricultor());
            if (!_agricultor.isPresent()) {
                return ApiResponse.error(RC.BAD_REQUEST, "El agricultor no existe");
            }
            detalleCarrito.setAgriProd(_agricultor.get());
        }

        // Validando stock
        if (itemDetalle.getIdAgricultor() != null && itemDetalle.getIdAgricultor() > 0) {
            Integer stockDisponible = detalleCarrito.getAgriProd().getStock().getCantidad();
            Integer stockSolicitado = itemDetalle.getCantidad();

            stockDisponible = stockDisponible != null ? stockDisponible : 0;
            stockSolicitado = stockSolicitado != null ? stockSolicitado : 0;

            if (stockDisponible >= stockSolicitado) {
                detalleCarrito.setCantidad(stockSolicitado == 0 ? 1 : stockSolicitado);
            } else {
                stockSolicitado = stockDisponible;
            }
            detalleCarrito.setCantidad(stockSolicitado);
        }
        detalleCarritoRepository.save(detalleCarrito);

        return ApiResponse.success(RC.OK, "Item actualizado correctamente", itemDetalle);
    }

    public ApiResponse<CabeceraFacturaDto> facturarItemsCarrito(Usuario usuario,
                                                                List<ItemCarritoDto> itemsFactura) {
        log.info("facturarItemsCarrito() called");

        Integer idCliente = usuario.getPersona().getCliente().getId();
        log.info(String.format("Cliente id: %d", idCliente));

        if (itemsFactura.isEmpty()) {
            return ApiResponse.error(RC.BAD_REQUEST, "No se encontraron Items");
        }

        // Validando si el producto pertence al cliente logueado
        if (!validarItems(itemsFactura, idCliente)) {
            return ApiResponse.error(RC.FORBIDDEN, "Uno o mas productos no pertence al cliente");
        }

        // Validando si el producto tiene stock disponible
        if (!validarStockAgricultorItem(itemsFactura)) {
            return ApiResponse.error(RC.BAD_REQUEST, "Uno o mas productos no tienen stock disponible");
        }

        return ApiResponse.success(RC.OK,"Factura creada exitosamente", generarFactura(itemsFactura, idCliente));

    }

    private Boolean validarItems(List<ItemCarritoDto> itemsCarrito, Integer idCliente) {
        log.info("validarItems() called");

        for (ItemCarritoDto itemCarritoDto : itemsCarrito) {
            log.info("itemCarritoDto: {}", itemCarritoDto);
            //Validando si el item existe
            Optional<DetalleCarrito> detalle = detalleCarritoRepository.findById(itemCarritoDto.getId());
            if (!detalle.isPresent()) {
                log.error("Item carrito no existe idItemCarrito: {}", itemCarritoDto.getId());
                return false;
            }

            if (!detalle.get().getCarrito().getCliente().getId().equals(idCliente)) {
                log.error("Item carrito no pertence al cliente clienteId:{} idItemCarrito:{}", idCliente, itemCarritoDto.getId());
                return false;
            }
        }
        return true;
    }

    private Boolean validarStockAgricultorItem(List<ItemCarritoDto> itemsCarrito) {
        log.info("validarAgricultorItems() called");

        for (ItemCarritoDto itemCarritoDto : itemsCarrito) {
            log.info("itemCarritoDto: {}", itemCarritoDto);
            Optional<DetalleCarrito> detalle = detalleCarritoRepository.findById(itemCarritoDto.getId());
            DetalleCarrito detalleCarrito = detalle.get();

            Integer stockDisponible = detalleCarrito.getAgriProd().getStock().getCantidad();
            Integer stockSolicitado = itemCarritoDto.getCantidad();

            stockDisponible = stockDisponible != null ? stockDisponible : 0;

            if (stockDisponible >= stockSolicitado) {
               return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private CabeceraFacturaDto generarFactura(List<ItemCarritoDto> itemsCarrito, Integer idCliente){
        log.info("generarFactura() called");
        Optional<Cliente> _cliente = clienteRepository.findById(idCliente);
        Cliente cliente = _cliente.get();

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setFechaCreacion(LocalDateTime.now());
        factura.setEstado(StateConstants.FACTURA_STATE_COMPLETE);
        facturaRepository.save(factura);
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarritoDto itemCarritoDto : itemsCarrito) {
            Optional<DetalleCarrito> _item = detalleCarritoRepository.findById(itemCarritoDto.getId());
            DetalleCarrito detalleCarrito = _item.get();
            AgriProd agriProd = detalleCarrito.getAgriProd();
            // creamos detalle factura
            DetalleFact detalleFact = new DetalleFact();
            detalleFact.setFactura(factura);
            detalleFact.setProducto(agriProd);
            detalleFact.setCantidad(itemCarritoDto.getCantidad());
            detalleFact.setSubtotal(itemCarritoDto.getCantidad() * agriProd.getProducto().getPrecio());

            total = total.add(new BigDecimal(detalleFact.getSubtotal()));
            detalleFactRepository.save(detalleFact);
            log.info("detalleFact id: {}", detalleFact.getId());
            log.info("detalleFact subtotal: {}", detalleFact.getSubtotal());
            //cambia estado del item del carrito a procesado
            detalleCarrito.setEstado(StateConstants.STATE_PROCESSED);
            detalleCarritoRepository.save(detalleCarrito);

            //actualizamos stock
            Stock stock = agriProd.getStock();
            Integer stockDisponible = stock.getCantidad() - itemCarritoDto.getCantidad();
            stock.setCantidad(stockDisponible);
            stockRepository.save(stock);
        }
        factura.setTotal(total.doubleValue());
        facturaRepository.save(factura);
        log.info("factura: {}", factura.getTotal());

        CabeceraFacturaDto cabeceraFacturaDto = new CabeceraFacturaDto();
        cabeceraFacturaDto.setNumero(factura.getId());
        cabeceraFacturaDto.setIdCliente(idCliente);
        cabeceraFacturaDto.setNombre(cliente.getPersona().getNombre());
        cabeceraFacturaDto.setApellido(cliente.getPersona().getApellido());
        cabeceraFacturaDto.setDni(cliente.getPersona().getCedula());
        cabeceraFacturaDto.setFecha(factura.getFechaCreacion());
        cabeceraFacturaDto.setValor(total);
        log.info("cabeceraFacturaDto: {}", cabeceraFacturaDto);
        return cabeceraFacturaDto;
    }
}
