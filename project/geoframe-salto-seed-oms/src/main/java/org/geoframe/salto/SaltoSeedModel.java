package org.geoframe.salto;

import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Execute;
import oms3.annotations.In;
import oms3.annotations.Initialize;
import oms3.annotations.Keywords;
import oms3.annotations.Label;
import oms3.annotations.License;
import oms3.annotations.Name;
import oms3.annotations.Out;
import oms3.annotations.Status;
import oms3.annotations.Unit;

/**
 * Minimal, runnable “SALTO-style” seed model.
 *
 * <p>Distributed (HRU-based) catchment model with two storages per HRU (soil water and groundwater)
 * and simple descriptor-controlled parameterisation.
 */
@Description("SALTO seed: HRU soil+groundwater with descriptor-scaled infiltration and linear baseflow.")
@Author(name = "GEOframe agent")
@Label("SALTO Seed")
@Name("geoframe.salto.seed")
@Keywords("SALTO, distributed catchment model, OMS")
@Status(Status.TESTED)
@License("GPL-3.0-or-later")
public class SaltoSeedModel {

    private static final double EPS = 1e-12;

    // Forcing
    @In
    @Description("Precipitation during the timestep (uniform across HRUs).")
    @Unit("mm/d")
    public double precipitation;

    @In
    @Description("Potential evapotranspiration during the timestep (uniform across HRUs).")
    @Unit("mm/d")
    public double potentialET;

    @In
    @Description("Timestep length.")
    @Unit("d")
    public double timeStep = 1.0;

    // Descriptors (per HRU)
    @In
    @Description("HRU areas.")
    @Unit("m2")
    public double[] hruArea;

    @In
    @Description("HRU slope proxy (dimensionless).")
    @Unit("-")
    public double[] slope;

    @In
    @Description("HRU soil depth.")
    @Unit("mm")
    public double[] soilDepth;

    @In
    @Description("HRU drainage density.")
    @Unit("1/m")
    public double[] drainageDensity;

    // Parameters
    @In
    @Description("Baseline infiltration capacity.")
    @Unit("mm/d")
    public double infiltrationCapacity = 10.0;

    @In
    @Description("Sensitivity of infiltration capacity to slope (multiplier: 1 + a*slope).")
    @Unit("-")
    public double slopeInfiltrationSensitivity = 0.0;

    @In
    @Description("Sensitivity of infiltration capacity to soil depth (multiplier: 1 + a*(soilDepth/1000)).")
    @Unit("-")
    public double soilDepthInfiltrationSensitivity = 0.0;

    @In
    @Description("Sensitivity of infiltration capacity to drainage density (divider: 1 + a*dd).")
    @Unit("-")
    public double drainageDensityInfiltrationSensitivity = 0.0;

    @In
    @Description("Quickflow coefficient applied to non-infiltrated water.")
    @Unit("-")
    public double quickflowCoefficient = 1.0;

    @In
    @Description("Percolation coefficient from soil to groundwater.")
    @Unit("1/d")
    public double percolationCoefficient = 0.05;

    @In
    @Description("Baseflow residence time for groundwater linear reservoir.")
    @Unit("d")
    public double baseflowResidenceTime = 20.0;

    @In
    @Description("Coefficient limiting ET by available soil water (AET=min(PET, c*S)).")
    @Unit("1/d")
    public double etSoilCoefficient = 0.02;

    // Initial states
    @In
    @Description("Initial soil water storage per HRU.")
    @Unit("mm")
    public double[] initialSoilWater;

    @In
    @Description("Initial groundwater storage per HRU.")
    @Unit("mm")
    public double[] initialGroundWater;

    // Outputs (catchment totals and updated states)
    @Out
    @Description("Total discharge (area-weighted), quickflow + baseflow.")
    @Unit("mm/d")
    public double discharge;

    @Out
    @Description("Quickflow component (area-weighted).")
    @Unit("mm/d")
    public double quickflow;

    @Out
    @Description("Baseflow component (area-weighted).")
    @Unit("mm/d")
    public double baseflow;

    @Out
    @Description("Actual evapotranspiration (area-weighted).")
    @Unit("mm/d")
    public double aet;

    @Out
    @Description("Updated soil water storage per HRU.")
    @Unit("mm")
    public double[] soilWater;

    @Out
    @Description("Updated groundwater storage per HRU.")
    @Unit("mm")
    public double[] groundWater;

    @Out
    @Description("Closure residual (P - AET - Q - dS/dt) in mm/d, area-weighted.")
    @Unit("mm/d")
    public double waterBalanceError;

