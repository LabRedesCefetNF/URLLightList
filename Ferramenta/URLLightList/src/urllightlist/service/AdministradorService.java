package urllightlist.service;

import java.util.ArrayList;
import java.util.List;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.entidade.Administrador;
import urllightlist.excecao.ColecaoException;
import urllightlist.excecao.ServiceException;

public class AdministradorService {
	private ColecaoAdministrador colecaoAdministrador;

	public AdministradorService(ColecaoAdministrador colecaoAdministrador) {
		this.colecaoAdministrador = colecaoAdministrador;
	}

	public Administrador buscarAdmPorLogin(String login)
			throws ColecaoException {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("login");
		listaRestricao.add(login);
		this.colecaoAdministrador.setListaRestricao(listaRestricao);
		return (Administrador) this.colecaoAdministrador.buscar();
	}

	public Administrador buscarAdm(String login, String Senha)
			throws ColecaoException {
		List<Object> listaRestricao = new ArrayList<Object>();
		listaRestricao.add("login");
		listaRestricao.add(login);
		listaRestricao.add("senha");
		listaRestricao.add(Senha);
		this.colecaoAdministrador.setListaRestricao(listaRestricao);
		return (Administrador) this.colecaoAdministrador.buscar();
	}

	public Administrador logar(Administrador adm) throws ColecaoException {
		Administrador administrador = null;
		administrador = this.buscarAdm(adm.getLogin(),adm.getSenha());
		return administrador;
	}

	public String adicionar(Administrador adm) throws ServiceException,
			ColecaoException {
		String mensagem = null;
		if (this.buscarAdmPorLogin(adm.getLogin()) == null) {
			if (this.colecaoAdministrador.validarLogin(adm.getLogin())) {
				if (this.colecaoAdministrador.validarSenha(adm.getSenha())) {
					this.colecaoAdministrador.adicionar(adm);
				} else {
					mensagem = "A senha deve conter 6 caracteres numericos.";
				}
			} else {
				mensagem = "O login deve ter no minimo 6 caracteres e no maximo 12 caracteres.";
			}
		} else {
			mensagem = "Login de administrador ja existe no Banco de Dados";
		}
		return mensagem;
	}

	public String atualizar(Administrador adm) throws ColecaoException {
		String mensagem = null;
		if (this.colecaoAdministrador.validarLogin(adm.getLogin())) {
			if (this.colecaoAdministrador.validarSenha(adm.getSenha())) {
				this.colecaoAdministrador.atualizar(adm);
			} else {
				mensagem = "A senha deve conter 6 caracteres numericos.";
			}
		} else {
			mensagem = "O login deve ter no minimo 6 caracteres e no maximo 12 caracteres.";
		}
		return mensagem;
	}

}