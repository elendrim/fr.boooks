package org.boooks.datageneration;

import java.util.List;

import org.boooks.db.dao.ITypeDAO;
import org.boooks.db.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeGenerator {

	@Autowired
	private ITypeDAO typeDao;


	static private String[] types = {"Nouvelle", "Roman", "Essai", "Thèse", "Manga"};

	public Type create(DataGenerator dg) throws Exception{

		List<Type> lt = typeDao.findAll();

		if(lt.size() == 0){
			for(String s : types){
				Type cur_type = new Type();
				cur_type.setLiType(s);				
				typeDao.save(cur_type);
			}

			lt = typeDao.findAll();
		}


		return dg.getItem(lt);

	}


	static public Type fill(Type g, DataGenerator dg){

		g.setLiType( dg.getItem(types) );

		return g;
	}

}
