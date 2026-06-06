package org.geoframe.linearreservoir;

import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Execute;
import oms3.annotations.In;
import oms3.annotations.Initialize;
import oms3.annotations.Label;
import oms3.annotations.License;
import oms3.annotations.Name;
import oms3.annotations.Out;
import oms3.annotations.Status;
import oms3.annotations.Keywords;
import oms3.annotations.Unit;

/**
 * Linear reservoir component for OMS-based workflows.
 */
@Description("Linear reservoir with constant residence time.")
@Author(name = "GEOframe agent")
@Label("Linear Reservoir")
@Name("geoframe.linearreservoir")
@Keywords("linear reservoir, OMS")
@Status(Status.TESTED)
@License("GPL-3.0-or-later")
public class LinearReservoir {

    private static final double EPS = 1e-12;

    @In
    @Description("Inflow entering the reservoir during the timestep.")
    @Unit("mm")
    public double inflow;

    @In
    @Description("Residence time of the reservoir.")
    @Unit("d")
    public double residenceTime = 1.0;

    @In
    @Description("Length of the timestep.")
    @Unit("d")
    public double timeStep = 1.0;

    @In
    @Description("Initial storage in the reservoir.")
    @Unit("mm")
    public double initialStorage = 0.0;

    @Out
    @Description("Storage at the end of the timestep.")
    @Unit("mm")
    public double storage;

    @Out
    @Description("Outflow at the end of the timestep.")
    @Unit("mm/d")
    public double outflow;

    @Out
    @Description("Exponential decay factor applied during the timestep.")
    public double decayFactor;

    @Initialize
    public void initialize() {
        storage = Math.max(0.0, initialStorage);
        outflow = storage / Math.max(residenceTime, EPS);
    }

    @Execute
    public void execute() {
        final double k = Math.max(residenceTime, EPS);
        final double dt = Math.max(timeStep, 0.0);
        final double qIn = Math.max(inflow, 0.0);

        decayFactor = Math.exp(-dt / k);
        storage = storage * decayFactor + qIn * k * (1.0 - decayFactor);
        outflow = storage / k;
    }
}
