package ode.gerenciaRiscos.ciu;

import java.util.Collection;
import java.util.Date;

import ode._infraestruturaBase.util.NucleoContexto;
import ode._infraestruturaCRUD.ciu.JanelaSimples;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vbox;

import com.ibm.icu.text.DateFormat;

public class JanelaCadastrarPlanoRisco extends JanelaSimples {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CtrlPlanoRisco ctrlPlanoRisco;

	protected JanelaSimples janDadosCdastrarPlanoRisco;

	
	public JanelaCadastrarPlanoRisco(final CtrlPlanoRisco ctrlPlanoRisco) {
		super();

		this.ctrlPlanoRisco = ctrlPlanoRisco;
		
		Hbox hbox = new Hbox();
		hbox.setParent(this);
		
		///////////////////////////////
		// Listbox
		///////////////////////////////
		listbox.setHeight("350px");
		listbox.setCheckmark(true);
		listbox.setMultiple(false);
		listbox.setMold("paging");
		listbox.setParent(hbox);
		
		Listhead listhead = new Listhead();
		listhead.setParent(listbox);


		this.setTitle("Web-GeRis - Ferramenta de Apoio à Gestão de Riscos");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setWidth("800px");

		Listheader listheader = new Listheader("Versão");
		listheader.setParent(listhead);

		listheader = new Listheader("Estado");
		listheader.setParent(listhead);

		listheader = new Listheader("Última Atualização");
		listheader.setParent(listhead);

		///////////////////////////////
		// vbox
		///////////////////////////////
		Vbox vbox = new Vbox();
		vbox.setParent(hbox);

		// Button que exibe a janela da nova versao de plano de risco.
		Button button = new Button("Nova Versão");
		button.setParent(vbox);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				ctrlPlanoRisco.verificarPlanoDeRisco(listbox);
				fecharJanelaCadastrarPlanoRisco();
			}
		});

		// Button que exibe a janela de plano de risco para editar.
		button = new Button("Editar");
		button.setParent(vbox);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				editrarJanelaPlanoRisco(listbox);
				fecharJanelaCadastrarPlanoRisco();
			}
		});
		
		
		// Button que exibe a janela de relatorio de plano de risco.
		button = new Button("Visualizar");
		button.setParent(vbox);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirRelatorioDePlanoRisco();
				fecharJanelaCadastrarPlanoRisco();
			}
		});
		
		// Button que finaliza o plano do risco que esta em aberto.
		button = new Button("Finalizar");
		button.setParent(vbox);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				finalizarPlanoRisco();
			}
		});
	
	}

	/**
	 * Exibe janela de versoes.
	 * @param listbox
	 */
	public void exibirJanelaDeVersoes(Listbox listbox) {
		JanelaControleVersoes janelaControleVersoes = new JanelaControleVersoes(ctrlPlanoRisco,listbox);
		janelaControleVersoes.mostrar();
	}
	
	/**
	 * Edita o plano de risco.
	 * @param listbox
	 * @throws InterruptedException
	 */
	public void editrarJanelaPlanoRisco(Listbox listbox) throws InterruptedException {

		if (listbox.getSelectedItem() == null) {
			try {
				Messagebox.show("Selecione um plano de risco.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		
		// Recupera o objeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil.getApplicationContext()
				.getBean(AplCadastrarPlanoRisco.class);
		
		// Recupera os planos de risco do projeto.
		Collection<PlanoRisco> planosRisco = aplCadastrarPlanoRisco.recuperarPorProjeto(NucleoContexto.recuperarProjeto());
		
		for(PlanoRisco planoRisco : planosRisco){
			if(planoRisco.getDtFinalizacao() != null) {
				try {
					Messagebox.show("Não é permitido editar este plano de risco. " +
							"Já está finalizado.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		
		PlanoRisco planoRisco = (PlanoRisco) listbox.getSelectedItem().getValue();
		aplCadastrarPlanoRisco.editarPlanoRisco(ctrlPlanoRisco, planoRisco);
	}
	
	/**
	 * Adiciona o plano de risco.
	 * @param planosRisco
	 */
	public void adicionarPlanosRisco(Collection<PlanoRisco> planosRisco){

		// Limpa o listbox.
		listbox.getItems().clear();
		
		for(PlanoRisco planoRisco : planosRisco){
			
			Listitem listitem = new Listitem();
			listitem.setParent(listbox);
			listitem.setValue(planoRisco);

			Listcell listcell = new Listcell();
			listcell.setParent(listitem);
			listcell.setLabel(planoRisco.getVersao());

			listcell = new Listcell();
			listcell.setParent(listitem);
			listcell.setLabel(planoRisco.getDtFinalizacao() == null ? "Em Aberto" : "Finalizado");

			listcell = new Listcell();
			listcell.setParent(listitem);
			listcell.setLabel(DateFormat.getInstance().format(planoRisco.getDtFinalizacao() == null ? planoRisco.getData(): planoRisco.getDtFinalizacao()));
		}
	}
	
	/**
	 * Finaliza o plano de Risco.
	 */
	public void finalizarPlanoRisco() {
				
		if (listbox.getSelectedItem() == null) {
			try {
				Messagebox.show("Selecione um plano de risco.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		
		PlanoRisco planoRisco = (PlanoRisco) listbox.getSelectedItem().getValue();
		
		planoRisco.setDtFinalizacao(new Date());
		try {
			Messagebox.show("Plano de risco finalizado com sucesso.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.ctrlPlanoRisco.atualizar();
	}
	
	/**
	 *  Exibe relatorio de plano de risco.
	 */
	public void exibirRelatorioDePlanoRisco() {

			if (listbox.getSelectedItem() == null) {
				try {
					Messagebox.show("Selecione um item de plano de risco para visualizar o relatório.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			
			PlanoRisco planoRisco = (PlanoRisco) listbox.getSelectedItem().getValue();
			Executions.getCurrent().sendRedirect("/paginas/gerenciaRiscos/relatorio.zul?id=" + planoRisco.getId().toString(), "_blank");

			planoRisco.setData(new Date());
	}
	
	/**
	 * Fecha janela cadastrar plano de risco.
	 */
	public void fecharJanelaCadastrarPlanoRisco() {
		janDadosCdastrarPlanoRisco = new JanelaSimples();
		janDadosCdastrarPlanoRisco.onClose();
	}
	Listbox listbox = new Listbox();

}
