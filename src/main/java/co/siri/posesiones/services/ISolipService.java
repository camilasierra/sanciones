package co.siri.posesiones.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-solip")
public interface ISolipService {

	@GetMapping("/api/funcionario/consultarFirmaFuncionario/{documento}")
	public String consultarFirmaDocumento(@PathVariable("documento") String documento);
}
