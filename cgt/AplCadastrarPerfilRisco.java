package ode.gerenciaRiscos.cgt;

import ode._infraestruturaBase.cgd.DAOBase;
import ode._infraestruturaBase.excecao.NucleoRegraNegocioExcecao;
import ode._infraestruturaCRUD.cgt.AplCRUD;
import ode.gerenciaRiscos.cdp.PerfilRisco;
import ode.gerenciaRiscos.cdp.PlanoRisco;
import ode.gerenciaRiscos.cgd.PerfilRiscoDAO;
import ode.gerenciaRiscos.cgd.PlanoRiscoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor= NucleoRegraNegocioExcecao.class )
public class AplCadastrarPerfilRisco extends AplCRUD<PerfilRisco> {
	
	@Autowired
	PerfilRiscoDAO perfilRiscoDAO;
	
	@Autowired
	PlanoRiscoDAO planoRiscoDAO;

	@Override
	public DAOBase<PerfilRisco> getNucleoDaoBase() {
		return perfilRiscoDAO;
	}
	
	/**
	 * salva perfil risco de um projeto.
	 * @param perfilRisco Perfil risco que se deseja salvar.
	 * @return perfilRissco
	 * @throws NucleoRegraNegocioExcecao
	 */
	public PerfilRisco salvarPerfilRiscoProjeto(PerfilRisco perfilRisco) throws NucleoRegraNegocioExcecao {
		
		PlanoRisco planoRiscoBanco = planoRiscoDAO.recuperarPorId(perfilRisco.getPlanoRisco().getId());
		perfilRisco.setPlanoRisco(planoRiscoBanco);
		perfilRisco = super.salvar(perfilRisco);
		planoRiscoBanco.getPerfisRisco().add(perfilRisco);
		
		return perfilRisco;
	}
}
