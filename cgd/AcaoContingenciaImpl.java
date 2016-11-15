package ode.gerenciaRiscos.cgd;

import java.util.List;

import ode._infraestruturaBase.cgd.DAOBaseImpl;
import ode.gerenciaRiscos.cdp.AcaoContingencia;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;

import org.springframework.stereotype.Repository;

@Repository
public class AcaoContingenciaImpl extends DAOBaseImpl<AcaoContingencia> implements AcaoContingenciaDAO {
	
	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.AcaoContingenciaDAO#recuperarAcoesContingenciaPorAvaliacaoRisco(ode.gerenciaRiscos.cdp.AvaliacaoRisco)
	 */
	@SuppressWarnings("unchecked")
	public List<AcaoContingencia> recuperarAcoesDeContingenciaPorAvaliacaoRisco(AvaliacaoRisco avaliacaoRisco) {
		return getEntityManager()
				.createQuery("from AcaoContingencia where avaliacaoRisco = :avaliacaoRisco")
				.setParameter("avaliacaoRisco", avaliacaoRisco)
				.getResultList();
	}
}
