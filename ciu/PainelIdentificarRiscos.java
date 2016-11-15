package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ode._infraestruturaBase.util.NucleoContexto;
import ode.conhecimento.risco.cdp.KRisco;
import ode.conhecimento.risco.cgd.KRiscoDAO;
import ode.conhecimento.risco.ciu.ListagemKRisco;
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

public class PainelIdentificarRiscos extends Panel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PlanoRisco planoRisco;
	
	ListagemKRisco listagemKRisco = new ListagemKRisco();
	
	
	public PainelIdentificarRiscos(PlanoRisco planoRisco)  {
		
		this.planoRisco = planoRisco;
	
		this.setTitle("Identificar Riscos");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setBorder("normal");
		this.setWidth("100%");
		this.setHeight("100%");
		
		Panelchildren painelchildrenIdentificarRiscos = new Panelchildren();
		painelchildrenIdentificarRiscos.setParent(this);
		
		Vlayout vlayout = new Vlayout();
		vlayout.setHeight("100%");
		vlayout.setParent(painelchildrenIdentificarRiscos);
		
		/////////////////////////////////
		// Itens
		/////////////////////////////////
		
		listagemKRisco = new ListagemKRisco();
		
		// Recupera os kriscos do banco
		KRiscoDAO kRiscoDAO = SpringUtil.getApplicationContext().getBean(KRiscoDAO.class);
		List<KRisco> lista = (List<KRisco>) kRiscoDAO.recuperarTodos();
		
		// Preenche o listbox com os kriscos do banco.
		listagemKRisco.setObjetos(lista);
		listagemKRisco.configurarComponentes();
		listagemKRisco.setParent(vlayout);
		listagemKRisco.preencherLista();
		
		final Collection<PerfilRisco> perfisRiscoDoBanco;
		
		// Recupera o objeto perfilRiscoDAO.
		PerfilRiscoDAO perfilRiscoDAO = SpringUtil.getApplicationContext().getBean(PerfilRiscoDAO.class);
		
		perfisRiscoDoBanco = perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco);
		
		// Cria uma lista Krisco pra guardar os Kriscos recuperado no banco.
		final List<KRisco> kRiscosSelecionados = new ArrayList<KRisco>();
		final List<KRisco> kRiscosSelecionado = new ArrayList<KRisco>();
		final List<PerfilRisco> listaPerfilRiscos = new ArrayList<PerfilRisco>();
		
		
		for (PerfilRisco perfilRisco : perfisRiscoDoBanco) {
			kRiscosSelecionados.add(perfilRisco.getKRisco());
		}
		
		
		for(Object objeto : listagemKRisco.getListBox().getItems()) {
		
			// Pega um listitem do listbox.
			Listitem listitem = (Listitem) objeto;
			
			// Pega o krisco do listitem.
			KRisco kRiscoItem = (KRisco) listitem.getValue();
			
			
			if(kRiscosSelecionados.contains(kRiscoItem)) {
				
				// Se for, marca o listitem.
				listitem.setSelected(true);
				kRiscosSelecionado.add(kRiscoItem);
			}
		}
		
		Div div = new Div();
		div.setParent(vlayout);
		div.setWidth("100%");
		div.setAlign("right");
		Button buttonSalvar = new Button("Salvar");
		buttonSalvar.setParent(div);
		buttonSalvar.setWidth("100px");
		buttonSalvar.addEventListener("onClick", new EventListener() {
		@Override
		public void onEvent(Event arg0) throws Exception {
		
			boolean ehsalvo = false;
			if (perfisRiscoDoBanco.isEmpty()) {
				salvarRiscosIdentificados(listaPerfilRiscos,getPlanoRisco());
			}else {
				for (PerfilRisco perfilRisco : perfisRiscoDoBanco) {
					
					// Recupera o objeto avaliacaoRiscoDAO.
					AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
					AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
					
						if(kRiscosSelecionado.contains(perfilRisco.getKRisco().getNome()) || 
								(avaliacaoRisco != null && listagemKRisco.getListBox().isCheckmark()) == false) {
							Messagebox.show("Erro ao salvar plano de risco. remova avaliação risco primeiro.");
							ehsalvo = true;
							break;
						}else {
							if(kRiscosSelecionado.contains(perfilRisco.getKRisco().getNome()) && avaliacaoRisco != null) {
								Messagebox.show("Erro ao salvar plano de risco. remova avaliação risco primeiro.");
								ehsalvo = true;
								break;
							}else{
								if(kRiscosSelecionado.contains(perfilRisco.getKRisco().getNome()) || avaliacaoRisco == null) {
									listaPerfilRiscos.add(perfilRisco);
								}
							}
						}
				}
				if(!ehsalvo) {
					salvarRiscosIdentificados(listaPerfilRiscos,getPlanoRisco());
				}
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

	/**
	 * Salva os riscos identificados de um plano de risco.
	 * @param listaPerfirisco
	 * @param planoRisco
	 * @throws InterruptedException
	 */
	public void salvarRiscosIdentificados(List<PerfilRisco> listaPerfirisco, PlanoRisco planoRisco) throws InterruptedException{

		// Recupera os kriscos selecionado.
		List<KRisco> kRiscosSelecionados = listagemKRisco.recuperarSelecionados();

		int size = kRiscosSelecionados.size();

		//Recupera o objeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarPlanoRisco.class);
		if(listaPerfirisco.isEmpty()) {						
			for(KRisco kRisco : kRiscosSelecionados) {

				PerfilRisco perfilRisco = new PerfilRisco();
				perfilRisco.setKRisco(kRisco);
				perfilRisco.setProjeto(NucleoContexto.recuperarProjeto());
				perfilRisco.setPlanoRisco(planoRisco);

				// Salva perfil risco.
				aplCadastrarPlanoRisco.salvar(listagemKRisco, planoRisco, perfilRisco,size,kRiscosSelecionados);
			}
		}else {
			for(PerfilRisco peRisco: listaPerfirisco) {
				
				// Salva perfil risco.
				aplCadastrarPlanoRisco.salvar(listagemKRisco, planoRisco, peRisco,size,kRiscosSelecionados);
			}
		}

		try {
			Messagebox.show("Riscos identificados salvos com sucesso.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Atualiza painel identificar risco.
	 * @param kRiscosSelecionados
	 */
	public void atualizarPainelIdentRificarRisco(List<KRisco> kRiscosSelecionados) {
		

		for(Object objeto : listagemKRisco.getListBox().getItems()) {
		
			// Pega um listitem do listbox.
			Listitem listitem = (Listitem) objeto;
			
			// Pega o krisco do listitem.
			KRisco kRiscoItem = (KRisco) listitem.getValue();
			
			
			if(kRiscosSelecionados.contains(kRiscoItem)) {
				
				// Se for, marca o listitem.
				listitem.setSelected(true);
			}
		}
	}
}
