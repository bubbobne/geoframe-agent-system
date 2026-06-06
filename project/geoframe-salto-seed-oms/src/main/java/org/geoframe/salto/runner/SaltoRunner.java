package org.geoframe.salto.runner;

import org.geoframe.salto.SaltoSeedModel;

/**
 * Minimal runnable example (no external inputs).
 */
public final class SaltoRunner {

    public static void main(String[] args) {
        SaltoSeedModel m = new SaltoSeedModel();

        m.hruArea = new double[] { 6_000_000.0, 4_000_000.0 };
        m.slope = new double[] { 0.2, 0.05 };
        m.soilDepth = new double[] { 800.0, 400.0 };
        m.drainageDensity = new double[] { 0.002, 0.004 };

        m.infiltrationCapacity = 12.0;
        m.slopeInfiltrationSensitivity = 1.0;
        m.soilDepthInfiltrationSensitivity = 0.5;
        m.drainageDensityInfiltrationSensitivity = 50.0;
        m.quickflowCoefficient = 1.0;
        m.percolationCoefficient = 0.03;
        m.baseflowResidenceTime = 30.0;
        m.etSoilCoefficient = 0.02;

        m.initialSoilWater = new double[] { 50.0, 30.0 };
        m.initialGroundWater = new double[] { 100.0, 60.0 };

        m.timeStep = 1.0;
        m.initialize();

        double[] p = new double[] { 10.0, 0.0, 5.0 };
        double[] pet = new double[] { 2.0, 2.0, 2.0 };

        System.out.println("t, P(mm/d), PET(mm/d), Q(mm/d), Qq(mm/d), Qb(mm/d), AET(mm/d)");
        for (int t = 0; t < p.length; t++) {
            m.precipitation = p[t];
            m.potentialET = pet[t];
            m.execute();
            System.out.printf("%d, %.3f, %.3f, %.6f, %.6f, %.6f, %.6f%n",
                    t, m.precipitation, m.potentialET, m.discharge, m.quickflow, m.baseflow, m.aet);
        }
    }
}
