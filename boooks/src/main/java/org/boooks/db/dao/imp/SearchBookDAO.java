package org.boooks.db.dao.imp;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

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
    
    
    
    public Page<Book> findBookByQuery (final String q, Pageable pageable) {
    	
    	/** search predicate **/
    	Specification<Book> selectSpec = new Specification<Book>() {
			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				

				// fetch authors
				root.fetch(Book_.authors, JoinType.LEFT);
				
				Predicate where = null;
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				if ( q != null ) {
					
					String queryUpperCase = q.toUpperCase();
					
					// search in author
					Subquery<Book> subquery = query.subquery(Book.class);
					Root<Book> subRootEntity = subquery.from(Book.class);
					Path<Author> authorSubQueryPath =  subRootEntity.join(Book_.authors);
					subquery.select(subRootEntity);
					
					Path<String> namePath = authorSubQueryPath.get(Author_.name);
					Predicate authorNameEquals = builder.like(builder.upper(namePath),  "%" + queryUpperCase + "%" );
					Predicate correlatePredicate = builder.equal(subRootEntity, root);
					
					Predicate subWhere = builder.and(correlatePredicate, authorNameEquals );
					subquery.where(subWhere);
					predicates.add(builder.exists(subquery));
					
				
					// search in title
					Path<String> titlePath = root.get(Book_.title);
					predicates.add(builder.like(builder.upper(titlePath),  "%" + queryUpperCase + "%" ));
				}

				if ( !predicates.isEmpty() ) {
					where = builder.or(predicates.toArray(new Predicate[predicates.size()]));
				}
				
				return where;
			}
		};
		
		Specification<Book> countSpec = new Specification<Book>() {
			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				Predicate where = null;
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				if ( q != null ) {
					
					String queryUpperCase = q.toUpperCase();
					
					// search in author
					Join<Book, Author> authorPath = root.join(Book_.authors, JoinType.LEFT);
					Path<String> namePath = authorPath.get(Author_.name);
					predicates.add(builder.like(builder.upper(namePath),  "%" + queryUpperCase + "%" ));
				
					// search in title
					Path<String> titlePath = root.get(Book_.title);
					predicates.add(builder.like(builder.upper(titlePath),  "%" + queryUpperCase + "%" ));
				}

				if ( !predicates.isEmpty() ) {
					where = builder.or(predicates.toArray(new Predicate[predicates.size()]));
				}
				
				return where;
			}
		};
		
		return findBook(selectSpec, countSpec, pageable);
    }
    
    public Page<Book> findBookByEmail(final String email, Pageable pageable) {
		Specification<Book> selectSpec = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				root.fetch(Book_.authors, JoinType.LEFT);
				Path<UserEntity> bookPath = root.get(Book_.user);
				Path<String> emailPath = bookPath.get(UserEntity_.email);
				return builder.equal(emailPath, email);
			}
		};
		
    	Specification<Book> countSpec = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Path<UserEntity> bookPath = root.get(Book_.user);
				Path<String> emailPath = bookPath.get(UserEntity_.email);
				return builder.equal(emailPath, email);
			}
		};
		
		return findBook(selectSpec, countSpec, pageable);
	} 
    
    public Page<Book> findBookByAuthor(final String author, Pageable pageable) {
		
    	Specification<Book> selectSpec = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				// author join
				root.fetch(Book_.authors, JoinType.LEFT); 
				
				// author predicate
				Subquery<Book> subquery = query.subquery(Book.class);
				Root<Book> subRootEntity = subquery.from(Book.class);
				Path<Author> authorSubQueryPath =  subRootEntity.join(Book_.authors);
				subquery.select(subRootEntity);
				
				Path<String> namePath = authorSubQueryPath.get(Author_.name);
				Predicate authorNameEquals = builder.equal(namePath, author);
				
				Predicate correlatePredicate = builder.equal(subRootEntity, root);
				
				Predicate where = builder.and(correlatePredicate, authorNameEquals );
				subquery.where(where);
				return builder.exists(subquery);
			}
		};
		
		Specification<Book> countSpec = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Path<Author> authorPath = root.join(Book_.authors);
				Path<String> namePath = authorPath.get(Author_.name);
				return builder.equal(namePath, author);
			}
		};
		return findBook(selectSpec, countSpec, pageable);
	}
    
 
    /**
     * Création d'un DAO spécifique car le fetch ne fonctionne page avec un SimpleJpaRepository
     * Le fetch s'effectue aussi sur le count, ce qui provoque une erreur.
     * 
     * @param specification
     * @param pageable
     * @return
     */
    private Page<Book> findBook(Specification<Book> selectSpec, Specification<Book> countSpec, Pageable pageable) {
    	
    	/** get content paginated **/
    	CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Book> query = builder.createQuery(Book.class);

		Root<Book> root = query.from(Book.class);
		query.select(root);
		Predicate predicate = selectSpec.toPredicate(root, query, builder);
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
		queryCount.select(builder.countDistinct(rootCount));
		Predicate predicateCount = countSpec.toPredicate(rootCount, queryCount, builder);
		if ( predicateCount != null ) {
			queryCount.where(predicateCount);
		}
		TypedQuery<Long> typeQueryCount = em.createQuery(queryCount);
		Long total = typeQueryCount.getSingleResult();
		
		return new PageImpl<Book>(books, pageable, total);
		  
    }

	


	

}
