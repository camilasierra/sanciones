package co.siri.posesiones.services.imp;

import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.repositories.ResultadoComiteRepository;
import co.siri.posesiones.services.imp.ResultadoComiteServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class ResultadoComiteServiceImplTest {

    @InjectMocks
    private ResultadoComiteServiceImpl resultadoComiteService;

    @Mock
    private ResultadoComiteRepository resultadoComiteRepository;

    @Test
    public void testFindAll() {
        // Given
        List<ResultadoComite> comites = Arrays.asList(new ResultadoComite(), new ResultadoComite());
        Mockito.when(resultadoComiteRepository.findAll()).thenReturn(comites);

        // When
        List<ResultadoComite> result = resultadoComiteService.findAll();

        // Then
        Assertions.assertThat(result).isEqualTo(comites);
        Mockito.verify(resultadoComiteRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteRepository.findById(anyInt())).thenReturn(Optional.of(comite));

        // When
        Optional<ResultadoComite> result = resultadoComiteService.findById(1);

        // Then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(comite);
        Mockito.verify(resultadoComiteRepository).findById(1);
    }

    @Test
    public void testSave() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteRepository.save(any(ResultadoComite.class))).thenReturn(comite);

        // When
        ResultadoComite result = resultadoComiteService.save(comite);

        // Then
        Assertions.assertThat(result).isEqualTo(comite);
        Mockito.verify(resultadoComiteRepository).save(comite);
    }

    @Test
    public void testDeleteById() {
        // Given
        Integer id = 1;

        // When
        resultadoComiteService.deleteById(id);

        // Then
        Mockito.verify(resultadoComiteRepository).deleteById(id);
    }
}
