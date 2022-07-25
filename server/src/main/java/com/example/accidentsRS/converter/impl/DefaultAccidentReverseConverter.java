package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.AccidentReverseConverter;
import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.model.AccidentModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultAccidentReverseConverter implements AccidentReverseConverter {

    @Override
    public AccidentData convert(AccidentModel accidentModel) {
        AccidentData accidentData = new AccidentData();
        accidentData.setType(accidentModel.getType());
        accidentData.setAddress(accidentModel.getAddress());
        accidentData.setDate_time(accidentModel.getDate());
        accidentData.setFatality(accidentModel.getFatality());
        accidentData.setInvolved_entities(accidentModel.getInvolvedEntities());
        accidentData.setClimate(accidentModel.getClimate());
        accidentData.setExternalId(accidentModel.getExternalId());
        return accidentData;
    }

    @Override
    public List<AccidentData> convertAll(List<AccidentModel> accidentModelModelList) {
        List<AccidentData> accidentDataList = new ArrayList<>();
        for (AccidentModel accidentModel : accidentModelModelList) {
            accidentDataList.add(convert(accidentModel));
        }
        return accidentDataList;
    }
}
