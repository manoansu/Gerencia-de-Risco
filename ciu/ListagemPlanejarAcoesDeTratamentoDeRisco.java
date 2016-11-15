package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;

public class ListagemPlanejarAcoesDeTratamentoDeRisco extends ListagemSimples<PerfilRisco>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {
		List<NucleoListHeader> coluna = new ArrayList<NucleoListHeader>();
		coluna.add(new NucleoListHeader("Risco", "", "50%"));
		coluna.add(new NucleoListHeader("Estratégia", "", "25%"));
		coluna.add(new NucleoListHeader("Planejada", "", "25%"));
		return coluna;
	}

	@Override
	protected String[] recuperarDadosObjeto(PerfilRisco objeto) {
		
		// Recupera o objeto de avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(objeto);
		
		// Pega a estrategia de tratamento do banco.
		String estrategia = avaliacaoRisco.getEstrategiaTratamento();
		String planejada = "";
		
		// Verifica se acoes ja foi planejada.
		if(avaliacaoRisco.getAcoesContingencia().size() != 0) {
			planejada = "Sim";
		}else {
			planejada = "Não";
		}
		return new String[] {objeto.getKRisco().getNome(),estrategia,planejada};
	}
	
	/**
	 *Atualiza o plano de Risco da  tela.
	 * @param planoRisco
	 */
	protected void atualizar(PlanoRisco planoRisco) {
		PerfilRiscoDAO perfilRiscoDAO = SpringUtil.getApplicationContext().getBean(PerfilRiscoDAO.class);
		this.setObjetos(perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco));
		this.preencherLista();
	}
}
