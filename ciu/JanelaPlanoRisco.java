package ode.gerenciaRiscos.ciu;

import java.util.Collection;

import ode._infraestruturaCRUD.ciu.JanelaSimples;
import ode.conhecimento.risco.ciu.ListagemKRisco;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;
import ode.gerenciaRiscos.cgt.AplCadastrarAvalicaoRisco;
import ode.gerenciaRiscos.cgt.AplCadastrarPlanoRisco;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.East;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;

public class JanelaPlanoRisco extends JanelaSimples {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Borderlayout borderlayout = new Borderlayout();


	// ---- paineis do setor leste e oeste do borderlayout ---- //


	PainelIdentificarRiscos panelIdentificarRiscos;
	
	PainelAcompanharAcoesDeTratatamentoDeRiscos panelAcompanharAcoesDeTratatamentoDeRiscos ;
	
	PainelAnalisarRiscos painelAnalisarRiscos;
	
	PainelMonitorarRiscos painelMonitorarRiscos ;
	
	PainelPlanejarAcoesDeTratatamentoDeRiscos painelPlanejarAcoesDeTratatamentoDeRiscos;
	
	PainelSelecionarEstrategiaDeTratatamentoDeRiscos painelSeleciponalrEstrategiaDeTratatamentoDeRiscos ;
	
	PainelPriorizarRiscos painelPriorizarRiscos ;
	
	Panel funcionalidades;
	
	Panel gerenciaConhecimento;
	
	PerfilRiscoDAO perfilRiscoDAO;

	CtrlPlanoRisco ctrlPlanoRisco;

	JanelaControleVersoes janelaControleVersoes;

	AplCadastrarPlanoRisco aplCadastrarPlanoRisco;

	AplCadastrarAvalicaoRisco aplCadastrarAvalicaoRisco;

	Collection<PerfilRisco> perfisRiscoDoBanco;

	PlanoRisco planoRisco = new PlanoRisco();

	Panel painelCentro;
	
	Panelchildren painelChildrenCentro;

	Center center;
	
	ListagemKRisco listagemKRisco = new ListagemKRisco();

	ListagemPriorizarRiscos listagemPriorizarRiscos = new ListagemPriorizarRiscos();

	ListagemSelecionarEstategiaDeTaratamentoDeRiscos listagemSelecionarEstrategiaDeTratamentoDeRisco = 
			new ListagemSelecionarEstategiaDeTaratamentoDeRiscos();

	ListagemPlanejarAcoesDeTratamentoDeRisco listagemPlanejarAcoesDeTratamentoDeRisco = 
			new ListagemPlanejarAcoesDeTratamentoDeRisco();

	ListagemMonitorarRiscos listagemMonitorarRisco = new ListagemMonitorarRiscos();

	ListagemPerfilRisco listagemPerfilRisco = new ListagemPerfilRisco();


	/**
	 * Cria east.
	 */
	public void criarEast(){
		
		East east = new East();
		Vbox vbox = new Vbox();

		east.setParent(borderlayout);
		vbox.setParent(east);

		east.setSize("290px");
		east.setFlex(true);
		east.setSplittable(true);
		east.setCollapsible(true);

		criaPainelGerenciaConhecimento();
		gerenciaConhecimento.setParent(vbox);
	}

	
	/**
	 * Cria center.
	 */
	public void criarCenter(){

		center = new Center();
		center.setParent(borderlayout);
		exibirPanelIdentificarRiscos();
	}

	
	/**
	 * Cria west.
	 */
	public void criarWest(){
		
		West west = new West();
		Vbox vbox = new Vbox();

		west.setParent(borderlayout);
		vbox.setParent(west);
		west.setSize("290px");
		west.setFlex(true);
		west.setSplittable(true);
		west.setCollapsible(true);

		criaPainelFuncionalidades();
		funcionalidades.setParent(vbox);
	}

