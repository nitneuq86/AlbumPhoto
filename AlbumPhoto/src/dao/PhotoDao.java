package dao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;

import org.apache.openjpa.util.OpenJPAId;

import metier.Sparql;
import modele.Photo;

public class PhotoDao extends JPADao<Photo, Integer> {

	public PhotoDao(EntityManager em) {
		super(em);
	}

	@Override
	public void delete(Photo obj) {
		supprimerPhotoGraph(((modele.Photo) obj).getId(), ((modele.Photo) obj).getAlbum().getId(), obj.getUrl());
		super.delete(obj);
	}
	
	@Override
	public OpenJPAId create(Photo obj) {
		OpenJPAId id = super.create(obj);
		obj.genererURL();
		update(obj);
		ajoutPhotoGraph(obj.getId(), obj.getTitre(), obj.getDate(), obj.getPhotographe(), obj.getAlbum().getId(), obj.getOu(), obj.getQui(), obj.getQuoi(), obj.getEvenement());
		return id;
	}
	

	@Override
	public Photo read(Integer id) {
		Photo photo = super.read(id);
		return photo;
	}
	
	public void supprimerPhotoGraph(int id, int idAlbum, String url){
		String supprPhoto = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {?photo ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		?photo ?p ?v ."
				+ "		FILTER (?photo = :photo" + id + ")"
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprPhoto);
				
		String supprPhotoAlbum = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {?album ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		?album ?p ?v ."
				+ "		FILTER (?album = :album" + idAlbum + " && ?p = :hasPhoto && ?v = :photo" + id + " )"
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprPhotoAlbum);
		
		try {
			System.out.println("File deleted : " + Files.deleteIfExists(Paths.get(filtre.FiltrePermissions.PATH_WORKSPACE + modele.Photo.path + url)));
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void ajoutPhotoGraph(int id, String titre, String date, String createur, int idAlbum, String ou, String[] qui, String[] quoi, String evenement) {	
		String quoiString = "";
		String selfie = "";
		String evenementString = "";
		for (int i = 0; i < quoi.length; i++) {
			if(!quoi[i].trim().equals("")){
				String uriQuoi = ":" + quoi[i].trim().replace(" ", "_").toLowerCase();
				String insereQuoi = 
						  "INSERT DATA {GRAPH IMSS: {"
				        + uriQuoi + " a :Objet ;"
		        		+ ":title \"" + quoi[i] + "\" ."
				        + "} }";
				Sparql.getSparql().requeteCRUD(insereQuoi);
				
				quoiString += " :quoi " + uriQuoi + " ;";
			}
		}
		
		String quiString = "";
		for (int i = 0; i < qui.length; i++) {
			if(!qui[i].equals("")){
				quiString += " :who <" + qui[i] + ">";
				if(i == qui.length - 1){
					quiString += " .";
				} else {
					quiString += " ;";
				}
			}
		}
		
		if(qui.length == 1 && ("<" + qui[0] + ">").equals(createur)){
			selfie += "rdf:type :Selfie ;"; 
		}
		
		if(!evenement.equals("")){
			evenementString += ":type <" + evenement + "> ;";
		}
		
		String inserePhoto = 
				  "INSERT DATA {GRAPH IMSS: {"
		        + "		:photo" + id + " rdf:type :Photo ;"
		        + "     				 dc:title \"" + titre + "\" ;"
        		+ "						 :hasID \"" + id + "\" ;"
				+ "						 :when \"" + date + "\" ;"
				+ "						 :creator " + createur + " ;"
				+ "						 :hasAlbum :album" + idAlbum + " ;"
				+ "						 " + selfie
				+ "						 :where " + ou + " ;"
				+ "						 " + quoiString
				+ "					 	 " + evenementString
				+ "						 " + quiString
		        + "} }";
		Sparql.getSparql().requeteCRUD(inserePhoto);
				
		String insereDansAlbum = 
				  "INSERT DATA {GRAPH IMSS: {"
		        + ":album" + idAlbum + " :hasPhoto :photo" + id + " ."
		        + "} }";
		Sparql.getSparql().requeteCRUD(insereDansAlbum);
	}
}
