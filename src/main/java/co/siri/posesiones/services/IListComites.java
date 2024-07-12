package co.siri.posesiones.services;

import co.siri.posesiones.dtos.ListaComitesDTO;

import java.util.List;

public interface IListComites {
    List<ListaComitesDTO> listaComites();
    List<ListaComitesDTO> comites();
}
