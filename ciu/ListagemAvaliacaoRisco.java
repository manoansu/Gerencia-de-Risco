package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;

public class ListagemAvaliacaoRisco extends ListagemSimples<AvaliacaoRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {
		List<NucleoListHeader> coluna = new ArrayList<NucleoListHeader>();
		coluna.add(new NucleoListHeader("Descrição", "", "50%"));
		coluna.add(new NucleoListHeader("Observação", "", "50%"));

		return coluna;
	}

	@Override
	protected String[] recuperarDadosObjeto(AvaliacaoRisco objeto) {
 
		return null;
	}
	
}
