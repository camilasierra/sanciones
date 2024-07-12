package co.siri.posesiones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.siri.posesiones.dtos.MiembroComiteDTO;
import co.siri.posesiones.services.imp.MiembroComiteService;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/gestionMiembros")
@Tag(name = "Gestion miembros de comites Controller")
public class GestionMiembrosController {

    @Autowired
    private MiembroComiteService miembroComiteService;

    @GetMapping("/allMiembros")
    public ResponseEntity<List<MiembroComiteDTO>> getAllMiembros() {
        List<MiembroComiteDTO> members = miembroComiteService.getAllMiembros();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/miembrosActivos")
    public ResponseEntity<List<MiembroComiteDTO>> getMiemborsActivos(){
        List<MiembroComiteDTO> miembroActivo = miembroComiteService.getMiembrosActivos();
        return new ResponseEntity<>(miembroActivo, HttpStatus.OK);
    }

    @PostMapping("/agregarMiembro")
    public ResponseEntity<MiembroComiteDTO> addMember(@RequestBody MiembroComiteDTO memberDTO) {
        MiembroComiteDTO savedMember = miembroComiteService.addMiembro(memberDTO);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PostMapping("/editar")
    public ResponseEntity<MiembroComiteDTO> createOrUpdateMember(@RequestBody MiembroComiteDTO memberDTO) {
        MiembroComiteDTO savedMember = miembroComiteService.saveOrUpdateMiembro(memberDTO);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }


    @PutMapping("/{idmiembrocomite}/replace")
    public ResponseEntity<Void> replaceMember(@PathVariable Integer idmiembrocomite, @RequestBody MiembroComiteDTO newMemberDTO) {
        try {
            miembroComiteService.replaceMiembro(idmiembrocomite, newMemberDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idmiembrocomite}/inactivate")
    public ResponseEntity<Void> inactivateMember(@PathVariable Integer idmiembrocomite) {
        try {
            miembroComiteService.inactivarMiembro(idmiembrocomite);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<MiembroComiteDTO>> searchMiembros(@RequestParam String searchText) {
        List<MiembroComiteDTO> miembros = miembroComiteService.getMiembrosOrdenadosFiltrados(searchText);
        return ResponseEntity.ok(miembros);
    }

    @GetMapping("/miembro/{id}")
    public ResponseEntity<MiembroComiteDTO> getMiembroById(@PathVariable Integer id) {
        MiembroComiteDTO miembro = miembroComiteService.getMiembroById(id);
        if (miembro != null) {
            return new ResponseEntity<>(miembro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}