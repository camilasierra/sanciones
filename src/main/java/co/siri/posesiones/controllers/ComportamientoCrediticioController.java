package co.siri.posesiones.controllers;
import co.siri.posesiones.dtos.ComportamientoCrediticioDTO;
import co.siri.posesiones.services.IComportamientoCrediticioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/comportamientoCrediticio")
@Tag(name = "servicio para  traer la informacion de centrales de riesgo y validar comportamiento crediticio")
public class ComportamientoCrediticioController {

    @Autowired
    private IComportamientoCrediticioService comportamientoCrediticioService;

    @GetMapping("/getComportamientoCrediticio/{idIdentidad}")
    public ResponseEntity<List<ComportamientoCrediticioDTO>> getComportamientoCrediticio(@PathVariable("idIdentidad") Long idIdentidad) {
            return ResponseEntity.status(HttpStatus.OK).body(comportamientoCrediticioService.getComportamientoCrediticio(idIdentidad));
    }
}
