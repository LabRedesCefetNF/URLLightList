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
import urllightlist.colecao.ColecaoDominio;
import urllightlist.colecao.ColecaoDominioEmBDR;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.excecao.ControllerException;
import urllightlist.service.CategoriaService;
import urllightlist.service.DominioService;
import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class BuscarDominioController
 */
public class BuscarDominioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuscarDominioController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String nome = request.getParameter("nomeDominio");
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(session,log);
		ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(session,log);
		try {
			if(nome!=null && nome.length()>0){
			DominioService dService = new DominioService(colecaoDominio);
			Dominio dominio = dService.buscarDominioPorNome(nome);
			CategoriaService categoriaService = new CategoriaService(colecaoCategoria);
			List<Categoria>listaCategoria = categoriaService.listarCategoria();
			request.setAttribute("listaCategoria", listaCategoria);
			request.setAttribute("dominio", dominio);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("gerenciarDominio.jsp");
			dispatcher.forward(request, response);
			}else{
				throw new ControllerException("Prencha o campo nome.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
