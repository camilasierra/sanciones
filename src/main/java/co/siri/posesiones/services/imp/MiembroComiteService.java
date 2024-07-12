package co.siri.posesiones.services.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.siri.posesiones.dtos.MiembroComiteDTO;
import co.siri.posesiones.entities.MiembroComite;
import co.siri.posesiones.repositories.MiembroComiteRepository;
import co.siri.posesiones.services.IMiembroComiteService;

@Service
public class MiembroComiteService implements IMiembroComiteService {

	@Autowired
	private MiembroComiteRepository miembroRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional(readOnly = true)
	public List<MiembroComiteDTO> getAllMiembros() {
		List<MiembroComite> miembros = miembroRepository.findAll();
		return miembros.stream().map(member -> modelMapper.map(member, MiembroComiteDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<MiembroComiteDTO> getMiembrosActivos(){
		List<MiembroComite> miembros = miembroRepository.miembrosActivos();
		return miembros.stream().map(member -> modelMapper.map(member, MiembroComiteDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public MiembroComiteDTO addMiembro(MiembroComiteDTO memberDTO) {
		MiembroComite member = modelMapper.map(memberDTO, MiembroComite.class);
		MiembroComite savedMember = miembroRepository.save(member);
		return modelMapper.map(savedMember, MiembroComiteDTO.class);
	}

	@Override
	@Transactional
	public MiembroComiteDTO saveOrUpdateMiembro(MiembroComiteDTO memberDTO) {
	    MiembroComite miembro;
	    if (memberDTO.getIdmiembrocomite() != null && miembroRepository.existsById(memberDTO.getIdmiembrocomite())) {
	        // Actualizar miembro existente
	        miembro = miembroRepository.findById(memberDTO.getIdmiembrocomite()).orElseThrow(() ->
	            new RuntimeException("Miembro no encontrado con el ID especificado: " + memberDTO.getIdmiembrocomite()));
	        modelMapper.map(memberDTO, miembro);
	    } else {
	        // Crear nuevo miembro
	        miembro = modelMapper.map(memberDTO, MiembroComite.class);
	    }

	    MiembroComite savedMember = miembroRepository.save(miembro);
	    return modelMapper.map(savedMember, MiembroComiteDTO.class);
	}


	@Override
	@Transactional
	public void replaceMiembro(Integer idmiembrocomite, MiembroComiteDTO newMemberDTO) {
		MiembroComite oldMember = miembroRepository.findById(idmiembrocomite)
				.orElseThrow(() -> new RuntimeException("Miembro no encontrado con id: " + idmiembrocomite));

		oldMember.setNombrePersona(newMemberDTO.getNombrePersona());
		oldMember.setNumeroIdentificacion(newMemberDTO.getNumeroIdentificacion());
		oldMember.setLogin(newMemberDTO.getLogin());
		oldMember.setTextoActa(newMemberDTO.getTextoActa());
		oldMember.setIpCliente(newMemberDTO.getIpCliente());
		oldMember.setAutorizadoComiteVirtual(newMemberDTO.getAutorizadoComiteVirtual());
		oldMember.setIdTipoMiembroComite(newMemberDTO.getIdTipoMiembroComite());
		oldMember.setOrden(newMemberDTO.getOrden());
		oldMember.setDelegatura(newMemberDTO.getDelegatura());
		oldMember.setCargo(newMemberDTO.getCargo());
		oldMember.setIdusuario(newMemberDTO.getIdusuario());

		miembroRepository.save(oldMember);
	}

	@Override
	@Transactional
	public void inactivarMiembro(Integer idmiembrocomite) {
		MiembroComite member = miembroRepository.findById(idmiembrocomite)
				.orElseThrow(() -> new RuntimeException("Miembro no encontrado con id: " + idmiembrocomite));

		member.setActivo("0");
		miembroRepository.save(member);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MiembroComiteDTO> getMiembrosOrdenadosFiltrados(String searchText) {
		List<MiembroComite> miembros = miembroRepository.encontrarTodosOrdenadosYFiltrados(searchText);
		return miembros.stream().map(member -> modelMapper.map(member, MiembroComiteDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public MiembroComiteDTO getMiembroById(Integer idmiembrocomite) {
	    MiembroComite miembro = miembroRepository.findById(idmiembrocomite).orElse(null);
	    if (miembro != null) {
	        return modelMapper.map(miembro, MiembroComiteDTO.class);
	    } else {
	        return null;
	    }
	}

}