package ode.gerenciaRiscos.cdp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ode._infraestruturaBase.cdp.ObjetoPersistente;
import ode.controleProjeto.cdp.Projeto;

@Entity
public class PlanoRisco extends ObjetoPersistente  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String versao;
	
	private Date data;
	
	private Date dtFinalizacao;
	
	public Set<PerfilRisco> perfisRisco = new HashSet<PerfilRisco>();
	
	public Projeto projeto;
	
	public PlanoRisco planoRisco;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDtFinalizacao() {
		return dtFinalizacao;
	}

	public void setDtFinalizacao(Date dtFinalizacao) {
		this.dtFinalizacao = dtFinalizacao;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "planorisco_id",referencedColumnName = "id")
	public Set<PerfilRisco> getPerfisRisco() {
		return perfisRisco;
	}

	public void setPerfisRisco(Set<PerfilRisco> perfisRisco) {
		this.perfisRisco = perfisRisco;
	}

	@ManyToOne
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
