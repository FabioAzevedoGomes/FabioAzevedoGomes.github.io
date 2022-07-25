package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.AccidentConverter;
import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.model.AccidentModel;
import org.springframework.stereotype.Component;

@Component
public class DefaultAccidentConverter implements AccidentConverter {

    @Override
    public AccidentModel convert(AccidentData accidentData) {
        AccidentModel accidentModel = new AccidentModel();
        accidentModel.setType(accidentData.getType());
        accidentModel.setAddress(accidentData.getAddress());
        accidentModel.setFatality(accidentData.getFatality());
        accidentModel.setInvolvedEntities(accidentData.getInvolved_entities());
        accidentModel.setDate(accidentData.getDate_time());
        accidentModel.setClimate(accidentData.getClimate());
        accidentModel.setExternalId(accidentData.getExternalId());
        return accidentModel;
    }

}
