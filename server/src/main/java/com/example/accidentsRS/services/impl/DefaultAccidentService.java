package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Climate;
import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.services.AccidentService;
import com.example.accidentsRS.services.factory.QueryFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultAccidentService implements AccidentService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    QueryFactory defaultQueryFactory;

    @Override
    public void createAccidentRecord(final AccidentModel accidentModel) {
        mongoOperations.save(accidentModel);
    }

    @Override
    public List<AccidentModel> findAllMatchingFilters(final List<FilterWrapperModel> accidentFilters) {
        return mongoOperations.find(
                defaultQueryFactory.createQueryFromFilters(accidentFilters),
                AccidentModel.class
        );
    }

    public Update createUpdate(final Climate climate) {
        Update update = new Update();
        update.set("climate", climate);
        return update;
    }

    @Override
    public void updateWithClimateData(final Climate climate, final Date dateTime) {
        mongoOperations.updateMulti(
                defaultQueryFactory.createTimeQuery(dateTime),
                createUpdate(climate),
                AccidentModel.class
        );
    }
}
