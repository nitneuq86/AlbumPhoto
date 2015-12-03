package filtre;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@WebFilter("/*")
public class FiltrePermissions implements Filter {

	public static String ATT_CONNECTION_REQUESTED_URL = "connectionRequestedUrl";
	
	public static String ATT_CONNECTION_REQUESTED_URL = "connectionRequestedUrl";
	
	public static String[] cheminsAccessibles = {"/", "/Connexion", "/ressources/.*"};

	public FiltrePermissions() {}

	public void destroy() {}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        
        String path = request.getRequestURI().substring("/AlbumPhoto".length());
        
        for(String cheminAccessible : cheminsAccessibles) {
        	Matcher matcher = (Pattern.compile(cheminAccessible)).matcher(path);
        	if(matcher.matches()) {
        		if(path.equals("/")) request.getRequestDispatcher("/vue/accueil.jsp").forward(request, response);
        		else chain.doFilter(request, response);
        		return;
        	}
        }
        
        if(session.getAttribute(Connexion.ATT_USER_SESSION) != null){
			chain.doFilter(request, response);
		}
		else {
			
			session.setAttribute(ATT_CONNECTION_REQUESTED_URL, request.getRequestURI());
			response.sendRedirect(request.getContextPath() + "/Connexion");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
