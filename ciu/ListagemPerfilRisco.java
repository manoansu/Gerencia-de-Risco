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

import com.ibm.icu.text.DateFormat;

public class ListagemPerfilRisco extends ListagemSimples<PerfilRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<NucleoListHeader> definirColunasTabela() {

		List<NucleoListHeader> colunas = new ArrayList<NucleoListHeader>();
		colunas.add(new NucleoListHeader("Risco", "", "50%"));
		colunas.add(new NucleoListHeader("Situação", "", "50%"));

		return colunas;
	}

	@Override
	protected String[] recuperarDadosObjeto(PerfilRisco objeto) {
		
		//Recupera o objeto de avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(objeto);
		
		//Pega a situacao de avaliacao, se for ou nao avaliado, e em que data.
		String situacao = avaliacaoRisco == null ? "Não avaliado" : "Avaliado em " + DateFormat.getInstance().format(avaliacaoRisco.getData());
		
		return new String[] { objeto.getKRisco().getNome(), situacao};

	}
	
	/**
	 * Atualiza listagem de um plano de risco na tela.
	 * @param planoRisco. 
	 */
	protected void atualizar(PlanoRisco planoRisco) {
		PerfilRiscoDAO perfilRiscoDAO = SpringUtil.getApplicationContext().getBean(PerfilRiscoDAO.class);
		this.setObjetos(perfilRiscoDAO.recuperarPerfisRiscoPorPlanoRisco(planoRisco));
		this.preencherLista();
	}
}