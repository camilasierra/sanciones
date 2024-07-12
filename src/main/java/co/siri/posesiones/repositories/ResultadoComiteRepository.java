package co.siri.posesiones.repositories;


import co.siri.posesiones.dtos.ComiteDetallesResponseDto;
import co.siri.posesiones.entities.ResultadoComite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResultadoComiteRepository extends JpaRepository<ResultadoComite, Integer> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE resultadocomite rc" +
            "   SET rc.anotacion = :anotacion, " +
            "       rc.idtipoestadotramiteposesion = :idtipoestadotramiteposesion " +
            " WHERE rc.idsesioncomite = :idsesioncomite " +
            "   AND rc.idtramiteposesion = :idtramiteposesion ", nativeQuery = true)
    void updateResultadoComite(@Param("anotacion") String anotacion,
                               @Param("idtipoestadotramiteposesion") Integer idtipoestadotramiteposesion,
                               @Param("idsesioncomite") Integer idsesioncomite,
                               @Param("idtramiteposesion") Integer idtramiteposesion);



    @Query(value =
            "SELECT  \n" +
                    "   COALESCE(p.PRIMERNOMBRE, '') || ' ' || \n" +
                    "   COALESCE(p.SEGUNDONOMBRE, '') || ' ' || \n" +
                    "   COALESCE(p.PRIMERAPELLIDO, '') || ' ' || \n" +
                    "   COALESCE(p.SEGUNDOAPELLIDO, '') AS nombreCompleto,\n" +
                    "   d.NUMERO AS identificacion,\n" +
                    "   t.NUMERORADICACION AS numeroRadicado,\n" +
                    "   e.SIGLA AS entidad,\n" +
                    "   tc.NOMBRE AS cargo,\n" +
                    "   uig.DIASTERMINO - uig.DIASHABILESSFC AS vencimiento\n" +
                    "   FROM SESIONCOMITE s \n" +
                    "   INNER JOIN RESULTADOCOMITE r ON s.IDSESIONCOMITE = r.IDSESIONCOMITE \n" +
                    "   INNER JOIN TRAMITEPOSESION t ON r.IDTRAMITEPOSESION = t.IDTRAMITEPOSESION \n" +
                    "   INNER JOIN TIPOCARGO tc ON t.IDTIPOCARGO = tc.IDTIPOCARGO \n" +
                    "   INNER JOIN PERSONA p ON t.IDPERSONA = p.IDPERSONA \n" +
                    "   INNER JOIN DOCUMENTOPERSONA d ON p.IDPERSONA =d.IDPERSONA \n" +
                    "   INNER JOIN ENTIDAD e ON t.IDENTIDAD = e.IDENTIDAD\n" +
                    "   LEFT  JOIN v_ig_tramiteposesion_ultimo uig ON p.IDPERSONA  = uig.idtramiteposesion\n" +
                    "   WHERE r.IDRESULTADOCOMITE = :idSesioncomite\n" +
                    "", nativeQuery = true)
    List<ComiteDetallesResponseDto> findComiteDetails(@Param("idSesioncomite") Long idSesioncomite);

    void deleteByidtramiteposesion(int idTramitePosesion);
}
