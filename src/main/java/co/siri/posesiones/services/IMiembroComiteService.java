package co.siri.posesiones.services;

import java.util.List;

import co.siri.posesiones.dtos.MiembroComiteDTO;

public interface IMiembroComiteService {
    List<MiembroComiteDTO> getAllMiembros();
    MiembroComiteDTO addMiembro(MiembroComiteDTO memberDTO);
    MiembroComiteDTO saveOrUpdateMiembro(MiembroComiteDTO memberDTO);
    void replaceMiembro(Integer idmiembrocomite, MiembroComiteDTO newMemberDTO);
    void inactivarMiembro(Integer idmiembrocomite);
    List<MiembroComiteDTO> getMiembrosOrdenadosFiltrados(String searchText);
    MiembroComiteDTO getMiembroById(Integer idmiembrocomite);
    List<MiembroComiteDTO> getMiembrosActivos();
}
