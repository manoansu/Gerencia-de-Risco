package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;

import org.zkoss.zkplus.spring.SpringUtil;

public class ListagemSelecionarEstategiaDeTaratamentoDeRiscos extends ListagemSimples<PerfilRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {
		List<NucleoListHeader> coluna = new ArrayList<NucleoListHeader>();
		coluna.add(new NucleoListHeader("Risco", "", "50%"));
		coluna.add(new NucleoListHeader("Estratégia Selecionada", "", "50%"));
		return coluna;
	}

	@Override
	protected String[] recuperarDadosObjeto(PerfilRisco objeto) {
		
		String estrategia ;
		
		// Recupera o objeto de avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(objeto);
		
		if( avaliacaoRisco != null) {
			estrategia = avaliacaoRisco.getEstrategiaTratamento();
		}else {
			estrategia = "";
		}
		return new String[] {objeto.getKRisco().getNome(),estrategia};
	}
	
	
	/**
	 * Atualiza avaliação Risco na tela quando eh Priorizado..
	 * @param planoRisco
	 */
	protected void atualizar(PlanoRisco planoRisco) {
		
		// Recupera o objeto de perfilRiscoDAO.
		PerfilRiscoDAO perfilRiscoDAO = SpringUtil.getApplicationContext().getBean(PerfilRiscoDAO.class);
		List<PerfilRisco> lista = (List<PerfilRisco>) perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco); 
		List<PerfilRisco> listaAvaliacaoRisco = new ArrayList<PerfilRisco>();
		
		for(PerfilRisco perfilRiscoLocal : lista) {
			
			// Recupera o objeto de avaliacaoRiscoDAO.
			AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
			AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRiscoLocal);
			
			if(avaliacaoRisco != null) {
				if(avaliacaoRisco.isEhPrioritario() == true) {
					listaAvaliacaoRisco.add(perfilRiscoLocal);
				}	
			}
		}
		this.setObjetos(listaAvaliacaoRisco);
		this.preencherLista();
	}
}
