package urllightlist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.entidade.Categoria;
import urllightlist.excecao.ControllerException;
import urllightlist.service.CategoriaService;
import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class ListarCategoriasController
 */
public class ListarCategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListarCategoriaController() {
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
		String pagina = request.getParameter("pag");
		List<Categoria> listaCategoria = null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();

		try {
			if (pagina != null) {

				ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(
						session, log);
				CategoriaService categoriaService = new CategoriaService(colecaoCategoria);
				
				listaCategoria = categoriaService.listarCategoria();
				request.setAttribute("listaCategoria", listaCategoria);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(pagina + ".jsp");
				dispatcher.forward(request, response);

			}else{
				throw new ControllerException("Não foi listar categoria");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			session.close();
		}
	}
}
