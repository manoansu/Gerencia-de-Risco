package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;

public class ListagemPriorizarRiscos extends ListagemSimples<PerfilRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {
		List<NucleoListHeader> coluna = new ArrayList<NucleoListHeader>();
		coluna.add(new NucleoListHeader("Risco", "", "30%"));
		coluna.add(new NucleoListHeader("Probabilidade(%)", "", "25%"));
		coluna.add(new NucleoListHeader("Impacto", "", "20%"));
		coluna.add(new NucleoListHeader("Grau de Exposição (G.E)", "", "25%"));
		return coluna;
	}

	@Override
	protected String[] recuperarDadosObjeto(PerfilRisco objeto) {
		
		//Recupera o objeto de avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(objeto);
		
			java.math.BigDecimal numero = java.math.BigDecimal.valueOf(100);
			
			// Calcula o grau de exposicao multiplicando probabilidade com impacto dividindo por 100.
			java.math.BigDecimal  grauDeExposicao = (avaliacaoRisco.getProbabilidade().multiply(avaliacaoRisco.getImpacto())).divide(numero);
		
		return new String[] {objeto.getKRisco().getNome(),avaliacaoRisco.getProbabilidade().toString(),avaliacaoRisco.getImpacto().toString(),grauDeExposicao.toString()};
	}
}
