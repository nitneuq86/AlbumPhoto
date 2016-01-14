package modele;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Personne {
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String prenom;
	@OneToMany(mappedBy = "createur", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Album> albums;
	@OneToOne(mappedBy="personne")
	private Utilisateur utilisateur;
	@Transient
	private String URI;

	public Personne() {}

	public Personne(String prenom, String nom) {
		this.nom = nom;
		this.prenom = prenom;
		this.albums = new ArrayList<Album>();
		this.utilisateur = null;
	}
	
	public Personne(String prenom, String nom, String URI){
		this.nom = nom;
		this.prenom = prenom;
		this.URI = URI;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Album> getAlbums() {
		if (albums == null) {
			albums = new ArrayList<Album>();
		}
		return this.albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}
	
	
}
