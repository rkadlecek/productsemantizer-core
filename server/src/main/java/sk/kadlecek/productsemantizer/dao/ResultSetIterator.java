package sk.kadlecek.productsemantizer.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.function.Function;

public class ResultSetIterator<T> implements Iterator<T> {

    private static final Logger logger = LoggerFactory.getLogger(ResultSetIterator.class);

    private ResultSet resultSet;
    private Function<ResultSet, T> mappingFunction;

    public ResultSetIterator(ResultSet resultSet, Function<ResultSet, T> mappingFunction) {
        super();
        this.resultSet = resultSet;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public boolean hasNext() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("{}", e);
            return false;
        }
    }

    @Override
    public T next() {
        return mappingFunction.apply(resultSet);
    }
}
