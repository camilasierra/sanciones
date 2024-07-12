package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.OtroAsistenteComiteDTO;
import co.siri.posesiones.entities.OtroAsistenteComite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtroAsistenteComiteRepository extends JpaRepository<OtroAsistenteComite, Long> {
    @Query(value = "Select *\n" +
            "  from otroasistentecomite oac\n" +
            " where oac.idsesioncomite = :idSesionComite", nativeQuery = true)
    List<OtroAsistenteComiteDTO> getOtroAsistenteComite(@Param("idSesionComite") Integer idSesionComite);

    @Query(value = "SELECT COALESCE(MAX(P.IDOTROASISTENTECOMITE), 0) + 1 AS next_id\n" +
            "FROM OTROASISTENTECOMITE P", nativeQuery = true)
    Long ultimoId();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO OTROASISTENTECOMITE (IDOTROASISTENTECOMITE, IDSESIONCOMITE, NOMBREOTROASISTENTE, CALIDADASISTENTE, IDUSUARIO, IPCLIENTE)\n" +
            "VALUES (:idOtroAsistente, :idSesionComite, :nombreAsistente, :calidadAsistente, :idUsuario, :ipCliente)",
            nativeQuery = true)
    void insertarOtroAsistente(
            @Param("idOtroAsistente") Long idOtroAsistente,
            @Param("idSesionComite") Integer idSesionComite,
            @Param("nombreAsistente") String nombreAsistente,
            @Param("calidadAsistente") String calidadAsistente,
            @Param("idUsuario") Integer idUsuario,
            @Param("ipCliente") String ipCliente);
}
