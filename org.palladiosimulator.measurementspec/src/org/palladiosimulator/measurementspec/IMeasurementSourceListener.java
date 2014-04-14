package org.palladiosimulator.measurementspec;


public interface IMeasurementSourceListener {

    public void newMeasurementAvailable(Measurement measurement);

    /**
     * After having registered at a {@link Calculator}, this method gets invoked
     * to inform the listener about being unregistered.
     */
    public void preUnregister();
}
