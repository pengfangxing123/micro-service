package com.genius.znzx.common.mongodb;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class MyMongoRepositoryFactoryBean <R extends MongoRepository<T, ID>, T, ID extends Serializable> extends MongoRepositoryFactoryBean<R, T, ID> {

	public MyMongoRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}
	
	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new MyMongoRepositoryFactory(operations);
	}

	private static class MyMongoRepositoryFactory<T, I extends Serializable> extends MongoRepositoryFactory {
		private final MongoOperations mongoOperations;
		
		public MyMongoRepositoryFactory(MongoOperations mongoOperations) {
			super(mongoOperations);
			this.mongoOperations=mongoOperations;
		}
		
		@Override
        protected Object getTargetRepository(RepositoryInformation information) {
			Class<T> clazz=(Class<T>) information.getDomainType();
			Class<?> repositoryInterface = information.getRepositoryInterface();  
			MongoEntityInformation<T,Serializable> entityInformation = getEntityInformation(clazz);
			if (isQueryDslRepository(repositoryInterface)) {  
                return new QueryDslMongoRepository(entityInformation, mongoOperations);
            } else {
            	return new MyMongoRepositoryImpl(entityInformation,mongoOperations);
            }
            
        }
		
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        	Class<?> clazz=isQueryDslRepository(metadata.getRepositoryInterface())?
        			QueryDslMongoRepository.class:MyMongoRepositoryImpl.class;
        	return clazz;
        }
        private static boolean isQueryDslRepository(Class<?> repositoryInterface) {  
            return QueryDslUtils.QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        } 
	}
}
