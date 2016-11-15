package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Vlayout;

public class PainelMonitorarRiscos extends Panel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PainelMonitorarRiscos(PlanoRisco planoRisco) throws InterruptedException{
	
		
		this.setTitle("Monitorar Riscos");
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
		
		//Recupera o objeto aplCadastrarPlanoRisco.
		AplCadastrarPlanoRisco aplCadastrarPlanoRisco = SpringUtil.getApplicationContext().getBean(AplCadastrarPlanoRisco.class);
		
		//Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		
		// Recupera os kriscos do banco
		List<PerfilRisco> lista = (List<PerfilRisco>) aplCadastrarPlanoRisco.recuperarPerfilRiscoPorPlanoRisco(planoRisco);
		final List<PerfilRisco> listaMonitorarRiscos = new ArrayList<PerfilRisco>();
		
		for(PerfilRisco perfilRisco : lista) {
			
			// Recupera o objeto de avaliacaoRisco.
			AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
			
			if(avaliacaoRisco != null) {
				perfilRisco.getKRisco().getNome();
				avaliacaoRisco.getEstrategiaTratamento();
				avaliacaoRisco.isOcorreu();
				avaliacaoRisco.getLimiar();
				listaMonitorarRiscos.add(perfilRisco);
			}
		}
		
		ListagemMonitorarRiscos listagemMonitorarRisco = new ListagemMonitorarRiscos();
		
		// preenche o listbox com os kriscos do banco.
		listagemMonitorarRisco.setObjetos(listaMonitorarRiscos);
		listagemMonitorarRisco.configurarComponentes();
		listagemMonitorarRisco.setParent(vlayout);
		listagemMonitorarRisco.preencherLista();
		listagemMonitorarRisco.getListBox().setCheckmark(false);
		}
}
