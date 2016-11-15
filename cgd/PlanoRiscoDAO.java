package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBase;
import ode.controleProjeto.cdp.Projeto;
import ode.gerenciaRiscos.cdp.PlanoRisco;

public interface PlanoRiscoDAO extends DAOBase<PlanoRisco> {
	
	/**
	 * Recupera planos de risco de um projeto.
	 * @param projeto Projeto que se deseja recuperar os planos de risco.
	 * @return Colletion de planos de risco recuperadas de um projeto.
	 */
	public Collection<PlanoRisco> recuperarPlanosRiscoPorProjeto(Projeto projeto);
	
}
