package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBaseImpl;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.Consequencia;

import org.springframework.stereotype.Repository;

@Repository
public class ConsequenciaImpl extends DAOBaseImpl<Consequencia> implements ConsequenciaDAO {

	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.ConsequenciaDAO#recuperarConsequenciaPorAvaliacaRisco(ode.gerenciaRiscos.cdp.AvaliacaoRisco)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Consequencia> recuperarConsequenciasPorAvaliacaRisco(AvaliacaoRisco avaliacaoRisco) {
		return getEntityManager()
				.createQuery("from Consequencia where avaliacaoRisco = :avaliacaoRisco")
				.setParameter("avaliacaoRisco", avaliacaoRisco)
				.getResultList();
	}
}
