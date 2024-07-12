package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.ComportamientoCrediticioDTO;
import co.siri.posesiones.entities.ComportamientoCrediticio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComportamientoCrediticioRepository  extends JpaRepository<ComportamientoCrediticio, Long> {

    @Query(value = "SELECT tcc.nombre clasecentral, cc.nombrecentral, tob.nombre tipoobligacion, cc.fechareporte, tcm.nombre Morade,\n" +
            "       cc.fechapago, tcrc.nombre Calidadreportecrediticio, cc.EMPRESAREPORTA empresareporta, \n" +
            "       apj.nombre nombrearchivojustificacion, DBMS_LOB.SUBSTR(apj.archivo , 2000, 1) justificacion,\n" +
            "       apr.nombre nombrearchivocertificacion, DBMS_LOB.SUBSTR(apr.archivo, 2000, 1) certificacion,\n" +
            "       apj.CONTENTTYPE typearchivojustificacion,apr.CONTENTTYPE typearchivocertificacion\n" +
            "       , '-----' AS separator, cc.*\n" +
            "  FROM comportamientocrediticio cc\n" +
            "       LEFT JOIN  tipoclasecentral tcc ON cc.idtipoclasecentral = tcc.idtipoclasecentral\n" +
            "       LEFT JOIN tipoobligacion tob ON cc.idtipoobligacion = tob.idtipoobligacion\n" +
            "       LEFT JOIN tipoclasificacionmora tcm ON cc.idtipoclasificacionmora = tcm.idtipoclasificacionmora\n" +
            "       LEFT JOIN tipocalidadreportecrediticio tcrc ON cc.idtipocalidadreportecrediticio = tcrc.idtipocalidadreportecrediticio\n" +
            "       LEFT JOIN archivopersona apj ON cc.idarchivojustificacion = apj.idarchivopersona\n" +
            "       LEFT JOIN archivopersona apr ON cc.idarchivocertificacion = apr.idarchivopersona\n" +
            " WHERE cc.idpersona = :idIdentidad\n" +
            "ORDER BY  cc.fechareporte DESC", nativeQuery = true)
    List<ComportamientoCrediticioDTO> getComportamientoCrediticioByIdIdentidad(@Param("idIdentidad") Long idIdentidad);
}
