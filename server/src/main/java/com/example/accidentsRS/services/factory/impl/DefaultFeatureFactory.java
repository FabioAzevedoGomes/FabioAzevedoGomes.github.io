package com.example.accidentsRS.services.factory.impl;

import com.example.accidentsRS.dao.AccidentDao;
import com.example.accidentsRS.dao.ClimateDao;
import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.model.prediction.Region;
import com.example.accidentsRS.services.ValueSearchService;
import com.example.accidentsRS.services.factory.FeatureFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DefaultFeatureFactory implements FeatureFactory {

    @Autowired
    ClimateDao defaultClimateDao;

    @Autowired
    AccidentDao defaultAccidentDao;

    @Autowired
    ValueSearchService defaultValueSearchService;

    private static final float TIME_OF_DAY_MAX = 82800000;
    private static final float MAX_ACCIDENTS_PER_REGION = 3.0f;
    private static final float DEATHS_MAX = 3.0f;
    private static final float SERIOUS_INJURIES_MAX = 5.0f;
    private static final float LIGHT_INJURIES_MAX = 25.0f;
    private static final float HEAVY_RAIN_PRECIPITATION_THRESHOLD_MM = 10.0f;

    private List<AccidentModel> getAllAccidents(final Region region, final Date date) {
        return defaultAccidentDao.getAccidentsWithinSpaceTime(region.getBounds(), date);
    }

    private List<ClimateModel> getAllClimate(final Date date) {
        return defaultClimateDao.getClimateOnDate(date);
    }

    private List<Date> getRelevantDates(final Date date) {
        List<Date> relevantDates = defaultValueSearchService.getAllDates();
        int position = relevantDates.indexOf(date);
        if (position < 7) {
            position = relevantDates.size() - 1;
        }

        return relevantDates.subList(position - 8, position - 1);
    }

    private float computeRiskLevel(float total_accidents, float deaths, float serious, float light) {
        return ((deaths / total_accidents) + ((serious / 2.0f) / total_accidents) + ((light / 3.0f) / total_accidents)) / 3.0f;
    }

    private float[] processAccidents(final List<AccidentModel> accidents, final Date relevantDate) {
        Calendar date = Calendar.getInstance();
        date.setTime(relevantDate);
        float[] featureArray = new float[]{
                //0.0f,
                //0.0f,
                //0.0f,
                //0.0f,
                0.0f,
                (date.get(Calendar.DAY_OF_WEEK) - 1) / 6.0f
                //0.0f
        };
        if (!CollectionUtils.isEmpty(accidents)) {
            float totalAccidents = accidents.size() / MAX_ACCIDENTS_PER_REGION;
            float totalDeaths = 0;
            float totalSerious = 0;
            float totalLight = 0;
            float avgTime = 0;
            for (AccidentModel accidentModel : accidents) {
                totalDeaths += accidentModel.getFatality().getDeaths();
                totalSerious += accidentModel.getFatality().getSerious_injuries();
                totalLight += accidentModel.getFatality().getLight_injuries();
                avgTime += accidentModel.getDate().getTime_of_day();
            }

            float avgRisk = computeRiskLevel(accidents.size(), totalDeaths, totalSerious, totalLight);
            totalDeaths /= accidents.size();
            totalDeaths /= DEATHS_MAX;
            totalSerious /= accidents.size();
            totalSerious /= SERIOUS_INJURIES_MAX;
            totalLight /= accidents.size();
            totalLight /= LIGHT_INJURIES_MAX;
            avgTime /= accidents.size();
            avgTime /= TIME_OF_DAY_MAX;
            featureArray = new float[]{
                    //Math.min(totalAccidents, 1.0f),
                    //Math.min(totalDeaths, 1.0f),
                    //Math.min(totalSerious, 1.0f),
                    //Math.min(totalLight, 1.0f),
                    Math.min(avgRisk, 1.0f),
                    (date.get(Calendar.DAY_OF_WEEK) - 1) / 6.0f
                    //Math.min(avgTime, 1.0f)
            };
        }
        return featureArray;
    }

    private float[] processClimate(final List<ClimateModel> climateModelList) {
        float[] featureArray = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        if (!CollectionUtils.isEmpty(climateModelList)) {
            float totalWeather = climateModelList.size();
            float avgVisibility = 0.0f;
            float avgPrecipitation = 0.0f;
            float avgHumidity = 0.0f;

            for (ClimateModel climateModel : climateModelList) {
                avgVisibility += climateModel.getVisibility();
                avgPrecipitation += climateModel.getPrecipitationMm();
                avgHumidity += climateModel.getRelativeHumidityPercentage();
            }

            avgVisibility /= totalWeather;
            avgPrecipitation /= totalWeather;
            avgPrecipitation /= HEAVY_RAIN_PRECIPITATION_THRESHOLD_MM;
            avgHumidity /= totalWeather;
            featureArray = new float[]{
                    Math.min(avgVisibility, 1.0f),
                    Math.min(avgPrecipitation, 1.0f),
                    //Math.min(avgHumidity, 1.0f)
            };
        }
        return featureArray;
    }

    @Override
    public INDArray getFeaturesForRegionAndDate(final Region region, final Date date) {
        int ACC_FEATURES = 2;
        int CLIMATE_FEATURES = 2;
        float[][] featuresJavaArray = new float[7][ACC_FEATURES + CLIMATE_FEATURES];
        int index = 0;
        for (final Date relevantDate : getRelevantDates(date)) {
            final List<AccidentModel> allAccidents = getAllAccidents(region, relevantDate);
            float[] accidentFeatures = processAccidents(allAccidents, relevantDate);
            System.arraycopy(accidentFeatures, 0, featuresJavaArray[index], 0, ACC_FEATURES);
            final List<ClimateModel> allClimate = getAllClimate(relevantDate);
            float[] climateFeatures = processClimate(allClimate);
            System.arraycopy(climateFeatures, 0, featuresJavaArray[index], ACC_FEATURES, CLIMATE_FEATURES);
            index++;
        }
        INDArray featureArray = Nd4j.create(featuresJavaArray);
        return featureArray.reshape(1, ACC_FEATURES + CLIMATE_FEATURES, 7);
    }
}
