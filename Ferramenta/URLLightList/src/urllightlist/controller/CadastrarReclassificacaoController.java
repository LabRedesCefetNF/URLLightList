package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import urllightlist.colecao.ColecaoAdministrador;
import urllightlist.colecao.ColecaoAdministradorEmBDR;
import urllightlist.colecao.ColecaoCategoria;
import urllightlist.colecao.ColecaoCategoriaEmBDR;
import urllightlist.colecao.ColecaoReclassificacao;
import urllightlist.colecao.ColecaoReclassificacaoEmBDR;
import urllightlist.entidade.Administrador;
import urllightlist.entidade.Categoria;
import urllightlist.entidade.Dominio;
import urllightlist.entidade.Reclassificacao;
import urllightlist.excecao.ControllerException;
import urllightlist.service.AdministradorService;
import urllightlist.service.ReclassificacaoService;
import urllightlist.utilitarios.Ouvinte;

import com.google.gson.Gson;

import configuracaoHibernate.Hibernate;

/**
 * Servlet implementation class CadastrarReclassificacaoCategoriaController
 */
public class CadastrarReclassificacaoController extends HttpServlet implements
		Ouvinte {
	private static final long serialVersionUID = 1L;
	private Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastrarReclassificacaoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	
		String categoriasSelecionadas = request.getParameter("categorias");
		String nome = request.getParameter("nomeReclassificacao");
		String[] categorias = new Gson().fromJson(categoriasSelecionadas,
				String[].class);

		HttpSession sessao=request.getSession(false);
		String login=(String) sessao.getAttribute("login");
		
		Transaction transaction = null;
		String mensagem = null;
		Logger log = Logger.getLogger("URLLightList");

		this.session = Hibernate.getSessionFactory().openSession();
		ColecaoCategoria colecaoCategoria = new ColecaoCategoriaEmBDR(
				this.session, log);
		ColecaoReclassificacao colecaoReclassificacao = new ColecaoReclassificacaoEmBDR(
				this.session, log);
		ColecaoAdministrador colecaoAdministrador = new ColecaoAdministradorEmBDR(
				this.session, log);

		try {
			if (nome != null && categorias.length > 0) {
				transaction = this.session.beginTransaction();
				Categoria categoria = new Categoria();
				categoria.setNome(nome);
				categoria.setChecado("notVerify");
				categoria.setGrauPerversidade(0);

				AdministradorService admService = new AdministradorService(
						colecaoAdministrador);
				Administrador admin = admService.buscarAdmPorLogin(login);

				Reclassificacao reclassificacao = new Reclassificacao();
				reclassificacao.setCategoria(categoria);
				reclassificacao.setAdministrador(admin);
				ReclassificacaoService rService = new ReclassificacaoService(
						colecaoReclassificacao, colecaoCategoria, this);
				mensagem = rService.adicionar(reclassificacao, categorias);

				if (transaction.isActive()) {
					transaction.commit();

				}
			} else {
				throw new ControllerException(
						"Campos não preenchidos corretamente.");
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

	@Override
	public void categoriaSalva(Categoria categoria) {
		if (this.session.getTransaction().isActive()) {
			this.session.getTransaction().commit();
			this.session.beginTransaction();
		}

	}

	@Override
	public void dominioSalvo(Dominio dominio) {
		if (this.session.getTransaction().isActive()) {
			this.session.getTransaction().commit();
			this.session.beginTransaction();
		}

	}

	@Override
	public void reclassificacaoSalva(Reclassificacao reclassificacao) {
		if (this.session.getTransaction().isActive()) {
			this.session.getTransaction().commit();
			this.session.beginTransaction();
		}

	}

}