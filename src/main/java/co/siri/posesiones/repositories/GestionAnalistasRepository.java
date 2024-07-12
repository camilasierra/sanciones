package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.GestionAnalistasDTO;
import co.siri.posesiones.entities.AnalistaTramitePosesion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GestionAnalistasRepository extends JpaRepository<AnalistaTramitePosesion, Long> {
    @Query(value = "SELECT a.IDANALISTATRAMITEPOSESION AS idAnalistaTramitePosesion, " +
               "a.LOGIN AS login, " +
               "a.IDENTIFICACION AS identificacion, " +
               "a.NOMBRE AS nombre, " +
               "a.IDTIPOANALISTA AS idTipoAnalista, " +
               "ta.NOMBRE AS nombreTipoAnalista, " +
               "a.IDTIPOINACTIVACIONANALISTA AS idTipoInactivacionAnalista, " +
               "tia.NOMBRE AS nombreTipoInactivacionAnalista, " +
               "DECODE(a.ACTIVO, 1, 'Activo', 0, 'Inactivo', 'Inactivo') AS activo, " +
               "a.LIMITECARGA AS limiteCarga, " +
               "a.IDUSUARIO AS idUsuario, " +
               "a.IPCLIENTE AS ipCliente, " +
               "ppa.IDPRIORIDADTRAMITEPOSESION AS idPrioridadTramitePosesion " +
               "FROM analistatramiteposesion a " +
               "JOIN tipoanalista ta ON a.idtipoanalista = ta.idtipoanalista " +
               "LEFT JOIN tipoinactivacionanalista tia ON a.idtipoinactivacionanalista = tia.idtipoinactivacionanalista " +
               "LEFT JOIN prioridadporanalista ppa ON a.IDANALISTATRAMITEPOSESION = ppa.IDANALISTATRAMITEPOSESION", 
       nativeQuery = true)
    List<GestionAnalistasDTO> listarAnalistas();

}
