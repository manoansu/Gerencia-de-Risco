package ode.gerenciaRiscos.ciu;

import java.util.Collection;

import ode._infraestruturaBase.ciu.CtrlBase;
import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaBase.util.NucleoContexto;
import ode._infraestruturaCRUD.cgt.AplCRUD;
import ode._infraestruturaCRUD.ciu.JanelaSimples;
import ode._infraestruturaCRUD.ciu.PainelCRUD;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.Consequencia;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarAvalicaoRisco;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.api.Listbox;

@Controller(CtrlPlanoRisco.Nome)
public class CtrlPlanoRisco extends CtrlBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String Nome = "CtrlPlanoRisco";
	
	private JanelaCadastrarPlanoRisco janelaCadastrarPlanoRisco;
	
	private FormAvaliacaoRisco formAvaliacaoRisco;
	
	private FormConsequencia formConsequencia;
	
	private FormSelecionarEstrategiaDeTratamentoDeRiscos formSelecionarEstrategiaDeTratamentoDeRiscos;
	
	private FormPlanejarAcoesDeTratamentoDeRisco formPlanejarAcoesDeTratamentoDeRisco;
	
	protected JanelaSimples janDadosAvalicaoRisco;
	
	protected JanelaSimples janDadosConsequencia;
	
	protected JanelaSimples janDadosEstrategiaDeTratamento;
	
	protected JanelaSimples janDadosPlanejarAcoesDeTratamentoDeRisco;
	
	protected JanelaSimples janDadosPlanoDeRiscosDeProjetos;
	
	
	@Autowired
	private AplCadastrarPlanoRisco aplCadastrarPlanoRisco;
	

	@Override
	public void iniciar() {
		exibirJanelaPrincipal();
	}

	/**
	 * Exibe janela principal.
	 */
	public void exibirJanelaPrincipal() {
		janelaCadastrarPlanoRisco = new JanelaCadastrarPlanoRisco(this);
		janelaCadastrarPlanoRisco.mostrar();
		atualizar();
	}
	
	/**
	 * Verifica plano de risco.
	 * @param listbox
	 * @throws InterruptedException
	 */
	public void verificarPlanoDeRisco(Listbox listbox) throws InterruptedException {
		
		Collection<PlanoRisco> planosRisco = aplCadastrarPlanoRisco.recuperarPorProjeto(NucleoContexto.recuperarProjeto());
		for(PlanoRisco planoRisco : planosRisco){
			if(planoRisco.getDtFinalizacao() == null) {
				try {
					Messagebox.show("Não pode criar uma nova versão de plano risco, " +
							"já existe um plano de risco em aberto no banco de dados que precisa ser finalizado.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		janelaCadastrarPlanoRisco.exibirJanelaDeVersoes((org.zkoss.zul.Listbox) listbox);
	}
	
	/**
	 * Atualiza os dados alterados no banco de dados.
	 */
	public void atualizar() {
		Collection<PlanoRisco> objetos = aplCadastrarPlanoRisco.recuperarPorProjeto(NucleoContexto.recuperarProjeto());		
		janelaCadastrarPlanoRisco.adicionarPlanosRisco(objetos);
	}

	public AplCRUD<PlanoRisco> definirAplCRUD() {
		return aplCadastrarPlanoRisco;
	}

	
	public PainelCRUD<PlanoRisco> definirPainelCRUD() {
		return new PainelCRUDPlanoRisco();
	}
	
	/**
	 *  Exibe formulario da janela de uma avaliacao de risco.
	 * @param perfilRisco.
	 * @param listagemPerfilRisco.
	 */
	public void exibirFormularioAvaliacaoRisco(PerfilRisco perfilRisco, ListagemPerfilRisco listagemPerfilRisco) {

		janDadosAvalicaoRisco = factoryJanelaSimples();
		janDadosAvalicaoRisco.setTitle("Avaliar Risco");
		
		Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(janDadosAvalicaoRisco);
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
		
		if (avaliacaoRisco == null) {
			avaliacaoRisco = new AvaliacaoRisco();
			avaliacaoRisco.setPerfilRisco(perfilRisco);
		}
		formAvaliacaoRisco = new FormAvaliacaoRisco(perfilRisco, avaliacaoRisco,listagemPerfilRisco,this);
		formAvaliacaoRisco.setParent(janDadosAvalicaoRisco);
		janDadosAvalicaoRisco.mostrar();
	}
		
	/**
	 * Exibe formulario da janela consequencia.
	 * @param consequencia.
	 * @param modo.
	 */
	public void exibirFormularioConsequencia(Consequencia consequencia, String modo) {
		
		janDadosConsequencia = factoryJanelaSimples();
		janDadosConsequencia.setTitle("Consequência");
		
		Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(janDadosConsequencia);
		
		formConsequencia = new FormConsequencia(formAvaliacaoRisco,consequencia,modo);
		formConsequencia.setCtrlPlanoRisco(this);
		formConsequencia.setParent(janDadosConsequencia);
		janDadosConsequencia.mostrar();
	}
	
	/**
	 * Exibe formulario da janela selecionar estrategia de tratamento de risco.
	 * @param perfilRisco.
	 * @param listagemEstategiaDeTaratamentoDeRiscos.
	 */
	public void exibirFormularioSelecionarEstrategiaDeTratamentoDeRiscos(PlanoRisco planoRisco,PerfilRisco perfilRisco
			, ListagemSelecionarEstategiaDeTaratamentoDeRiscos listagemEstategiaDeTaratamentoDeRiscos) {
		

		janDadosEstrategiaDeTratamento = factoryJanelaSimples();
		janDadosEstrategiaDeTratamento.setTitle("Selecionar Estratégia de Tratamento de Riscos");
		
		Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(janDadosEstrategiaDeTratamento);
		
		formSelecionarEstrategiaDeTratamentoDeRiscos = new FormSelecionarEstrategiaDeTratamentoDeRiscos(planoRisco,perfilRisco
				,listagemEstategiaDeTaratamentoDeRiscos);
		formSelecionarEstrategiaDeTratamentoDeRiscos.setCtrlPlanoRisco(this);
		formSelecionarEstrategiaDeTratamentoDeRiscos.setParent(janDadosEstrategiaDeTratamento);
		janDadosEstrategiaDeTratamento.mostrar();
	}
	
	/**
	 * Exibe formulario da janelea planejar acoes de tratamento de risco.
	 * @param perfilRisco.
	 * @param planoRisco.
	 */
	public void exibirFormularioPlanejarAcoesDeTratamentoDeRiscos(PerfilRisco perfilRisco, PlanoRisco planoRisco) {
		

		janDadosPlanejarAcoesDeTratamentoDeRisco= factoryJanelaSimples();
		janDadosPlanejarAcoesDeTratamentoDeRisco.setTitle("Planejar Ações de Tratamento de Risco");
		
		Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(janDadosPlanejarAcoesDeTratamentoDeRisco);
				
		formPlanejarAcoesDeTratamentoDeRisco = new FormPlanejarAcoesDeTratamentoDeRisco(perfilRisco,planoRisco);
		formPlanejarAcoesDeTratamentoDeRisco.setCtrlPlanoRisco(this);
		formPlanejarAcoesDeTratamentoDeRisco.setParent(janDadosPlanejarAcoesDeTratamentoDeRisco);
		janDadosPlanejarAcoesDeTratamentoDeRisco.mostrar();
	}
	
	
	/**
	 * Salva os riscos priorizados de uma avaliacao de risco.
	 * @param listagemPriorizarRiscos.
	 * @throws InterruptedException.
	 */
	public void salvarRiscoPriorizado(ListagemPriorizarRiscos listagemPriorizarRiscos) throws InterruptedException {
		 
		for(Object objeto : listagemPriorizarRiscos.getListBox().getItems()) {
			
			// Pega um listitem do listbox.
			Listitem listitem = (Listitem) objeto;
			 
			// Pega o PerfilRisco do listitem.
			PerfilRisco perfilRiscoItem = (PerfilRisco) listitem.getValue();
			
			// Recupera o objeto avaliacaoRiscoDAO.
			AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
			AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRiscoItem);
			
			// Recupera o objeto aplCadastrarAvalicaoRisco.
			AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarAvalicaoRisco.class);
			
			if (listitem.isSelected()) {
				avaliacaoRisco.setEhPrioritario(true);
			} else {
				avaliacaoRisco.setEhPrioritario(false);
			}
			
			// salva avaliacaoRisco.
			try {
				aplCadastrarAvalicaoRisco.salvar(avaliacaoRisco);
			} catch (NucleoRegraNegocioExcecao e) {
				Messagebox.show("Erro ao salvar riscos priorizados.");
			}
		}
		Messagebox.show("Riscos priorizados com sucesso.");
	}
	
	/**
	 * Exclue consequencia.
	 * @param consequencia.
	 * @param modo.
	 */
	public void excluirConsequencia(Consequencia consequencia, String modo) {
		formAvaliacaoRisco.excluirConsequencia(consequencia,modo);
	}

	/**
	 * Fecha janela consequencia.
	 */
	public void fecharJanelaConsequencia() {
		janDadosConsequencia.onClose();
	}
	
	/**
	 * Fecha janela avaliacao de risco.
	 */
	public void fecharJanelaAvaliacaoRisco() {
		janDadosAvalicaoRisco.onClose();
	}
	
	/**
	 * Fecha janela estrategia de tratamento de risco.
	 */
	public void fecharJanelaSelecionarEstrategiaDeTratamentoDeRiscos() {
		janDadosEstrategiaDeTratamento.onClose();
	}
	

	/**
	 * Fecha janela planejar acoes de tratamento de risco.
	 */
	public void fecharJanelaPlanejarAcoesDeTratamentoDeRiscos() {
		janDadosPlanejarAcoesDeTratamentoDeRisco.onClose();
	}
	
	
	/**
	 * 
	 * @return Colletion de planos de risco de um projeto.
	 */
	public Collection<PlanoRisco> listarPlanoRisco(){
		Collection<PlanoRisco> listas = aplCadastrarPlanoRisco.recuperarPorProjeto(NucleoContexto.recuperarProjeto());		
		return listas;
	}
	
	/**
	 * Salva o plano de risco.
	 * @param planoRisco.
	 * @throws NucleoRegraNegocioExcecao.
	 */
	public void salvarPlanoRisco(PlanoRisco planoRisco) throws NucleoRegraNegocioExcecao {
		aplCadastrarPlanoRisco.salvar(planoRisco);
	}
}
