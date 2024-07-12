package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ParametroAnalistaDTO;
import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.repositories.ParametroRepository;
import co.siri.posesiones.services.imp.ParametroService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class ParametroServiceTest {

    @InjectMocks
    private ParametroService parametroService;

    @Mock
    private ParametroRepository parametroRepository;

    @Test
    public void getParametroTest() {
        // given
        Mockito.when(parametroRepository.findByIdParametro(any())).thenReturn(createParametro());

        // when
        Parametros parametro = parametroService.getParametro(55);

        // expect
        Assertions.assertThat(parametro).isNotNull();
    }

    private Parametros createParametro() {
        Parametros parametros = new Parametros();
        parametros.setIdParametro(1L);
        parametros.setNombre("parametro");
        parametros.setValor("valor parametro");
        parametros.setIpCliente("123456789");
        parametros.setIdUsuario(1L);
        return parametros;
    }

    @Test
    public void getParametrosAnalistaTest(){

        Parametros parametros = new Parametros();
        parametros.setIdParametro(64L);
        parametros.setNombre("cargaMaximaAnalistaApoyo");
        parametros.setValor("5");
        parametros.setIpCliente("123456789");
        parametros.setIdUsuario(1L);

        Parametros parametrosDos = new Parametros();
        parametrosDos.setIdParametro(63L);
        parametrosDos.setNombre("cargaMaximaAnalistaBase");
        parametrosDos.setValor("25");
        parametrosDos.setIpCliente("123456789");
        parametrosDos.setIdUsuario(1L);

        Parametros parametrosTres = new Parametros();
        parametrosTres.setIdParametro(65L);
        parametrosTres.setNombre("diferenciaMaximaCarga");
        parametrosTres.setValor("5");
        parametrosTres.setIpCliente("123456789");
        parametrosTres.setIdUsuario(1L);

        List<Parametros> listaParametros = Arrays.asList(parametros,parametrosDos,parametrosTres);

        Mockito.when(parametroRepository.findParametrosByNames(anyList())).thenReturn(listaParametros);

        List<Parametros> parametrosList = parametroService.getParametrosAnalista();

        //expect
        Assertions.assertThat(parametrosList).isNotNull()
        .hasSize(3)
        .containsExactlyInAnyOrder(parametros, parametrosDos, parametrosTres);

        Mockito.verify(parametroRepository).findParametrosByNames(Arrays.asList("64","64","65"));
    }

    @Test
    public void actualizarParametrosAnalistaTest(){
        ParametroAnalistaDTO parametroAnalista = new ParametroAnalistaDTO();
        parametroAnalista.setCargaMaximaAnalistaApoyo(30);
        parametroAnalista.setCargaMaximaAnalistaBase(25);
        parametroAnalista.setCargaMaximaAnalistaBase(5);

        Parametros parametros = new Parametros();
        parametros.setIdParametro(64L);
        parametros.setNombre("cargaMaximaAnalistaApoyo");
        parametros.setValor("5");
        parametros.setIpCliente("123456789");
        parametros.setIdUsuario(1L);

        Parametros parametrosDos = new Parametros();
        parametrosDos.setIdParametro(63L);
        parametrosDos.setNombre("cargaMaximaAnalistaBase");
        parametrosDos.setValor("25");
        parametrosDos.setIpCliente("123456789");
        parametrosDos.setIdUsuario(1L);

        Parametros parametrosTres = new Parametros();
        parametrosTres.setIdParametro(65L);
        parametrosTres.setNombre("diferenciaMaximaCarga");
        parametrosTres.setValor("5");
        parametrosTres.setIpCliente("123456789");
        parametrosTres.setIdUsuario(1L);

        List<Parametros> listaParametros = Arrays.asList(parametros,parametrosDos,parametrosTres);

        Mockito.when(parametroRepository.findParametrosByNames(anyList())).thenReturn(listaParametros);
        Mockito.when(parametroRepository.save(any(Parametros.class))).thenAnswer(InvocationOnMock::getArguments);

        parametroService.actualizarParametros(parametroAnalista);

        Mockito.verify(parametroRepository).findParametrosByNames(anyList());
        Mockito.verify(parametroRepository).save(parametros);
        Mockito.verify(parametroRepository).save(parametrosDos);
        Mockito.verify(parametroRepository).save(parametrosTres);

        Assertions.assertThat(parametros.getValor()).isEqualTo("30");
        Assertions.assertThat(parametros.getValor()).isEqualTo("25");
        Assertions.assertThat(parametros.getValor()).isEqualTo("20");
    }
}
