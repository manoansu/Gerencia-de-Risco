package ode.gerenciaRiscos.cdp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AcaoContingencia extends Acao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AvaliacaoRisco avaliacaoRisco;

	@ManyToOne
	public AvaliacaoRisco getAvaliacaoRisco() {
		return avaliacaoRisco;
	}
	
	public void setAvaliacaoRisco(AvaliacaoRisco avaliacaoRisco) {
		this.avaliacaoRisco = avaliacaoRisco;
	}
}
