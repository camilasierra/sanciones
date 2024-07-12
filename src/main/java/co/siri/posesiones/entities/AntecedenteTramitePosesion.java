package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ANTECEDENTETRAMITEPOSESION")
@Data
public class AntecedenteTramitePosesion {
	
	
	@Id
    @SequenceGenerator(name = "ANTECEDENTETRAMITEPOSESION_SEQ", sequenceName = "ANTECEDENTETRAMITEPOSESION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANTECEDENTETRAMITEPOSESION_SEQ")
    @Column(name = "IDANTECEDENTETRAMITEPOSESION")
    private Long idAntecedenteTramitePosesion;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTIPOCONVENIOANTECEDENTE")
    private TipoConvenioAntecedente idTipoConvenioAntecedente;
    
    
    
    @Column(name = "REPORTADO")
    private char reportado;
    @Column(name = "COMENTARIO")
    private String comentario;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDARCHIVOTRAMITEPOSESION")
    private ArchivoTramitePosesion idArchivoTramitePosesion;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRAMITEPOSESION")
    private TramitePosesion idTramitePosesion;
    
    @Column(name = "IPCLIENTE")
    private String ipCliente;
    @Column(name = "IDUSUARIO")
    private Long idUsuario;
    
    
}
