package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import metier.Sparql;

@Entity
public class Photo {
	public static final String path = "/Albumz/";
	
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Album album;
	private Personne createur;
	private String url;
	private String titre;
	
	public Photo() {
	}

	public Photo(Album album, Personne createur, String titre) {
		this.album = album;
		this.createur = createur;
		this.album.getPhotos().add(this);
		this.titre = titre;
	}
	
	public void genererURL(){
		this.url = id + ".jpg";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Personne getCreateur() {
		return createur;
	}

	public void setCreateur(Personne createur) {
		this.createur = createur;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
}
