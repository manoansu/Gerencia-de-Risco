package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBase;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;

public interface PerfilRiscoDAO extends DAOBase<PerfilRisco> {
	
	/**
	 * Recupera perfis risco de um plano de risco.
	 * @param planoRisco Plano de risco que se deseja recuperar os perfis risco.
	 * @return Colletion de perfis riscos recuperadas de um plano de risco.
	 */
	public Collection<PerfilRisco> recuperarPerfisRiscoPorPlanoRisco(PlanoRisco planoRisco);

}
