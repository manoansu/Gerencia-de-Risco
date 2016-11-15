package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Vlayout;

public class PainelSelecionarEstrategiaDeTratatamentoDeRiscos extends Panel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CtrlPlanoRisco ctrlPlanoRisco;
	
	public PainelSelecionarEstrategiaDeTratatamentoDeRiscos(final PlanoRisco planoRisco, CtrlPlanoRisco ctrlPlanoRisco) throws InterruptedException{
	
		this.ctrlPlanoRisco = ctrlPlanoRisco;
		
		this.setTitle("Selecionar Estratégia de Tratamento de Riscos");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setBorder("normal");
		this.setWidth("100%");
		this.setHeight("100%");
		
		Panelchildren painelChildrenCentro  = new Panelchildren();
		painelChildrenCentro.setParent(this);
		
		Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(painelChildrenCentro);
		
		// Recupera o objeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarPlanoRisco.class);
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		
		// Recupera todos os kriscos do banco
		List<PerfilRisco> lista = (List<PerfilRisco>) aplCadastrarPlanoRisco.recuperarPerfilRiscoPorPlanoRisco(planoRisco);
		final List<PerfilRisco> listaEstrategiaTratamentoDeEstrategiaDeRiscos = new ArrayList<PerfilRisco>();
		
		for(PerfilRisco perfilRiscoLocal : lista) {
			
			AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRiscoLocal);
			
			if(avaliacaoRisco != null) {
				if(avaliacaoRisco.isEhPrioritario() == true) {
					perfilRiscoLocal.getKRisco().getNome();
					listaEstrategiaTratamentoDeEstrategiaDeRiscos.add(perfilRiscoLocal);
				}
			}
		}
		
		final ListagemSelecionarEstategiaDeTaratamentoDeRiscos listagemSelecionarEstrategiaDeTratamentoDeRisco = 
				new ListagemSelecionarEstategiaDeTaratamentoDeRiscos();
		
		// Preenche o listbox com os kriscos do banco.
		listagemSelecionarEstrategiaDeTratamentoDeRisco.setObjetos(listaEstrategiaTratamentoDeEstrategiaDeRiscos);
		listagemSelecionarEstrategiaDeTratamentoDeRisco.configurarComponentes();
		listagemSelecionarEstrategiaDeTratamentoDeRisco.setParent(vlayout);
		listagemSelecionarEstrategiaDeTratamentoDeRisco.preencherLista();
		listagemSelecionarEstrategiaDeTratamentoDeRisco.getListBox().setMultiple(false);
		
		Div div = new Div();
		div.setParent(vlayout);
		div.setAlign("right");
		Button buttonSelecionarEstrategia = new Button("Selecionar Estratégia");
		buttonSelecionarEstrategia.setParent(div);
		buttonSelecionarEstrategia.setWidth("150px");
		buttonSelecionarEstrategia.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (listagemSelecionarEstrategiaDeTratamentoDeRisco.getSelecionado() != null) {
					getCtrlPlanoRisco().exibirFormularioSelecionarEstrategiaDeTratamentoDeRiscos(planoRisco,
							listagemSelecionarEstrategiaDeTratamentoDeRisco.getSelecionado()
							, listagemSelecionarEstrategiaDeTratamentoDeRisco);
				} else {
					Messagebox.show("Selecione um item.");
				}
			}
		});
	}

	public CtrlPlanoRisco getCtrlPlanoRisco() {
		return ctrlPlanoRisco;
	}

	public void setCtrlPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco) {
		this.ctrlPlanoRisco = ctrlPlanoRisco;
	}
}
