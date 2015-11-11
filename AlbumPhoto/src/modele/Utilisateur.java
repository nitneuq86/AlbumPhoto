package modele;

public class Utilisateur {
	private Personne personne;
	private String login;
	
	public Utilisateur(Personne personne, String login){
		this.personne = personne;
		this.login = login;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
