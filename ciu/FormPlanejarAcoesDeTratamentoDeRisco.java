package ode.gerenciaRiscos.ciu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ode._controleRecursoHumano.cdp.RecursoHumano;
import ode._controleRecursoHumano.cgd.RecursoHumanoDAO;
import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode.conhecimento.risco.cdp.KAcaoRisco;
import ode.gerenciaRiscos.cdp.AcaoContingencia;
import ode.gerenciaRiscos.cdp.AcaoMitigacao;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.api.Decimalbox;

public class FormPlanejarAcoesDeTratamentoDeRisco extends Vlayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	CtrlPlanoRisco ctrlPlanoRisco;
	PerfilRisco perfilRisco;
		
	public FormPlanejarAcoesDeTratamentoDeRisco(PerfilRisco perfilRisco, PlanoRisco planoRisco) {
		super();
		this.perfilRisco = perfilRisco;
		criarIterface();
	}
	
	public void criarIterface() {

		Vbox vbox = new Vbox();
		vbox.setParent(this);
		
		
		Hbox hboxRisco = new Hbox();
		Label labelRisco = new Label("Risco: ");
		Label labelKRisco = new Label(perfilRisco.getKRisco().getNome());
		labelRisco.setParent(hboxRisco);
		labelKRisco.setParent(hboxRisco);
		hboxRisco.setParent(vbox);
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		final AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
		
		Hbox hboxEstrategia = new Hbox();
		Label labelEstrategia = new Label("Estratégia: ");
		Label labelTratamento = new Label(avaliacaoRisco.getEstrategiaTratamento());
		labelEstrategia.setParent(hboxEstrategia);
		labelTratamento.setParent(hboxEstrategia);
		hboxEstrategia.setParent(vbox);
		
		
		Hbox hboxResponsavel = new Hbox();
		
		Label labelResponsavel = new Label("Responsável: ");
		final Listbox listboxRecursohumano = new Listbox();
		
		listboxRecursohumano.setParent(vbox);
		listboxRecursohumano.setCheckmark(false);
		listboxRecursohumano.setMold("select");
		listboxRecursohumano.setRows(1);
		
		// Recupera o objeto recursoHumanoDAO.
		RecursoHumanoDAO recursoHumanoDAO = SpringUtil.getApplicationContext().getBean(RecursoHumanoDAO.class);
		List<RecursoHumano> listaRecursoHumano = new ArrayList<RecursoHumano>();
		listaRecursoHumano = (List<RecursoHumano>) recursoHumanoDAO.recuperarRecursosHumanosAtivos();
		
		for(RecursoHumano recursoHumano: listaRecursoHumano) {
			Listitem listitem = new Listitem(recursoHumano.getNome());
			listitem.setParent(listboxRecursohumano);
			listitem.setValue(recursoHumano);
		}
	
		labelResponsavel.setParent(hboxResponsavel);
		listboxRecursohumano.setParent(hboxResponsavel);
		hboxResponsavel.setParent(vbox);

		// Grupo de tabs
		Tabbox tabbox = new Tabbox();
		tabbox.setParent(vbox);

		// Lista de nome de tabs
		Tabs tabs = new Tabs();
		tabs.setParent(tabbox);

		Tab tabAcoesDeMitigacao = new Tab("Ações de Mitigação");
		tabAcoesDeMitigacao.setParent(tabs);

		Tab tabAcoesDeContingencia = new Tab("Ações de Contingência");
		tabAcoesDeContingencia.setParent(tabs);
	

		// Lista de tabs
		Tabpanels tabpanels = new Tabpanels();
		tabpanels.setParent(tabbox);
		
		
		Hbox hboxdivisao = new Hbox();
		
		Label labelAcoes = new Label("Limiar para Iniciar Ações: ");

		final Decimalbox decimalboxAcoes = new org.zkoss.zul.Decimalbox();
		decimalboxAcoes.setHeight("20px");
		decimalboxAcoes.setWidth("400");
		
		labelAcoes.setParent(hboxdivisao);
		decimalboxAcoes.setParent(hboxdivisao);
		hboxdivisao.setParent(vbox);
		

		// /////////////////
		// Tabpanel Acoes de Mitigacao.
		// /////////////////

		Tabpanel tabpanelAcoesDeMitigacao = new Tabpanel();
		tabpanelAcoesDeMitigacao.setParent(tabpanels);
		
		final Listbox listboxAcaoDeMitigacao = new Listbox();
		listboxAcaoDeMitigacao.setParent(tabpanelAcoesDeMitigacao);
		listboxAcaoDeMitigacao.setMultiple(true);
		listboxAcaoDeMitigacao.setCheckmark(true);
		
		// cabecalho do listbox estrategia.
		Listhead listheadmitigacao = new Listhead();
		listheadmitigacao.setParent(listboxAcaoDeMitigacao);
		
		Listheader listheader = new Listheader("Ação");
		listheader.setParent(listheadmitigacao);
		
		int ehlimiar = 0;
		int recursoHumanoIndex = 0;
		
		for(KAcaoRisco kAcaoRisco: perfilRisco.getKRisco().getAcoesMitigacao()) {	
			Listitem listitem = new Listitem(kAcaoRisco.toString());
			listitem.setParent(listboxAcaoDeMitigacao);
			listitem.setValue(kAcaoRisco);
		}
		
		
		
		if(!avaliacaoRisco.getAcoesMitigacao().isEmpty()) {
			
			// Marca listitens de acao mitigacao selecionados, caso existam.
			for(Object objeto : listboxAcaoDeMitigacao.getItems()) {
				
				// Pega um listitem do listbox.
				Listitem listitem = (Listitem) objeto;
				
				for(AcaoMitigacao acaoMitigacaoRhumano: avaliacaoRisco.getAcoesMitigacao()) {
					
					for(Object objetoRecursoHumano: listboxRecursohumano.getItems()) {
						
						Listitem listitemRecursoHumano = (Listitem)objetoRecursoHumano;
						
						if(listboxRecursohumano.getItems().size() == 1) {
							if(acaoMitigacaoRhumano.getRecursoHumanoResponsavel().equals(listitemRecursoHumano.getValue())) {
								listboxRecursohumano.setSelectedIndex(0);
							}
						}else {
							if(acaoMitigacaoRhumano.getRecursoHumanoResponsavel().equals(listitemRecursoHumano.getValue())) {
								listboxRecursohumano.setSelectedIndex(recursoHumanoIndex);
								recursoHumanoIndex += 1;
							}
						}
						
					}
				}
				

				for (AcaoMitigacao acaoMitigacaoAvaliacao : avaliacaoRisco.getAcoesMitigacao()) {
					
					// Pega o limiar de avaliação risco.
					BigDecimal limiar = avaliacaoRisco.getLimiar();
					
					if (acaoMitigacaoAvaliacao.getKacaoRisco().equals((KAcaoRisco) listitem.getValue())) {
						
						// Marca as kacoes riscos selecionaodos.
						listitem.setSelected(true);
						ehlimiar += 1;
						
						// Preenche a caixa limiar para iniciar açoes.
						if(ehlimiar == 1) 
							decimalboxAcoes.setValue(limiar);
					}
				}
			}
		}

					
		// /////////////////
		// Tabpanel Acoes de Contingencia.
		// /////////////////

		Tabpanel tabpanelAcoesDeContingencia = new Tabpanel();
		tabpanelAcoesDeContingencia.setParent(tabpanels);
		
		final Listbox listboxAcaoDecontingencia = new Listbox();
		listboxAcaoDecontingencia.setParent(tabpanelAcoesDeContingencia);
		listboxAcaoDecontingencia.setMultiple(true);
		listboxAcaoDecontingencia.setCheckmark(true); 
		
		
		Hbox hboxContingencia = new Hbox();
		hboxContingencia.setParent(tabpanelAcoesDeContingencia);
		
		Listhead listheadContingencia = new Listhead();
		listheadContingencia.setParent(listboxAcaoDecontingencia);
		
		Listheader listheadercontigencia = new Listheader("Ação");
		listheadercontigencia.setParent(listheadContingencia);

			
		for(KAcaoRisco kacaoRisco: perfilRisco.getKRisco().getAcoesContingencia()) {
			
			Listitem listitem = new Listitem(kacaoRisco.toString());
			listitem.setParent(listboxAcaoDecontingencia);
			listitem.setValue(kacaoRisco);
		}

		if(!avaliacaoRisco.getAcoesContingencia().isEmpty()) {
			int index = 0;
		
			for(Object objeto : listboxAcaoDecontingencia.getItems()) {
								
				// Pega um listitem do listbox.
				Listitem listitemContingencia = (Listitem) objeto;
				
				if(recursoHumanoIndex == 0) {
					for(AcaoContingencia acaoContingenciaRhumano: avaliacaoRisco.getAcoesContingencia()) {
						
						for(Object objetoRecursoHumano: listboxRecursohumano.getItems()) {
									
								Listitem listitemRecursoHumano = (Listitem)objetoRecursoHumano;
								
								if(listboxRecursohumano.getItems().size() == 1) {					
									if(acaoContingenciaRhumano.getRecursoHumanoResponsavel().equals(listitemRecursoHumano.getValue())) {
										listboxRecursohumano.setSelectedIndex(0);
									}
								}else {
									if(acaoContingenciaRhumano.getRecursoHumanoResponsavel().equals(listitemRecursoHumano.getValue())) {
										listboxRecursohumano.setSelectedIndex(index);
										index += 1;
									}
								}
							}
						}
					}
				
			
				for(AcaoContingencia acaoContingenciaAvaliacao: avaliacaoRisco.getAcoesContingencia()) {
					
					// Pega o limiar de avaliacaoRisco.
					BigDecimal limiar = avaliacaoRisco.getLimiar();
					
					// Verifica se eh o kacaorisco do listitem.
					if((acaoContingenciaAvaliacao.getKacaoRisco().equals((KAcaoRisco)listitemContingencia.getValue()))) {
						
						// Se for, marca o listitem
						listitemContingencia.setSelected(true);
						
						// Preenche a caixa limiar para iniciar açoes...
						if(ehlimiar == 0) 
							decimalboxAcoes.setValue(limiar);
					}
				}	
			}
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
				
				avaliacaoRisco.setLimiar(decimalboxAcoes.getValue());
				Set<AcaoMitigacao> listaAcoesDeMitigacao = new HashSet<AcaoMitigacao>();
				for (Object item : listboxAcaoDeMitigacao.getSelectedItems()) {
					
					AcaoMitigacao acaoMitigacao = new AcaoMitigacao();
					
					for(Object objeto: listboxRecursohumano.getItems()) {
						Listitem listitem = (Listitem)objeto;
						RecursoHumano recursoHumano = (RecursoHumano)listitem.getValue();

						if(recursoHumano.equals(listitem.getValue())) {
							acaoMitigacao.setRecursoHumanoResponsavel(recursoHumano);
						}
					}
					acaoMitigacao.setKacaoRisco((KAcaoRisco)((Listitem)item).getValue());
					listaAcoesDeMitigacao.add(acaoMitigacao);
				}
				avaliacaoRisco.setAcoesMitigacao(listaAcoesDeMitigacao);
				
				///////////////////////////////////
				/////Acoes Contingencias.
				//////////////////////////////////
				
				Set<AcaoContingencia> listaAcoesDeContingencia = new HashSet<AcaoContingencia>();
				for (Object item : listboxAcaoDecontingencia.getSelectedItems()) {
					
					AcaoContingencia acaoContingencia = new AcaoContingencia();
					
					for(Object objeto: listboxRecursohumano.getItems()) {
						Listitem listitem = (Listitem)objeto;
						RecursoHumano recursoHumano = (RecursoHumano)listitem.getValue();

						if(recursoHumano.equals(listitem.getValue())) {
							acaoContingencia.setRecursoHumanoResponsavel(recursoHumano);
						}
					}
					acaoContingencia.setKacaoRisco((KAcaoRisco)((Listitem)item).getValue());
					listaAcoesDeContingencia.add(acaoContingencia);
				}
				avaliacaoRisco.setAcoesContingencia(listaAcoesDeContingencia);
				
				salvarJanelaPlanejarAcoesDeTratamentoDeRisco(avaliacaoRisco);
				
				fecharJanelaPlanejarAcoesDeTratamentoDeRisco();
			}
		});

		Button buttonCancelar = new Button("Cancelar");
		buttonCancelar.setParent(div);
		buttonCancelar.setWidth("100px");
		buttonCancelar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					fecharJanelaPlanejarAcoesDeTratamentoDeRisco();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		}
	
	
	/**
	 * Salva janela de planejar acoes de tratamento de risco.
	 * @param avaliacaoRisco.
	 * @throws InterruptedException.
	 */
	public void salvarJanelaPlanejarAcoesDeTratamentoDeRisco(AvaliacaoRisco avaliacaoRisco) throws InterruptedException {
		
		// Recupera o objeto aplCadastrarAvalicaoRisco.
		AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarAvalicaoRisco.class);
		
		try {
			aplCadastrarAvalicaoRisco.salvar(avaliacaoRisco);
		} catch (NucleoRegraNegocioExcecao e) {
			Messagebox.show("Erro ao salvar. " + e.getMessage());
		}
		
		Messagebox.show("Ações de Mitigações e de Contingência são salvas com sucesso.");
	}
	
	
	public void fecharJanelaPlanejarAcoesDeTratamentoDeRisco() {
		ctrlPlanoRisco.fecharJanelaPlanejarAcoesDeTratamentoDeRiscos();
	}
	
	public void setCtrlPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco) {
		this.ctrlPlanoRisco = ctrlPlanoRisco;
	}

}

