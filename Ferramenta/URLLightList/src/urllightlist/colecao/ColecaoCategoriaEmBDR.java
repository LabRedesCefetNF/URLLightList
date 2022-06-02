package urllightlist.colecao;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;
import urllightlist.entidade.Categoria;
import urllightlist.excecao.ColecaoException;

public class ColecaoCategoriaEmBDR extends ColecaoEmBDR<Categoria> implements
		ColecaoCategoria {
	public ColecaoCategoriaEmBDR(Session session, Logger log) {
		super(session, Categoria.class, log);
	}

	public boolean validarNome(String nome) throws ColecaoException {
		boolean valido=true;
		int tamanho = nome.length();
		if (tamanho < 3 || tamanho > 12) {
			valido=false;					
		}
	return valido;	

	}


}
