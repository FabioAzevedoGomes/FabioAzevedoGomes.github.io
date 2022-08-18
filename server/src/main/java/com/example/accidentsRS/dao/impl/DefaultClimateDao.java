package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.ClimateDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultClimateDao extends AbstractDao<ClimateModel> implements ClimateDao {

    @Override
    public List<ClimateModel> get(final List<FilterWrapperModel> filters) {
        return super.get(filters, ClimateModel.class);
    }

    @Override
    public void update(final List<FilterWrapperModel> filters,
                       final List<UpdateModel> updateValues) throws PersistenceException {
        super.update(filters, updateValues, ClimateModel.class);
    }

    @Override
    public void delete(final List<FilterWrapperModel> filters) throws PersistenceException {
        super.delete(filters, ClimateModel.class);
    }
}
