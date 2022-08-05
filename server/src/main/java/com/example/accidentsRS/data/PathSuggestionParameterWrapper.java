package com.example.accidentsRS.data;

public final class PathSuggestionParameterWrapper {

    private String startPointId;
    private String endPointId;
    private boolean considerAccidentPredictions;

    public String getStartPointId() {
        return startPointId;
    }

    public void setStartPointId(String startPointId) {
        this.startPointId = startPointId;
    }

    public String getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(String endPointId) {
        this.endPointId = endPointId;
    }

    public boolean isConsiderAccidentPredictions() {
        return considerAccidentPredictions;
    }

    public void setConsiderAccidentPredictions(boolean considerAccidentPredictions) {
        this.considerAccidentPredictions = considerAccidentPredictions;
    }
}
