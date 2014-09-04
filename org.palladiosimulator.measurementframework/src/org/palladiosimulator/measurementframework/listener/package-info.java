/**
 * This package provides listeners that can be used by observers to be informed about new measurements.
 * The observable itself is implemented as a {@link MeasurementSource}. For example, calculators inherit
 * from {@link MeasurementSource} such that they can serve as sources within the pipes and filters chain
 * of measurements.
 * 
 * TODO Does MeasurementSource really belong to this package (I would move it up;
 *      otherwise provide a good rationale here)? [Lehrig]
 * 
 * @author Steffen Becker, Sebastian Lehrig
 */
package org.palladiosimulator.measurementframework.listener;