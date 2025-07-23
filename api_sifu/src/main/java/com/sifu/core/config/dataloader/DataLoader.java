package com.sifu.core.config.dataloader;

import com.sifu.core.repo.*;
import com.sifu.core.utils.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {
    private final Logger log = LogManager.getLogger(DataLoader.class);

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private RolRepository rolRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AgricultorRepository agricultorRepository;
    @Autowired
    private CategoriaProdRepository categoriaProdRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private AgriProdRepository agriProdRepository;
    @Autowired
    private StockRepository stockRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("DataLoader start");
        log.info("Insertando roles");
        Rol rolAdmin = rolRepo.findByNombre("ADMIN").orElseGet(() ->
                rolRepo.save(Rol.builder().nombre("ADMIN").build()));

        Rol rolCliente = rolRepo.findByNombre("CLIENTE").orElseGet(() ->
                rolRepo.save(Rol.builder().nombre("CLIENTE").build()));

        Rol rolAgricultor = rolRepo.findByNombre("AGRICULTOR").orElseGet(() ->
                rolRepo.save(Rol.builder().nombre("AGRICULTOR").build()));

        log.info("Termina insert roles");
        log.info("Inicia insertando usuarios");
        // Crear persona y usuario admin
        crearPersonaYUsuario("1000000001", "admin", "admin@sifu.com", "0981234561", "admin", "admin", rolAdmin);
        // Crear persona y usuario cliente
        crearPersonaYUsuario("2000000001", "cliente", "cliente@sifu.com", "0982334562", "cliente", "clave123", rolCliente);
        // Crear persona y usuario agricultor
        crearPersonaYUsuario("3000000001", "agricultor", "agricultor@sifu.com", "0812334563", "agricultor", "clave123", rolAgricultor);
        log.info("Termina insert usuarios");

        try {
            crearProductosDemo();
        }catch (Exception e) {
            log.error("Error al insertar productos");
        }
    }

    private void crearPersonaYUsuario(String cedula, String nombre, String correo, String celular,
                                      String alias, String clave, Rol rol) {
        try {
            Persona persona = personaRepository.findByCedula(cedula).orElseGet(() -> {
                Persona p = new Persona();
                p.setCedula(cedula);
                p.setNombre(nombre);
                p.setApellido(nombre);
                p.setCorreo(correo);
                p.setCelular(celular);
                p.setProvincia("PICHINCHA");
                p.setCanton("Quito");
                return personaRepository.save(p);
            });
            log.info("Persona con cedula " + cedula + " registrado");

            if (usuarioRepo.findByAlias(alias).isEmpty()) {
                String encodedPassword = encoder.encode(clave);
                usuarioRepo.save(new Usuario(null, alias, encodedPassword, true, persona, rol));
            }
            log.info("Persona con alias " + alias + " registrado");

            if (rol.getNombre().equals("CLIENTE") && clienteRepository.findByPersonaId(persona.getId()).isEmpty() ) {
                Cliente cliente = new Cliente();
                cliente.setPersona(persona);
                cliente.setEntidadSFL(false);
                clienteRepository.save(cliente);
            }

            if (rol.getNombre().equals("AGRICULTOR") && agricultorRepository.findByPersonaId(persona.getId()).isEmpty()) {
                Agricultor agricultor = new Agricultor();
                agricultor.setPersona(persona);
                agricultorRepository.save(agricultor);
            }

        } catch (Exception e) {
            log.error("Error insertando usuario:{}", e.getMessage());
        }
    }

    private void crearProductosDemo(){
        log.info("Creando productos demo...");
        String[] categoriasNombres = {"Frutas", "Verduras", "Hortalizas", "Legumbres"};
        List<CategoriaProd> categorias = new ArrayList<>();

        // 1. Crear categorías si no existen
        for (String nombre : categoriasNombres) {
            CategoriaProd categoria = categoriaProdRepository.findByNombre(nombre)
                    .orElseGet(() -> categoriaProdRepository.save(new CategoriaProd(null, nombre)));
            categorias.add(categoria);
        }
        log.info("Buscando agricultor");

        // 2. Obtener el primer agricultor
        Optional<Agricultor> optAgricultor = agricultorRepository.findAll().stream().findFirst();
        if (optAgricultor.isEmpty()) {
            log.error("No hay agricultores registrados");
            return;
        }

        List<String> frutas = List.of("Manzana", "Plátano", "Naranja", "Mango", "Piña");
        List<String> verduras = List.of("Lechuga", "Espinaca", "Brócoli", "Coliflor", "Pepino");
        List<String> hortalizas = List.of("Zanahoria", "Tomate", "Cebolla", "Ajo", "Rábano");
        List<String> legumbres = List.of("Lentejas", "Frijoles", "Garbanzos", "Arvejas", "Haba");

        List<List<String>> listaProductos = List.of(frutas, verduras, hortalizas, legumbres);

        Agricultor agricultor = optAgricultor.get();

        for (int i = 0; i < categoriasNombres.length; i++) {
            log.info("Creando productos demo por categoria:{}", categoriasNombres[i]);
            CategoriaProd categoria = categorias.get(i);
            List<String> productos = listaProductos.get(i);

            for (String nombreProd : productos) {
                Optional<Producto> optProducto = productoRepository.findByNombre(nombreProd);

                if (optProducto.isPresent()) {
                    log.info("El producto ya existe");
                    continue;
                }
                log.info("Creando producto :{}", "https://storage.googleapis.com/sifu-images-app-bucket-2025/"+nombreProd+".png");
                double numero = 1.50 + Math.random() * 3;// Precio entre 1.50 y 4.50
                DecimalFormat df = new DecimalFormat("#.##");
                String numeroRedondeado = df.format(numero);

                Producto producto = new Producto();
                producto.setNombre(nombreProd);
                producto.setPrecio(Double.parseDouble(numeroRedondeado));
                producto.setCategoriaProd(categoria);
                producto.setImage("https://storage.googleapis.com/sifu-images-app-bucket-2025/"+nombreProd+".png");
                productoRepository.save(producto);

                AgriProd agriProd = new AgriProd();
                agriProd.setAgricultor(agricultor);
                agriProd.setProducto(producto);
                agriProdRepository.save(agriProd);

                Stock stock = new Stock();
                stock.setAgriProd(agriProd);
                stock.setCantidad(100);
                stock.setUniMedida("kg");
                stockRepository.save(stock);
            }
        }


    }
}
