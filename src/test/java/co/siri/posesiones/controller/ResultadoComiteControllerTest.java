package co.siri.posesiones.controller;

import co.siri.posesiones.controllers.ResultadoComiteController;
import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.services.IResultadoComiteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class ResultadoComiteControllerTest {

    @InjectMocks
    private ResultadoComiteController resultadoComiteController;

    @Mock
    private IResultadoComiteService resultadoComiteService;

    @Test
    public void testGetAll() {
        // Given
        List<ResultadoComite> comites = Arrays.asList(new ResultadoComite(), new ResultadoComite());
        Mockito.when(resultadoComiteService.findAll()).thenReturn(comites);

        // When
        ResponseEntity<List<ResultadoComite>> response = resultadoComiteController.getAll();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(comites);
    }

    @Test
    public void testGetById() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteService.findById(anyInt())).thenReturn(Optional.of(comite));

        // When
        ResponseEntity<ResultadoComite> response = resultadoComiteController.getById(1);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(comite);
    }

    @Test
    public void testGetById_NotFound() {
        // Given
        Mockito.when(resultadoComiteService.findById(anyInt())).thenReturn(Optional.empty());

        // When
        ResponseEntity<ResultadoComite> response = resultadoComiteController.getById(1);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreate() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteService.save(any(ResultadoComite.class))).thenReturn(comite);

        // When
        ResponseEntity<ResultadoComite> response = resultadoComiteController.create(comite);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(comite);
    }

    @Test
    public void testUpdate() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteService.findById(anyInt())).thenReturn(Optional.of(comite));
        Mockito.when(resultadoComiteService.save(any(ResultadoComite.class))).thenReturn(comite);

        // When
        ResponseEntity<ResultadoComite> response = resultadoComiteController.update(1, comite);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(comite);
    }

    @Test
    public void testUpdate_NotFound() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteService.findById(anyInt())).thenReturn(Optional.empty());

        // When
        ResponseEntity<ResultadoComite> response = resultadoComiteController.update(1, comite);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDelete() {
        // Given
        ResultadoComite comite = new ResultadoComite();
        Mockito.when(resultadoComiteService.findById(anyInt())).thenReturn(Optional.of(comite));

        // When
        ResponseEntity<Void> response = resultadoComiteController.delete(1);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Mockito.verify(resultadoComiteService).deleteById(1);
    }

    @Test
    public void testDelete_NotFound() {
        // Given
        Mockito.when(resultadoComiteService.findById(anyInt())).thenReturn(Optional.empty());

        // When
        ResponseEntity<Void> response = resultadoComiteController.delete(1);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
