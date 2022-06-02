package urllightlist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import urllightlist.excecao.ControllerException;
import urllightlist.service.DownloadService;
import urllightlist.utilitarios.ManipulacaoDeArquivoXML;

import com.google.gson.Gson;

/**
 * Servlet implementation class DownloadMD5Controller
 */
public class DownloadMD5Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadMD5Controller() {
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
		String mensagem=null;
		String urlMD5 = request.getParameter("urlMD5");
		try {

			if (urlMD5.length() > 0 && urlMD5 != null) {
				Logger log = Logger.getLogger("URLLightList");
				ManipulacaoDeArquivoXML mArqXML = new ManipulacaoDeArquivoXML(
						log);
				DownloadService dService = new DownloadService(mArqXML);
				dService.downloadMD5(urlMD5);
				mensagem=dService.existeNovoArqURLBlackList();
				
				Resposta resposta = new Resposta(true, mensagem);
				String json = (new Gson()).toJson(resposta);
				response.getWriter().write(json);

			} else {
				throw new ControllerException("Preencha a URL para o download.");
			}
		} catch (Exception e) {
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);
			throw new ServletException(e);
		}

	}
}
