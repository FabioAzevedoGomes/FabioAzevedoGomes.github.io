package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.IntersectionData;
import com.example.accidentsRS.model.IntersectionModel;
import org.springframework.core.convert.converter.Converter;

public interface IntersectionConverter extends Converter<IntersectionData, IntersectionModel> {
    IntersectionModel convert(IntersectionData intersectionData);
}
