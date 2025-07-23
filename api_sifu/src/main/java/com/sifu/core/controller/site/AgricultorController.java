package com.sifu.core.controller.site;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.sifu.core.service.google.GoogleCloudStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sifu.core.service.AgriProdService;
import com.sifu.core.service.AgricultorService;
import com.sifu.core.service.AnuncioService;
import com.sifu.core.service.CategoriaProdService;
import com.sifu.core.service.ProductoService;
import com.sifu.core.service.StockService;
import com.sifu.core.service.security.CustomIUserDetails;
import com.sifu.core.utils.Utils;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.entity.AgriProd;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Anuncio;
import com.sifu.core.utils.entity.CategoriaProd;
import com.sifu.core.utils.entity.Cliente;
import com.sifu.core.utils.entity.Producto;
import com.sifu.core.utils.entity.Stock;
import com.sifu.core.utils.entity.Usuario;

@Controller
@RequestMapping("/agricultor")
public class AgricultorController {

	private static final Logger log = LogManager.getLogger(AgricultorController.class);

	@Autowired
	private AnuncioService anuncioService;
	@Autowired
	private AgricultorService agricultorService;
	@Autowired
	private AgriProdService agriProdService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private StockService stockService;
	@Autowired
	private CategoriaProdService categoriaProdService;
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;


	@GetMapping("/agregar-productos")
    public String mostraraAgregarProd(Authentication authentication, Model model) {
		
		CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
	    Integer id = userDetails.getUsuario().getPersona().getAgricultor().getId();
	    
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        model.addAttribute("productos", productoService.listarTodoProductos());
        model.addAttribute("categorias", categoriaProdService.listarTodasCategorias());
        model.addAttribute("medidas", Utils.obtenerMedidas());
        return "agricultor/agregar-productos";
    }

	@PostMapping("/agregar-productos")
	public String agregarProducto(@ModelAttribute Producto producto,
	                              @RequestParam("fileImage") MultipartFile fileImage,
	                              @RequestParam("stockCantidad") Integer stockCantidad,
	                              @RequestParam("uniMedida") String uniMedida,
	                              Authentication authentication,
	                              RedirectAttributes redirectAttributes) {
	    try {
	        // Obtener ID del agricultor autenticado
	        Integer agricultorId = ((CustomIUserDetails) authentication.getPrincipal())
	                                .getUsuario()
	                                .getPersona()
	                                .getAgricultor()
	                                .getId();

	        // Obtener lista de productos en general
	        List<Producto> listaProductos = productoService.listarTodoProductos();
	        // Obtener lista de productos solo del agricultor
	        List <AgriProd> productosExistentes = agriProdService.findByAgricultor_Id(agricultorId);

	        // Validar si ya existe un producto con el mismo nombre para este agricultor
	        boolean existe = productosExistentes.stream()
	            .anyMatch(ap -> ap.getProducto().getNombre().equalsIgnoreCase(producto.getNombre()));

	        if (existe) {
	            redirectAttributes.addFlashAttribute("error", "Ya tienes un producto con ese nombre.");
	            return "redirect:/agricultor/agregar-productos";
	        }

	        // Validar categoría del producto
	        CategoriaProd categoria = categoriaProdService.obtenerPorId(producto.getCategoriaProd().getId());
	        if (categoria == null) {
	            throw new RuntimeException("No se encontró la categoría de producto");
	        }
	        producto.setCategoriaProd(categoria);

	        // Guardar el producto nuevo
	        Producto nuevoProducto = productoService.crearProducto(producto);

			// Guardar imagen como Base64 si se subió una
			if (!fileImage.isEmpty()) {
				String base64 = Base64.getEncoder().encodeToString(fileImage.getBytes());
				log.info("Imagen producto base64:{}",base64);
				String name = nuevoProducto.getId()+"_producto";
				try {
					String urlImage = googleCloudStorageService.uploadBase64Image(base64,name);
					nuevoProducto.setImage(urlImage);
					productoService.actualizarProducto(nuevoProducto.getId(),nuevoProducto);
				} catch (IOException e) {
					log.error("Error al subir imagen de producto", e.getMessage());
				}
			}

	        // Obtener agricultor y crear nuevo AgriProd para la relación
	        Agricultor agricultor = agricultorService.obtenerPorId(agricultorId);
	        AgriProd agriProd = new AgriProd();
	        agriProd.setAgricultor(agricultor);
	        agriProd.setProducto(nuevoProducto);

	        // Configurar stock con la relación bidireccional
	        Stock stock = new Stock();
	        stock.setCantidad(stockCantidad);
	        stock.setUniMedida(uniMedida);
	        stock.setAgriProd(agriProd);

	        agriProd.setStock(stock);

	        // Guardar AgriProd
	        agriProdService.crearAgriProd(agriProd);

	        redirectAttributes.addFlashAttribute("success", "Producto registrado correctamente.");
	        return "redirect:/agricultor/agregar-productos";

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al registrar producto: " + e.getMessage());
	        return "redirect:/agricultor/agregar-productos";
	    }
	}



	
	@PostMapping("/ver-productos")
	public String actualizarProducto(
			@RequestParam Integer id,
	        @RequestParam String nombre,
	        @RequestParam Double precio,
	        @RequestParam Integer stockCantidad,
	        @RequestParam String uniMedida,
	        RedirectAttributes redirectAttributes) {
	    try {
	        AgriProd agriProd = agriProdService.obtenerPorId(id);
	        if (agriProd == null) {
	            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
	            return "redirect:/agricultor/ver-productos";
	        }

	        agriProd.getProducto().setPrecio(precio);
	        agriProd.getStock().setCantidad(stockCantidad);

	        agriProdService.actualizarAgriProd(id, agriProd);

	        redirectAttributes.addFlashAttribute("success", "Producto actualizado correctamente");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al actualizar producto: " + e.getMessage());
	    }
	    return "redirect:/agricultor/ver-productos";
	}

