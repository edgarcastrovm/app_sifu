package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.repo.TblPersonaRepository;
import com.sifu.core.repo.TblProductoRepository;
import com.sifu.core.repo.TblProductorRepository;
import com.sifu.core.repo.TblUsuarioRepository;
import com.sifu.core.utils.Constants;
import com.sifu.core.utils.Utils;
import com.sifu.core.utils.dto.TblPersonaDto;
import com.sifu.core.utils.dto.TblProductoDto;
import com.sifu.core.utils.dto.TblProductorDto;
import com.sifu.core.utils.dto.TblUsuarioDto;
import com.sifu.core.utils.entity.TblPersona;
import com.sifu.core.utils.entity.TblProducto;
import com.sifu.core.utils.entity.TblProductor;
import com.sifu.core.utils.entity.TblUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class RegistryService {
    private final Logger log = LogManager.getLogger(RegistryService.class);

    private final TblProductorRepository tblProductorRepository;
    private final TblProductoRepository tblProductoRepository;
    private final TblUsuarioRepository tblUsuarioRepository;
    private final TblPersonaRepository tblPersonaRepository;

    public RegistryService(TblProductorRepository tblProductorRepository, TblProductoRepository tblProductoRepository, TblUsuarioRepository tblUsuarioRepository, TblPersonaRepository tblPersonaRepository) {
        this.tblProductorRepository = tblProductorRepository;
        this.tblProductoRepository = tblProductoRepository;
        this.tblUsuarioRepository = tblUsuarioRepository;
        this.tblPersonaRepository = tblPersonaRepository;
    }

    public ApiResponse registryPerson(TblPersonaDto item) {
        TblPersona tblPersona;
        try {
            tblPersona = item.toEntity();
            tblPersonaRepository.save(tblPersona);
            log.info("registry person success");
            return ApiResponse.success(new TblPersonaDto(tblPersona));
        } catch (Exception e) {
            log.error("Error registry person: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se pudo registrar la información personal");
        }
    }

    public ApiResponse registryUser(TblUsuarioDto item) {
        TblUsuario tblUsuario;
        try {
            tblUsuario = item.toEntity();
            tblUsuarioRepository.save(tblUsuario);
            log.info("registry user success");
            return ApiResponse.success(new TblUsuarioDto(tblUsuario));
        } catch (Exception e) {
            log.error("Error registry user: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se pudo registrar el usuario");
        }
    }

    public ApiResponse registryProducer(TblProductorDto item) {
        TblProductor tblProductor;
        try {
            TblPersona tblPersona;
            // validar si la persona existe
            Optional<TblPersona> _persona = tblPersonaRepository
                    .findByPerIdentificacion(item.getPerRef().getPerIdentificacion());
            if (_persona.isPresent()) {
                return ApiResponse.error(RC.BAD_REQUEST, "No se pudo registrar el productor");
            } else {
                tblPersona = item.getPerRef().toEntity();
                tblPersonaRepository.save(tblPersona);

                TblUsuario tblUsuario = new TblUsuario();
                tblUsuario.setPerRef(tblPersona);
                tblUsuario.setUsuNombreUsuario(tblPersona.getPerEmail());
                tblUsuario.setUsuPasswordHash(Utils.getMd5(tblPersona.getPerIdentificacion()));
                tblUsuario.setUsuRol(Constants.ROL_PRODUCER);
                tblUsuario.setUsuEstado(Constants.STATE_ACTIVE);
                tblUsuario.setUsuFechaCreacion(OffsetDateTime.now());
                tblUsuarioRepository.save(tblUsuario);

                tblProductor = item.toEntity();
                tblProductor.setPerRef(tblPersona);

                tblProductorRepository.save(tblProductor);
                log.info("registry producer success");
                return ApiResponse.success(new TblProductorDto(tblProductor));
            }
        } catch (Exception e) {
            log.error("Error registry producer: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se pudo registrar el productor");
        }
    }

    public ApiResponse registryProduct(TblProductoDto item) {
        TblProducto tblProducto;
        try {
            tblProducto = item.toEntity();
            tblProductoRepository.save(tblProducto);
            log.info("registry product success");
            return ApiResponse.success(new TblProductoDto(tblProducto));
        } catch (Exception e) {
            log.error("Error registry product: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se pudo registrar el producto");
        }
    }


}
