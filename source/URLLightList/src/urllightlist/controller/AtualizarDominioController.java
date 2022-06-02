package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoDominioEmBDR;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.service.CategoriaService;
import urllightlist.service.DominioService;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class AlterarDominioController
 */
public class AtualizarDominioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AtualizarDominioController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nomeCategoria = request.getParameter("categoria");
		Long id = Long.valueOf(request.getParameter("id"));

		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		

		try {

			if (nomeCategoria != null && nomeCategoria.length() > 0) {
				ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(session,
						log);
				ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(session, log);				
				transaction = session.beginTransaction();
				CategoriaService categoriaService = new CategoriaService(
						colecaoCategoria);
				Categoria categoria = categoriaService
						.buscarCategoriaPorNome(nomeCategoria);

				DominioService dominioService = new DominioService(
						colecaoDominio);
				Dominio dominio=dominioService.buscarDominioPorId(id);
				dominio.setCategoria(categoria);
		        dominioService.atualizar(dominio);
				transaction.commit();
				
				Resposta resposta = new Resposta(true);
				String json = (new Gson()).toJson(resposta);
				response.getWriter().write(json);
			}
		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.fatal(e.getMessage());
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

		} finally {
			session.close();

		}

	}
}