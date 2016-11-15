package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ode.gerenciaRiscos.cdp.Acao;
import ode.gerenciaRiscos.cdp.AcaoContingencia;
import ode.gerenciaRiscos.cdp.AcaoMitigacao;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarAvalicaoRisco;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Vlayout;

public class PainelAcompanharAcoesDeTratatamentoDeRiscos extends Panel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean ehavaliacao = false;
	AvaliacaoRisco avaliacaoRisco;

	
	public PainelAcompanharAcoesDeTratatamentoDeRiscos(PlanoRisco planoRisco) throws InterruptedException{
	
	
		this.setTitle("Acompanhar Ações de Tratamento de Riscos");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setBorder("normal");
		this.setWidth("100%");
		this.setHeight("100%");
		
		Panelchildren painelChildrenCentro  = new Panelchildren();
		painelChildrenCentro.setParent(this);
		
		final Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(painelChildrenCentro);
		
		Hbox hbox = new Hbox();
		hbox.setParent(vlayout);
		
		/////////////////////////////////
		// Itens
		/////////////////////////////////
		final Listbox listboxAcao = new Listbox();
		
		final Listbox listboxKRiscos = new Listbox();
		listboxKRiscos.setParent(vlayout);
		listboxKRiscos.setMultiple(false);
		listboxKRiscos.setCheckmark(false);
		
		// Cabecalho do listbox estrategia.
		Listhead listheadRiscosComAcoesPlanejadas = new Listhead();
		listheadRiscosComAcoesPlanejadas.setParent(listboxKRiscos);
		
		Listheader listheader = new Listheader("Riscos com Ações Planejadas");
		listheader.setParent(listheadRiscosComAcoesPlanejadas);
		
		Div divisaoListbox = new Div();
		divisaoListbox.setParent(vlayout);
		divisaoListbox.setHeight("30px");
		
		Label labelAcoesDeRiscosSelecionado = new Label("Ações de Riscos Selecionado");
		labelAcoesDeRiscosSelecionado.setParent(vlayout);
		labelAcoesDeRiscosSelecionado.setStyle("color: blue;font-weight: bold;font-size:14px;");
		
		// Recupera o objeto de perfilRiscoDAO.
		PerfilRiscoDAO perfilRiscoDAO = SpringUtil.getApplicationContext().getBean(PerfilRiscoDAO.class);
		
		Collection<PerfilRisco> perfisRiscoDoBanco = perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco);
		
		Listitem listitem = new Listitem();
		final Listbox listboxAcoesRiscoSelecionado = new Listbox();
		
		for(PerfilRisco perfilRisco: perfisRiscoDoBanco) {	
			listitem = new Listitem(perfilRisco.getKRisco().getNome());
			listitem.setParent(listboxKRiscos);
			
			// Recupera o objeto de avaliacaoRiscoDAO.
			AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
			avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
			
			listitem.setValue(avaliacaoRisco);
		}
		
		////////////////////////////////////////
		//Listbox de açoes do riscos selecionado.
		//////////////////////////////////////////
		
		listboxAcoesRiscoSelecionado.setParent(vlayout);
		listboxAcoesRiscoSelecionado.setMultiple(false);
		listboxAcoesRiscoSelecionado.setCheckmark(false);
		
		Listhead listheadAcoes = new Listhead();
		listheadAcoes.setParent(listboxAcoesRiscoSelecionado);
		
		Listheader listheaderPlanejada = new Listheader("Ação");
		listheaderPlanejada.setWidth("325px");
		listheaderPlanejada.setParent(listheadAcoes);
		
		Listheader listheaderTipo = new Listheader("Tipo");
		listheaderTipo.setWidth("100px");
		listheaderTipo.setParent(listheadAcoes);
		
		Listheader listheaderResponsavel = new Listheader("Responsável");
		listheaderResponsavel.setWidth("325px");
		listheaderResponsavel.setParent(listheadAcoes);
		
		Listheader listheaderStatus = new Listheader("Status");
		listheaderStatus.setWidth("100%");
		listheaderStatus.setParent(listheadAcoes);
		
		listboxKRiscos.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
			
				// Pegar o item selecionado.
				Listitem listitemselecionado = new Listitem();
				listitemselecionado = listboxKRiscos.getSelectedItem();
				
				// Preencher o outro listbox de acaoes.
				if(listitemselecionado.getValue() != null) {
					atualizarListboxAcoesRiscoSelecionado(listboxAcao,(AvaliacaoRisco)listitemselecionado.getValue(), listboxAcoesRiscoSelecionado,ehavaliacao);
				}
				
			}
		});
			
		Div div = new Div();
		div.setParent(vlayout);
		div.setAlign("right");
		Button buttonPlanejar = new Button("Salvar");
		buttonPlanejar.setParent(div);
		buttonPlanejar.setWidth("150px");
		buttonPlanejar.addEventListener("onClick", new EventListener() {
		@Override
		public void onEvent(Event arg0) throws Exception {
		
			// Recupera o objeto aplCadastrarAvalicaoRisco;
			AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarAvalicaoRisco.class);
			
			// Lista de acoes de mitigacao.
			Set<AcaoMitigacao> listaAcoesMitigacao = new HashSet<AcaoMitigacao>();
			
			// Lista de acoes de contingencia.
			Set<AcaoContingencia> listaAcoesContingencia = new HashSet<AcaoContingencia>();

			// Recupera acoes do risco selecionado.
			for (Object object : listboxAcoesRiscoSelecionado.getItems()) {
			
				
				// Verifica se eh uma instancia de acao de mitigacao.
				if (((Listitem)object).getValue() instanceof AcaoMitigacao) {
				
					// Recupera a acao do listitem.
					AcaoMitigacao acaoMitigacao = (AcaoMitigacao) ((Listitem)object).getValue();
					
					// Recupera a informacao selecianada do listbox status do listitem
					String estado = ((Listbox)((Listcell)((Listitem)object).getChildren().get(3)).getChildren().get(0)).getSelectedItem().getLabel();
					
					// Seta status da acao e salva.
					acaoMitigacao.setEstado(estado);
					listaAcoesMitigacao.add(acaoMitigacao);
					ehavaliacao = true;
				} else {
							
					
					// Verifica se eh uma instancia de acao de contingencia.
					if (((Listitem)object).getValue() instanceof AcaoContingencia) {
					
					// Recupera a acao do listitem.
					AcaoContingencia acaoContingencia = (AcaoContingencia) ((Listitem)object).getValue();
					
					// Recupera a acao do listitem.
					String estado = ((Listbox)((Listcell)((Listitem)object).getChildren().get(3)).getChildren().get(0)).getSelectedItem().getLabel();
					
					// Recupera a informacao selecianada do listbox status do listitem.
					acaoContingencia.setEstado(estado);
					listaAcoesContingencia.add(acaoContingencia);
					}
				}
			}
			avaliacaoRisco.setAcoesMitigacao(listaAcoesMitigacao);
			avaliacaoRisco.setAcoesContingencia(listaAcoesContingencia);
			aplCadastrarAvalicaoRisco.salvar(avaliacaoRisco);
			Messagebox.show("Ações de tratamento de riscos salvas com sucesso.");
		}
		
		});
	}
	
	/**
	 * Atualiza listbox de acoes de risco selecionado.
	 * @param listboxAcao
	 * @param avaliacaoRisco
	 * @param listboxAcoesRiscoSelecionado
	 * @param ehavaliacao
	 */
	public void atualizarListboxAcoesRiscoSelecionado(Listbox listboxAcao,AvaliacaoRisco avaliacaoRisco, Listbox listboxAcoesRiscoSelecionado, boolean ehavaliacao) {

		listboxAcoesRiscoSelecionado.getItems().clear();
		
		// Percorre a lista de ação de mitigacao.
		for (AcaoMitigacao acaoMitigacao : avaliacaoRisco.getAcoesMitigacao()) {

			Listitem listitem = new Listitem();
			listitem.setParent(listboxAcoesRiscoSelecionado);
			listitem.setValue(acaoMitigacao);

			Listcell listcellNome = new Listcell(acaoMitigacao.getKacaoRisco()
					.getNome());
			listcellNome.setParent(listitem);
			
			Listcell listcellTipo = new Listcell("Mitigação");
			listcellTipo.setParent(listitem);

			Listcell listcellResponsavel = new Listcell(acaoMitigacao
					.getRecursoHumanoResponsavel().getNome());
			listcellResponsavel.setParent(listitem);

			// Retorna listbox com os seus itens preenchidos.
			listboxAcao = criarListBListbox(acaoMitigacao);

			Listcell listcellStatus = new Listcell();
			listcellStatus.setParent(listitem);
			listboxAcao.setParent(listcellStatus);
		}

		// Percorre a lista de acao de contingencia.
		for (AcaoContingencia acaoContingencia : avaliacaoRisco
				.getAcoesContingencia()) {

			Listitem listitem = new Listitem();
			listitem.setParent(listboxAcoesRiscoSelecionado);
			listitem.setValue(acaoContingencia);

			Listcell listcellNome = new Listcell(acaoContingencia.getKacaoRisco()
					.getNome());
			listcellNome.setParent(listitem);

			Listcell listcellTipo = new Listcell("Contingência");
			listcellTipo.setParent(listitem);

			Listcell listcellResponsavel = new Listcell(acaoContingencia
					.getRecursoHumanoResponsavel().getNome());
			listcellResponsavel.setParent(listitem);

			// Retorna listbox com os seus itens preenchidos.
			listboxAcao = criarListBListbox(acaoContingencia);

			Listcell listcellStatus = new Listcell();
			listcellStatus.setParent(listitem);
			listboxAcao.setParent(listcellStatus);
		}	
	}
 
	/**
	 * 	Cria listbox.
	 * @param acao
	 * @return
	 */
	public Listbox criarListBListbox(Acao acao) {

		List<String> listaAcoesPlanejadas = new ArrayList<String>();
		
		listaAcoesPlanejadas.add(Acao.ACAO_PLANEJADA_PLANEJADA);
		listaAcoesPlanejadas.add(Acao.ACAO_PLANEJADA_EM_EXECUCAO);
		listaAcoesPlanejadas.add(Acao.ACAO_PLANEJADA_FINALIZADA);

		Listbox listbox = new Listbox();
		listbox.setMold("select");
		
		for(String planejada: listaAcoesPlanejadas) {
			Listitem listitem = new Listitem(planejada);
			listitem.setParent(listbox);
			listitem.setValue(planejada);
		}
		listbox.setSelectedIndex(0);

		// Se a acao nao tem estado, retorna listbox com o primeiro item selecionado.
		if(acao.getEstado() == null || acao.getEstado().isEmpty()) 
			return listbox;
			
			
		// Caso contrario, seleciona o item de acordo com o estado salvo.	
		if (acao.getEstado().compareToIgnoreCase(Acao.ACAO_PLANEJADA_PLANEJADA) == 0) {
			listbox.setSelectedIndex(0);
		} else {
			if(acao.getEstado().compareToIgnoreCase(Acao.ACAO_PLANEJADA_EM_EXECUCAO) == 0) {
				listbox.setSelectedIndex(1);
			}else {
					listbox.setSelectedIndex(2);
			}
		}

		return listbox;
	}
}
