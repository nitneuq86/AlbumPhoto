package extra;

import java.util.Date;

import modele.Album;

public class Data {
	public static Album getAlbumTest() {
		Album album = new Album("Vacances en Floride", "Quentin", new Date());
		album.ajouterPhoto("photo1.jpg");
		album.ajouterPhoto("photo7.jpg");
		album.ajouterPhoto("photo23.jpg");
		return album;
	}
}
