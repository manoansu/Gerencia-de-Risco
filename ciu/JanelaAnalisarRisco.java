package ode.gerenciaRiscos.ciu;

import ode._infraestruturaCRUD.ciu.JanelaSimples;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

public class JanelaAnalisarRisco extends JanelaSimples {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	PainelCRUDPlanoRisco painelCRUDPlanoRisco;
	
	public JanelaAnalisarRisco(CtrlPlanoRisco ctrlPlanoRisco, final Listbox listbox) {
		super();
		
		// Cria painel de titulo.
		this.setTitle("Analisar Risco");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setWidth("260px");
		this.setHeight("120px");
		

		Vbox vbox = new Vbox();
		vbox.setParent(this);
		
		Hbox hbox = new Hbox();
		hbox.setParent(this);
		
		Label label = new Label();				
		label.setValue("Nome da Versão:");
		label.setMaxlength(100);
		label.setMultiline(true);
		label.setParent(vbox);
		
		final Textbox tbNome = new Textbox();
		tbNome.setWidth("120px");
		tbNome.setMaxlength(300);
		tbNome.setMultiline(true);
		tbNome.setParent(hbox);
		
		Div div =  new Div();
		div.setParent(this);
		div.setWidth("100%");
		div.setAlign("left");

		Button button = new Button("OK");
		button.setParent(this);
		button.setWidth("60px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
			
			}
		});
		
		div = new Div();
		button.setParent(this);
		div.setWidth("100%");
		div.setAlign("center");
		
		button = new Button("Cancelar");
		button.setParent(this);
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
}