	/**
	 * Cria painel funcionalidades que fica a esquerda.
	 */
	public void criaPainelFuncionalidades(){

		funcionalidades = new Panel();

		funcionalidades.setTitle("Funcionalidades");
		funcionalidades.setClosable(true);
		funcionalidades.setMaximizable(true);
		funcionalidades.setMinimizable(true);
		funcionalidades.setBorder("normal");

		Panelchildren painelchildrenFuncionalidades = new Panelchildren();
		Vlayout vlayout = new Vlayout();


		Toolbarbutton toolbarbuttonIdentificarRiscos = criarToolBarButton(vlayout,"Identificar Riscos","/imagens/xmag.png");
		toolbarbuttonIdentificarRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirPanelIdentificarRiscos();
			}
		});


		Toolbarbutton toolbarbuttonAnalisarRiscos = criarToolBarButton(vlayout,"Analisar Riscos","/imagens/korganizer_todo.png");
		toolbarbuttonAnalisarRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirpanelAnalisarRiscos();
			}
		});

		Toolbarbutton toolbarbuttonPriorizarRiscos = criarToolBarButton(vlayout,"Priorizar Riscos","/imagens/view_choose.png");
		toolbarbuttonPriorizarRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirpanelPriorizarRiscos();

			}
		});

		Toolbarbutton toolbarbuttonSelecionarEstrategiaDeTratamentoDeRiscos = criarToolBarButton(vlayout,"Selecionar Estratégia de Tratamento de Riscos"
				,"/imagens/view_detailed.png");
		toolbarbuttonSelecionarEstrategiaDeTratamentoDeRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirpanelEstrategiaDeTratametoDeRiscos();

			}
		});
		Toolbarbutton toolbarbuttonPlanejarAcoesDeTratamentoDeRiscos = criarToolBarButton(vlayout,"Planejar Ações de Tratamento de Riscos"
				,"/imagens/karm.png");
		toolbarbuttonPlanejarAcoesDeTratamentoDeRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirpanelPlanejaAcoesDeTratametoDeRiscos();			
			}
		});

		Toolbarbutton toolbarbuttonMonitorarRiscos = criarToolBarButton(vlayout,"Monitorar Riscos","/imagens/template_source.png");
		toolbarbuttonMonitorarRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirpanelMonitorarRiscos();

			}
		});

		Toolbarbutton toolbarbuttonAcompanharAcoesDeTratamentoDeRiscos = criarToolBarButton(vlayout,"Acompanhar Ações de Tratamento de Riscos"
				,"/imagens/button_accept.png");
		toolbarbuttonAcompanharAcoesDeTratamentoDeRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				exibirpanelAcompanharAcoesDeTratametoDeRiscos();

			}
		});

		Toolbarbutton toolbarbuttonGerarPlanoDeRiscosDeProjetos = criarToolBarButton(vlayout,"Gerar Plano de Riscos de Projetos"
				,"/imagens/spreadsheet.png");
		toolbarbuttonGerarPlanoDeRiscosDeProjetos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				Executions.getCurrent().sendRedirect("/paginas/gerenciaRiscos/relatorio.zul?id=" + planoRisco.getId().toString(), "_blank");
			}
		});


		vlayout.setParent(painelchildrenFuncionalidades);
		painelchildrenFuncionalidades.setParent(funcionalidades);
	}

	
	/**
	 * Cria painel gerencia de conhecimento que fica a direita.
	 */
	public void criaPainelGerenciaConhecimento(){

		gerenciaConhecimento = new Panel();

		gerenciaConhecimento.setTitle("Apoio da Gerência de Conhecimento");
		gerenciaConhecimento.setMinimizable(true);	
		gerenciaConhecimento.setMaximized(true);
		gerenciaConhecimento.setClosable(true);
		gerenciaConhecimento.setBorder("normal");

		Panelchildren painelchildrenGerenciaDeConhecimento = new Panelchildren();
		painelchildrenGerenciaDeConhecimento.setParent(gerenciaConhecimento);

		Vlayout vlayout = new Vlayout();
		vlayout.setParent(painelchildrenGerenciaDeConhecimento);

		Label labelIensDeConhecimentoSugeridos = new Label("Itens de Conhecimento Sugeridos:");
		labelIensDeConhecimentoSugeridos.setStyle("color:blue;");   
		labelIensDeConhecimentoSugeridos.setParent(vlayout);

		Toolbarbutton toolbarbuttonDeclararRiscos = criarToolBarButton(vlayout,"Como  Identificar Riscos()","/imagens/view_detailed.png");
		toolbarbuttonDeclararRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}
		});

		
		Toolbarbutton toolbarbuttonMelhoresTecnicasDeIdenticacoesDosRiscos = criarToolBarButton(vlayout,"Melhores Técnicas de Identificações dos Riscos()","/imagens/view_detailed.png");
		toolbarbuttonMelhoresTecnicasDeIdenticacoesDosRiscos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}
		});
		
		
		Toolbarbutton toolbarbuttonGerenciaDeRiscosEmProjetoDeSoftware = criarToolBarButton(vlayout,"Gerëncia de Riscos em Projeto de Software()"
				,"/imagens/view_detailed.png");
		toolbarbuttonGerenciaDeRiscosEmProjetoDeSoftware.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}

		});

		
		Toolbarbutton toolbarbuttonCadastraRiscosEmAmbienteOde = criarToolBarButton(vlayout,"Cadastrar Riscos em Ambiente Ode()"
				,"/imagens/view_detailed.png");
		toolbarbuttonCadastraRiscosEmAmbienteOde.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}

		});

		Label labelPlanosDeRiscosDeProjetosSemelhantes = new Label("Planos de Riscos de Projetos Semelhantes:");
		labelPlanosDeRiscosDeProjetosSemelhantes.setStyle("color:blue;");
		labelPlanosDeRiscosDeProjetosSemelhantes.setParent(vlayout);

		Toolbarbutton toolbarbuttonSistemasDeControleAereo = criarToolBarButton(vlayout,"Sistemas de Controle Aereo","/imagens/spreadsheet.png");
		toolbarbuttonSistemasDeControleAereo.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}

		});

		Toolbarbutton toolbarbuttonSistemasDeReservasDeRescursos = criarToolBarButton(vlayout,"Sistemas de Reservas de Recursos"
				,"/imagens/spreadsheet.png");
		toolbarbuttonSistemasDeReservasDeRescursos.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}

		});

		Label labelFuncionalidades  = new Label("Funcionalidades:");
		labelFuncionalidades.setStyle("color:blue;");
		labelFuncionalidades.setParent(vlayout);

		Toolbarbutton toolbarbuttonCriarItensDeConhecimento = criarToolBarButton(vlayout,"Criar Itens de Conhecimento","/imagens/kwrite.png");
		toolbarbuttonCriarItensDeConhecimento.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}

		});

		
		Toolbarbutton toolbarbuttonBuscarItensDeConhecimento = criarToolBarButton(vlayout,"Buscar Itens de Conhecimento","/imagens/kpdf.png");
		toolbarbuttonBuscarItensDeConhecimento.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
			}
		});
	}

	
	/**
	 * Cria tool bar button dos paineis.
	 * @param vlayout
	 * @param nome
	 * @param path
	 * @return
	 */
	public Toolbarbutton criarToolBarButton(Vlayout vlayout, String nome, String path){


		Toolbarbutton toolbarbutton = new Toolbarbutton();		

		toolbarbutton.setLabel(nome);
		toolbarbutton.setImage(path);

		toolbarbutton.setParent(vlayout);

		return toolbarbutton;

	}
	
	/**
	 * Janela plano de risco..
	 * @param ctrlPlanoRisco
	 * @param planoRisco
	 */
	public JanelaPlanoRisco(CtrlPlanoRisco ctrlPlanoRisco, PlanoRisco planoRisco) {
		super();

		this.ctrlPlanoRisco = ctrlPlanoRisco;
		this.planoRisco = planoRisco;

		this.setTitle("Plano de Riscos - Versão " + planoRisco.getVersao());
		this.setClosable(true);
		this.setMaximizable(true);
		this.setWidth("100%");
		this.setHeight("100%");


		borderlayout.setParent(this);
		criarWest();
		criarEast();
		criarCenter();
	}
	
	
	/**
	 * Exibe painel identificar risco.
	 */
	public void exibirPanelIdentificarRiscos() {
		
		// Limpa o conteudo do centro.
		center.getChildren().clear();
		
		// Destroi painel antigo caso existe.
		if (panelIdentificarRiscos != null)
			panelIdentificarRiscos.detach();
		
		// Cria novo painel.
		panelIdentificarRiscos = new PainelIdentificarRiscos(planoRisco);
		panelIdentificarRiscos.setParent(center);
		
	}

	
	/**
	 * Exibe panel acompanhar acoes de tratatamento de riscos.
	 * @throws InterruptedException
	 */
	public void exibirpanelAcompanharAcoesDeTratametoDeRiscos() throws InterruptedException {
		
		// Limpa o conteudo do centro.
		center.getChildren().clear();
		
		// Destroi painel caso exista.
		if(panelAcompanharAcoesDeTratatamentoDeRiscos != null)
			panelAcompanharAcoesDeTratatamentoDeRiscos.detach();
		
		panelAcompanharAcoesDeTratatamentoDeRiscos = new PainelAcompanharAcoesDeTratatamentoDeRiscos(planoRisco);
		panelAcompanharAcoesDeTratatamentoDeRiscos.setParent(center);
	}
	

	/**
	 * Exibe panel analisar riscos.
	 * @throws InterruptedException
	 */
	public void exibirpanelAnalisarRiscos() throws InterruptedException {
		
		//Limpa o conteudo do centro.
		center.getChildren().clear();
		
		// Destroi painel caso exista.
		if(painelAnalisarRiscos != null)
			painelAnalisarRiscos.detach();
		
		painelAnalisarRiscos = new PainelAnalisarRiscos(planoRisco, ctrlPlanoRisco);
		painelAnalisarRiscos.setParent(center);
	}
	
	/**
	 * Exibe panel monitorar Riscos.
	 * @throws InterruptedException
	 */
	public void exibirpanelMonitorarRiscos() throws InterruptedException {
		
		//Limpa o conteudo do centro.
		center.getChildren().clear();
		
		// Destroi o panel caso exista.
		if(painelMonitorarRiscos != null)
			painelMonitorarRiscos.detach();
		
		painelMonitorarRiscos = new PainelMonitorarRiscos(planoRisco);
		painelMonitorarRiscos.setParent(center);
	}
	
	/**
	 * Exibe panel planejar acoes de tratamento de riscos.
	 * @throws InterruptedException
	 */
	public void exibirpanelPlanejaAcoesDeTratametoDeRiscos() throws InterruptedException {
	
		//Limpa o conteudo do centro.
		center.getChildren().clear();
	
		// Destroi o panel caso exista.
		if(painelPlanejarAcoesDeTratatamentoDeRiscos != null)
			painelPlanejarAcoesDeTratatamentoDeRiscos.detach();
		
		painelPlanejarAcoesDeTratatamentoDeRiscos = new PainelPlanejarAcoesDeTratatamentoDeRiscos(planoRisco, ctrlPlanoRisco);
		painelPlanejarAcoesDeTratatamentoDeRiscos.setParent(center);
	}
	
	/**
	 * Exibe panel priorizar riscos.
	 * @throws InterruptedException
	 */
	public void exibirpanelPriorizarRiscos() throws InterruptedException {
		
		//Limpa o conteudo do centro.
		center.getChildren().clear();
		
		// Destroi o panel caso exista.
		if(painelPriorizarRiscos != null)
			painelPriorizarRiscos.detach();
		
		painelPriorizarRiscos = new PainelPriorizarRiscos(planoRisco, ctrlPlanoRisco);
		painelPriorizarRiscos.setParent(center);
	}
	
	/**
	 * Exibe panel estrategia de tratamento de riscos.
	 * @throws InterruptedException
	 */
	public void exibirpanelEstrategiaDeTratametoDeRiscos() throws InterruptedException {
	
		//Limpa o conteudo do centro.
		center.getChildren().clear();
		
		// destroi o panel caso exista.
		if(painelSeleciponalrEstrategiaDeTratatamentoDeRiscos != null)
			painelSeleciponalrEstrategiaDeTratatamentoDeRiscos.detach();
			
		painelSeleciponalrEstrategiaDeTratatamentoDeRiscos = new PainelSelecionarEstrategiaDeTratatamentoDeRiscos(planoRisco, ctrlPlanoRisco);
		painelSeleciponalrEstrategiaDeTratatamentoDeRiscos.setParent(center);
	}
}
