package urllightlist.colecao;

import urllightlist.entidade.Categoria;
import urllightlist.excecao.ColecaoException;


public interface ColecaoCategoria extends Colecao<Categoria>{
	
	public boolean validarNome(String nome) throws ColecaoException;
	
}
