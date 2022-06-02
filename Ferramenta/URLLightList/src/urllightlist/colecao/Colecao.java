package urllightlist.colecao;

import java.util.List;

import org.hibernate.classic.Session;

import urllightlist.excecao.ColecaoException;

public interface Colecao<T>  {
		
	public void adicionar(T obj)throws ColecaoException; 
	public void atualizar(T obj) throws ColecaoException;
	public void excluir(Long id)throws ColecaoException;
	public Object buscar()throws ColecaoException;
	public List<T> todos()throws ColecaoException;
	public List<T> todos(int primeiro)throws ColecaoException;
	public List<T> todosComExcecao() throws ColecaoException;
	public List<T> todosComExcecao(int primeiro) throws ColecaoException;
	public List<T> todosComCampoMaiorQue(String campoMaior,Object valorMaior)throws ColecaoException;
	public Long contar()throws ColecaoException;
	public Long contarComCampoMaiorQue(String campoMaior, Object valorMaior)throws ColecaoException;
	public Long contarComExcecao() throws ColecaoException; 
	public void setClasse(Class<T> classe);
	public void setSession(Session session);
	public void setMaxResultado(int maxResultado);
	public void setCampoOrdenacao(String campoOrdenacao);
	public void setListaRestricao(List<Object> listaRestricao);
	
		
}