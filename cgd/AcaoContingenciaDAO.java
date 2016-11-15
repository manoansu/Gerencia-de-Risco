package ode.gerenciaRiscos.cgd;

import java.util.List;

import ode._infraestruturaBase.cgd.DAOBase;
import ode.gerenciaRiscos.cdp.AcaoContingencia;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;

public interface AcaoContingenciaDAO extends DAOBase<AcaoContingencia> {
	
	
	/**
	 * Recupera acoes de contingencia de uma avaliacao de risco.
	 * @param avaliacaoRisco Avaliacao de risco que se deseja recuperar as acoes de contingencia.
	 * @return Lista de acoes de contingencia recuperadas de uma avaliacao de risco.
	 */
	public List<AcaoContingencia> recuperarAcoesDeContingenciaPorAvaliacaoRisco(AvaliacaoRisco avaliacaoRisco);
	

}
