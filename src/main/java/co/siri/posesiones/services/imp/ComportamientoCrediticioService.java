package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ComportamientoCrediticioDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ComportamientoCrediticioRepository;
import co.siri.posesiones.services.IComportamientoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

import static co.siri.posesiones.utilidades.Constantes.ERRORCONSULTA;

@Service
public class ComportamientoCrediticioService implements IComportamientoCrediticioService {

    @Autowired
    private ComportamientoCrediticioRepository crediticioRepository;

    /***
     * Metodo que realiza la consulta del historial crediticio asociado al cliente con el idIdentidad
     * @param idIdentidad -- id de consulta
     * @return lista con reportes asociados al cliente
     */
    @Override
    public List<ComportamientoCrediticioDTO> getComportamientoCrediticio(Long idIdentidad) {
        try{
            List<ComportamientoCrediticioDTO> response = crediticioRepository.getComportamientoCrediticioByIdIdentidad(idIdentidad);
            if (response.isEmpty()) {
                throw new ExcepcionPersonalizada("No se encontró historial crediticio, con el id: " + idIdentidad, HttpStatus.NO_CONTENT);
            }
            return response;
        }catch (ExcepcionPersonalizada ex){
            throw  new ExcepcionPersonalizada(ERRORCONSULTA + " " + ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
