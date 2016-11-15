package ode.gerenciaRiscos.ciu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import ode._infraestruturaCRUD.ciu.ListagemSimples;
import ode._infraestruturaCRUD.ciu.NucleoListHeader;
import ode.gerenciaRiscos.cdp.Consequencia;

public class ListagemConsequencia extends ListagemSimples<Consequencia> {

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
	protected String[] recuperarDadosObjeto(Consequencia objeto) {
		return new String[] { objeto.getDescricao(), objeto.getObservacao() };
	}
	
	/**
	 * Adiciona a Consequencia.
	 * @param consequencia
	 */
	public void adicionarConsequencia(Consequencia consequencia, String modo) {
		
		// Instancia um listitem.
		Listitem lt = new Listitem();
		lt.setValue(consequencia);

		// Recupera os dados do objeto a serem exibidos na lista.
		String[] dadosLista = this.recuperarDadosObjeto(consequencia);

		// Preenche as celulas da listitem.
		for (int j = 0; j < dadosLista.length; j++) {
			Listcell listCell = new Listcell(dadosLista[j]);
			if (estilos != null && !estilos[j].equals("")) {
				listCell.setStyle(estilos[j]);
			}
			lt.appendChild(listCell);
		}
		if (modo.compareToIgnoreCase(FormConsequencia.MODO_EDICAO) == 0) {
			
			// Pega o item selecioando.
			Listitem listitem = listBox.getSelectedItem();
			
			// Pega as celulas do item.
			@SuppressWarnings("unchecked")
			List<AbstractComponent> celulas = listitem.getChildren();
			
			// Atualiza as celulas do item.
			Listcell listcell = (Listcell)celulas.get(0);
			listcell.setLabel(consequencia.getDescricao());
			listcell = (Listcell)celulas.get(1);
			listcell.setLabel(consequencia.getObservacao());
			
		}else {
			
			// Insere o listitem no listbox.
			this.getListBox().appendChild(lt);
			}
	}
	
	
	/**
	 * Exclue conseuqencia.
	 * @param consequencia
	 * @param modo
	 */
	public void excluirConseuqencia(Consequencia consequencia,String modo) {
		
		// Instancia um listitem.
		Listitem lt = new Listitem();
		lt.setValue(consequencia);

		// Recupera os dados do objeto a serem exibidos na lista.
		String[] dadosLista = this.recuperarDadosObjeto(consequencia);

		// Preenche as celulas de listitem.
		for (int j = 0; j < dadosLista.length; j++) {
			Listcell listCell = new Listcell(dadosLista[j]);
			if (estilos != null && !estilos[j].equals("")) {
				listCell.setStyle(estilos[j]);
			}
		}
		
		if (modo.compareToIgnoreCase(FormConsequencia.MODO_REMOCAO) == 0) {
			
			// Pega o item selecioando.
			Listitem listitem = listBox.getSelectedItem();
			
			// Apenas remove o item selecionado.
			this.getListBox().getItems().remove(listitem);
		}
	}
	
	
	/**
	 * Recupera as consequencias do listbox.
	 * @return Lista de consequencias recuperadas.
	 */
	public Set<Consequencia> recuperarConsequenciasListbox(){
		
		Set<Consequencia> consequencias = new HashSet<Consequencia>();
		
		for (Object item : listBox.getItems()) {
			Listitem listitem = (Listitem) item;
			consequencias.add((Consequencia)listitem.getValue());
		}
		return consequencias;
	}
}
