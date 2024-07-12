package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.InhabilidadPosesionDTO;
import co.siri.posesiones.dtos.InhabilidadPosesionProjection;
import co.siri.posesiones.dtos.TipoCargoDTO;
import co.siri.posesiones.entities.InhabilidadPosesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InhabilidadPosesionRepository extends JpaRepository<InhabilidadPosesion, Long> {

    @Query(value = "SELECT * FROM PAR_INHABILIDADAPLICACION WHERE IDTIPOSECCIONINHABILIDADCARGO = 2", nativeQuery = true)
    List<InhabilidadPosesion> findAllByTipoSeccionInhabilidadCargo();

    @Query(value = "SELECT CAST(ROW_NUMBER() OVER (ORDER BY tip.orden) AS INTEGER) AS orden, tip.descripcion, decode(ip.inhabilitado,1,'*SI*','no') as inhabilitado " +
            "FROM inhabilidadposesion ip " +
            "JOIN tipoinhabilidadposesion tip ON ip.idtipoinhabilidadposesion = tip.idtipoinhabilidadposesion " +
            "WHERE ip.idtramiteposesion = :idTramitePosesion " +
            "AND ip.idtiposeccioninhabilidadcargo = 1 " +
            "ORDER BY tip.orden", nativeQuery = true)
    List<Map<String, Object>>findInhabilidadPosesionByTramiteAndSeccion(Long idTramitePosesion);

    @Query(value = "Select CAST(ROW_NUMBER() OVER (ORDER BY tip.orden) AS INTEGER) AS orden, ip.idtipoinhabilidadposesion, tip.descripcion, decode(ip.inhabilitado,1,'*SI*','no') inhabilitado\n" +
            "from inhabilidadposesion ip\n" +
            "         join tipoinhabilidadposesion tip on ip.idtipoinhabilidadposesion = tip.idtipoinhabilidadposesion\n" +
            "where ip.idtramiteposesion = :idTramitePosesion " +
            "  and ip.idtiposeccioninhabilidadcargo = 2 " +
            "order by tip.orden", nativeQuery = true)
    List<Map<String, Object>> findTipoCargosByTramitePosesionId(Long idTramitePosesion);

    @Query(value =
            """
            SELECT 
            CAST(ROW_NUMBER() OVER (ORDER BY tip.orden) AS INTEGER) AS orden, 
            tip.descripcion, 
            CASE WHEN ip.inhabilitado = 1 THEN '*SI*' ELSE 'no' END AS inhabilitado, 
            ip.idtiposeccioninhabilidadcargo AS idtiposeccioninhabilidadcargo, tic.NOMBRE AS nombre
            FROM inhabilidadposesion ip
            JOIN tipoinhabilidadposesion tip ON ip.idtipoinhabilidadposesion = tip.idtipoinhabilidadposesion
            JOIN tiposeccioninhabilidadcargo tic on ip.IDTIPOSECCIONINHABILIDADCARGO = tic.IDTIPOSECCIONINHABILIDADCARGO
            WHERE ip.idtiposeccioninhabilidadcargo IN (:idSecciones)
            AND ip.idtramiteposesion = :idTramite
            ORDER BY tip.orden
            """,
            nativeQuery = true)
    List<InhabilidadPosesionProjection> obtenerInhabilidadesComunesPorSeccionYTramite(
            @Param("idSecciones") List<Integer> idSecciones,
            @Param("idTramite") int idTramite);


    @Query(value = "SELECT tip.descripcion, CASE WHEN ip.inhabilitado = 1 THEN '*SI*' ELSE 'no' END AS inhabilitado " +
            "FROM inhabilidadposesion ip " +
            "JOIN tipoinhabilidadposesion tip ON ip.idtipoinhabilidadposesion = tip.idtipoinhabilidadposesion " +
            "WHERE ip.idtramiteposesion = :idTramite " +
            "AND ip.idtipoinhabilidadposesion IN " +
            "(SELECT pia.idtipoinhabilidadposesion " +
            "FROM par_inhabilidadaplicacion pia " +
            "WHERE (pia.idtipocargo, pia.idtipoentidad, pia.idtipocalidadcargo) IN " +
            "(SELECT tp.idtipocargo, e.idtipoentidad, tp.idtipocalidadcargo " +
            "FROM tramiteposesion tp " +
            "JOIN entidad e ON tp.identidad = e.identidad " +
            "WHERE tp.idtramiteposesion = :idTramite)) " +
            "ORDER BY tip.orden",
            nativeQuery = true)
    List<InhabilidadPosesionProjection> obtenerInhabilidadesPorTramite(@Param("idTramite") Long idTramite);

    @Query(value = "SELECT tip.descripcion AS descripcion, " +
            "       CASE WHEN ip.inhabilitado = 1 THEN 'SI' ELSE 'no' END AS inhabilitado " +
            "FROM inhabilidadposesion ip " +
            "JOIN tipoinhabilidadposesion tip ON ip.idtipoinhabilidadposesion = tip.idtipoinhabilidadposesion " +
            "WHERE ip.idtramiteposesion = :idTramitePosesion " +
            "  AND ip.idtiposeccioninhabilidadcargo = 2 " +
            "ORDER BY tip.orden", nativeQuery = true)
    List<Map<String, Object>> findInhabilidadPosesion(@Param("idTramitePosesion") int idTramitePosesion);


    @Query(value = "Select CAST(ROW_NUMBER() OVER (ORDER BY tip.orden) AS INTEGER) AS orden, tip.descripcion, decode(ip.inhabilitado,1,'SI','no') inhabilitado\n" +
            "from inhabilidadposesion ip\n" +
            "         join tipoinhabilidadposesion tip on ip.idtipoinhabilidadposesion = tip.idtipoinhabilidadposesion\n" +
            "where ip.idtramiteposesion = :idTramitePosesion  --PARAMETRO\n" +
            "  and ip.idtiposeccioninhabilidadcargo = 3 --idSeccion constante\n" +
            "order by tip.orden", nativeQuery = true )
    List<InhabilidadPosesionProjection>GetOtrasSituaciones(@Param("idTramitePosesion") int idTramitePosesion);

}

