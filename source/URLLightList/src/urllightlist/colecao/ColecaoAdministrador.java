package urllightlist.colecao;

import urllightlist.entidade.Administrador;


public interface ColecaoAdministrador extends Colecao<Administrador>{
	public boolean validarSenha(String senha);
	public boolean validarLogin(String login);
	
	
}
