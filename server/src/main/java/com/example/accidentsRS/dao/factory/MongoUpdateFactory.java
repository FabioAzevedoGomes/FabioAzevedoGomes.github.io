package com.example.accidentsRS.dao.factory;

import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface MongoUpdateFactory {
    Update createUpdateFromValues(final List<UpdateModel> updateValues);
}
