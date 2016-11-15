package ode.gerenciaRiscos.cdp;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AcaoMitigacao extends Acao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal limiar;
	
	private AvaliacaoRisco avaliacaoRisco;

	public BigDecimal getLimiar() {
		return limiar;
	}

	public void setLimiar(BigDecimal limiar) {
		this.limiar = limiar;
	}

	@ManyToOne
	public AvaliacaoRisco getAvaliacaoRisco() {
		return avaliacaoRisco;
	}

	public void setAvaliacaoRisco(AvaliacaoRisco avaliacaoRisco) {
		this.avaliacaoRisco = avaliacaoRisco;
	}
}
