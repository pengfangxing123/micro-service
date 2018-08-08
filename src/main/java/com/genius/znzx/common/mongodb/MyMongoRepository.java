package com.genius.znzx.common.mongodb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyMongoRepository <T, ID extends Serializable> extends MongoRepository<T, ID>{
	default T findOne(Query query) {
        return findOne(query, null);
    }

    default List<T> findAll(Query query) {
        return findAll(query, "STK_FORM_LIST_MORE");
    }

    default boolean delete(Query query) {
        return delete(query, null);
    }

    T findOne(Query query, String collectionName);

    List<T> findAll(Query query, String collectionName);

    default <S> List<S> listAll(Class<S> clazz) {
        return listAll(clazz, null);
    }

    <S> List<S> listAll(Class<S> clazz, String collectionName);

    default <S> List<S> listAll(Query query, Class<S> clazz) {
        return listAll(query, clazz, null);
    }

    <S> List<S> listAll(Query query, Class<S> clazz, String collectionName);

    boolean update(Query query, Update update);

    boolean upsert(Query query, Update update);

    default long count(Query query) {
        return count(query, null);
    }

    long count(Query query, String collectionName);

    boolean delete(Query query, String collectionName);

    <S> AggregationResults<S> aggregate(TypedAggregation<T> aggregation, Class<S> clazz);

    default <S> List<S> distinct(String fieldName) {
        return distinct(fieldName, null);
    }

    default <S> List<S> distinct(Criteria criteria, String fieldName) {
        return distinct(criteria, fieldName, null);
    }

    <S> List<S> distinct(String fieldName, String collectionName);

    <S> List<S> distinct(Criteria criteria, String fieldName, String collectionName);

    default <S> List<S> listOne(String field){
    	return listOne(null,field,0,null);
    }

    default <S> List<S> listOne(String key, Object value, String field) {
        return listOne(key, value, field, 0, null);
    }

    default <S> List<S> listOne(String key, Object value, String field, Sort sort) {
        return listOne(key, value, field, 0, sort);
    }

    default <S> List<S> listOne(String key, Object value, String field, int limit) {
        return listOne(key, value, field, limit, null);
    }

    default <S> List<S> listOne(String key, Object value, String field, int limit, Sort sort) {
        Criteria c = Criteria.where(key).is(value);
        return listOne(c, field, null, limit, sort);
    }

    default <S> List<S> listOne(Criteria criteria, String field, int limit, Sort sort) {
        return listOne(criteria, field, null, limit, sort);
    }

    <S> List<S> listOne(Criteria criteria, String fieldName, String collectionName, int limit, Sort sort);

    default <S> Map<ID, S> mapOne(Criteria criteria, String field) {
        return mapOne(criteria, field, null);
    }

    default <S> Map<ID, S> mapOne(Criteria criteria, String field, String collectionName) {
        return mapOne(criteria,null, "_id", field, collectionName, 0);
    }

    default <S> Map<ID, S> mapOne(Criteria criteria, String field, int limit) {
        return mapOne(criteria,null, "_id", field, null, limit);
    }

    <V, S> Map<V, S> mapOne(Criteria criteria, Sort sort, String key, String field, String collectionName, int limit);

    default BulkOperations bulkOps(BulkMode mode) {
        return bulkOps(mode, null);
    }

    BulkOperations bulkOps(BulkMode mode, String collectionName);

	<S>AggregationResults<S> aggregate(Aggregation aggregation, String collectionName, Class<S> outputType);

    <S> List<S> listObj(Criteria criteria, String field, int limit, Sort sort, String collectionName, Class<S> outClazz);

    default <S> List<S> listObj(Criteria criteria, String field, String collectionName, Class<S> outClazz) {
        return listObj(criteria, field, 0, null, collectionName, outClazz);
    }
}
