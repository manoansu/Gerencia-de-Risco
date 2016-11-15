package ode.gerenciaRiscos.cdp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ode._infraestruturaBase.cdp.ObjetoPersistente;

/**
 * @author 06062593760
 *
 */
@Entity
public class AvaliacaoRisco extends ObjetoPersistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String ESTRATEGIA_TRATAMENTO_ACEITAR = "Aceitar";
	public static String ESTRATEGIA_TRATAMENTO_MONITORAR = "Monitorar";
	public static String ESTRATEGIA_TRATAMENTO_REAGIR = "Reagir";
	public static String ESTRATEGIA_TRATAMENTO_TRANFERIR = "Tranferir";
	public static String ESTRATEGIA_TRATAMENTO_ELIMINAR = "Eliminar";
	public static String ESTRATEGIA_TRATAMENTO_MITIGAR = "Mitigar";
	public static String ESTRATEGIA_TRATAMENTO_MITIGAR_REAGIR = "Mitigar/Reagir";
	
	private Date data;
	
	private BigDecimal probabilidade; 
	
	private BigDecimal impacto; 
	
	private boolean ocorreu;
	
	private boolean ehPrioritario;
	
	private String estrategiaTratamento;
	
	private BigDecimal limiar;
	
	private String justificativaEstrategiaTratamento;
	
	private Set<Consequencia> consequencias = new HashSet<Consequencia>();
	
	private Set<AcaoMitigacao> acoesMitigacao = new HashSet<AcaoMitigacao>();
	
	private Set<AcaoContingencia> acoesContingencia = new HashSet<AcaoContingencia>();
	
	private PerfilRisco perfilRisco;
	
	public Date getData() {
		return data;
	}

	
	public void setData(Date date) {
		this.data = (Date) date;
	}

	
	public BigDecimal getProbabilidade() {
		return probabilidade;
	}

	
	public void setProbabilidade(BigDecimal probabilidade) {
		this.probabilidade = probabilidade;
	}

	
	public BigDecimal getImpacto() {
		return impacto;
	}

	
	public void setImpacto(BigDecimal impacto) {
		this.impacto = impacto;
	}

	
	public boolean isOcorreu() {
		return ocorreu;
	}

	
	public void setOcorreu(boolean ocorreu) {
		this.ocorreu = ocorreu;
	}

	
	public boolean isEhPrioritario() {
		return ehPrioritario;
	}

	
	public BigDecimal getLimiar() {
		return limiar;
	}

	
	public void setLimiar(BigDecimal limiar) {
		this.limiar = limiar;
	}

	
	public void setEhPrioritario(boolean ehPrioritario) {
		this.ehPrioritario = ehPrioritario;
	}

	
	public String getEstrategiaTratamento() {
		return estrategiaTratamento;
	}

	
	public void setEstrategiaTratamento(String estrategiaTratamento) {
		this.estrategiaTratamento = estrategiaTratamento;
	}

	
	public String getJustificativaEstrategiaTratamento() {
		return justificativaEstrategiaTratamento;
	}

	
	public void setJustificativaEstrategiaTratamento(
			String justificativaEstrategiaTratamento) {
		this.justificativaEstrategiaTratamento = justificativaEstrategiaTratamento;
	}
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "avaliacaorisco_id",referencedColumnName = "id")
	public Set<Consequencia> getConsequencias() {
		return consequencias;
	}

	
	public void setConsequencias(Set<Consequencia> consequencias) {
		this.consequencias = consequencias;
	}
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "avaliacaorisco_id",referencedColumnName = "id", insertable=true, updatable=true)
	public Set<AcaoMitigacao> getAcoesMitigacao() {
		return acoesMitigacao;
	}

	
	public void setAcoesMitigacao(Set<AcaoMitigacao> acoesMitigacao) {
		this.acoesMitigacao = acoesMitigacao;
	}

	
	@OneToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "avaliacaorisco_id",referencedColumnName = "id",insertable=true, updatable=true)
	public Set<AcaoContingencia> getAcoesContingencia() {
		return acoesContingencia;
	}

	
	public void setAcoesContingencia(Set<AcaoContingencia> acoesContingencia) {
		this.acoesContingencia = acoesContingencia;
	}


	public static List<String> recuperarEstrategiasTratamento(){
		
		// Define uma lista de String vazia..
		List<String> lista = new ArrayList<String>();
		
		//Insere toda as Estrategias de Tratamento dentro da Lista...
		lista.add(ESTRATEGIA_TRATAMENTO_ACEITAR);
		lista.add(ESTRATEGIA_TRATAMENTO_MONITORAR);
		lista.add(ESTRATEGIA_TRATAMENTO_REAGIR);
		lista.add(ESTRATEGIA_TRATAMENTO_TRANFERIR);
		lista.add(ESTRATEGIA_TRATAMENTO_ELIMINAR);
		lista.add(ESTRATEGIA_TRATAMENTO_MITIGAR);
		lista.add(ESTRATEGIA_TRATAMENTO_MITIGAR_REAGIR);
		
		return lista;
	}

	
	@ManyToOne
	public PerfilRisco getPerfilRisco() {
		return perfilRisco;
	}

	
	public void setPerfilRisco(PerfilRisco perfilRisco) {
		this.perfilRisco = perfilRisco;
	}
}
