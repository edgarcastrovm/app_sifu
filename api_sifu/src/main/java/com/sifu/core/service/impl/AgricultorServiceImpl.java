package com.sifu.core.service.impl;

import com.sifu.core.repo.AgricultorRepository;
import com.sifu.core.repo.RolRepository;
import com.sifu.core.service.*;
import com.sifu.core.service.google.GoogleCloudStorageService;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AgricultorServiceImpl implements AgricultorService {

	private static final Logger log = LogManager.getLogger(AgricultorServiceImpl.class);
	@Autowired
	private AgricultorRepository agricultorRepository;
	@Autowired
	private PersonaService personaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private StockService stockService;
	@Autowired
	private RolRepository rolRepository;
    @Autowired
    private AgriProdService agriProdService;
    @Autowired
    private CategoriaProdService categoriaProdService;
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

	@Override
	public List<Agricultor> obtenerTodas() {
		// TODO Auto-generated method stub
		return agricultorRepository.findAll();
	}

    @Override
    public Agricultor crearAgricultor(CrearAgricultorDto registro) {
		log.info("crearAgricultor() called");
    	// Verificar si el alias ya existe
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorAlias(registro.getAlias());
        if (usuarioExistente.isPresent()) {
            log.warn("Alias '{}' ya está registrado", registro.getAlias());
            throw new IllegalArgumentException("El alias ya existe. Por favor, elige otro.");
        }
        
        Persona persona = new Persona();
        persona.setNombre(registro.getNombre());
        persona.setApellido(registro.getApellido());
        persona.setCorreo(registro.getCorreo());
        persona.setCedula(registro.getCedula());
        persona.setCelular(registro.getCelular());
        persona.setProvincia(registro.getProvincia());
    	persona.setCanton(registro.getCanton());
        Persona personaGuardado = personaService.crearPersona(persona);
        
        Usuario usuario = new Usuario();
        usuario.setAlias(registro.getAlias());
        log.info("Creando usuario: {}", usuario.getAlias());
        usuario.setClave(registro.getClave());
        log.info("Creando clave: {}" ,registro.getClave());
        usuario.setPersona(personaGuardado);
        usuario.setActivo(true);

		Optional<Rol> rolAgricultor = rolRepository.findByNombre("AGRICULTOR");
		if (!rolAgricultor.isPresent()) {
			throw new RuntimeException("No se encontró el rol 'AGRICULTOR'");
		}
		usuario.setRol(rolAgricultor.get());
		usuarioService.crearUsuario(usuario);
		log.info("Usuario clave: {} ", usuario.getClave());

		Agricultor agricultor = new Agricultor();
		agricultor.setPersona(personaGuardado);
		log.info("Agricultor registrado con ID: {}", agricultor.getId());

		return agricultorRepository.save(agricultor);
	}

	@Override
	public Agricultor obtenerPorId(Integer id) {
		log.info("obtenerPorId() called");
		// TODO Auto-generated method stub
		return agricultorRepository.findById(id).orElse(null);
	}

	@Override
	public Optional<Agricultor> findByPersonaId(Integer personaId) {
		log.info("findByPersonaId() called");
		// TODO Auto-generated method stub
		return agricultorRepository.findByPersonaId(personaId);
	}

	@Override
	public Agricultor actualizarAgricultor(Integer id, CrearAgricultorDto agricultorDto) {

		log.info("actualizarAgricultor() called");
		Agricultor agricultorExistente = agricultorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agricultor no encontrado con ID: " + id));

		Persona persona = agricultorExistente.getPersona();
		// Validar campos no nulos
		if (agricultorDto.getNombre() == null || agricultorDto.getApellido() == null ||
			agricultorDto.getCedula() == null || agricultorDto.getCorreo() == null ||
			agricultorDto.getCelular() == null || agricultorDto.getProvincia() == null ||
			agricultorDto.getCanton()== null){
			throw new IllegalArgumentException("Todos los campos de persona son obligatorios");
		}

		// Actualizar campos de Persona
		persona.setNombre(agricultorDto.getNombre());
		persona.setApellido(agricultorDto.getApellido());
		persona.setCedula(agricultorDto.getCedula());
		persona.setCorreo(agricultorDto.getCorreo());
		persona.setCelular(agricultorDto.getCelular());
		persona.setProvincia(agricultorDto.getProvincia());
		persona.setCanton(agricultorDto.getCanton());
		personaService.actualizarPersona(persona.getId(), persona);

		return agricultorRepository.save(agricultorExistente);
	}

	public String  agregarProductoAricultor(Producto producto,
										  String fileImage,
										  Integer stockCantidad,
										  String uniMedida,
										  Integer agricultorId,
										  RedirectAttributes redirectAttributes){

		// Obtener lista de productos asociados a ese agricultor
		List<AgriProd> listaProductos = agriProdService.findByAgricultor_Id(agricultorId);

		// Validar si ya existe un producto con el mismo nombre para este agricultor
		boolean existe = listaProductos.stream()
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
		if ( fileImage != null && !fileImage.isEmpty() &&  fileImage.trim().length() > 0 ) {
			//String base64 = Base64.getEncoder().encodeToString(fileImage.getBytes());
			String name = nuevoProducto.getId()+"_producto";
            try {
                String urlImage = googleCloudStorageService.uploadBase64Image(fileImage,name);
				producto.setImage(urlImage);
			} catch (IOException e) {
				log.error("Error al subir imagen de producto", e.getMessage());
			}
		}

		// Obtener agricultor y crear nuevo AgriProd para la relación
		Agricultor agricultor = obtenerPorId(agricultorId);
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
	}
}
