package modele;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Utilisateur {
	@OneToOne(fetch=FetchType.LAZY)
	private Personne personne;
	@Id
	private String login;
	private String password;

	public Utilisateur(Personne personne, String login, String password) {
		this.personne = personne;
		this.login = login;
		this.password = password;
	}

	public Utilisateur() {	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
