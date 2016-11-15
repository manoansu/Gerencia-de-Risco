package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBaseImpl;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;

import org.springframework.stereotype.Repository;

@Repository
public class PerfilRiscoDAOImpl extends DAOBaseImpl<PerfilRisco> implements PerfilRiscoDAO {
	
	/*
	 * (non-Javadoc)
	 * @see ode.gerenciaRiscos.cgd.PerfilRiscoDAO#recuperarPorPlanoRisco(ode.gerenciaRiscos.cdp.PlanoRisco)
	 */
	@SuppressWarnings("unchecked")
	public Collection<PerfilRisco> recuperarPerfisRiscoPorPlanoRisco(PlanoRisco planoRisco){
		return getEntityManager()
				.createQuery("from PerfilRisco where planoRisco = :planoRisco")
				.setParameter("planoRisco", planoRisco)
				.getResultList();
	}
}
