package ode.gerenciaRiscos.ciu;

import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarAvalicaoRisco;
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

public class PainelAnalisarRiscos extends Panel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PlanoRisco planoRisco;
	private CtrlPlanoRisco ctrlPlanoRisco;

	
	public PainelAnalisarRiscos(PlanoRisco planoRisco, CtrlPlanoRisco ctrlPlanoRisco) throws InterruptedException{
	
		this.planoRisco = planoRisco;
		this.ctrlPlanoRisco = ctrlPlanoRisco;
		
		this.setTitle("Analisar Riscos");
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
		
		/////////////////////////////////
		// Itens
		/////////////////////////////////
		
		final ListagemPerfilRisco listagemPerfilRisco = new ListagemPerfilRisco();
		
		// Recpere o obrjeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarPlanoRisco.class);
		
		listagemPerfilRisco.setObjetos(aplCadastrarPlanoRisco.recuperarPerfilRiscoPorPlanoRisco(planoRisco));
		listagemPerfilRisco.configurarComponentes();
		listagemPerfilRisco.setParent(vlayout);
		listagemPerfilRisco.preencherLista();
		listagemPerfilRisco.getListBox().setMultiple(false);
		
		Div div = new Div();
		div.setParent(vlayout);
		div.setAlign("right");
		Button buttonAnalisar = new Button("Analisar");
		buttonAnalisar.setParent(div);
		buttonAnalisar.setWidth("100px");
		buttonAnalisar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (listagemPerfilRisco.getSelecionado() != null) {
					getCtrlPlanoRisco().exibirFormularioAvaliacaoRisco(listagemPerfilRisco.getSelecionado(),listagemPerfilRisco);
				} else {
					Messagebox.show("Selecione um item.");
				}
			}
		});
		
		Button buttonExcluir = new Button("Excluir");
		buttonExcluir.setParent(div);
		buttonExcluir.setWidth("100px");
		buttonExcluir.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(listagemPerfilRisco.getSelecionado() != null) {
					excluirRiscosIdentificados(listagemPerfilRisco, getPlanoRisco());
				}else{
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

	/**
	 * Exclui os riscos identificados no plano de risco.
	 * @param listagemPerfilRisco
	 * @param planoRisco
	 * @throws InterruptedException
	 */
	public void excluirRiscosIdentificados(ListagemPerfilRisco listagemPerfilRisco, PlanoRisco planoRisco) throws InterruptedException {
	
		// Recupera o objeto aplCadastrarAvalicaoRisco.
		AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarAvalicaoRisco.class);
		
		PerfilRisco perfilRiscoLocal = listagemPerfilRisco.getSelecionado();
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRiscoLocal);
		
		if(avaliacaoRisco == null) {
			Messagebox.show("Risco não avaliado.");
		}else {
			if(avaliacaoRisco.isEhPrioritario() == true) {
				Messagebox.show("Os Riscos Priorizados deve ser excluido primeiro pra poder excluir essa avaliação.");
			}else {
				// Apenas exclui a avaliação risco.
				try {
					aplCadastrarAvalicaoRisco.excluir(avaliacaoRisco);
				} catch (NucleoRegraNegocioExcecao e1) {
					Messagebox.show("Erro ao excluir. " + e1.getMessage());
				}
				
				// Atualiza a tela de analisar risco.
				listagemPerfilRisco.atualizar(planoRisco);
			
				try {
					Messagebox.show("Riscos identificados excluidos com sucesso.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
