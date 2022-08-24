package com.example.accidentsRS.data;

import com.example.accidentsRS.model.prediction.Region;
import org.springframework.web.multipart.MultipartFile;

public class InboundPredictor {
    private Region[] domain;
    private MultipartFile predictiveModel;

    public InboundPredictor(Region[] domain, MultipartFile predictiveModel) {
        this.domain = domain;
        this.predictiveModel = predictiveModel;
    }

    public MultipartFile getPredictiveModel() {
        return predictiveModel;
    }

    public void setPredictiveModel(MultipartFile predictiveModel) {
        this.predictiveModel = predictiveModel;
    }

    public Region[] getDomain() {
        return domain;
    }

    public void setDomain(Region[] domain) {
        this.domain = domain;
    }
}
