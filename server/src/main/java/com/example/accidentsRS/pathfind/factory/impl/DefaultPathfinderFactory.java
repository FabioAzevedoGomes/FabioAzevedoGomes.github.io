package com.example.accidentsRS.pathfind.factory.impl;

import com.example.accidentsRS.pathfind.PathFinder;
import com.example.accidentsRS.pathfind.algorithm.AStarPathFinder;
import com.example.accidentsRS.pathfind.algorithm.AbstractPathFinder;
import com.example.accidentsRS.pathfind.factory.GraphFactory;
import com.example.accidentsRS.pathfind.factory.PathfinderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultPathfinderFactory implements PathfinderFactory {

    @Autowired
    AutowireCapableBeanFactory beanFactory;

    @Override
    public PathFinder getAStarPathfinder() {
        final AbstractPathFinder pathFinder = new AStarPathFinder();
        pathFinder.setDefaultGraphFactory(beanFactory.getBean(GraphFactory.class));
        return pathFinder;
    }
}
