package ode.gerenciaRiscos.ciu;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.PainelCRUD;
import ode.gerenciaRiscos.cdp.PlanoRisco;

public class PainelCRUDPlanoRisco extends PainelCRUD<PlanoRisco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ListagemSimples<PlanoRisco> definirListagem() {
		return new ListagemPlanoRisco();
	}	
}
