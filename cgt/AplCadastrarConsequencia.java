package ode.gerenciaRiscos.cgt;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBase;
import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaCRUD.cgt.AplCRUD;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.Consequencia;
import ode.gerenciaRiscos.cgd.ConsequenciaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = NucleoRegraNegocioExcecao.class)
public class AplCadastrarConsequencia extends AplCRUD<Consequencia>{
	
	@Autowired
	ConsequenciaDAO consequenciaDAO;
	
	@Override
	public DAOBase<Consequencia> getNucleoDaoBase() {
		return consequenciaDAO;
	}
	

	/**
	 * Recupera consequencias de uma avaliacao de risco.
	 * @param avaliacaRisco Avaliacao Risco que se deseja recuperar as consequencias.
	 * @return Colletion de consequencias recuperadas de uma avaliacao de risco.
	 */
	public Collection<Consequencia> recuperarPorProjeto(AvaliacaoRisco avaliacaoRisco){
		return this.consequenciaDAO.recuperarConsequenciasPorAvaliacaRisco(avaliacaoRisco);
	}
	
	/**
	 * Salva consequencia.
	 * @param consequencia Consequencia que se deseja salvar.
	 */
	public void salvarConsequencia(Consequencia consequencia) {
		
		if(consequencia != null) 
			consequenciaDAO.salvar(consequencia);		
	}
	
	
	/**
	 * Exclui consequencia.
	 * @param consequencia Consequencia que se deseja excluir.
	 */
	public void excluirConsequencia(Consequencia consequencia) {
		
		if(consequencia.getId() != null)
			consequenciaDAO.excluir(consequencia);
	}
}
