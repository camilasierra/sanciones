package co.siri.posesiones.controller;

import co.siri.posesiones.controllers.TramitesPosesionesController;
import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.services.ITramitePosesionService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TramitesPosesionesControllerTest {

	@InjectMocks
	private TramitesPosesionesController tramitesPosesionesController;

	@Mock
	private ITramitePosesionService tramitePosesionService;

	@Test
	public void testGetAllActiveTipoEstadoTramitePosesion() {
		// Given, configura el mock para retornar una lista
		List<TipoEstadoTramitePosesion> estados = Arrays.asList(new TipoEstadoTramitePosesion(),
				new TipoEstadoTramitePosesion());
		when(tramitePosesionService.getAllActiveTipoEstadoTramitePosesion()).thenReturn(estados);

		// When llama al metodo del controlador
		ResponseEntity<List<TipoEstadoTramitePosesion>> response = tramitesPosesionesController
				.getAllActiveTipoEstadoTramitePosesion();

		// Then Verifica que el responseEntity tenga status OK y que el cuerpo de la
		// respuesta sea igual a la lista pre definida
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(estados);
		Mockito.verify(tramitePosesionService).getAllActiveTipoEstadoTramitePosesion();
	}

	@Test
	public void testChangeEstadoTramite() {
		// Given Prepara un objeto CambiarEstadoTramitePosesionRequestDto para la
		// solicitud
		CambiarEstadoTramitePosesionRequestDto request = new CambiarEstadoTramitePosesionRequestDto();

		// When Llama al método changeEstadoTramite del controlador con el objeto de
		// solicitud
		ResponseEntity<?> response = tramitesPosesionesController.changeEstadoTramite(request);

		// Then Verifica que el ResponseEntity tenga un status OK y que el método del
		// servicio fue llamado con el objeto de solicitud
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Mockito.verify(tramitePosesionService).changeEstadoTramite(any(CambiarEstadoTramitePosesionRequestDto.class));
	}


	@Test
	void testInfoServidorPublico() {

		InfoServidorPublico infoServidorPublico = InfoServidorPublico.builder()
				.cargoServidorPublico("Cargo Servidor")
				.entidadServidorPublico("Entidad Servidor")
				.build();

		when(tramitePosesionService.consultarInformacionServidorPublico(anyLong()))
				.thenReturn(infoServidorPublico);

		ResponseEntity<InfoServidorPublico> responseEntity = tramitesPosesionesController.getInfoServidorPublico(Mockito.anyLong());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testInfoOtrosRepresentantes() {

		InfoOtrosRepresentantes infoOtrosRepresentantes = InfoOtrosRepresentantes.builder()
				.claseRepresentante("Clase")
				.actividadRepresentante("Actividad")
				.build();

		when(tramitePosesionService.consultarInformacionOtrosRepresentantes(anyLong()))
				.thenReturn(infoOtrosRepresentantes);

		ResponseEntity<InfoOtrosRepresentantes> responseEntity = tramitesPosesionesController.getInfoOtrosRepresentantes(Mockito.anyLong());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testInfoJuntaDirectiva() {

		InfoJuntaDirectiva infoJuntaDirectiva = InfoJuntaDirectiva.builder()
				.build();

		when(tramitePosesionService.consultarInformacionJuntaDirectiva(anyLong()))
				.thenReturn(infoJuntaDirectiva);

		ResponseEntity<InfoJuntaDirectiva> responseEntity = tramitesPosesionesController.getInfoJuntaDirectiva(Mockito.anyLong());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testInfoAdicionalesDefensorConsumidor() {

		InfoAdicionalDefensorConsumidor infoAdicionalDefensorConsumidor = InfoAdicionalDefensorConsumidor.builder()
				.build();

		when(tramitePosesionService.consultarInformacionAdicionalDefensorConsumidor(anyLong()))
				.thenReturn(infoAdicionalDefensorConsumidor);

		ResponseEntity<InfoAdicionalDefensorConsumidor> responseEntity = tramitesPosesionesController.getInfoAdicionalesDefensorConsumidor(Mockito.anyLong());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
