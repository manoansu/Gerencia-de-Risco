package ode.gerenciaRiscos.ciu;

import java.util.Collection;
import java.util.Date;

import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaBase.util.NucleoContexto;
import ode._infraestruturaCRUD.ciu.JanelaSimples;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

public class JanelaControleVersoes extends JanelaSimples {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CtrlPlanoRisco ctrlPlanoRisco;

	protected JanelaSimples janDadosControleVersoes;

	private Listbox listbox;

	public JanelaControleVersoes(CtrlPlanoRisco ctrlPlanoRisco,
			final Listbox listbox) {
		super();

		this.ctrlPlanoRisco = ctrlPlanoRisco;
		this.listbox = listbox;

		if (this.listbox.getSelectedItem() == null) {

			// Criacao de painel de titulo.
			this.setTitle("Mensagem");
			this.setClosable(true);
			this.setWidth("210px");
			this.setHeight("135px");
			this.setStyle("text-align:center");

			Vbox vbox = new Vbox();
			vbox.setParent(this);

			final Textbox tbNome = new Textbox();
			Label label = new Label();
			label.setValue("Informe o nome da versão:");
			label.setMaxlength(100);
			label.setParent(vbox);

			tbNome.setWidth("120px");
			tbNome.setMaxlength(300);
			tbNome.setParent(vbox);

			Hbox hbox = new Hbox();
			hbox.setParent(vbox);
			hbox.setAlign("end");

			Button button = new Button("OK");
			button.setParent(hbox);
			button.setWidth("90px");
			button.setStyle("margin-right: 5px;");
			button.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					boolean ehverdadeiro = true;
					boolean situacao = true;
					controlarVersoes(tbNome, listbox, ehverdadeiro, situacao);
					fecharJanelaDeMensagem();
				}
			});

			button = new Button("Cancelar");
			button.setParent(hbox);
			button.setWidth("90px");
			button.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					try {
						onClose();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
			
		} else {

			if (this.listbox.getSelectedItem() != null) {

				this.setTitle("Mensagem");
				this.setClosable(true);
				this.setWidth("190px");
				this.setHeight("110px");
				this.setStyle("text-align:center");

				Vbox vbox = new Vbox();
				vbox.setParent(this);

				Label label = new Label();
				label.setValue("Deseja criar um nova versão?");
				label.setMaxlength(100);
				label.setParent(vbox);
				label.setStyle("margin-top:4; margin-bottom:4px;");

				Hbox hbox = new Hbox();
				hbox.setParent(vbox);
				hbox.setAlign("end");

				final Textbox tbNome = new Textbox();
				Button button = new Button("Sim");
				button.setParent(hbox);
				button.setWidth("60px");
				button.setStyle("margin-right: 5px; margin-top:3px;");
				button.addEventListener("onClick", new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						boolean ehverdadeiro = true;
						boolean situacao = true;
						atualizarVersoes(tbNome, listbox, ehverdadeiro,
								situacao);
						fecharJanelaDeMensagem();
					}
				});

				button = new Button("Não");
				button.setParent(hbox);
				button.setWidth("60px");
				button.setStyle("margin-top:3px;");
				button.addEventListener("onClick", new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						boolean ehverdadeiro = true;
						boolean situacao = false;
						atualizarVersoes(tbNome, listbox, ehverdadeiro,
								situacao);
						fecharJanelaDeMensagem();
					}
				});
			}
		}
	}

	/**
	 * Atualiza versoes.
	 * 
	 * @param tbNome
	 * @param listbox
	 * @param ehverdadeiro
	 */
	public void atualizarVersoes(final Textbox tbNome, final Listbox listbox,
			boolean ehverdadeiro, final boolean situacao) {

		// Criacao de painel de titulo.
		this.setTitle("Mensagem");
		this.setClosable(true);
		this.setWidth("210px");
		this.setHeight("135px");

		// Limpa os componentes na tela.
		this.getChildren().clear();

		Vbox vbox = new Vbox();
		vbox.setParent(this);

		Label label = new Label();
		label.setValue("Informe o nome da versão:");
		label.setMaxlength(100);
		label.setParent(vbox);

		tbNome.setWidth("120px");
		tbNome.setMaxlength(300);
		tbNome.setParent(vbox);

		Hbox hbox = new Hbox();
		hbox.setParent(vbox);
		hbox.setAlign("end");

		Button button = new Button("OK");
		button.setParent(hbox);
		button.setWidth("90px");
		button.setStyle("margin-right: 5px;");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				boolean ehverdadeiro = true;
				controlarVersoes(tbNome, listbox, ehverdadeiro, situacao);
			}
		});

		button = new Button("Cancelar");
		button.setParent(hbox);
		button.setWidth("90px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					onClose();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Controla versoes.
	 * 
	 * @param tbNome
	 * @param listbox
	 * @param ehversao
	 * @throws InterruptedException
	 */
	public void controlarVersoes(Textbox tbNome, Listbox listbox,
			boolean ehditar, boolean situacao) throws InterruptedException {

		// Pega lista de versoes de planos de risco.
		Collection<PlanoRisco> listaPlanoRiscos = ctrlPlanoRisco
				.listarPlanoRisco();
		PlanoRisco planoRisco = new PlanoRisco();
		
		// Se opcao de versao for vazia.
		if (tbNome.getValue().isEmpty()) {
			
			// Exibe a mensagem de erro caso usuario nao digitou o numero de versao.
			try {
				Messagebox.show("Erro! Informe a versão de Plano de Risco");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}

		//Se o item nao for selecionado.
		if (listbox.getSelectedItem() == null) {

			// Cria novo plano de risco incluindo a data e a nova versao se o item nao for selecionado.
			planoRisco = new PlanoRisco();
			planoRisco.setProjeto(NucleoContexto.recuperarProjeto());
			planoRisco.setData(new Date()); // Pega a nova data.
			planoRisco.setVersao(tbNome.getValue()); // Pega a nova versao que vai ser cadastrado.

			try {
				
				// Salva o plano de Risco.
				ctrlPlanoRisco.salvarPlanoRisco(planoRisco);
			} catch (NucleoRegraNegocioExcecao e) {
				try {
					Messagebox.show("Erro ao acessar o Banco");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
			JanelaPlanoRisco janelaPlanoRisco = new JanelaPlanoRisco(ctrlPlanoRisco, planoRisco);
			janelaPlanoRisco.mostrar();
			ctrlPlanoRisco.atualizar();
		} else {
			
			// Se usuario seleciona um plano de risco, e clicar em opcao sim.
			if (situacao) {
				
				// Copia plano de risco.
				copiarPlanoRisco(listaPlanoRiscos, tbNome);
				
			} else {// Caso contraio, cria novo plano de risco.
				
				try {
					criarNovolanoRisco(tbNome);
				} catch (NucleoRegraNegocioExcecao e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Copia plano de risco.
	 * 
	 * @param listaPlanoRiscos
	 * @param tbNome
	 * @param ehversao
	 * @throws InterruptedException
	 */
	public void copiarPlanoRisco(Collection<PlanoRisco> listaPlanoRiscos,
			Textbox tbNome) throws InterruptedException {

		// Recupera o objeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil
				.getApplicationContext().getBean(AplCadastrarPlanoRisco.class);
		
		// Pega o plano de risco selecionado para iniciar a copia.
		PlanoRisco planoRiscoSelecionado = (PlanoRisco) listbox.getSelectedItem().getValue();
		
		
		try {
			//Cria novo plano de risco.
			PlanoRisco planoRiscoNovo = aplCadastrarPlanoRisco.copiarPlanoRisco(planoRiscoSelecionado, tbNome.getValue());
			
			JanelaPlanoRisco janelaPlanoRisco = new JanelaPlanoRisco(ctrlPlanoRisco, planoRiscoNovo);
			janelaPlanoRisco.mostrar();
			ctrlPlanoRisco.atualizar();
			
		} catch (WrongValueException e) {
			e.printStackTrace();
		} catch (NucleoRegraNegocioExcecao e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria novo plano de risco.
	 * 
	 * @param tbNome
	 */
	public void criarNovolanoRisco(Textbox tbNome) throws NucleoRegraNegocioExcecao  {

		// Recupera o objeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil
						.getApplicationContext().getBean(AplCadastrarPlanoRisco.class);

		try {

			// Cria novo plano de risco.
			PlanoRisco novoplPlanoRisco  = aplCadastrarPlanoRisco.criarNovolanoRisco(tbNome.getValue()); 

			JanelaPlanoRisco janelaPlanoRisco = new JanelaPlanoRisco(ctrlPlanoRisco, novoplPlanoRisco);
			janelaPlanoRisco.mostrar();
			ctrlPlanoRisco.atualizar();
																
		}catch (WrongValueException e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * Fecha janela de mensagem. 
	 */
	public void fecharJanelaDeMensagem() {
		janDadosControleVersoes = new JanelaSimples();
		janDadosControleVersoes.onClose();
	}
}
