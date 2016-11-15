package ode.gerenciaRiscos.cgt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import ode._infraestruturaBase.cgd.DAOBase;
import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaBase.util.NucleoContexto;
import ode._infraestruturaCRUD.cgt.AplCRUD;
import ode.conhecimento.risco.cdp.KRisco;
import ode.conhecimento.risco.ciu.ListagemKRisco;
import ode.controleProjeto.cdp.Projeto;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;
import ode.gerenciaRiscos.cgd.PlanoRiscoDAO;
import ode.gerenciaRiscos.ciu.CtrlPlanoRisco;
import ode.gerenciaRiscos.ciu.JanelaPlanoRisco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zul.Messagebox;

@Service
@Transactional(rollbackFor = NucleoRegraNegocioExcecao.class)
public class AplCadastrarPlanoRisco extends AplCRUD<PlanoRisco> {
	
	@Autowired
	PlanoRiscoDAO planoRiscoDAO;
	
	@Autowired
	PerfilRiscoDAO perfilRiscoDAO;
	
	@Autowired
	AvaliacaoRiscoDAO avaliacaoRiscoDAO;
	
	@Autowired
	AplCadastrarPerfilRisco aplCadastrarPerfilRisco;

	@Override
	public DAOBase<PlanoRisco> getNucleoDaoBase() {
		return planoRiscoDAO;
	}
	
	/**
	 * Recupera planos de risco de um projeto.
	 * @param projeto Projeto que se deseja recuperar os planos de risco.
	 * @return Colletion de Planos de risco recuperadas de um projeto.
	 */
	public Collection<PlanoRisco> recuperarPorProjeto(Projeto projeto){
		return this.planoRiscoDAO.recuperarPlanosRiscoPorProjeto(projeto);
	}
	
	
	/**
	 * salva o perfil Risco no banco..
	 * @param listagemKRisco
	 * @param planoRisco
	 * @param perfilRisco
	 * @param size
	 * @param kRiscosSelecionados
	 */
	public void salvar(ListagemKRisco listagemKRisco, PlanoRisco planoRisco, PerfilRisco perfilRisco,int size,List<KRisco> kRiscosSelecionados ) {
		
		// Verifica se ja existe perfil risco com krisco no banco.
		boolean existePerfilRiscoNoBanco = false;
		
		//Recupera planos de risco salvo no banco.
		Collection<PerfilRisco> listaPerfilRiscoBanco = recuperarPerfilRiscoPorPlanoRisco(planoRisco);
		
		// Percorre a lista e verifica se o perfilRsco existe no banco.
		for(PerfilRisco perfilRiscoBanco : listaPerfilRiscoBanco) {
			if((perfilRiscoBanco.getKRisco().getId().equals(perfilRisco.getKRisco().getId()))) {
				existePerfilRiscoNoBanco = true;
			}	
		}
		
		// se nao existe  perfilRisco salvo no banco, salva o perfilRisco vindo da tela.
		if (!existePerfilRiscoNoBanco) {
			perfilRiscoDAO.salvar(perfilRisco);
			}
		
		if(listaPerfilRiscoBanco.size() > size) {
			for(PerfilRisco perfilRisco2: listaPerfilRiscoBanco) {
				
				// Verifica se o krisco nao foi selecionado no banco. Se nao exclui o PerfilRisco. 
				if(!kRiscosSelecionados.contains(perfilRisco2.getKRisco())) 
					perfilRiscoDAO.excluir(perfilRisco2);
			}
		}
	}
	
