package ode.gerenciaRiscos.cdp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ode._infraestruturaBase.cdp.ObjetoPersistente;
import ode.conhecimento.risco.cdp.KRisco;
import ode.controleProjeto.cdp.Projeto;

@Entity
public class PerfilRisco extends ObjetoPersistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Projeto projeto;
	
	private KRisco kRisco;
	
	private PlanoRisco planoRisco;
	
	
	@ManyToOne
	public Projeto getProjeto() {
		return projeto;
	}

	
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	@ManyToOne
	public KRisco getKRisco() { 
		return kRisco;
	}
	
	public void setKRisco(KRisco kRisco) {
		this.kRisco = kRisco;
	}
	
	@ManyToOne
	public PlanoRisco getPlanoRisco() {
		return planoRisco;
	}
	
	public void setPlanoRisco(PlanoRisco planoRisco) {
		this.planoRisco = planoRisco;
	}
}
