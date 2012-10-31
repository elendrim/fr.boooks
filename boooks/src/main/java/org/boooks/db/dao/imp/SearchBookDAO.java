package org.boooks.db.dao.imp;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.boooks.db.entity.Author;
import org.boooks.db.entity.Author_;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Book_;
import org.boooks.db.entity.UserEntity;
import org.boooks.db.entity.UserEntity_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class SearchBookDAO {

    @PersistenceContext
    private EntityManager em;
    
 
    public Page<Book> findBook(final String email, final String author, Pageable pageable) {
    	
    	/** search predicate **/
    	Specification<Book> specification = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				Predicate where = null;
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				if ( email != null ) {
					Path<UserEntity> bookPath = root.get(Book_.user);
					Path<String> emailPath = bookPath.get(UserEntity_.email);
					predicates.add(builder.equal(emailPath, email));
				}
				
				if ( author != null ) {
					Path<Author> authorPath = root.join(Book_.authors);
					Path<String> namePath = authorPath.get(Author_.name);
					predicates.add(builder.equal(namePath, author));
				}

				if ( !predicates.isEmpty() ) {
					where = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				
				return where;
			}
		};
    	
		/** get content paginated **/
    	CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Book> query = builder.createQuery(Book.class);

		Root<Book> root = query.from(Book.class);
		root.fetch(Book_.authors, JoinType.LEFT);
		query.select(root);
		Predicate predicate = specification.toPredicate(root, query, builder);
		if ( predicate != null ) {
			query.where(predicate);
		}

		if (pageable.getSort() != null) {
			query.orderBy(toOrders(pageable.getSort(), root, builder));
		}

		TypedQuery<Book> typedQuery = em.createQuery(query);
		
		typedQuery.setFirstResult(pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<Book> books = typedQuery.getResultList();
		
		
		/** count **/
		
		CriteriaQuery<Long> queryCount = builder.createQuery(Long.class);
		Root<Book> rootCount = queryCount.from(Book.class);
		queryCount.select(builder.count(rootCount));
		Predicate predicateCount = specification.toPredicate(rootCount, queryCount, builder);
		if ( predicateCount != null ) {
			queryCount.where(predicateCount);
		}
		TypedQuery<Long> typeQueryCount = em.createQuery(queryCount);
		Long total = typeQueryCount.getSingleResult();
		
		return new PageImpl<Book>(books, pageable, total);
		  
    } 

}
