package ode.gerenciaRiscos.cgd;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBase;
import ode.controleProjeto.cdp.Projeto;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;

public interface AvaliacaoRiscoDAO extends DAOBase<AvaliacaoRisco> {
	
	/**
	 * Recupera avaliacoes de risco de um projeto.
	 * @param projeto Projeto que se deseja recuperar as avaliacoes de risco.
	 * @return Colletion de avaliacoes de risco recuperadas de um projeto.
	 */
	public Collection<AvaliacaoRisco> recuperarAvaliacoesRiscoPorProjeto (Projeto projeto);
	
	/**
	 * Recupera avaliacoes de risco de um perfil risco.
	 * @param perfilRisco PerfilRisco que se deseja recuperar as avaliacoes de risco.
	 * @return Avaliacao de risco recuperada de um perfil risco.
	 */
	public AvaliacaoRisco recuperarAvaliacoesRiscoPorPerfilRisco(PerfilRisco perfilRisco);

}
