package urllightlist.colecao;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.entidade.Administrador;

public class ColecaoAdministradorEmBDR extends ColecaoEmBDR<Administrador>
		implements ColecaoAdministrador {

	public ColecaoAdministradorEmBDR(Session session, Logger log) {
		super(session, Administrador.class, log);
	}


	public boolean validarSenha(String senha) {
		boolean valido = true;
		if (this.senhaSoPossuiNumeros(senha)) {
			Integer tamanho = senha.length();
			if (!(tamanho == 6)) {
				valido = false;
			}
		} else {
			valido = false;
		}
		return valido;
	}

	
	public boolean validarLogin(String login) {
		boolean valido = true;
		int tamanho = login.length();
		if (tamanho < 6 || tamanho > 12) {
			valido = false;
		}
		return valido;
	}

	public boolean senhaSoPossuiNumeros(String senha) {
		boolean senhaSoPossuiNumeros=true;
		for (char caracter : senha.toCharArray()){
			if (caracter < '0' || caracter > '9'){
				senhaSoPossuiNumeros=false;
				break;
			}
		}
		return senhaSoPossuiNumeros;

	}

}
