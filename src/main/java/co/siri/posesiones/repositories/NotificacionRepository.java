package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.NotificacionTramiteDto;
import co.siri.posesiones.entities.AsignacionTramiteAnalista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<AsignacionTramiteAnalista, Long> {

    @Query(value = "SELECT atp.IDANALISTATRAMITEPOSESION, atp.LOGIN, atp.IDENTIFICACION, atp.NOMBRE, atp.IDTIPOANALISTA, atp.IDTIPOINACTIVACIONANALISTA, atp.ACTIVO, atp.LIMITECARGA, atp.IDUSUARIO AS idUsuarioAnalista, atp.IPCLIENTE AS ipClienteAnalista, \n" +
            "ata.IDASIGNACIONTRAMITEANALISTA, ata.IDTRAMITEPOSESION, ata.CARGA, ata.IDUSUARIO AS idUsuarioTramite, ata.IPCLIENTE AS ipClienteTramite FROM asignaciontramiteanalista ata\n" +
            "JOIN analistatramiteposesion atp on ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
            "WHERE ata.idtramiteposesion = :idtramiteposesion \n" +
            "AND ata.idanalistatramiteposesion = :idanalistatramiteposesion", nativeQuery = true)
    List<NotificacionTramiteDto> listAsignacionTramite(
            @Param("idtramiteposesion") Long idTramitePosesion,
            @Param("idanalistatramiteposesion") Long idAanalistaTramitePosesion
    );


    @Query(value = "SELECT atp.IDANALISTATRAMITEPOSESION, atp.LOGIN, atp.IDENTIFICACION, atp.NOMBRE, atp.IDTIPOANALISTA, atp.IDTIPOINACTIVACIONANALISTA, atp.ACTIVO, atp.LIMITECARGA, atp.IDUSUARIO AS idUsuarioAnalista, atp.IPCLIENTE AS ipClienteAnalista, \n" +
            "ata.IDASIGNACIONTRAMITEANALISTA, ata.IDTRAMITEPOSESION, ata.CARGA, ata.IDUSUARIO AS idUsuarioTramite, ata.IPCLIENTE AS ipClienteTramite FROM asignaciontramiteanalista ata\n" +
            "JOIN analistatramiteposesion atp on ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
            "WHERE ata.idanalistatramiteposesion = :idanalistatramiteposesion", nativeQuery = true)
    List<NotificacionTramiteDto> listNotificacionTramite(
            @Param("idanalistatramiteposesion") Long idanalistatramiteposesion
    );
}
