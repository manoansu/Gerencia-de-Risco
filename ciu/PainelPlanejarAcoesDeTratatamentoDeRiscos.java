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

public class PainelPlanejarAcoesDeTratatamentoDeRiscos extends Panel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PlanoRisco planoRisco;
	private CtrlPlanoRisco ctrlPlanoRisco;
	ListagemPlanejarAcoesDeTratamentoDeRisco listagemPlanejarAcoesDeTratamentoDeRisco;
	
	public PainelPlanejarAcoesDeTratatamentoDeRiscos(PlanoRisco planoRisco, CtrlPlanoRisco ctrlPlanoRisco) throws InterruptedException{
		
		this.ctrlPlanoRisco = ctrlPlanoRisco;
		this.planoRisco = planoRisco;
		
		this.setTitle("Planejar Açoes de Tratamento de Riscos");
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
		
		// Recupera os kriscos do banco
		List<PerfilRisco> lista = (List<PerfilRisco>) aplCadastrarPlanoRisco.recuperarPerfilRiscoPorPlanoRisco(planoRisco);
		final List<PerfilRisco> listaPlanejarAcoesDeTratamentoDeEstrategiaDeRiscos = new ArrayList<PerfilRisco>();


		for(PerfilRisco perfilRisco : lista) {
			
			// Recupera o objeto de avaliacaoRisco.
			AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);

			if(avaliacaoRisco != null) {
				
				if(avaliacaoRisco.getEstrategiaTratamento() != null && (avaliacaoRisco.getEstrategiaTratamento().equals("Mitigar") || 
						avaliacaoRisco.getEstrategiaTratamento().equals("Reagir")) || 
						avaliacaoRisco.getEstrategiaTratamento().equals("Mitigar/Reagir")) {
					perfilRisco.getKRisco().getNome();
					listaPlanejarAcoesDeTratamentoDeEstrategiaDeRiscos.add(perfilRisco);
				}
			}
		}

		listagemPlanejarAcoesDeTratamentoDeRisco  = new ListagemPlanejarAcoesDeTratamentoDeRisco();

		// Preenche o listbox com os kriscos do banco.
		listagemPlanejarAcoesDeTratamentoDeRisco.setObjetos(listaPlanejarAcoesDeTratamentoDeEstrategiaDeRiscos);
		listagemPlanejarAcoesDeTratamentoDeRisco.configurarComponentes();
		listagemPlanejarAcoesDeTratamentoDeRisco.setParent(vlayout);
		listagemPlanejarAcoesDeTratamentoDeRisco.preencherLista();
		listagemPlanejarAcoesDeTratamentoDeRisco.getListBox().setMultiple(false);

		Div div = new Div();
		div.setParent(vlayout);
		div.setAlign("right");
		Button buttonPlanejar = new Button("Planejar");
		buttonPlanejar.setParent(div);
		buttonPlanejar.setWidth("150px");
		buttonPlanejar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (listagemPlanejarAcoesDeTratamentoDeRisco.getSelecionado() != null) {
					getCtrlPlanoRisco().exibirFormularioPlanejarAcoesDeTratamentoDeRiscos(listagemPlanejarAcoesDeTratamentoDeRisco
							.getSelecionado(), getPlanoRisco());
				} else {
					Messagebox.show("Selecione um item.");
				}
			}
		});
	}

	public PlanoRisco getPlanoRisco() {
		return planoRisco;
	}

	public void setPlanoRisco(PlanoRisco planoRisco) {
		this.planoRisco = planoRisco;
	}

	public CtrlPlanoRisco getCtrlPlanoRisco() {
		return ctrlPlanoRisco;
	}

	public void setCtrlPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco) {
		this.ctrlPlanoRisco = ctrlPlanoRisco;
	}
}
