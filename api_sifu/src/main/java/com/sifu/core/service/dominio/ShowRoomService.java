package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.repo.TblOfertaProductoRepository;
import com.sifu.core.repo.TblProductoRepository;
import com.sifu.core.repo.TblProductorRepository;
import com.sifu.core.repo.TblUsuarioRepository;
import com.sifu.core.utils.dto.TblOfertaProductoDto;
import com.sifu.core.utils.dto.TblProductoDto;
import com.sifu.core.utils.dto.TblProductorDto;
import com.sifu.core.utils.entity.TblOfertaProducto;
import com.sifu.core.utils.entity.TblProducto;
import com.sifu.core.utils.entity.TblProductor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowRoomService {
    private final Logger log = LogManager.getLogger(ShowRoomService.class);

    private final TblProductoRepository tblProductoRepository;
    private final TblProductorRepository tblProductorRepository;
    private final TblOfertaProductoRepository tblOfertaProductoRepository ;

    public ShowRoomService(TblProductoRepository tblProductoRepository, TblProductorRepository tblProductorRepository, TblOfertaProductoRepository tblOfertaProductoRepository) {
        this.tblProductoRepository = tblProductoRepository;
        this.tblProductorRepository = tblProductorRepository;
        this.tblOfertaProductoRepository = tblOfertaProductoRepository;
    }


    public ApiResponse showAllProducts() {
        try {
            List<TblProducto> tblProductos = tblProductoRepository.findAll();
            log.info("get all products success");
            return ApiResponse.success(tblProductos.stream().map(TblProductoDto::new).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error get all products: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se listar los productos");
        }
    }

    public ApiResponse showProductsByCatalog(String category) {
        try {
            List<TblProducto> tblProductos = tblProductoRepository.findAllByCatRef_CatNombre(category);
            log.info("get all products by catalog success");
            return ApiResponse.success(tblProductos.stream().map(TblProductoDto::new).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error get all products: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se listar los productos por categorias");
        }
    }

    public ApiResponse showAllProductors() {
        try {
            List<TblProductor> tblProductors = tblProductorRepository.findAll();
            log.info("get all products success");
            return ApiResponse.success(tblProductors.stream().map(TblProductorDto::new).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error get all products: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se listar los productos");
        }
    }
    public ApiResponse showProductsByProductorId(Integer productorId) {
        try {
            List<TblOfertaProducto> tblOfertaProductos = tblOfertaProductoRepository.findAllByPrdRef_Id(productorId);
            log.info("get all products by producer success");
            return ApiResponse.success(tblOfertaProductos.stream().map(TblOfertaProductoDto::new).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error get all products  by producer: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se listar los productos por productor");
        }
    }

}
