package urllightlist.colecao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import urllightlist.excecao.ColecaoException;

public class ColecaoEmBDR<T> implements Colecao<T> {

	private Session session;
	private Logger log;
	private Class<T> classe;
	private String campoOrdenacao;
	private int maxResultado;
	private List<Object> listaRestricao;
	

	public ColecaoEmBDR(Session session, Class<T> classe, Logger log) {
		this.session = session;
		this.classe = classe;
		this.log = log;
		this.campoOrdenacao = "id";
	}

	@Override
	public void adicionar(T obj) throws ColecaoException {
		try {
			this.session.persist(obj);
		} catch (Exception e) {
			log.fatal("[Erro ao salvar:] " + e.getMessage());
			throw new ColecaoException("Erro ao salvar:" + e.getMessage());
		}
	}

	@Override
	public void atualizar(T obj) throws ColecaoException {
		try {
			this.session.update(obj);
		} catch (Exception e) {
			log.fatal("[Erro ao atualizar:] " + e.getMessage());
			throw new ColecaoException("Erro ao atualizar:" + e.getMessage());
		}
	}

	@Override
	public void excluir(Long id) throws ColecaoException {
		try {
			this.session.delete(this.session.load(this.classe, id));
		} catch (Exception e) {
			log.fatal("[Erro ao excluir:]" + e.getMessage());
			throw new ColecaoException("Erro ao excluir:" + e.getMessage());
		}
	}

