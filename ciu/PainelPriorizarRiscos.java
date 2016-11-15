package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ode.conhecimento.risco.cdp.KRisco;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Vlayout;

public class PainelPriorizarRiscos extends Panel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CtrlPlanoRisco ctrlPlanoRisco;


	public PainelPriorizarRiscos(PlanoRisco planoRisco, CtrlPlanoRisco ctrlPlanoRisco) throws InterruptedException{

		this.ctrlPlanoRisco = ctrlPlanoRisco;
		
		this.setTitle("Priorizar Riscos");
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
		
		// Recupera os kriscos do banco.
		List<PerfilRisco> lista = (List<PerfilRisco>) aplCadastrarPlanoRisco.recuperarPerfilRiscoPorPlanoRisco(planoRisco);
		final List<PerfilRisco> listaRecuperaAvalicaoRisco = new ArrayList<PerfilRisco>();
		
		for(PerfilRisco perfilRisco : lista) {

			AvaliacaoRisco avaliacaoRiscoLocal  = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
			if(avaliacaoRiscoLocal != null) {
				perfilRisco.getKRisco().getNome();
				avaliacaoRiscoLocal.getProbabilidade();
				avaliacaoRiscoLocal.getImpacto();
				listaRecuperaAvalicaoRisco.add(perfilRisco);
			}
		}
		
		final ListagemPriorizarRiscos listagemPriorizarRiscos = new ListagemPriorizarRiscos();
		
		// preenche o listbox com os kriscos do banco.
		listagemPriorizarRiscos.setObjetos(listaRecuperaAvalicaoRisco);
		listagemPriorizarRiscos.configurarComponentes();
		listagemPriorizarRiscos.setParent(vlayout);
		listagemPriorizarRiscos.preencherLista();
		
		// Recupera o objeto perfilRiscoDAO.
		PerfilRiscoDAO perfilRiscoDAO = SpringUtil.getApplicationContext().getBean(PerfilRiscoDAO.class);
		
		Collection<PerfilRisco> perfisRiscoDoBanco = perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco);
		List<KRisco> kRiscosSelecionados = new ArrayList<KRisco>();
		
		for (PerfilRisco perfilRisco : perfisRiscoDoBanco) {
			kRiscosSelecionados.add(perfilRisco.getKRisco());
		}
		
		for(Object objeto : listagemPriorizarRiscos.getListBox().getItems()) {
		
			// pega um listitem do listbox.
			Listitem listitem = (Listitem) objeto;
			
			// Pega o krisco do listitem.
			KRisco kRiscoItem =  ((PerfilRisco) listitem.getValue()).getKRisco();
			
			// Pega avaliação risco do  listitem.
			AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco((PerfilRisco) listitem.getValue());
			
			if(kRiscosSelecionados.contains(kRiscoItem) && avaliacaoRisco.isEhPrioritario() == true) {
				// se for, marca o listitem.
				listitem.setSelected(true);
			}
		}
		
		Div div = new Div();
		div.setParent(vlayout);
		div.setAlign("right");
		Button buttonSalvar = new Button("Salvar");
		buttonSalvar.setParent(div);
		buttonSalvar.setWidth("100px");
		buttonSalvar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (listagemPriorizarRiscos.getSelecionado() != null) {
					getCtrlPlanoRisco().salvarRiscoPriorizado(listagemPriorizarRiscos);
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
