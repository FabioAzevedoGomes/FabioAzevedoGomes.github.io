package com.example.accidentsRS.pathfind;

import com.example.accidentsRS.exceptions.NoSuchPathException;
import com.example.accidentsRS.model.Location;

import java.util.List;

public interface PathFinder {
    List<Location> getPathBetween(String start, String end) throws NoSuchPathException;
}
