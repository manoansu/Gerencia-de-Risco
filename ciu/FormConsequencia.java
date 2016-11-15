package ode.gerenciaRiscos.ciu;

import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaCRUD.ciu.GridDados;
import ode.gerenciaRiscos.cdp.Consequencia;
import ode.gerenciaRiscos.cgt.AplCadastrarConsequencia;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;

public class FormConsequencia extends Vlayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String MODO_INSERCAO = "Inserção";
	
	public static String MODO_EDICAO = "Edição";
	
	public static String MODO_REMOCAO = "Remocão";
	
	private CtrlPlanoRisco ctrlPlanoRisco;
	
	private FormAvaliacaoRisco formAvaliacaoRisco ;
	
	Consequencia consequencia;
	
	private AplCadastrarConsequencia aplCadastrarConsequencia;
	
	String modo;
	
	public FormConsequencia(FormAvaliacaoRisco formAvaliacaoRisco, Consequencia consequencia, String modo) {
		super();
		
		this.formAvaliacaoRisco = formAvaliacaoRisco;
		this.consequencia = (consequencia != null ? consequencia : new Consequencia()); 
		this.modo = modo;
		definirInterfaceConsequencias();
	}

	public void definirInterfaceConsequencias() {
		
		// Layout vertical.
		Vbox vbox = new Vbox();
		vbox.setParent(this);
		
		GridDados gridConsequencia = new GridDados();
		
		final Textbox textboxDescricao = new Textbox();
		textboxDescricao.setWidth("780");
		textboxDescricao.setHeight("20px");

		
		final Textbox textboxObservacao = new Textbox();
		textboxObservacao.setWidth("780");
		textboxObservacao.setHeight("20px");
	
		gridConsequencia.adicionarLinha("Descrição", textboxDescricao);
		gridConsequencia.adicionarLinha("Observação", textboxObservacao);
		gridConsequencia.setParent(vbox);
		
		/////////////////////////////////////
		// Preenche dados da consequencia.
		////////////////////////////////////
		
		textboxDescricao.setValue(consequencia.getDescricao());
		textboxObservacao.setValue(consequencia.getObservacao());
		
		Div div = new Div();
		div.setParent(vbox);
		div.setWidth("100%");
		div.setAlign("right");
		
		Button button = new Button("OK");
		button.setParent(div);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				consequencia.setDescricao(textboxDescricao.getValue());
				consequencia.setObservacao(textboxObservacao.getValue());
				formAvaliacaoRisco.adicionarConsequencia(consequencia,modo);
				fecharJanelaConsequencia();
			}
		});

		button = new Button("Cancelar");
		button.setParent(div);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					fecharJanelaConsequencia();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void fecharJanelaConsequencia() {
		ctrlPlanoRisco.fecharJanelaConsequencia();
	}
	
	
	/**
	 * Salva Consequencia.
	 * @param consequencia.
	 * @param textboxDescricao.
	 * @param textboxObeservacao.
	 * @throws NucleoRegraNegocioExcecao.
	 */
	public void salvarConsequencia(Consequencia consequencia,Textbox textboxDescricao, Textbox textboxObeservacao) throws NucleoRegraNegocioExcecao {
		
		// Recupera o objeto aplCadastrarConsequencia.
		aplCadastrarConsequencia = SpringUtil.getApplicationContext().getBean(AplCadastrarConsequencia.class);
		
		if(consequencia != null) {
			consequencia.setDescricao(textboxDescricao.getValue());
			consequencia.setObservacao(textboxObeservacao.getValue());
			aplCadastrarConsequencia.salvar(consequencia);
		}try {
			Messagebox.show("Consequência salvo com sucesso.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the ctrlPlanoRisco
	 */
	public CtrlPlanoRisco getCtrlPlanoRisco() {
		return ctrlPlanoRisco;
	}

	/**
	 * @param ctrlPlanoRisco the ctrlPlanoRisco to set
	 */
	public void setCtrlPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco) {
		this.ctrlPlanoRisco = ctrlPlanoRisco;
	}
}
