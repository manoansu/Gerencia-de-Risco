package ode.gerenciaRiscos.cgd;

import java.util.List;

import ode._infraestruturaBase.cgd.DAOBaseImpl;
import ode.gerenciaRiscos.cdp.AcaoMitigacao;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;

import org.springframework.stereotype.Repository;

@Repository
public class AcaoMitigacaoImpl extends DAOBaseImpl<AcaoMitigacao> implements AcaoMitigacaoDAO {

	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.AcaoMitigacaoDAO#recuperarAcaoMitigacaoPorAvaliacaoRisco(ode.gerenciaRiscos.cdp.AvaliacaoRisco)
	 */
	@SuppressWarnings("unchecked")
	public List<AcaoMitigacao> recuperarAcoesDeMitigacaoPorAvaliacaoRisco(AvaliacaoRisco avaliacaoRisco) {
		return getEntityManager()
				.createQuery("from AcaoMitigacao where avaliacaoRisco = :avaliacaoRisco")
				.setParameter("avaliacaoRisco", avaliacaoRisco)
				.getResultList();
	}
}
