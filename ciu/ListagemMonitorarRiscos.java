package ode.gerenciaRiscos.ciu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;

public class ListagemMonitorarRiscos extends ListagemSimples<PerfilRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {
		List<NucleoListHeader> coluna = new ArrayList<NucleoListHeader>();
		coluna.add(new NucleoListHeader("Riscos Identificados", "", "30%"));
		coluna.add(new NucleoListHeader("GE", "", "5%"));
		coluna.add(new NucleoListHeader("Estratégia", "", "15%"));
		coluna.add(new NucleoListHeader("Ações Planejada", "", "17%"));
		coluna.add(new NucleoListHeader("Ocorreu?", "", "10%"));
		coluna.add(new NucleoListHeader("limiar", "", "10%"));
		coluna.add(new NucleoListHeader("Disparar Ações?", "", "13%"));
		return coluna;
	}

	@Override
	protected String[] recuperarDadosObjeto(PerfilRisco objeto) {
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(objeto);
		
		java.math.BigDecimal numero = java.math.BigDecimal.valueOf(100);
		java.math.BigDecimal  grauDeExposicao = (avaliacaoRisco.getProbabilidade().multiply(avaliacaoRisco.getImpacto())).divide(numero);
		
		//Pega a estrategia de tratamento do banco.
		String estrategia = avaliacaoRisco.getEstrategiaTratamento();
		String planejada = "";
		String ocorreu, str_limiar, disparAçoes = "";
		
		// Pega o limiar do banco.
		BigDecimal limiar = avaliacaoRisco.getLimiar();
		
		if(limiar != null) {
			str_limiar = limiar.toString();
			disparAçoes = "Sim";
		}else {
			str_limiar = "Não Definido";
			disparAçoes = "Não";
		}
		
		// Verifica se acoes ja foi planejada.
		if(avaliacaoRisco.getAcoesContingencia().size() != 0) {
			planejada = "Sim";
		}else {
			planejada = "Não";
		}
		
		// Pega se ha ou nao ocorrencia de avaliação Risco no banco.
		boolean isocorreu = avaliacaoRisco.isOcorreu();
		ocorreu = String.valueOf(isocorreu);

		return new String[] {objeto.getKRisco().getNome(), grauDeExposicao.toString(), estrategia,planejada,ocorreu,str_limiar,disparAçoes};
	}
}
