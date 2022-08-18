package com.example.accidentsRS.dao;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;

import java.util.List;

public interface GenericDao<T> {
    void save(T model) throws PersistenceException;
    List<T> get(List<FilterWrapperModel> filters);
    void update(List<FilterWrapperModel> filters, List<UpdateModel> updateValues) throws PersistenceException;
    void delete(List<FilterWrapperModel> filters) throws PersistenceException;
}
