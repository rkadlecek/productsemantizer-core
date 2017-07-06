package sk.kadlecek.productsemantizer.dao;

import sk.kadlecek.productsemantizer.exception.DatabaseException;

public interface GenericDAO {

    void createTableSchemaIfNotExists() throws DatabaseException;

}
