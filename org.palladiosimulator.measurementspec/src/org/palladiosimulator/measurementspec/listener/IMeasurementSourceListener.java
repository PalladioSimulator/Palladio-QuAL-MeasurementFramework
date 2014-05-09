package org.palladiosimulator.measurementspec.listener;

import org.palladiosimulator.measurementspec.Measurement;

/**
 * Once measurement sources have new measurements at hand, they have to inform registered observers
 * about these new measurements. To do so, measurement sources provide this dedicated interfaces
 * with call-back methods that observers have to implement. Measurement sources bind this interface
 * to the generic type parameter <code>T</code> of <code>AbstractObservable</code> to implement this
 * observer pattern.
 * 
 * @author Sebastian Lehrig
 */
public interface IMeasurementSourceListener {

    /**
     * Call-back method for observers, informing these about a new measurement.
     * 
     * @param newMeasurement The newly available Measurement.
     */
    public void newMeasurementAvailable(Measurement newMeasurement);

    /**
     * After having registered, e.g., at a {@link Calculator}, this method gets invoked to inform the
     * listener about being unregistered.
     */
    public void preUnregister();
}
