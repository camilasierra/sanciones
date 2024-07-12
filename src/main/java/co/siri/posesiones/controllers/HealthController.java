package co.siri.posesiones.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/health")
public class HealthController {
    @Value("${spring.application.name}")
    private String nombreApp;
    
    @GetMapping("/check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Esta conectado correctamente al API: "+nombreApp);
    }
}
