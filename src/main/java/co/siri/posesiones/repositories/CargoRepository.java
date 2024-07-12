package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.TipoCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<TipoCargo, Long> {
    @Query(value = "SELECT * \n" +
            "FROM TIPOCARGO tc \n" +
            "WHERE tc.IDTIPOGRUPOCARGO=1 \n" +
            "AND tc.ACTIVO=1 \n" +
            "ORDER BY ORDEN", nativeQuery = true)
    List<TipoCargo> listCargo();
}
