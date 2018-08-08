package com.genius.znzx.common.mongodb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

@NoRepositoryBean
public class MyMongoRepositoryImpl <T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements MyMongoRepository<T, ID> {

    private final MongoOperations mongoTemplate;
    private final MongoEntityInformation<T, ID> entityInformation;
    
	public MyMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoTemplate) {
		super(metadata, mongoTemplate);
		this.mongoTemplate = mongoTemplate;
	    this.entityInformation = metadata;
	}

	 @Override
	    public boolean update(Query query, Update update) {
	        WriteResult r = mongoTemplate.updateMulti(query, update, entityInformation.getJavaType(),
	                entityInformation.getCollectionName());
	        return r.isUpdateOfExisting();
	    }

	    @Override
	    public long count(Query query, String collectionName) {
	        if (collectionName == null)
	            collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.count(query, entityInformation.getJavaType(), collectionName);
	    }

	    @Override
	    public boolean delete(Query query, String collectionName) {
	        if (collectionName == null)
	            collectionName = entityInformation.getCollectionName();
	        WriteResult r = mongoTemplate.remove(query, entityInformation.getJavaType(), collectionName);
	        return r.isUpdateOfExisting();
	    }

	    @Override
	    public T findOne(Query query, String collectionName) {
	        if (query == null) {
	            return null;
	        }
	        if (collectionName == null)
	            collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.findOne(query, entityInformation.getJavaType(), collectionName);
	    }

	    @Override
	    public List<T> findAll(Query query, String collectionName) {
	        if (query == null) {
	            return Collections.emptyList();
	        }
	        if (collectionName == null)
	            collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.find(query, entityInformation.getJavaType(), collectionName);
	    }
	    @Override
	    public <S> AggregationResults<S> aggregate(TypedAggregation<T> aggregation, Class<S> clazz) {
	        return mongoTemplate.aggregate(aggregation, clazz);
	    }

	    @Override
	    public <S> List<S> distinct(String fieldName, String collectionName) {
	        if (Objects.isNull(collectionName)) collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.getCollection(collectionName).distinct(fieldName);
	    }

	    @Override
	    public <S> List<S> distinct(Criteria criteria, String fieldName, String collectionName) {
	        if (Objects.isNull(collectionName)) collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.getCollection(collectionName).distinct(fieldName, criteria.getCriteriaObject());
	    }

	    @Override
	    public <S> List<S> listAll(Query query, Class<S> clazz, String collectionName) {
	        Class<T> subclass = entityInformation.getJavaType();
	        if (!clazz.isAssignableFrom(subclass)) {
	            throw new IllegalStateException(
	                    String.format("the [%s] must be subclass of [%s]", subclass.getName(), clazz.getName()));
	        }
	        if (collectionName == null)
	            collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.find(query, clazz, collectionName);
	    }

	    @Override
	    public <S> List<S> listAll(Class<S> clazz, String collectionName) {
	        Class<T> subclass = entityInformation.getJavaType();
	        if (!clazz.isAssignableFrom(subclass)) {
	            throw new IllegalStateException(
	                    String.format("the [%s] must be subclass of [%s]", subclass.getName(), clazz.getName()));
	        }
	        if (collectionName == null)
	            collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.findAll(clazz, collectionName);
	    }

	    public boolean upsert(Query query, Update update) {
	        WriteResult r = mongoTemplate.upsert(query, update, entityInformation.getJavaType(),
	                entityInformation.getCollectionName());
	        return r.isUpdateOfExisting();

	    }

	    @Override
	    public <S> List<S> listOne(Criteria criteria, String fieldName, String collectionName, int limit, Sort sort) {
	        List<S> l = new ArrayList<>();
	        if (collectionName == null) collectionName = entityInformation.getCollectionName();

	        List<AggregationOperation> list = new ArrayList<>();
	        if (Objects.nonNull(criteria)) list.add(Aggregation.match(criteria));
	        if (Objects.nonNull(sort)) list.add(Aggregation.sort(sort));
	        if (limit > 0) list.add(Aggregation.limit(limit));
	        list.add(Aggregation.project(fieldName));
	        Aggregation agg = Aggregation.newAggregation(list);
	        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(agg, collectionName, BasicDBObject.class);
	        int idx = fieldName.indexOf('.');
	        String field = idx > 0 ? fieldName.substring(idx + 1) : fieldName;
	        results.forEach(dbObject -> {
	            S s = (S) dbObject.get(field);
	            l.add(s);
	        });
	        return l;
	    }

	    @Override
	    public <V, S> Map<V, S> mapOne(Criteria criteria, Sort sort, String keyParam, String valueParam, String collectionName, int limit) {
	        if (collectionName == null) collectionName = entityInformation.getCollectionName();
	        int idx = valueParam.lastIndexOf('.');
	        String field = idx > 0 ? valueParam.substring(idx + 1) : valueParam;
	        List<AggregationOperation> list = new ArrayList<>();
	        list.add(Aggregation.match(criteria));
	        if (limit > 0) list.add(Aggregation.limit(limit));
	        if (Objects.nonNull(sort)) list.add(Aggregation.sort(sort));
	        list.add(Aggregation.project(keyParam, valueParam));
	        Aggregation agg = Aggregation.newAggregation(list);
	        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(agg, collectionName, BasicDBObject.class);
	        Map<V, S> map = new LinkedHashMap<>();
	        results.forEach(dbObject -> {
	            S s = (S) dbObject.get(field);
	            V key = (V) dbObject.get(keyParam);
	            map.put(key, s);
	        });
	        return map;
	    }

	    @Override
	    public BulkOperations bulkOps(BulkMode mode, String collectionName) {
	        if (Objects.isNull(collectionName)) collectionName = entityInformation.getCollectionName();
	        return mongoTemplate.bulkOps(mode, collectionName);
	    }

	    @Override
	    public <S> AggregationResults<S> aggregate(Aggregation aggregation, String collectionName, Class<S> outputType) {
	        return mongoTemplate.aggregate(aggregation, collectionName, outputType);
	    }

	    @Override
	    public <S> List<S> listObj(Criteria criteria, String fields, int limit, Sort sort, String collectionName, Class<S> outClazz) {
	        String[] f = fields.split(",");
	        if (collectionName == null) collectionName = entityInformation.getCollectionName();
	        List<AggregationOperation> list = new ArrayList<>();
	        if (Objects.nonNull(criteria)) list.add(Aggregation.match(criteria));
	        if (Objects.nonNull(sort)) list.add(Aggregation.sort(sort));
	        if (limit > 0) list.add(Aggregation.limit(limit));
	        list.add(Aggregation.project(f));
	        Aggregation agg = Aggregation.newAggregation(list);
	        AggregationResults<S> results = mongoTemplate.aggregate(agg, collectionName, outClazz);
	        return results.getMappedResults();
	    }

}
