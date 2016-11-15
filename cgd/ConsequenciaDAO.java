package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBase;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.Consequencia;

public interface ConsequenciaDAO extends DAOBase<Consequencia> {

	/**
	 * Recupera consequencias de uma avaliacao de risco.
	 * @param avaliacaoRisco Avaliacao de risco que se deseja recuperar as consequencias.
	 * @return Colletion de consequencias recuperadas de uma avaliacao de risco.
	 */
	Collection<Consequencia> recuperarConsequenciasPorAvaliacaRisco(AvaliacaoRisco avaliacaoRisco);//??????????????

}
