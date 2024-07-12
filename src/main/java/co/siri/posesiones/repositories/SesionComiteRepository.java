package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.ListaComitesDTO;
import co.siri.posesiones.entities.SesionComite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import co.siri.posesiones.dtos.MiembrosComiteDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Repository
public interface SesionComiteRepository extends JpaRepository<SesionComite, Long> {
    @Query(value = "Select sc.idsesioncomite,\n" +
            "       sc.fechacomite, sc.numeroacta, tsc.nombre \n" +
            "  from sesioncomite sc\n" +
            "       left join tiposesioncomite tsc on sc.idtiposesioncomite = tsc.idtiposesioncomite\n" +
            " where sc.fechacomite <= trunc(sysdate)", nativeQuery = true)
    List<ListaComitesDTO> listSesionComite();

    @Query(value = "Select sc.idsesioncomite,\n" +
            "       sc.fechacomite, tsc.nombre \n" +
            "  from sesioncomite sc\n" +
            "       left join tiposesioncomite tsc on sc.idtiposesioncomite = tsc.idtiposesioncomite\n" +
            " where sc.numeroacta is null\n" +
            "   and sc.horainiciocomite is null\n" +
            "   and sc.horafincomite is null", nativeQuery = true)
    List<ListaComitesDTO> listComite();

    @Query(value = "Select mc.cargo, mc.nombrepersona, mc.idtipomiembrocomite\n" +
            "from sesioncomite sc\n" +
            "join asistentecomite ac on sc.idsesioncomite = ac.idsesioncomite\n" +
            "join miembrocomite mc on ac.idmiembrocomite = mc.idmiembrocomite\n" +
            "where mc.activo = 1\n" +
            "and mc.idtipomiembrocomite in (1,2,3,4)\n" +
            "and sc.idsesioncomite =:idSesionComite\n" +
            "order by mc.orden", nativeQuery = true)
    List<MiembrosComiteDTO> getMiembrosComite(@Param("idSesionComite") Integer idSesionComite);

    @Query("SELECT sc FROM SesionComite sc ORDER BY sc.fechaComite DESC")
    Page<SesionComite> obtenerSesionesComite(
            Pageable pageable
    );

    Optional<SesionComite> findByfechaComite(Date fecha);
}
