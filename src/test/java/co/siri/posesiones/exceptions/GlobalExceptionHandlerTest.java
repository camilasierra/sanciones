package co.siri.posesiones.exceptions;

import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;


    @Test
    public void testHandleExcepcionPersonalizada() {
        // Given
        ExcepcionPersonalizada exception = new ExcepcionPersonalizada("Test Custom Exception", null);

        // When
        ResponseEntity<String> response = globalExceptionHandler.handleExcepcionPersonalizado(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Test Custom Exception");
    }

    @Test
    public void testHandleDataAccessException() {
        // Given
        DataAccessException exception = mock(DataAccessException.class);
        WebRequest request = mock(WebRequest.class);
        Mockito.when(exception.getMessage()).thenReturn("Test Data Access Exception");

        // When
        ResponseEntity<?> response = globalExceptionHandler.handleDataAccessException(exception, request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(response.getBody()).isEqualTo("Database Error: Test Data Access Exception");
    }

    @Test
    public void testHandleGlobalException() {
        // Given
        Exception exception = new Exception("Test Global Exception");
        WebRequest request = mock(WebRequest.class);

        // When
        ResponseEntity<?> response = globalExceptionHandler.handleGlobalException(exception, request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("An unexpected error occurred: Test Global Exception");
    }
}
