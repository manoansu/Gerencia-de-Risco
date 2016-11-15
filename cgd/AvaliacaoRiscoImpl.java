package ode.gerenciaRiscos.cgd;

import java.util.Collection;
import java.util.List;

import ode._infraestruturaBase.cgd.DAOBaseImpl;
import ode.controleProjeto.cdp.Projeto;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;

import org.springframework.stereotype.Repository;

@Repository
public class AvaliacaoRiscoImpl extends DAOBaseImpl<AvaliacaoRisco> implements AvaliacaoRiscoDAO {

	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO#recuperarAvaliacaoRiscoPorProjeto(ode.controleProjeto.cdp.Projeto)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoRisco> recuperarAvaliacoesRiscoPorProjeto(Projeto projeto) {//??????????????
		return getEntityManager()
				.createQuery("from AvaliacaoRisco where projeto = :projeto")
				.setParameter("projeto", projeto)
				.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO#recuperarAvaliacaoRiscoPorPerfilRisco(ode.gerenciaRiscos.cdp.PerfilRisco)
	 */
	@SuppressWarnings("unchecked")
	public AvaliacaoRisco recuperarAvaliacoesRiscoPorPerfilRisco(PerfilRisco perfilRisco) {//??????????????
		List<AvaliacaoRisco> lista = getEntityManager()
				.createQuery("from AvaliacaoRisco where perfilRisco = :perfilRisco")
				.setParameter("perfilRisco", perfilRisco)
				.getResultList();
		
		if (lista.size() > 0)
			return (AvaliacaoRisco) lista.get(0);
		
		 return null;
	}
}
