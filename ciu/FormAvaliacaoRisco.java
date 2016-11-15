package ode.gerenciaRiscos.ciu;

import java.math.BigDecimal;
import java.util.Date;

import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaCRUD.ciu.GridDados;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.Consequencia;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cgt.AplCadastrarAvalicaoRisco;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.api.Decimalbox;

import com.ibm.icu.text.DateFormat;

public class FormAvaliacaoRisco extends Vlayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CtrlPlanoRisco ctrlPlanoRisco;
	
	private ListagemConsequencia listagemConsequencia;
	
	private ListagemPerfilRisco listagemPerfilRisco;
	
	private FormConsequencia formConsequencia;
	
	int probabilidadeZero,probabilidadeVinte;
	
	int probabilidadeVinteUm, probabilidadeQuarenta;
	
	int probabilidadeQuarentaUm, probabilidadeSessenta;
	
	int probabilidadeSessentaUm , probabilidadeOitenta;
	
	int probabilidadeOitentaUm , probabilidadeCem;
	 
	 
	BigDecimal maxprobabilidadeVinte = java.math.BigDecimal.valueOf(20);
	BigDecimal minprobabilidadeZero = BigDecimal.valueOf(0);
	
	BigDecimal maxprobabilidadeQuarenta = BigDecimal.valueOf(40);
	BigDecimal minprobabilidadeVinteUm = BigDecimal.valueOf(21);
	
	BigDecimal maxprobabilidadeSessenta = BigDecimal.valueOf(60);
	BigDecimal minprobabilidadeQuarentaUm = BigDecimal.valueOf(41);
	
	BigDecimal maxprobabilidadeOitenta = BigDecimal.valueOf(80);
	BigDecimal minprobabilidadeSessentaUm = BigDecimal.valueOf(61);
	
	BigDecimal maxprobabilidadeCem = BigDecimal.valueOf(100);
	BigDecimal minprobabilidadeOitentaUm = BigDecimal.valueOf(81);
	
	int impactoZero, impactoDois;
	
	int impactoTres, impactoCinco;
	
	int impactoSeis, impactoSete;
	
	int impactoOito, impactoDez;
	
	BigDecimal maxImpactoDois = BigDecimal.valueOf(2);
	BigDecimal minImpactoZero = BigDecimal.valueOf(0);
	
	BigDecimal maxImpactoCinco = BigDecimal.valueOf(5);
	BigDecimal minImpactoTres = BigDecimal.valueOf(3);
	
	BigDecimal maxImpactoSete = BigDecimal.valueOf(7);
	BigDecimal minImpactoSeis = BigDecimal.valueOf(6);
	
	BigDecimal maxImpactoDez = BigDecimal.valueOf(10);
	BigDecimal minImpactoOito = BigDecimal.valueOf(8);


	private AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco;
	
	private PerfilRisco perfilRisco;
	
	private AvaliacaoRisco avaliacaoRisco;

	public FormAvaliacaoRisco(PerfilRisco perfilRisco, AvaliacaoRisco avaliacaoRisco, ListagemPerfilRisco listagemPerfilRisco
			, CtrlPlanoRisco ctrlPlanoRisco) {
		super();

		this.avaliacaoRisco = avaliacaoRisco;
		this.listagemPerfilRisco = listagemPerfilRisco;
		this.ctrlPlanoRisco = ctrlPlanoRisco;
		this.perfilRisco = perfilRisco;

		avaliacaoRisco.setData(new Date());
		
		// Layout vertical.
		Vbox vbox = new Vbox();
		vbox.setParent(this);

		// Grupo de tabs.
		Tabbox tabbox = new Tabbox();
		tabbox.setParent(vbox);

		// Lista de nome de tabs.
		Tabs tabs = new Tabs();
		tabs.setParent(tabbox);

		Tab tabGeral = new Tab("Geral");
		tabGeral.setParent(tabs);

		Tab tabConsequencias = new Tab("Consequências");
		tabConsequencias.setParent(tabs);

		// Lista de tabs.
		Tabpanels tabpanels = new Tabpanels();
		tabpanels.setParent(tabbox);

		// /////////////////
		// Tabpanel geral.
		// /////////////////

		Tabpanel tabpanelGeral = new Tabpanel();
		tabpanelGeral.setParent(tabpanels);

		// Atribui o conteudo a tab.
		GridDados gridGeral = new GridDados();
				
		Label labelRisco = new Label(perfilRisco.getKRisco().getNome());
		gridGeral.adicionarLinhaObrigatoria("Risco", labelRisco);
		gridGeral.setParent(vbox);

		Label labelDataAnalise = new Label(DateFormat.getInstance().format(avaliacaoRisco.getData()));
		gridGeral.adicionarLinhaObrigatoria("Data de Análise", labelDataAnalise);
		gridGeral.setParent(tabpanelGeral);

		Radiogroup radiogroupSimNao = new Radiogroup();

		final Radio radioSim = new Radio("Sim");
		radioSim.setChecked(false);

		final Radio radioNao = new Radio("Não");
		radioNao.setChecked(true);

		radioSim.setParent(radiogroupSimNao);
		radioNao.setParent(radiogroupSimNao);

		gridGeral.adicionarLinhaObrigatoria("Ocorreu", radiogroupSimNao);
		gridGeral.setParent(tabpanelGeral);

		Vbox vboxprobabilidade = new Vbox();
		Hbox hboxdivisao = new Hbox();

		String descricao = "Uma probabilidade de 0% a 100% deve ser informada."
				+ "Probabilidade de 0% a 20% é classificada como Improvável."
				+ "Probabilidade de 21% a 40% é classificada como Baixa."
				+ "Probabilidade de 41% a 60% é classificada como Média."
				+ "Probabilidade de 61% a 80% é classificada como Alta."
				+ "Probabilidade de 81% a 100% é classificada como Muito Alta.";

		Label labelProbabilidade = new Label(descricao);
		Label labelPercentagem = new Label("%");

		///////////////////////
		// Probabilidade.
		//////////////////////
		
		final Decimalbox decimalboxProbabilidade = new org.zkoss.zul.Decimalbox();
		decimalboxProbabilidade.setHeight("20px");
		decimalboxProbabilidade.setWidth("400");
		
		decimalboxProbabilidade.setParent(hboxdivisao);
		labelPercentagem.setParent(hboxdivisao);

		labelProbabilidade.setParent(vboxprobabilidade);
		hboxdivisao.setParent(vboxprobabilidade);
		
		gridGeral.adicionarLinhaObrigatoria("Probabilidade", vboxprobabilidade);
		gridGeral.setParent(tabpanelGeral);
		
		final Label labelClassificacaoDeProbabilidade = new Label("Baixa");
		gridGeral.adicionarLinhaObrigatoria("Classificação de Probabilidade",
				labelClassificacaoDeProbabilidade);
		gridGeral.setParent(tabpanelGeral);
			
		decimalboxProbabilidade.addEventListener("onBlur", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String classificacao = "Baixa";
				if(decimalboxProbabilidade.getValue() != null) {
					probabilidadeZero = decimalboxProbabilidade.getValue().compareTo(minprobabilidadeZero);
					probabilidadeVinte = decimalboxProbabilidade.getValue().compareTo(maxprobabilidadeVinte);
					
					probabilidadeVinteUm = decimalboxProbabilidade.getValue().compareTo(minprobabilidadeVinteUm);
					probabilidadeQuarenta = decimalboxProbabilidade.getValue().compareTo(maxprobabilidadeQuarenta);
					
					probabilidadeQuarentaUm = decimalboxProbabilidade.getValue().compareTo(minprobabilidadeQuarentaUm);
					probabilidadeSessenta = decimalboxProbabilidade.getValue().compareTo(maxprobabilidadeSessenta);
					
					probabilidadeSessentaUm = decimalboxProbabilidade.getValue().compareTo(minprobabilidadeSessentaUm);
					probabilidadeOitenta = decimalboxProbabilidade.getValue().compareTo(maxprobabilidadeOitenta);
					
					probabilidadeOitentaUm = decimalboxProbabilidade.getValue().compareTo(minprobabilidadeOitentaUm);
					probabilidadeCem = decimalboxProbabilidade.getValue().compareTo(maxprobabilidadeCem);
					
					if (probabilidadeZero == -1 || probabilidadeZero == 0 || probabilidadeVinte == -1 || probabilidadeVinte == 0) {
						classificacao = "Improvável";
					} else {
						if (probabilidadeVinteUm == -1 || probabilidadeVinteUm == 0 || probabilidadeQuarenta == -1 || probabilidadeQuarenta == 0) {
							classificacao = "Baixa";
						}else {
							if (probabilidadeQuarentaUm == -1 || probabilidadeQuarentaUm == 0 || probabilidadeSessenta == -1 || probabilidadeSessenta == 0) {
								classificacao = "Média";
							}else {
								if (probabilidadeSessentaUm == -1 || probabilidadeSessentaUm == 0 || probabilidadeOitenta == -1 || probabilidadeOitenta == 0) {
									classificacao = "Alta";
								}else {
									if (probabilidadeOitentaUm == -1 || probabilidadeOitentaUm == 0 || probabilidadeCem == -1 || probabilidadeCem == 0) {
										classificacao = "Muito Alta";
									}
								}
							}
						}
					}
					labelClassificacaoDeProbabilidade.setValue(classificacao);
				}else {
					Messagebox.show("O Campo probabilidade deve ser preenchido.");
				}
			}
		});
		

		////////////////
		// Impacto.
		///////////////
		
		final Decimalbox decimalboxImpacto = new org.zkoss.zul.Decimalbox();
		decimalboxImpacto.setHeight("20px");
		decimalboxImpacto.setWidth("400");

		String str_impacto = "Um Valor de impacto de 0 a 10 deve ser informada."
				+ "Impacto de 0 a 2 é classificado como Negligenciável."
				+ "Impacto de 3 a 5 é classificado como Superficial."
				+ "Impacto de 6 a 7 é classificado como Crítico."
				+ "Probabilidade de 8  a 10 é classificado como Catastrófico.";

		Vbox vboxImpacto = new Vbox();

		Label labelImpacto = new Label(str_impacto);

		labelImpacto.setParent(vboxImpacto);
		decimalboxImpacto.setParent(vboxImpacto);

		labelImpacto.setHeight("150px");
		labelImpacto.setWidth("450");
		gridGeral.adicionarLinhaObrigatoria("Impacto", vboxImpacto);
		gridGeral.setParent(tabpanelGeral);
		
		final Label labelClassificacaoDeImpacto = new Label("Catastrófico");
		gridGeral.adicionarLinhaObrigatoria("Classificação de Impacto",
				labelClassificacaoDeImpacto);
		gridGeral.setParent(tabpanelGeral);
		

		decimalboxImpacto.addEventListener("onBlur", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String classificacao = "Catastrófico";
				if(decimalboxImpacto.getValue() != null) {
					impactoZero = decimalboxImpacto.getValue().compareTo(minImpactoZero);
					impactoDois = decimalboxImpacto.getValue().compareTo(maxImpactoDois);
					
					impactoTres = decimalboxImpacto.getValue().compareTo(minImpactoTres);
					impactoCinco = decimalboxImpacto.getValue().compareTo(maxImpactoCinco);
					
					impactoSeis = decimalboxImpacto.getValue().compareTo(minImpactoSeis);
					impactoSete = decimalboxImpacto.getValue().compareTo(maxImpactoSete);
					
					impactoOito = decimalboxImpacto.getValue().compareTo(minImpactoOito);
					impactoDez = decimalboxImpacto.getValue().compareTo(maxImpactoDez);
	
	
					if (impactoZero == -1 || impactoZero == 0 || impactoDois == -1 || impactoDois == 0) {
						classificacao = "Negligenciável";
					} else {
						if (impactoTres == -1 || impactoTres == 0 || impactoCinco == -1 || impactoCinco == 0) {
							classificacao = "Superficial";
						}else {
							if (impactoSeis == -1 || impactoSeis == 0 || impactoSete == -1 || impactoSete == 0) {
								classificacao = "Crítico";
							}else {
								if (impactoOito == -1 || impactoOito == 0 || impactoDez == -1 || impactoDez == 0) {
									classificacao = "Catastrófico";
								}
							}
						}
					}
					labelClassificacaoDeImpacto.setValue(classificacao);
				}else {
					Messagebox.show("O Campo Impacto deve ser preenchido.");
				}
			}
		});

		// /////////////////////////
		// Tabpanel Consequencias.
		// ////////////////////////

		Tabpanel tabpanelConsequencia = new Tabpanel();
		tabpanelConsequencia.setParent(tabpanels);

		Hbox hboxConsequencia = new Hbox();
		hboxConsequencia.setParent(tabpanelConsequencia);
	
		listagemConsequencia = new ListagemConsequencia();
		listagemConsequencia.configurarComponentes();
		listagemConsequencia.getListBox().setMultiple(false);
		listagemConsequencia.setParent(hboxConsequencia);

		Vbox vboxConsequencia = new Vbox();

		Div div = new Div();
		div.setParent(vbox);
		div.setWidth("100%");
		div.setAlign("right");

		Button buttonIncluir = new Button("Incluir");
		buttonIncluir.setParent(div);
		buttonIncluir.setWidth("100px");
		buttonIncluir.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				FormAvaliacaoRisco.this.ctrlPlanoRisco.exibirFormularioConsequencia(null,FormConsequencia.MODO_INSERCAO);				
			}
		});

		Button buttonEditar = new Button("Editar");
		buttonEditar.setParent(div);
		buttonEditar.setWidth("100px");
		buttonEditar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(listagemConsequencia.getSelecionado() != null) {
					FormAvaliacaoRisco.this.ctrlPlanoRisco.exibirFormularioConsequencia(listagemConsequencia.getSelecionado(),FormConsequencia.MODO_EDICAO);
				}else {
					Messagebox.show("Seleciona um Item.");
				}
			}
		});

		Button buttonRemover = new Button("Remover");
		buttonRemover.setParent(div);
		buttonRemover.setWidth("100px");
		buttonRemover.addEventListener("onClick", new EventListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(listagemConsequencia.getSelecionado() != null) {
					FormAvaliacaoRisco.this.ctrlPlanoRisco.excluirConsequencia(listagemConsequencia.getSelecionado(),formConsequencia.MODO_REMOCAO);
				}else {
					Messagebox.show(" Seleciona um Item.");
				}
			}
		});

		buttonIncluir.setParent(vboxConsequencia);
		buttonEditar.setParent(vboxConsequencia);
		buttonRemover.setParent(vboxConsequencia);
		vboxConsequencia.setParent(hboxConsequencia);
		
		Button button = new Button("Salvar");
		button.setParent(div);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				
				// Verifica se avaliacao ocorreu, e a conseuqencia nao for cadastrado exibe a mensagem de erro.
				if(radioSim.isChecked() && listagemConsequencia.recuperarConsequenciasListbox().isEmpty())  {
					Messagebox.show("As consequências devem ser informadas caso o risco tenha ocorrido.");
				} else {
					salvarAvalicaoRisco(decimalboxProbabilidade, decimalboxImpacto,
								radioSim);
				}
			}
		});

		button = new Button("Cancelar");
		button.setParent(div);
		button.setWidth("100px");
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					fecharJanelaAvaliacaoRisco();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		
		//////////////////////////////
		// preenche as consequencias.
		/////////////////////////////
		
		if (avaliacaoRisco.isPersistente()) {
			listagemConsequencia.setObjetos(avaliacaoRisco.getConsequencias());
			listagemConsequencia.preencherLista();
		}
		
		/////////////////////////////////
		// preenche dados da avaliacao.
		///////////////////////////////
		
		if (avaliacaoRisco.isPersistente()) {
			if (avaliacaoRisco.isOcorreu()) {
				radioSim.setChecked(true);
		} else {
			radioNao.setChecked(true); 
		}
			decimalboxProbabilidade.setValue(avaliacaoRisco.getProbabilidade());
			decimalboxImpacto.setValue(avaliacaoRisco.getImpacto());
		}
	}
	
	
	/**
	 * Fecha janela avaliacao de risco.
	 */
	public void fecharJanelaAvaliacaoRisco() {
		ctrlPlanoRisco.fecharJanelaAvaliacaoRisco();
	}
	
	
	/**
	 * Adiciona consequencia.
	 * @param consequencia.
	 */
	public void adicionarConsequencia(Consequencia consequencia, String modo) {
			listagemConsequencia.adicionarConsequencia(consequencia, modo);	
	}
	
	/**
	 * Exclue consequencia.
	 * @param consequencia
	 * @param modo
	 */
	public void excluirConsequencia(Consequencia consequencia,String modo) {
		listagemConsequencia.excluirConseuqencia(consequencia,modo);
	}

	/**
	 * Salva avaliacao risco de um perfil risco.
	 * 
	 * @param avaliacaoRisco
	 *            Objeto avaliacao risco.
	 * @param decimalboxProbabilidade
	 *            Probabilidade capturada da interface grafica.
	 * @param decimalboxImpacto
	 * @param radioSim
	 * @throws InterruptedException 
	 */
	public void salvarAvalicaoRisco(Decimalbox decimalboxProbabilidade, Decimalbox decimalboxImpacto,
			Radio radioSim) throws InterruptedException {

		// Recupera o objeto aplCadastrarAvalicaoRisco.
		aplCadastrarAvalicaoRisco = SpringUtil.getApplicationContext().getBean(
				AplCadastrarAvalicaoRisco.class);

		avaliacaoRisco.setProbabilidade(decimalboxProbabilidade.getValue());
		avaliacaoRisco.setImpacto(decimalboxImpacto.getValue());
		avaliacaoRisco.setOcorreu(radioSim.isChecked() ? true : false);
		avaliacaoRisco.getConsequencias().clear();
		avaliacaoRisco.setConsequencias(listagemConsequencia.recuperarConsequenciasListbox());
						
		int probabilidadeMinimo , probabilidadeMaximo, impactoMinimo, impactoMaximo;
		
		BigDecimal maxprobabilidade = BigDecimal.valueOf(100);
		BigDecimal minprobabilidade = BigDecimal.valueOf(0);
		
		probabilidadeMinimo = decimalboxProbabilidade.getValue().compareTo(minprobabilidade);
		probabilidadeMaximo = decimalboxProbabilidade.getValue().compareTo(maxprobabilidade);
		
		BigDecimal maxImpacto = BigDecimal.valueOf(10);
		BigDecimal minImpacto = BigDecimal.valueOf(0);
		
		impactoMaximo = decimalboxImpacto.getValue().compareTo(maxImpacto);
		impactoMinimo = decimalboxImpacto.getValue().compareTo(minImpacto);
	
		if(probabilidadeMaximo == 1 && probabilidadeMinimo == 1) {
			try {
				Messagebox.show("O valor da probabilidade não deve estar fora do intervalo de 0 à 100.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}else {
			if(impactoMaximo == 1 && impactoMinimo == 1) {
				try {
					Messagebox.show("O valor do impacto não deve estar fora do intervalo de 0 à 10.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}else {

				try {
					aplCadastrarAvalicaoRisco.salvar(avaliacaoRisco);
				} catch (NucleoRegraNegocioExcecao e) {
					Messagebox.show("Erro ao salvar. " + e.getMessage());
					return;
				}
				Messagebox.show("Avaliação do Risco salva com sucesso.");
			}
		}
		fecharJanelaAvaliacaoRisco();
		listagemPerfilRisco.atualizar(perfilRisco.getPlanoRisco());
	}		

	/**
	 * @return the ctrlPlanoRisco.
	 */
	public CtrlPlanoRisco getCtrlPlanoRisco() {
		return ctrlPlanoRisco;
	}

	/**
	 * @param ctrlPlanoRisco
	 *            the ctrlPlanoRisco to set
	 */
	public void setCtrlPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco) {
		this.ctrlPlanoRisco = ctrlPlanoRisco;
	}
}
