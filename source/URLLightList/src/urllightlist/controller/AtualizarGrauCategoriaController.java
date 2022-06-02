package urllightlist.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.service.CategoriaService;
import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class AlterarCategoriaController
 */
public class AtualizarGrauCategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AtualizarGrauCategoriaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String lista = request.getParameter("lista");
		String[] listaCategoria = lista.split(",");

		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(session,
				log);
		try {
			transaction = session.beginTransaction();
			CategoriaService categoriaService = new CategoriaService(
					colecaoCategoria);
			categoriaService.atualizar(listaCategoria);
			transaction.commit();

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("ListarCategoriaController.do?pag=gerenciarGrauCategoria");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ServletException(e);

		} finally {
			session.close();
		}
	}
}