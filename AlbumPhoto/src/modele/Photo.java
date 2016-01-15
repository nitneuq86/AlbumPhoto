package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
	@Transient
	private String date;
	@Transient
	private String photographe;
	@Transient
	private String ou;
	@Transient
	private String[] qui;
	@Transient
	private String[] quoi;
	@Transient
	private String evenement;
	
	
	public Photo() {
	}
	
	public Photo(Album album, Personne createur, String titre, String date, String photographe, String ou, String[] qui, String[] quoi, String evenement) {
		this.album = album;
		this.createur = createur;
		this.album.getPhotos().add(this);
		this.url = "";
		this.titre = titre;
		this.date = date;
		this.photographe = photographe;
		this.ou = ou;
		this.qui = qui;
		this.quoi = quoi;
		this.evenement = evenement;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPhotographe() {
		return photographe;
	}

	public void setPhotographe(String photographe) {
		this.photographe = photographe;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String[] getQui() {
		return qui;
	}

	public void setQui(String[] qui) {
		this.qui = qui;
	}

	public String[] getQuoi() {
		return quoi;
	}

	public void setQuoi(String[] quoi) {
		this.quoi = quoi;
	}

	public String getEvenement() {
		return evenement;
	}

	public void setEvenement(String evenement) {
		this.evenement = evenement;
	}
	
	
}
