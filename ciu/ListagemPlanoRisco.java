package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.List;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.PlanoRisco;

public class ListagemPlanoRisco extends ListagemSimples<PlanoRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {
		List<NucleoListHeader> colunas = new ArrayList<NucleoListHeader>();
		colunas.add(new NucleoListHeader("Nome", "versao", "50%"));
		return colunas;
	}

	@Override
	protected String[] recuperarDadosObjeto(PlanoRisco objeto) {
		return new String[] { objeto.getVersao(),
				objeto.getProjeto().getNome()};
	}
}
