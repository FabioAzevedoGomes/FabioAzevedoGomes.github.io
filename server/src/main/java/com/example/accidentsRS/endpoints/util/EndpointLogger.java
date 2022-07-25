package com.example.accidentsRS.endpoints.util;

import java.io.Serializable;
import java.util.logging.Logger;

public final class EndpointLogger {

    private EndpointLogger() { }

    public void logReceivedData(final Logger LOGGER, final Serializable object) {
        LOGGER.info("Received object " + object.toString());
    }
}