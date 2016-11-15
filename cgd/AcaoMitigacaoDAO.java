package ode.gerenciaRiscos.cgd;

import java.util.List;

import ode._infraestruturaBase.cgd.DAOBase;
import ode.gerenciaRiscos.cdp.AcaoMitigacao;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;

public interface AcaoMitigacaoDAO extends DAOBase<AcaoMitigacao> {

	/**
	 * Recupera acoes de mitigacao de uma avaliacao de risco.
	 * @param avaliacaoRisco Avaliacao de risco que se deseja recuperar as acoes de mitigacao.
	 * @return Lista de acoes de mitigacao recuperadas de uma avaliacao de risco.
	 */
	public List<AcaoMitigacao> recuperarAcoesDeMitigacaoPorAvaliacaoRisco(AvaliacaoRisco avaliacaoRisco);
}