	 @GetMapping("/eliminar-producto/{id}")
	    public String eliminarProducto(@PathVariable Integer id) {
		 agriProdService.eliminarAgriProd(id);
		 return "redirect:/agricultor/agregar-productos";
	}


	@GetMapping("/ver-productos")
	public String listarProductos(Authentication authentication, Model model) {
	    CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
	    Integer agricultorId = userDetails.getUsuario().getPersona().getAgricultor().getId();
	    
	    List<AgriProd> productos = agriProdService.findByAgricultor_Id(agricultorId);
	    model.addAttribute("productos", productos);
	    //model.addAttribute("productos", agriProdService.findByAgricultor_Id(agricultorId));
	    
	    return "agricultor/ver-productos";
	}
	
	@GetMapping("/validarAgricultor")
	public String validarAgricultor(Authentication auth) {
		log.info("validarAgricultor called");
		CustomIUserDetails userDetails = (CustomIUserDetails) auth.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		String rol = usuario.getRol().getNombre();

		Integer idPersona = usuario.getPersona().getId();

		if ("AGRICULTOR".equalsIgnoreCase(rol)) {
			Optional<Agricultor> agricultorOpt = agricultorService.findByPersonaId(idPersona);
			if (agricultorOpt.isPresent()) {
				return "redirect:/site/crearAnuncio/" + agricultorOpt.get().getId();
			}
		}
		return "redirect:/site/validarAgricultor";

	}
	
	//METODOS DE ANUNCIOS
	@GetMapping("/anuncios")
	public String mostrarFormulario(Model model, Authentication authentication) {
		log.info("mostrarFormulario() called");
		CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		Integer id = usuario.getPersona().getAgricultor().getId();
		log.info(String.format("Agricultor id: %d", id));

		Anuncio anuncio = new Anuncio();
		Agricultor agricultor = agricultorService.obtenerPorId(id);
		List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultor.getId());

		model.addAttribute("anuncio", anuncio);
		model.addAttribute("agricultor", agricultor);
		model.addAttribute("anunciosGuardados", anuncios);
		model.addAttribute("modoEdicion", false);
		return "agricultor/anuncios";
	}


	 @PostMapping("/crearAnuncio/{id}")
	 public String crearAnuncio(@PathVariable Integer id, @ModelAttribute Anuncio anuncio, Model model) {
	     log.info("crearAnuncio() called");
	     anuncio.setId(null);
	     Agricultor agricultor = agricultorService.obtenerPorId(id);
	     anuncio.setAgricultor(agricultor);
	     anuncio.setFechaCreacion(LocalDateTime.now());

	     anuncioService.crearAnuncio(anuncio);

	     // Recargar datos para mostrar en la misma página
	     List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultor.getId());
	     model.addAttribute("agricultor", agricultor);
	     model.addAttribute("anuncio", new Anuncio()); // limpiar el formulario
	     model.addAttribute("anunciosGuardados", anuncios);
	     model.addAttribute("modoEdicion", false);

	     return "agricultor/anuncios";
	 }

	
	@GetMapping("/editarAnuncio/{id}")
	public String editarAnuncio(@PathVariable Integer id, Model model, @ModelAttribute Anuncio anuncio) {
		log.info("editarAnuncio() called");
		Anuncio anuncioExistente = anuncioService.obtenerPorId(id);
		anuncio.setDescripcion(anuncioExistente.getDescripcion());
		anuncio.setEstado(anuncioExistente.getEstado());
		anuncioService.actualizarAnuncio(id, anuncioExistente);
		return "agricultor/anuncios";
	}

	@GetMapping("/eliminarAnuncio/{id}")
	public String eliminarAnuncio(@PathVariable Integer id, Authentication authentication, Model model) {
	    log.info("eliminarAnuncio() called");
	    anuncioService.eliminarAnuncio(id);

	    // Recuperar agricultorId del usuario autenticado
	    CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
	    Integer agricultorId = userDetails.getUsuario().getPersona().getAgricultor().getId();

	    // Recargar el agricultor y la lista de anuncios
	    Agricultor agricultor = agricultorService.obtenerPorId(agricultorId);
	    List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultorId);

	    // Cargar todos los datos al modelo
	    model.addAttribute("agricultor", agricultor);
	    model.addAttribute("anuncio", new Anuncio());
	    model.addAttribute("anunciosGuardados", anuncios);
	    model.addAttribute("modoEdicion", false);

	    return "agricultor/anuncios";
	}

}