	@Override
	public Object buscar() throws ColecaoException {
		Object obj = null;
		Criteria criteria;
		try {
			criteria = this.session.createCriteria(this.classe);
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.eq(
							(String) this.listaRestricao.get(contador),
							this.listaRestricao.get(contador + 1)));
				}
			}
			obj = criteria.uniqueResult();
		} catch (Exception e) {
			log.fatal("[Erro ao buscar:] " + e.getMessage());
			throw new ColecaoException("Erro ao buscar:" + e.getMessage());
		}
		return obj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> todos() throws ColecaoException {
		List<T> lista = null;
		try {
			Criteria criteria = this.session.createCriteria(this.classe);

			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.eq(
							(String) this.listaRestricao.get(contador),
							this.listaRestricao.get(contador + 1)));
				}
			}
			criteria.addOrder(Order.asc(this.campoOrdenacao));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			lista = criteria.list();

		} catch (Exception e) {
			log.fatal("[Erro ao listar:]" + e.getMessage());
			throw new ColecaoException("Erro ao listar: " + e.getMessage());
		}
		return lista;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> todos(int primeiro) throws ColecaoException {
		List<T> lista = null;
		try {
			Criteria criteria = this.session.createCriteria(this.classe);
				if (this.listaRestricao != null) {
					for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
						criteria.add(Restrictions.eq(
								(String) this.listaRestricao.get(contador),
								this.listaRestricao.get(contador + 1)));
					}
				}	
			criteria.setFirstResult(primeiro);
			criteria.setMaxResults(this.maxResultado);
			criteria.addOrder(Order.asc(this.campoOrdenacao));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			lista = criteria.list();

		} catch (Exception e) {
			log.fatal("[Erro ao listar:]" + e.getMessage());
			throw new ColecaoException("Erro ao listar: " + e.getMessage());
		}
		return lista;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> todosComExcecao() throws ColecaoException {
		List<T> lista = null;
		try {

			Criteria criteria = this.session.createCriteria(this.classe);
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.or(
							Restrictions.isNull((String) this.listaRestricao
									.get(contador)), (Restrictions.ne(
									(String) this.listaRestricao.get(contador),
									this.listaRestricao.get(contador + 1)))));

				}
			}
			criteria.addOrder(Order.asc(this.campoOrdenacao));
			lista = criteria.list();

		} catch (Exception e) {
			log.fatal("[Erro ao listar:]" + e.getMessage());
			throw new ColecaoException("Erro ao listar: " + e.getMessage());
		}
		return lista;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<T> todosComExcecao(int primeiro) throws ColecaoException {
		List<T> lista = null;
		try {

			Criteria criteria = this.session.createCriteria(this.classe);
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.or(
							Restrictions.isNull((String) this.listaRestricao
									.get(contador)), (Restrictions.ne(
									(String) this.listaRestricao.get(contador),
									this.listaRestricao.get(contador + 1)))));

				}
			}
			criteria.setFirstResult(primeiro);
			criteria.setMaxResults(this.maxResultado);
			criteria.addOrder(Order.asc(this.campoOrdenacao));
			lista = criteria.list();

		} catch (Exception e) {
			log.fatal("[Erro ao listar:]" + e.getMessage());
			throw new ColecaoException("Erro ao listar: " + e.getMessage());
		}
		return lista;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<T> todosComCampoMaiorQue(String campoMaior, Object valorMaior)
			throws ColecaoException {
		List<T> lista = null;
		Criteria criteria;

		try {
			criteria = this.session.createCriteria(this.classe);
			criteria.add(Restrictions.gt(campoMaior, valorMaior));
			
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.eq(
							(String) this.listaRestricao.get(contador),
							this.listaRestricao.get(contador + 1)));
				}
			}

			criteria.setMaxResults(this.maxResultado);
			criteria.addOrder(Order.asc(this.campoOrdenacao));
			lista = criteria.list();
		} catch (Exception e) {
			log.fatal("[Erro ao listar]: " + e.getMessage());
			throw new ColecaoException("Erro ao listar: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public Long contar() throws ColecaoException {
		Long qtd = null;
		try {
			Criteria criteria = this.session.createCriteria(this.classe);
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.eq(
							(String) this.listaRestricao.get(contador),
							this.listaRestricao.get(contador + 1)));
				}
			}
			criteria.setProjection(Projections.rowCount());
			qtd = (Long) criteria.uniqueResult();
		} catch (Exception e) {
			log.fatal("[Erro ao contar:]" + e.getMessage());
			throw new ColecaoException("Erro ao contar: " + e.getMessage());
		}
		return qtd;
	}
	
	
	@Override
	public Long contarComCampoMaiorQue(String campoMaior, Object valorMaior)
	throws ColecaoException {	
		Long qtd = null;
		try {
			Criteria criteria = this.session.createCriteria(this.classe);
			criteria.add(Restrictions.gt(campoMaior, valorMaior));
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.eq(
							(String) this.listaRestricao.get(contador),
							this.listaRestricao.get(contador + 1)));
				}
			}
			criteria.setProjection(Projections.rowCount());
			qtd = (Long) criteria.uniqueResult();
		} catch (Exception e) {
			log.fatal("[Erro ao contar:]" + e.getMessage());
			throw new ColecaoException("Erro ao contar: " + e.getMessage());
		}
		return qtd;
	}	
	
	
	@Override
	public Long contarComExcecao() throws ColecaoException {
		Long qtd = null;
		try {
			Criteria criteria = this.session.createCriteria(this.classe);
			if (this.listaRestricao != null) {
				for (int contador = 0; contador < this.listaRestricao.size(); contador = contador + 2) {
					criteria.add(Restrictions.or(
							Restrictions.isNull((String) this.listaRestricao
									.get(contador)), (Restrictions.ne(
									(String) this.listaRestricao.get(contador),
									this.listaRestricao.get(contador + 1)))));

				}
			}
			criteria.setProjection(Projections.rowCount());
			qtd = (Long) criteria.uniqueResult();
		} catch (Exception e) {
			log.fatal("[Erro ao contar:]" + e.getMessage());
			throw new ColecaoException("Erro ao contar: " + e.getMessage());
		}
		return qtd;
	}
	
	
	

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Class<T> getClasse() {
		return classe;
	}

	public void setClasse(Class<T> classe) {
		this.classe = classe;
	}

	public String getCampoOrdenacao() {
		return campoOrdenacao;
	}

	public void setCampoOrdenacao(String campoOrdenacao) {
		this.campoOrdenacao = campoOrdenacao;
	}

	public int getMaxResultado() {
		return maxResultado;
	}

	public void setMaxResultado(int maxResultado) {
		this.maxResultado = maxResultado;
	}

	public List<Object> getListaRestricao() {
		return listaRestricao;
	}

	public void setListaRestricao(List<Object> listaRestricao) {
		this.listaRestricao = listaRestricao;
	}

			
}
