package com.example.accidentsRS.dao.factory.impl;

import com.example.accidentsRS.dao.factory.MongoUpdateFactory;
import com.example.accidentsRS.model.update.UpdateModel;
import com.example.accidentsRS.model.update.UpdateTypeEnum;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultMongoUpdateFactory implements MongoUpdateFactory {

    @Override
    public Update createUpdateFromValues(final List<UpdateModel> updateValues) {
        final Update update = new Update();
        updateValues.forEach(updateWrapperModel -> {
            if (UpdateTypeEnum.PUSH.equals(updateWrapperModel.getType())) {
                update.push(updateWrapperModel.getField(), updateWrapperModel.getValue());
            } else if (UpdateTypeEnum.SET.equals(updateWrapperModel.getType())) {
                update.push(updateWrapperModel.getField(), updateWrapperModel.getValue());
            }
        });
        return update;
    }
}
