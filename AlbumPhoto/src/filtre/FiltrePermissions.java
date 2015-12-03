package filtre;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controleur.Connexion;
import modele.Utilisateur;

//@WebFilter(urlPatterns = {"/Album", "/Album/*"})
@WebFilter(urlPatterns = { "/Album", "/Album/*" })
public class FiltrePermissions implements Filter {

	public static String ATT_CONNECTION_REQUESTED_URL = "connectionRequestedUrl";

	public FiltrePermissions() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		if (session.getAttribute(Connexion.ATT_USER_SESSION) != null) {
			chain.doFilter(request, response);
		} else {
			session.setAttribute(ATT_CONNECTION_REQUESTED_URL, ((HttpServletRequest) request).getRequestURI());
			((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/Connexion");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
