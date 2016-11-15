package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBaseImpl;
import ode.controleProjeto.cdp.Projeto;
import ode.gerenciaRiscos.cdp.PlanoRisco;

import org.springframework.stereotype.Repository;

@Repository
public class PlanoRiscoImpl extends DAOBaseImpl<PlanoRisco> implements PlanoRiscoDAO {

	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.PlanoRiscoDAO#recuperarPorProjeto(ode.controleProjeto.cdp.Projeto)
	 */
	@SuppressWarnings("unchecked")
	public Collection<PlanoRisco> recuperarPlanosRiscoPorProjeto(Projeto projeto){
		return getEntityManager()
				.createQuery("from PlanoRisco where projeto = :projeto")
				.setParameter("projeto", projeto)
				.getResultList();
	}
}