    @Initialize
    public void initialize() {
        int n = requireSameLength(hruArea, slope, soilDepth, drainageDensity);
        soilWater = new double[n];
        groundWater = new double[n];

        if (initialSoilWater != null) {
            if (initialSoilWater.length != n) {
                throw new IllegalArgumentException("initialSoilWater length must match HRU count");
            }
            for (int i = 0; i < n; i++) {
                soilWater[i] = Math.max(0.0, initialSoilWater[i]);
            }
        }

        if (initialGroundWater != null) {
            if (initialGroundWater.length != n) {
                throw new IllegalArgumentException("initialGroundWater length must match HRU count");
            }
            for (int i = 0; i < n; i++) {
                groundWater[i] = Math.max(0.0, initialGroundWater[i]);
            }
        }
    }

    @Execute
    public void execute() {
        int n = requireSameLength(hruArea, slope, soilDepth, drainageDensity);
        final double dt = Math.max(timeStep, 0.0);
        final double p = Math.max(precipitation, 0.0);
        final double pet = Math.max(potentialET, 0.0);

        double areaSum = 0.0;
        for (int i = 0; i < n; i++) {
            areaSum += Math.max(0.0, hruArea[i]);
        }
        areaSum = Math.max(areaSum, EPS);

        double qQuickW = 0.0;
        double qBaseW = 0.0;
        double aetW = 0.0;
        double dStoreW = 0.0;

        final double kBase = Math.max(baseflowResidenceTime, EPS);
        final double kPerc = Math.max(percolationCoefficient, 0.0);
        final double cEt = Math.max(etSoilCoefficient, 0.0);

        for (int i = 0; i < n; i++) {
            final double a = Math.max(0.0, hruArea[i]);
            if (a <= 0.0) {
                continue;
            }

            final double s0 = Math.max(0.0, soilWater[i]);
            final double g0 = Math.max(0.0, groundWater[i]);

            // Actual ET limited by available soil water
            final double aetStep = Math.min(pet, (dt <= 0.0) ? 0.0 : (cEt * s0));
            final double aetFlux = (dt <= 0.0) ? 0.0 : aetStep; // mm/d
            final double aetDepth = aetFlux * dt; // mm

            double s = Math.max(0.0, s0 - aetDepth);
            double g = g0;

            // Descriptor-scaled infiltration capacity
            double cap = Math.max(infiltrationCapacity, 0.0);
            cap *= (1.0 + Math.max(0.0, slopeInfiltrationSensitivity) * Math.max(0.0, slope[i]));
            cap *= (1.0 + Math.max(0.0, soilDepthInfiltrationSensitivity) * (Math.max(0.0, soilDepth[i]) / 1000.0));
            cap /= (1.0 + Math.max(0.0, drainageDensityInfiltrationSensitivity) * Math.max(0.0, drainageDensity[i]));

            final double pDepth = p * dt;
            final double infilDepth = Math.min(pDepth, cap * dt);
            final double excessDepth = Math.max(0.0, pDepth - infilDepth);

            // Soil receives infiltrated water
            s += infilDepth;

            // Quickflow from excess water
            final double qQuickDepth = Math.min(excessDepth, Math.max(0.0, quickflowCoefficient) * excessDepth);
            final double qQuickFlux = (dt <= 0.0) ? 0.0 : (qQuickDepth / dt);

            // Percolation from soil to groundwater
            final double percDepth = Math.min(s, kPerc * s * dt);
            s -= percDepth;
            g += percDepth;

            // Baseflow linear reservoir from groundwater
            final double qbFlux = g / kBase; // mm/d
            final double qbDepth = Math.min(g, qbFlux * dt);
            g -= qbDepth;

            soilWater[i] = s;
            groundWater[i] = g;

            qQuickW += (a / areaSum) * qQuickFlux;
            qBaseW += (a / areaSum) * ((dt <= 0.0) ? 0.0 : (qbDepth / dt));
            aetW += (a / areaSum) * aetFlux;
            dStoreW += (a / areaSum) * ((dt <= 0.0) ? 0.0 : ((s + g - s0 - g0) / dt));
        }

        quickflow = qQuickW;
        baseflow = qBaseW;
        discharge = quickflow + baseflow;
        aet = aetW;
        waterBalanceError = p - aet - discharge - dStoreW;
    }

    private static int requireSameLength(double[]... arrays) {
        if (arrays == null || arrays.length == 0) {
            throw new IllegalArgumentException("Missing HRU descriptor arrays");
        }
        int n = -1;
        for (double[] a : arrays) {
            if (a == null) {
                throw new IllegalArgumentException("HRU descriptor array is null");
            }
            if (n < 0) {
                n = a.length;
            } else if (a.length != n) {
                throw new IllegalArgumentException("HRU descriptor arrays must have the same length");
            }
        }
        if (n <= 0) {
            throw new IllegalArgumentException("HRU count must be > 0");
        }
        return n;
    }
}
