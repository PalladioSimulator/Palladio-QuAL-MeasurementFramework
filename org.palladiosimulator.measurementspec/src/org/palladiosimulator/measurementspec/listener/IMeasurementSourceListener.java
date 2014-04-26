package org.palladiosimulator.measurementspec.listener;

import org.palladiosimulator.measurementspec.Measurement;

public interface IMeasurementSourceListener {

    public void newMeasurementAvailable(Measurement newMeasurement);

    /**
     * After having registered at a {@link Calculator}, this method gets invoked
     * to inform the listener about being unregistered.
     */
    public void preUnregister();
}
