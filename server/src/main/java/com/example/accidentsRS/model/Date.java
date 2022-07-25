package com.example.accidentsRS.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Date {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private java.util.Date date;
    private Integer weekday;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone="GMT-3")
    private java.util.Date hour;
    private Long time_of_day;

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public java.util.Date getHour() {
        return hour;
    }

    public void setHour(java.util.Date hour) {
        this.hour = hour;
    }

    public Long getTime_of_day() {
        return time_of_day;
    }

    public void setTime_of_day(Long time_of_day) {
        this.time_of_day = time_of_day;
    }
}