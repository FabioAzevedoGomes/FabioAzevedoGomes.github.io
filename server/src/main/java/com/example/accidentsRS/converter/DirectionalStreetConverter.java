package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.model.DirectionalStreetModel;
import org.springframework.core.convert.converter.Converter;

public interface DirectionalStreetConverter extends Converter<DirectionalStreetData, DirectionalStreetModel> {
    DirectionalStreetModel convert(DirectionalStreetData directionalStreetData);
}
