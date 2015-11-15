package metier;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import extra.Data;
import modele.Utilisateur;

public class FormConnexion {
	private static final String CHAMP_LOGIN  = "login";
    private static final String CHAMP_PASS   = "pass";
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Utilisateur connexionUtilisateur(HttpServletRequest request ) {
        String login = getValeurChamp(request, CHAMP_LOGIN);
        String pass = getValeurChamp(request, CHAMP_PASS);

        Utilisateur utilisateur = null;

        try {
            utilisateur = validationUtilisateur(login, pass);
        } catch (Exception e ) {
            setErreur( CHAMP_LOGIN + "/" + CHAMP_PASS, e.getMessage() );
        }
        
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la connexion.";
            
        } else {
            resultat = "Échec de connexion.";
        }

        return utilisateur;
    }
    
    private Utilisateur validationUtilisateur(String login, String pass) throws Exception{
    	Utilisateur utilisateur = Data.verificationUtilisateur(login, pass);
    	if(utilisateur == null) throw new Exception( "Mauvaise combinaison login/mot de passe." );
    	return utilisateur;
    }
    
    
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}
