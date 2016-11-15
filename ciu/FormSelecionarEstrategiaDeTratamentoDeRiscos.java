package ode.gerenciaRiscos.ciu;

import java.util.List;

import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarAvalicaoRisco;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

public class FormSelecionarEstrategiaDeTratamentoDeRiscos extends Vlayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CtrlPlanoRisco ctrlPlanoRisco;
	PerfilRisco perfilRisco;
	PlanoRisco planoRisco;
	
	ListagemSelecionarEstategiaDeTaratamentoDeRiscos listagemSelecionarEstategiaDeTaratamentoDeRiscos;
	
	public FormSelecionarEstrategiaDeTratamentoDeRiscos(PlanoRisco planoRisco,PerfilRisco perfilRisco
			,ListagemSelecionarEstategiaDeTaratamentoDeRiscos listageEstrategia) {
		super();
		this.perfilRisco = perfilRisco; 
		this.planoRisco = planoRisco;
		listagemSelecionarEstategiaDeTaratamentoDeRiscos = listageEstrategia;
		this.criarInterface();
	}
	
	public void criarInterface() {

		// Listbox estrategia.
		final Listbox listboxEstrategia = new Listbox();
		listboxEstrategia.setParent(this);
		listboxEstrategia.setMultiple(false);
		listboxEstrategia.setCheckmark(true);
		
		// Cabecalho do listbox estrategia.
		Listhead listhead = new Listhead();
		listhead.setParent(listboxEstrategia);
		
		Listheader listheader = new Listheader("Estratégia");
		listheader.setParent(listhead);
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
		
		final List<String> listaEstrategias = AvaliacaoRisco.recuperarEstrategiasTratamento();
		
		for (String estragegia : listaEstrategias) {
			Listitem listitem = new Listitem(estragegia);
			listitem.setParent(listboxEstrategia);
			listitem.setValue(estragegia);
		}
				
		Label labelMensagem = new  Label("Justificativas/Observações:");
		labelMensagem.setParent(this);
		
		final Textbox textboxJustificativa = new Textbox();
		textboxJustificativa.setRows(3);
		textboxJustificativa.setParent(this);
		textboxJustificativa.setWidth("97%");
		
		
		//Verifica se  estrategia de tratamento foi salvo no banco.
		if (!avaliacaoRisco.getEstrategiaTratamento().isEmpty()) {
			

			for (Object object : listboxEstrategia.getItems()) {
				
				// Pega um listitem do listbox.
				Listitem listitem = (Listitem)object;
				
				// Pega a estrategia do listitem.
				String estrategia = (String)listitem.getValue();
				
				if(estrategia.equals((avaliacaoRisco.getEstrategiaTratamento()))) {
					
					// Se for, marca o listitem.
					listitem.setSelected(true);

				}
			}	
			
			//Recupera justificativas e observacoes salvaono banco.
			textboxJustificativa.setValue(avaliacaoRisco.getJustificativaEstrategiaTratamento());
		}

		
		Div div = new Div();
		div.setParent(this);
		div.setWidth("100%");
		div.setAlign("right");

		Button buttonSalvar = new Button("Salvar");
		buttonSalvar.setParent(div);
		buttonSalvar.setWidth("100px");
		buttonSalvar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Listitem lisititemSelecionado = listboxEstrategia.getSelectedItem();
				
				// Se listboxEstrategia selecionado nao eh nulo.
				if(listboxEstrategia.getSelectedItem() != null) {	
					salvarEstrategiaDeTratamentoDeRiscos(lisititemSelecionado, textboxJustificativa,perfilRisco);
					listagemSelecionarEstategiaDeTaratamentoDeRiscos.atualizar(planoRisco);
					fecharJanelaSelecionarEstrategiaDeTratamentoDeRiscos();
				}else {
					Messagebox.show("Seleciona um item.");
					}
			}
		});

		Button buttonCancelar = new Button("Cancelar");
		buttonCancelar.setParent(div);
		buttonCancelar.setWidth("100px");
		buttonCancelar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					fecharJanelaSelecionarEstrategiaDeTratamentoDeRiscos();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		}
	
	/**
	 * Fecha janela de estrategia de trantamento de risco.
	 */
	public void fecharJanelaSelecionarEstrategiaDeTratamentoDeRiscos() {
		ctrlPlanoRisco.fecharJanelaSelecionarEstrategiaDeTratamentoDeRiscos();
	}
	
	
	/**
	 * Salva estrategia de tratamento e risco.
	 * @param listitem
	 * @param textboxJustificativa
	 * @param perfilRisco
	 * @throws InterruptedException
	 */
	public void salvarEstrategiaDeTratamentoDeRiscos(Listitem listitem, Textbox textboxJustificativa
			,PerfilRisco perfilRisco) throws InterruptedException {
		 
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);

		// Recupera o objeto aplCadastrarAvalicaoRisco.
		AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarAvalicaoRisco.class);
		
		if (listitem.isSelected()) {
			avaliacaoRisco.setEstrategiaTratamento(listitem.getValue().toString());
			avaliacaoRisco.setJustificativaEstrategiaTratamento(textboxJustificativa.getValue());
		} 
		
		// salva avaliacaoRisco.
		try {
			aplCadastrarAvalicaoRisco.salvar(avaliacaoRisco);
		} catch (NucleoRegraNegocioExcecao e) {
			Messagebox.show("Erro ao salvar Justificativa e Estratégias de Risco.");
		}
		// Exibe a mensagem na tela.
		Messagebox.show("Estratégia e Jusstificativa de Avaliaçção Risco são salvas com sucesso.");
		
		// Atualiza a janaela de estrategia Selecionada.
		listagemSelecionarEstategiaDeTaratamentoDeRiscos.atualizar(perfilRisco.getPlanoRisco());
		
	}
		
	/**
	 * @param ctrlPlanoRisco the ctrlPlanoRisco to set
	 */
	public void setCtrlPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco) {
		this.ctrlPlanoRisco = ctrlPlanoRisco;
	}
}
