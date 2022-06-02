package urllightlist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.colecao.ColecaoAdministradorEmBDR;
import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.colecao.ColecaoReclassificacao;
import urllightlist.colecao.ColecaoReclassificacaoEmBDR;
import urllightlist.entidade.Administrador;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Reclassificacao;
import urllightlist.service.AdministradorService;
import urllightlist.service.CategoriaService;
import urllightlist.service.ReclassificacaoService;
import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class ListarCategoriaController
 */
public class ListarReclassificacaoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ListarReclassificacaoController() {
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

		
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(session,
				log);
		ColecaoReclassificacao colecaoReclassificacao = new ColecaoReclassificacaoEmBDR(
				session, log);
		ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
				session, log);

		
		HttpSession sessao=request.getSession(false);
		String login=(String) sessao.getAttribute("login");

		try {
			CategoriaService cService = new CategoriaService(colecaoCategoria);
			List<Categoria>listaCategoria = cService.listarCategoria();
			request.setAttribute("listaCategoria", listaCategoria);

			ReclassificacaoService rService = new ReclassificacaoService(
					colecaoReclassificacao);
			AdministradorService admService= new AdministradorService(colecaoAdministrador);
			Administrador adm=admService.buscarAdmPorLogin(login);
			
			List<Reclassificacao>listaReclassificacao = rService.listarReclassificaoPorLogin(adm.getId());
			request.setAttribute("listaReclassificacao", listaReclassificacao);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("reclassificarCategoria.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
