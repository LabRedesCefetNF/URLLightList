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
import urllightlist.excecao.ControllerException;
import urllightlist.service.CategoriaService;
import urllightlist.service.DominioService;
import urllightlist.utilitarios.ResolucaoDeDominio;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class CadastrarDominioController
 */
public class CadastrarDominioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastrarDominioController() {
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

		String nomeCategoria = request.getParameter("categoria");
		String nome = request.getParameter("nome");

		String mensagem=null;
		Transaction transaction = null;
		Logger log = Logger.getLogger("URLLightList");
		Session session = Hibernate.getSessionFactory().openSession();
		ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(session,
				log);
		ColecaoDominio colecaoDominio = new ColecaoDominioEmBDR(session, log);

		try {

			if (nome != null && nome.length() > 0 && nomeCategoria != null
					&& nomeCategoria.length() > 0) {
				transaction = session.beginTransaction();
				CategoriaService cService = new CategoriaService(
						colecaoCategoria);

				Categoria categoria = cService
						.buscarCategoriaPorNome(nomeCategoria);
				Dominio dominio = new Dominio();
				dominio.setNome(nome);
				dominio.setCategoria(categoria);
				DominioService dominioService = new DominioService(
						colecaoDominio);
				ResolucaoDeDominio resolucao = new ResolucaoDeDominio(log);
				mensagem=dominioService.adicionar(dominio, resolucao);
				transaction.commit();

			} else {
				throw new ControllerException(
						"Preencha os campos corretamente.");
			}
			
			Resposta resposta = new Resposta(true, mensagem);
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);

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