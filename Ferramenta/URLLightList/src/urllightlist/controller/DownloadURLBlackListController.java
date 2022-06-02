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
 * Servlet implementation class DownloadURLBlackListController
 */
public class DownloadURLBlackListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadURLBlackListController() {
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
		String urlBlack = request.getParameter("urlBlack");
		String urlMD5 = request.getParameter("urlMD5");
		try {

			if (urlBlack.length() > 0 && urlBlack != null
					&& urlMD5.length() > 0 && urlMD5!=null) {
				Logger log = Logger.getLogger("URLLightList");
				ManipulacaoDeArquivoXML mArq = new ManipulacaoDeArquivoXML(log);
				DownloadService dService = new DownloadService(mArq);
				dService.downloadURLBlackList(urlBlack, urlMD5);

				Resposta resposta = new Resposta(true);
				String json = (new Gson()).toJson(resposta);
				response.getWriter().write(json);

			} else {
				throw new ControllerException(
						"Preencha a URL e nome do arquivo corretamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Resposta resposta = new Resposta(false, e.getMessage(), e
					.getClass().getName());
			String json = (new Gson()).toJson(resposta);
			response.getWriter().write(json);
		}

	}

}
