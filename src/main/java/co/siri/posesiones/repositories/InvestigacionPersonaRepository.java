package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.ComportamientoCrediticioDTO;
import co.siri.posesiones.dtos.InvestigacionesPersonasDTO;
import co.siri.posesiones.entities.InvestigacionPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestigacionPersonaRepository  extends JpaRepository<InvestigacionPersona, Long> {

    @Query(value = "SELECT ti.nombre tipoinvestigacion, teis.nombre entidadinvestiga, tmis.nombre materiainvestigacion, tais.nombre tipoacto,\n" +
            "       ip.numero, tesis.nombre estadoinvestigacion, ip.fechadesde\n" +
            "       , '-----' AS separator, ip.*\n" +
            "  FROM investigacionpersona ip\n" +
            "       LEFT JOIN tipoinvestigacion ti on ip.idtipoinvestigacion = ti.idtipoinvestigacion\n" +
            "       LEFT JOIN tipoentidadinvestigacionsanc teis on ip.idtipoentidadinvestigacionsanc = teis.idtipoentidadinvestigacionsanc\n" +
            "       LEFT JOIN tipomateriainvestigacionsanc tmis on ip.idtipomateriainvestigacionsanc = tmis.idtipomateriainvestigacionsanc\n" +
            "       LEFT JOIN tipoactoinvestigacionsancion tais on ip.idtipoactoinvestigacionsancion = tais.idtipoactoinvestigacionsancion\n" +
            "       LEFT JOIN tipoestadoinvestigacion tesis on ip.idtipoestadoinvestigacion = tesis.idtipoestadoinvestigacion\n" +
            "  WHERE ip.idpersona = :idPersona", nativeQuery = true)
    List<InvestigacionesPersonasDTO> getInvestigacionPersonaByIdPersona(@Param("idPersona") Long idPersona);
}