	/**
	 * Exclui perfis risco identificados.
	 * @param perfilRisco Perfilrisco que se deseja excluir. 
	 */
	public void excluirPerfilRiscos(PerfilRisco perfilRisco) {
			perfilRiscoDAO.excluir(perfilRisco);
	}

	
	/**
	 * Recupera perfis risco de um de plano risco.
	 * @param planoRisco Plano de risco que se deseja recuperar os perfis risco.
	 * @return Colletion perfis riscos recuperadas de um plano de risco.
	 */
	public Collection<PerfilRisco> recuperarPerfilRiscoPorPlanoRisco(PlanoRisco planoRisco){
		return perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco);
	}
	
	/**
	 * Executa as acoes necessarias antes da gravacao (inclusao ou alteracao de
	 * dados) de um objeto. As aplicacoes que herdam desta classe devem
	 * sobrescrever este metodo para obter um comportamento especifico antes da
	 * gravacao.
	 * 
	 * @param objeto
	 *            Objeto sendo gravado.
	 * @throws NucleoDAOExcecao
	 *             Caso ocorra algum problema no acesso ao banco de dados.
	 * @throws NucleoRegraNegocioExcecao
	 *             Caso haja algum erro de regra de negocio.
	 */
	protected void antesSalvar(PlanoRisco objeto) throws NucleoRegraNegocioExcecao {
		
		///////////////////////////////////
		// Verifica a duplicidade de nomes
		// das versoes dos planos de riscos
		// do mesmo projeto.
		///////////////////////////////////
		
		// Recupero os planos do projeto.
		Collection<PlanoRisco> planosRiscoProjeto = planoRiscoDAO.recuperarPlanosRiscoPorProjeto(objeto.getProjeto());
		
		// Remove o objeto caso ja exista no banco para nao ser comparado .
		planosRiscoProjeto.remove(objeto);
		
		// Verifica se a versao digitada ja existe no banco.
		for (PlanoRisco planoRiscoBanco : planosRiscoProjeto) {
			
			if (planoRiscoBanco.getVersao().compareToIgnoreCase(objeto.getVersao()) == 0) {
				throw new NucleoRegraNegocioExcecao("Já existe um plano de risco com essa versão.");
			}
		}
	}
	
	/**
	 * Copia plano risco.
	 * @param planoRiscoSelecionado que se deseja copiar.
	 * @param nomeNovaVersao que se deseja criar.
	 * @return Plano de risco copiada.
	 * @throws NucleoRegraNegocioExcecao.
	 */
	public PlanoRisco copiarPlanoRisco(PlanoRisco planoRiscoSelecionado, String nomeNovaVersao) throws NucleoRegraNegocioExcecao {

		////////////////////////////////////////////
		// PRIMEIRA PARTE: Salvar o plano de riscos
		////////////////////////////////////////////
		
		// Cria novo plano de riscos para ser salvo.
		PlanoRisco planoRiscoNovo = new PlanoRisco();
		planoRiscoNovo.setProjeto(planoRiscoSelecionado.getProjeto());
		planoRiscoNovo.setData(new Date());
		planoRiscoNovo.setVersao(nomeNovaVersao);
		
		// Recupero planos de risco de um projeto.
		Collection<PlanoRisco> planosRiscoProjeto = planoRiscoDAO.recuperarPlanosRiscoPorProjeto(planoRiscoNovo.getProjeto());
		
		// Remove o objeto caso ja exista no banco para nao ser comparado. 
		planosRiscoProjeto.remove(planoRiscoNovo);
		
		// Verifica se a versao digitada ja existe no banco.
		for (PlanoRisco planoRiscoBanco : planosRiscoProjeto) {
			
			if (planoRiscoBanco.getVersao().compareToIgnoreCase(nomeNovaVersao) == 0) {
				try {
					Messagebox.show("Já existe um plano de risco com essa versão.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		// Salva o novo plano de risco.
		planoRiscoNovo = this.salvar(planoRiscoNovo);

		///////////////////////////////////////////////////////////
		// SEGUNDA PARTE: Salvar os perfis riscos individualmente.
		///////////////////////////////////////////////////////////

		// Recupera perfis risco de um plano de risco selecionado. 
		Collection<PerfilRisco> perfisRiscoBanco = perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRiscoSelecionado);
	
		for (PerfilRisco perfilRiscoBanco : perfisRiscoBanco) {
			
			// Cria novo perfil risco pra copiar as informacao dos perfis risco existentes no banco de dados.
			PerfilRisco perfilRiscoNovo = new PerfilRisco();
			perfilRiscoNovo.setProjeto(perfilRiscoBanco.getProjeto());
			perfilRiscoNovo.setKRisco(perfilRiscoBanco.getKRisco());
			perfilRiscoNovo.setPlanoRisco(planoRiscoNovo);
			
			// salva o perfil risco novo.
			perfilRiscoNovo = aplCadastrarPerfilRisco.salvar(perfilRiscoNovo);
					
			///////////////////////////////////////////////////////////
			// TERCEIRA PARTE: Salvar as avaliacoes individulamente. 
			///////////////////////////////////////////////////////////
			
			// Recupera avaliacao risco do banco de dados a patir do um perfil risco salvo no banco de dados.
			AvaliacaoRisco avaliacaoRiscoBanco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRiscoBanco);
			if(avaliacaoRiscoBanco != null) {
								
				// Cria uma nova avaliacao risco, e copia.
				AvaliacaoRisco avaliacaoRiscoNovo = new AvaliacaoRisco();
				avaliacaoRiscoNovo.setEstrategiaTratamento(avaliacaoRiscoBanco.getEstrategiaTratamento());
				avaliacaoRiscoNovo.setProbabilidade(avaliacaoRiscoBanco.getProbabilidade());
				avaliacaoRiscoNovo.setImpacto(avaliacaoRiscoBanco.getImpacto());
				avaliacaoRiscoNovo.setData(avaliacaoRiscoBanco.getData());
				avaliacaoRiscoNovo.setOcorreu(avaliacaoRiscoBanco.isOcorreu());
				avaliacaoRiscoNovo.setJustificativaEstrategiaTratamento(avaliacaoRiscoBanco.getJustificativaEstrategiaTratamento());
				avaliacaoRiscoNovo.setPerfilRisco(perfilRiscoNovo);

				// Salva a avaliacao risco.
				avaliacaoRiscoDAO.salvar(avaliacaoRiscoNovo);
			}
		}
		
		return planoRiscoNovo;
	}
	
	
	/**
	 * Cria novo plano de risco.
	 * 
	 * @param nomeNovaVersao que se deseja criar.
	 * @rturn Plano risco criado.
	 */
	public PlanoRisco criarNovolanoRisco(String nomeNovaVersao) {

		// Cria novo plano de risco.
		PlanoRisco novoplPlanoRisco = new PlanoRisco();

		novoplPlanoRisco.setProjeto(NucleoContexto.recuperarProjeto());
		novoplPlanoRisco.setData(new Date());
		novoplPlanoRisco.setVersao(nomeNovaVersao);
		
		// Recupera planos de risco de um projeto.
		Collection<PlanoRisco> planosRiscoProjeto = planoRiscoDAO.recuperarPlanosRiscoPorProjeto(novoplPlanoRisco.getProjeto());
		
		// Remove o objeto caso ja exista no banco de dados para nao ser comparado. 
		planosRiscoProjeto.remove(novoplPlanoRisco);
		
		// Verifica se a versao digitada ja existe no banco de dados.
		for (PlanoRisco planoRiscoBanco : planosRiscoProjeto) {
			
			if (planoRiscoBanco.getVersao().compareToIgnoreCase(nomeNovaVersao) == 0) {
				try {
					Messagebox.show("Já existe um plano de risco com essa versão.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}

		// Salva o novo plano de risco.
		try {
			this.salvar(novoplPlanoRisco);
		} catch (NucleoRegraNegocioExcecao e) {
			e.printStackTrace();
		} 
		
		return novoplPlanoRisco;
	}	
	
	
	/**
	 * Edita plano de risco.
	 * @param ctrlPlanoRisco
	 * @param planoRisco
	 */
	public void editarPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco, PlanoRisco planoRisco)  {
		
		JanelaPlanoRisco janelaPlanoRisco = new JanelaPlanoRisco(
				ctrlPlanoRisco, planoRisco);
		janelaPlanoRisco.mostrar();
	}
}
