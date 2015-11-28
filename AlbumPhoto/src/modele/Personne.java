package modele;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Personne {
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String prenom;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Album> albums;
	
	public Personne() {}
	
	public Personne(String prenom, String nom){
		this.nom = nom;
		this.prenom = prenom;
		this.albums = new ArrayList<Album>();
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
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
	
	public List<Album> getAlbums(){
		if(albums == null){
			albums= new ArrayList<Album>();
		}
		return this.albums;
	}
	
	public void setAlbums(List<Album> albums){
		this.albums = albums;
	}
}
