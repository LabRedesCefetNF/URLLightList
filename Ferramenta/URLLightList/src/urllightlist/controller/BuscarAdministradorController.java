package urllightlist.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.colecao.ColecaoAdministradorEmBDR;
import urllightlist.entidade.Administrador;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AdministradorService;
import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class BuscarUsuarioController
 */
public class BuscarAdministradorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuscarAdministradorController() {
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
		String nome = request.getParameter("nome");
	

		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
				session, log);
		try {
			if (nome != null && nome.length() > 0) {
				AdministradorService admService = new AdministradorService(colecaoAdministrador);
				Administrador adm = admService.buscarAdmPorLogin(nome);
				request.setAttribute("adm", adm);
				RequestDispatcher dispatcher = request.getRequestDispatcher("gerenciarAdministrador.jsp");
				dispatcher.forward(request, response);

			} else {
				throw new ControllerException(
						"Preencha o campo nome para a busca");
			}

		} catch (Exception e) {
			throw new ServletException(e);

		}
	}

}
