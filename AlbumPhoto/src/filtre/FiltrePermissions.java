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

/**
 * Servlet Filter implementation class FiltrePermissions
 */
@WebFilter(urlPatterns = {"/Album", "/Album/*"})
public class FiltrePermissions implements Filter {
	public static final String ACCES_PUBLIC        = "/Connexion";
	
    public FiltrePermissions() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		if(session.getAttribute(Connexion.ATT_SESSION_USER) != null){
			chain.doFilter(request, response);
		} else {
//			((HttpServletRequest) request).getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + ACCES_PUBLIC);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
