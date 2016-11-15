package ode.gerenciaRiscos.cgt;

import java.util.Collection;

import ode._infraestruturaBase.cgd.DAOBase;
import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaCRUD.cgt.AplCRUD;
import ode.controleProjeto.cdp.Projeto;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cgd.AcaoContingenciaDAO;
import ode.gerenciaRiscos.cgd.AcaoMitigacaoDAO;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = NucleoRegraNegocioExcecao.class)
public class AplCadastrarAvalicaoRisco extends AplCRUD<AvaliacaoRisco> {
	
	@Autowired
	AvaliacaoRiscoDAO avaliacaoRiscoDAO;
	
	@Autowired
	PerfilRiscoDAO perfilRiscoDAO;
	
	@Autowired
	AcaoMitigacaoDAO acaoMitigacaoDAO;
	
	@Autowired
	AcaoContingenciaDAO acaoContingenciaDAO;
	
	@Override
	public DAOBase<AvaliacaoRisco> getNucleoDaoBase() {
		return avaliacaoRiscoDAO;
	}
	
	
	/**
	 * Recupera avaliacoes de risco de um projeto.
	 * @param projeto que se deseja recuperar as avaliacoes de risco. 
	 * @return Colletion de avaliacoes de risco recuperado de um projeto.
	 */
	public Collection<AvaliacaoRisco> recuperarPorProjeto(Projeto projeto){
		return this.avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorProjeto(projeto);
	}
}
