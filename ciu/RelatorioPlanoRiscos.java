package ode.gerenciaRiscos.ciu;

import java.util.Collection;
import java.util.List;

import ode._infraestruturaBase.util.NucleoUtil;
import ode.controleProjeto.cdp.Projeto;
import ode.controleProjeto.cgd.ProjetoDAO;
import ode.gerenciaRiscos.cdp.AcaoContingencia;
import ode.gerenciaRiscos.cdp.AcaoMitigacao;
import ode.gerenciaRiscos.cdp.AvaliacaoRisco;
import ode.gerenciaRiscos.cdp.Consequencia;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.AvaliacaoRiscoDAO;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;
import ode.gerenciaRiscos.cgd.PlanoRiscoDAO;

import org.zkoss.zhtml.Br;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

public class RelatorioPlanoRiscos extends Window {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Projeto projeto;
	AvaliacaoRisco avaliacaoRisco;
	AcaoContingencia acaoContingencia;
	

	public void onCreate() throws InterruptedException {
		
		// Recupera o objeto planoRiscoDAO.
		PlanoRiscoDAO planoRiscoDAO = SpringUtil
				.getApplicationContext().getBean(PlanoRiscoDAO.class);
		
		// Recupera o objeto planoRisco.
		PlanoRisco planoRisco = planoRiscoDAO.
				recuperarPorId(Long.parseLong(Executions.getCurrent().getParameter("id")));
		
		// Recupera o objeto perfilRiscoDAO.
		PerfilRiscoDAO perfilRiscoDAO= SpringUtil
				.getApplicationContext().getBean(PerfilRiscoDAO.class);
		
		Collection<PerfilRisco> listaPerfisRisco = perfilRiscoDAO
				.recuperarPerfisRiscoPorPlanoRisco(planoRisco);
		
		// Recupera o objeto avaliacaoRiscoDAO.
		AvaliacaoRiscoDAO avaliacaoRiscoDAO = SpringUtil
				.getApplicationContext().getBean(AvaliacaoRiscoDAO.class);
		
		// Verifica se existe perfis risco para criar o relatorio.
		if(listaPerfisRisco.size() > 0) {
			
			for (PerfilRisco perfilRisco : listaPerfisRisco) {			
				AvaliacaoRisco avaliacaoRiscoLocal = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
				if (avaliacaoRiscoLocal != null) {
					this.avaliacaoRisco = avaliacaoRiscoLocal;
					
					for(AcaoContingencia acaoContingencia: this.avaliacaoRisco.getAcoesContingencia()) {
						this.acaoContingencia = acaoContingencia;
					}
				}
			}
		}
		
		//////////////////////////////////////
		//Inicia a criacao de relatorio.
		//////////////////////////////////////
		
		Vbox vbox = new Vbox();
		vbox.setParent(this);
			
		Label labelTitulo = new Label("Plano de Riscos");
		labelTitulo.setParent(vbox);
		labelTitulo.setHeight("30px");
		labelTitulo.setStyle("margin-botton: 25px;padding:50px; display: block;text-align: " +
				"center;color: black;font-weight: bold;font-size:25px;margin-left:20px;");
		
		// Label projeto.
		ProjetoDAO projetoDAO = SpringUtil.getApplicationContext().getBean(ProjetoDAO.class);
		List<Projeto> listaProjetos = (List<Projeto>) projetoDAO.recuperarTodos();
		for(Projeto projeto: listaProjetos) {
			this.projeto  = projeto;
		}
		
		// Label hboxProjeto.
		Hbox hboxProjeto = new Hbox();
		hboxProjeto.setParent(vbox);
		hboxProjeto.setHeight("10px");
		hboxProjeto.setStyle("margin-top: 07px;font-size:18px;margin-left:20px;");
		
		// Label projeto.
		Label labelProjeto = new Label("Projeto: ");
		labelProjeto.setStyle("color: black;font-weight: bold;font-size:18px");
		labelProjeto.setParent(hboxProjeto);
		
		// Label nome de projeto.
		Label labelNomeDeProjeto = new Label(projeto.getNome());
		labelNomeDeProjeto.setStyle("font-size:18px;");
		labelNomeDeProjeto.setParent(hboxProjeto);

		// Label hboxVersao.
		Hbox hboxVersao = new Hbox();
		hboxVersao.setParent(vbox);
		hboxVersao.setHeight("10px");
		hboxVersao.setStyle("margin-top: 07px;font-size:18px;margin-left:20px;");
		
		// Label versao.
		Label labelVersao = new Label("Versão: ");
		labelVersao.setStyle("color: black;font-weight: bold;font-size:18px;");
		labelVersao.setParent(hboxVersao);
		
		// Label nome de versao.
		Label labelNumDeVersao = new Label(planoRisco.getVersao());
		labelNumDeVersao.setStyle("font-size:18px;");
		labelNumDeVersao.setParent(hboxVersao);
		
		
		// Label hboxData.
		Hbox hboxData = new Hbox();
		hboxData.setParent(vbox);
		hboxData.setHeight("10px");
		hboxData.setStyle("margin-top: 07px;margin-left:20px;");
		
		// Label data.
		Label labelData = new Label("Data: ");
		labelData.setStyle("color: black;font-weight: bold;font-size:18px;");
		labelData.setParent(hboxData);
		
		// Label situacao da data.
		Label labelSituacaoData = new Label(NucleoUtil.formataData(avaliacaoRisco.getData()) 
				+ " ( " + (planoRisco.getDtFinalizacao() == null ? "Em Aberto" : "Finalizado") + " ) ");
		labelSituacaoData.setStyle("font-size:18px;");
		labelSituacaoData.setParent(hboxData);
		
		
		// Label hboxResponsavel.
		Hbox hboxResponsavel = new Hbox();
		hboxResponsavel.setParent(vbox);
		hboxResponsavel.setHeight("10px");
		hboxResponsavel.setStyle("display: block;margin-top: 07px;margin-left:20px;");
		
		
		// Label responsavel.
		Label labelResponsavel = new Label("Responsável: ");
		labelResponsavel.setStyle("color: black;font-weight: bold;font-size:18px;");
		labelResponsavel.setParent(hboxResponsavel);
		
		// Label nome de responsavel.
		if(acaoContingencia != null) {
			Label labelNomeDeResponsavel = new Label(acaoContingencia.getRecursoHumanoResponsavel().getNome());
			labelNomeDeResponsavel.setStyle("font-size:18px;");
			labelNomeDeResponsavel.setParent(hboxResponsavel);
			(new Br()).setParent(hboxResponsavel);
		}else {
			Label labelNomeDeResponsavel = new Label("Não existe ações para esse plano");
			labelNomeDeResponsavel.setStyle("font-size:18px;");
			labelNomeDeResponsavel.setParent(hboxResponsavel);
			(new Br()).setParent(hboxResponsavel);
		}
			
		
		// Label introducao.
		Label labelIntroducao = new Label("1.Introdução: ");
		labelIntroducao.setParent(vbox);
		labelIntroducao.setHeight("10px");
		labelIntroducao.setStyle("display: block;margin-top: 18px; color: black;font-weight: bold;font-size:18px;margin-left:20px;");
		
		
		// Label descricao de introducao.
		Label labelDescricaoDaIntroducao = new Label("Este documento apresenta o Plano de Riscos para o " + projeto.getNome() +
				". Na próxima seção, as seguintes informações são apresentadas para cada risco: nome, categoria, probabilidade, " +
				"impacto, grau de exposição, consequência, estratégia de tratamento selecionada, ocorrência e ações de tratamento planejadas.");
		labelDescricaoDaIntroducao.setParent(vbox);
		labelDescricaoDaIntroducao.setHeight("35px");
		labelDescricaoDaIntroducao.setStyle("display: block;margin-top: 18px;font-size:18px;margin-left:20px;");
		
		// Label risco.
		Label labelRiscos = new Label("2. Riscos: ");
		labelRiscos.setParent(vbox);
		labelRiscos.setHeight("30px");
		labelRiscos.setStyle("display: block;margin-top: 18px; color: black;font-weight: bold;font-size:18px;margin-left:20px;");
		
		
		// verifica se existe perfis risco pra preencher a tabela de relatorio. 
		if(listaPerfisRisco.size() > 0) {
			
			for (PerfilRisco perfilRisco : listaPerfisRisco) {
			
				AvaliacaoRisco avaliacaoRiscoBanco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
				
				if(avaliacaoRiscoBanco != null) {
					avaliacaoRisco = avaliacaoRiscoBanco;				
					
					// Table.
					Table table = new Table();
					table.setParent(vbox);
					table.setDynamicProperty("border","1");
					table.setStyle("margin-left:20px;");
					(new Br()).setParent(table);
					(new Br()).setParent(table);
					
					
					// Linha de label nome: e de nome de kRisco.
					Tr trNomeKRisco = new Tr();
					trNomeKRisco.setParent(table);
					
					// Coluna de Td nome.
					Td tdNome = new Td();
					tdNome.setParent(trNomeKRisco);
					
					//Label nome.
					Label labelNome = new Label("Nome: ");
					labelNome.setStyle("font-size:18px;");
					labelNome.setParent(tdNome);
					
					
					// Coluna Td de label nome de KRisco.
					Td tdKRisco = new Td();
					tdKRisco.setParent(trNomeKRisco);
					
					//Label nome de KRisco.
					Label labelKRisco = new Label(perfilRisco.getKRisco().getNome());
					labelKRisco.setStyle("display: block;margin-top: 18px; color: black;font-weight: bold;font-size:18px;");
					labelKRisco.setParent(tdKRisco);
					
					
					// Linha de label categoria: e de tipo de categoria.
					Tr trTipoCategoria = new Tr();
					trTipoCategoria.setParent(table);
					
					// Coluna de label categoria.
					Td tdCtegoria = new Td();
					tdCtegoria.setParent(trTipoCategoria);
					
					//Label categoria.
					Label labelCategoria = new Label("Categoria: ");
					labelCategoria.setStyle("font-size:18px;");
					labelCategoria.setParent(tdCtegoria);
					
					
					// Coluna de label nome de categoria.
					Td tdNomeCategoria = new Td();
					tdNomeCategoria.setParent(trTipoCategoria);
					
					
					//Label nome de categoria.
					Label labelNomeCategoria = new Label(perfilRisco.getKRisco().getkCategoriaRisco().toString());
					labelNomeCategoria.setStyle("font-size:18px;");
					labelNomeCategoria.setParent(tdNomeCategoria);

					
					// Linha de label probabilidade: e de % de probabilidade.
					Tr trProbabilidade = new Tr();
					trProbabilidade.setParent(table);
					
					
					// Coluna de label probabilidade.
					Td tdProbabilidade = new Td();
					tdProbabilidade.setParent(trProbabilidade);
					
					//Label probabilidade.
					Label labelProbabilidade = new Label("Probabilidade: ");
					labelProbabilidade.setStyle("font-size:18px;");
					labelProbabilidade.setParent(tdProbabilidade);
					
					
					// Coluna de label % de probabilidade.
					Td tdPorcentagemDeProbabilidade = new Td();
					tdPorcentagemDeProbabilidade.setParent(trProbabilidade);
					
					//Label porcentagem da probabilidade.
					Label labelPorcentagemDeProbabilidade = new Label(avaliacaoRisco.getProbabilidade().toString());
					labelPorcentagemDeProbabilidade.setStyle("font-size:18px;");
					labelPorcentagemDeProbabilidade.setParent(tdPorcentagemDeProbabilidade);

					
					// Linha de label impacto: e de nome de impacto.
					Tr trImpacto = new Tr();
					trImpacto.setParent(table);
					
					
					// Coluna de label impacto.
					Td tdImpacto = new Td();
					tdImpacto.setParent(trImpacto);
					
					//Label impacto.
					Label labelImpacto = new Label("Impacto: ");
					labelImpacto.setStyle("font-size:18px;");
					labelImpacto.setParent(tdImpacto);
					
					
					// Coluna de label de nome de impacto.
					Td tdNomeDeImpacto = new Td();
					tdNomeDeImpacto.setParent(trImpacto);
					
					
					//Label nome de impacto.
					Label labelNomeDeImpacto= new Label(avaliacaoRisco.getImpacto().toString());
					labelNomeDeImpacto.setStyle("font-size:18px;");
					labelNomeDeImpacto.setParent(tdNomeDeImpacto);
					

					// Linha de label nome de grau de exposicao: e de grau de exposicao.
					Tr trGrauDeExposicao = new Tr();
					trGrauDeExposicao.setParent(table);
					
					// Recupera o objeto avaliacaoRisco.
					AvaliacaoRisco avaliacaoRisco = avaliacaoRiscoDAO.recuperarAvaliacoesRiscoPorPerfilRisco(perfilRisco);
					
					java.math.BigDecimal numero = java.math.BigDecimal.valueOf(100);
					java.math.BigDecimal  grauDeExposicao = (avaliacaoRisco.getProbabilidade().multiply(avaliacaoRisco.getImpacto())).divide(numero);
								
					
					// Coluna de label de nome de grau de exposicao.
					Td tdGrauDeExposicao = new Td();
					tdGrauDeExposicao.setParent(trGrauDeExposicao);
					
					//Label grau de exposicao.
					Label labelDeGrauDeExposicao = new Label("Grau de Exposição: ");
					labelDeGrauDeExposicao.setStyle("font-size:18px;");
					labelDeGrauDeExposicao.setParent(tdGrauDeExposicao);
					
					
					// Coluna de label de grau de exposicao.
					Td tdNomeDeGrauDeExposicao = new Td();
					tdNomeDeGrauDeExposicao.setParent(trGrauDeExposicao);
					
					//Label nome de grau de exposicao.
					Label labelNomeDeGrauDeExposicao= new Label(grauDeExposicao.toString());
					labelNomeDeGrauDeExposicao.setStyle("font-size:18px;");
					labelNomeDeGrauDeExposicao.setParent(tdNomeDeGrauDeExposicao);
					
					
					
					// Linha de label nome consequencia: e de consequencia..
					Tr trConsequencia= new Tr();
					trConsequencia.setParent(table);
					
					// Coluna de label nome consequencia.
					Td tdNomeDeConsequencia = new Td();
					tdNomeDeConsequencia.setParent(trConsequencia);
					
					//Label nome de consequencia.
					Label labelNomeDeConsequencia = new Label("Consequência: ");
					labelNomeDeConsequencia.setStyle("font-size:18px;");
					labelNomeDeConsequencia.setParent(tdNomeDeConsequencia);
					
					// Coluna de label de consequencia.
					Td tdConsequencia= new Td();
					tdConsequencia.setParent(trConsequencia);
										
					
					if(!avaliacaoRisco.getConsequencias().isEmpty()) {
							
						for(Consequencia consequencia: avaliacaoRisco.getConsequencias()) {

							Vbox vboxConsequencia = new Vbox();
							vboxConsequencia.setParent(tdConsequencia);
																					
							//Label consequencia.
							Label labelConsequencia = new Label(consequencia.getDescricao());
							labelConsequencia.setStyle("font-size:18px;");
							labelConsequencia.setParent(vboxConsequencia);
							tdConsequencia.setParent(trConsequencia);
							(new Br()).setParent(vboxConsequencia);
						}
						
					}else {
												
							//Label consequencia.
							Label labelConsequencia = new Label("Não há consequência registrado pra essa avalição.");
							labelConsequencia.setStyle("font-size:18px;");
							labelConsequencia.setParent(tdConsequencia);
							
					}
				
					
					// Linha de label nome estrategia de tratamento: e de estrátegia de tratamento.
					Tr trEstrategiaDeTratamento = new Tr();
					trEstrategiaDeTratamento.setParent(table);
					
					// Coluna de label nome estrategia de tratamento.
					Td tdNomeDeEstrategiaDeTratamento= new Td();
					tdNomeDeEstrategiaDeTratamento.setParent(trEstrategiaDeTratamento);
					
					//Label nome de estrategia de tratamento.
					Label labelNomeDeEstrategiaDeTratamento= new Label("Estratégia de Tratamento: ");
					labelNomeDeEstrategiaDeTratamento.setStyle("font-size:18px;");
					labelNomeDeEstrategiaDeTratamento.setParent(tdNomeDeEstrategiaDeTratamento);			
					
					// Coluna de label de estrategia de tratamento.
					Td tdEstrategiaDeTratamento= new Td();
					tdEstrategiaDeTratamento.setParent(trEstrategiaDeTratamento);
					
					//Label de estrategia de tratamento.
					Label labelEstrategiaDeTratamento= new Label(avaliacaoRisco.getEstrategiaTratamento() + " (Limiar: " + avaliacaoRisco.getLimiar() + ")");
					labelEstrategiaDeTratamento.setStyle("font-size:18px;");
					labelEstrategiaDeTratamento.setParent(tdEstrategiaDeTratamento);
				
					
					// Linha de label nome ocorreu: e de ocorreu.
					Tr trOcorrencia= new Tr();
					trOcorrencia.setParent(table);
					
					
					// Coluna de label nome ocorrencia.
					Td tdNomeDeOcorrencia = new Td();
					tdNomeDeOcorrencia.setParent(trOcorrencia);
					
					//Label nome de ocorrencia.
					Label labelNomeDeOcorrencia = new Label("Ocorrência: ");
					labelNomeDeOcorrencia.setStyle("font-size:18px;");
					labelNomeDeOcorrencia.setParent(tdNomeDeOcorrencia);
					
					
					// Coluna de label de ocorrencia.
					Td tdOcorrencia = new Td();
					tdOcorrencia.setParent(trOcorrencia);
					
					//Label de ocorrencia.
					Label labelOcorrencia = new Label(String.valueOf(avaliacaoRisco.isOcorreu()? "Sim": "Não"));
					labelOcorrencia.setStyle("font-size:18px;");
					labelOcorrencia.setParent(tdOcorrencia);
									
					
					///////////////////////////////////
					///////// Acoes de Tratamento
					//////////////////////////////////
					
					
					// Linha de label nome acoes de tratamento: e de acoes de tratamento.
					Tr trAcoesDeTratamento= new Tr();
					trAcoesDeTratamento.setParent(table);
					
					// Coluna de label nome acoes de tratamento.
					Td tdNomeDeAcoesDeTratamento= new Td();
					tdNomeDeAcoesDeTratamento.setParent(trAcoesDeTratamento);
					
					//Label nome de acoes de tratamento.
					Label labelNomeAcoesDeTratamento= new Label("Acões de Tratamento: ");
					labelNomeAcoesDeTratamento.setStyle("font-size:18px;");
					labelNomeAcoesDeTratamento.setParent(tdNomeDeAcoesDeTratamento);
					
					
					// Coluna de label de acoes de tratamento .
					Td tdAcoesDeTratamento = new Td();
					tdAcoesDeTratamento.setParent(trAcoesDeTratamento);
								
					///////////////////////////////////
					///////// Acoes de Mitgacoes.
					////////////////////////////
					
					Vbox vboxMitigacao = new Vbox();
					vboxMitigacao.setParent(tdAcoesDeTratamento);
					
					for(AcaoMitigacao acaoMitigacao: avaliacaoRisco.getAcoesMitigacao()) {
						
						Hbox hboxAcao = new Hbox();
						hboxAcao.setParent(vboxMitigacao);
						
						//Label de mitigacoes.
						Label labelAcaoDeMitgacao = new Label("Ação de Mitigação: ");
						labelAcaoDeMitgacao.setStyle("color: black;font-weight: bold;font-size:18px;");
						labelAcaoDeMitgacao.setParent(hboxAcao);
						
						//Label de acoes de tratamento.
						Label labelAcoesDeTratamento = new Label(acaoMitigacao.getKacaoRisco().getNome());
						labelAcoesDeTratamento.setStyle("font-size:18px;margin-bottom:10px");
						labelAcoesDeTratamento.setParent(hboxAcao);
						
						//Hbox de responsavel de acoes de mitigacoes.
						Hbox hboxResponsavelAcao = new Hbox();
						hboxResponsavelAcao.setParent(vboxMitigacao);
						
						///Label de responsavel de acoes de tratamento.
						Label labelResponsavelDeAcoesDeTratamentoDeMitigacao = new Label("Responsável: ");
						labelResponsavelDeAcoesDeTratamentoDeMitigacao.setStyle("color: black;font-weight: bold;font-size:18px;");
						labelResponsavelDeAcoesDeTratamentoDeMitigacao.setParent(hboxResponsavelAcao);
						
						//Label nome de responsavel de acoes de tratamento.
						Label labelNomeResponsavelDeAcoesDeTratamento  = new Label(acaoMitigacao.getRecursoHumanoResponsavel().getNome());
						labelNomeResponsavelDeAcoesDeTratamento.setStyle("font-size:18px;margin-bottom:5px");
						labelNomeResponsavelDeAcoesDeTratamento.setParent(hboxResponsavelAcao);
						
						Hbox hboxStatus = new Hbox();
						hboxStatus.setParent(vboxMitigacao);
						
						///Label de status de acoes de mitigacoes.
						Label labelStatusDeMitgacao = new Label("Status: ");
						labelStatusDeMitgacao.setStyle("color: black;font-weight: bold;font-size:18px;");
						labelStatusDeMitgacao.setParent(hboxStatus);
						
						///Label nome de status de acoes de mitigacoes.
						Label labelNomeDeStatus = new Label(acaoMitigacao.getEstado());
						labelNomeDeStatus.setStyle("font-size:18px;margin-bottom:5px");
						labelNomeDeStatus.setParent(hboxStatus);
						
						(new Br()).setParent(vboxMitigacao);
					}
					
					
					///////////////////////////////////
					///////// Acoes de Contingencia.
					//////////////////////////////////
					
					Vbox vboxContingengia = new Vbox();
					vboxContingengia.setParent(tdAcoesDeTratamento);
					
					for(AcaoContingencia acaoContingencia: avaliacaoRisco.getAcoesContingencia()) {

						
						Hbox hboxAcao = new Hbox();
						hboxAcao.setParent(vboxContingengia);
						
						//Label de contingencia.
						Label labelAcaoDeContingencia = new Label("Ação de Contingência: ");
						labelAcaoDeContingencia.setStyle("color: black;font-weight: bold;font-size:18px;");
						labelAcaoDeContingencia.setParent(hboxAcao);
						
						//Label de acoes de tratamento.
						Label labelAcoesDeTratamento = new Label(acaoContingencia.getKacaoRisco().getNome());
						labelAcoesDeTratamento.setStyle("font-size:18px;margin-bottom:10px");
						labelAcoesDeTratamento.setParent(hboxAcao);
						
						//Hbox de responsavel de acoes de contingencia.
						Hbox hboxResponsavelAcao = new Hbox();
						hboxResponsavelAcao.setParent(vboxContingengia);
						
						///Label de responsavel de acoes de tratamento.
						Label labelResponsavelDeAcoesDeTratamentoDeContingencia= new Label("Responsável: ");
						labelResponsavelDeAcoesDeTratamentoDeContingencia.setStyle("color: black;font-weight: bold;font-size:18px;");
						labelResponsavelDeAcoesDeTratamentoDeContingencia.setParent(hboxResponsavelAcao);
						
						//Label nome de responsavel de acoes de tratamento.
						Label labelNomeDeResponsavelDeAcoesDeTratamento  = new Label(acaoContingencia.getRecursoHumanoResponsavel().getNome());
						labelNomeDeResponsavelDeAcoesDeTratamento.setStyle("font-size:18px;margin-bottom:5px");
						labelNomeDeResponsavelDeAcoesDeTratamento.setParent(hboxResponsavelAcao);
						
						Hbox hboxStatus = new Hbox();
						hboxStatus.setParent(vboxContingengia);
						
						///Label de status de acoes de contingencia.
						Label labelStatusDeContingencia = new Label("Status: ");
						labelStatusDeContingencia.setStyle("color: black;font-weight: bold;font-size:18px;");
						labelStatusDeContingencia.setParent(hboxStatus);
						
						///Label nome de status de acoes de contingencia.
						Label labelNomeDeStatus = new Label(acaoContingencia.getEstado());
						labelNomeDeStatus.setStyle("font-size:18px;margin-bottom:5px");
						labelNomeDeStatus.setParent(hboxStatus);
						
						(new Br()).setParent(vboxContingengia);
						
					}
					
				}else {						
					Label labelMensagem = new Label("Não existe perfil risco pra esse plano de risco.");
					labelMensagem.setParent(this);
										
				}
			}
		}

	}	
}
