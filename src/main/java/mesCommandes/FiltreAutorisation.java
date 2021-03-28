package mesCommandes;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FiltreAutorisation implements Filter {
	private FilterConfig filterConfig = null;
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {
		String nom = null;
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		Cookie[] cookies = hrequest.getCookies();
		// test s'il existe un cookie dont l'attribut est "nom"
		nom = Identification.chercheNom(cookies);
		if(nom==null) { 
			// cas ou il n'existe pas
			hresponse.sendRedirect(hrequest.getContextPath()+"/servlet/sinscrire");
		}
		else {
			chain.doFilter(request, response); }
		}
	public void destroy() {
		this.filterConfig = null;
		}
}
