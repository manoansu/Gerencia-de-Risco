package ode.gerenciaRiscos.cdp;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import ode._controleRecursoHumano.cdp.RecursoHumano;
import ode._infraestruturaBase.cdp.ObjetoPersistente;
import ode.conhecimento.risco.cdp.KAcaoRisco;

@MappedSuperclass
public class Acao extends ObjetoPersistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String ACAO_PLANEJADA_PLANEJADA = "Planejada";
	public static String ACAO_PLANEJADA_EM_EXECUCAO = "Em Execução";
	public static String ACAO_PLANEJADA_FINALIZADA = "Finalizada";
	
	private String estado;
	
	private RecursoHumano recursoHumanoResposavel;
	
	private KAcaoRisco kAcaoRisco;
	
	@ManyToOne
	public RecursoHumano getRecursoHumanoResponsavel() {
		return recursoHumanoResposavel;
	}

	public void setRecursoHumanoResponsavel(RecursoHumano recursoHumano) {
		this.recursoHumanoResposavel = recursoHumano;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@ManyToOne
	public KAcaoRisco getKacaoRisco() {
		return kAcaoRisco;
	}

	public void setKacaoRisco(KAcaoRisco kAcaoRisco) {
		this.kAcaoRisco = kAcaoRisco;
	}	
}
