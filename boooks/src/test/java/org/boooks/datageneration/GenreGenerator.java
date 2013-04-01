package org.boooks.datageneration;

import java.util.List;

import org.boooks.db.dao.IGenreDAO;
import org.boooks.db.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreGenerator {

	@Autowired
	private IGenreDAO genreDao;


	static private String[] genres = {"Science fiction", "Policier", "Amour", "Fantastique", "Heroic Fantasy"};

	public Genre create(DataGenerator dg) throws Exception{

		List<Genre> lg = genreDao.findAll();

		if(lg.size() == 0){
			for(String s : genres){
				Genre cur_genre = new Genre();
				cur_genre.setLiGenre(s);				
				genreDao.save(cur_genre);
			}

			lg = genreDao.findAll();
		}


		return dg.getItem(lg);

	}


	static public Genre fill(Genre g, DataGenerator dg){

		g.setLiGenre( dg.getItem(genres) );

		return g;
	}

}
