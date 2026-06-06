package org.geoframe.salto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SaltoSeedModelTest {

    @Test
    void closesWaterBalanceForSingleHruSingleStepWithoutPercolation() {
        SaltoSeedModel m = new SaltoSeedModel();
        m.hruArea = new double[] { 1.0 };
        m.slope = new double[] { 0.0 };
        m.soilDepth = new double[] { 1000.0 };
        m.drainageDensity = new double[] { 0.0 };

        m.infiltrationCapacity = 5.0; // mm/d
        m.quickflowCoefficient = 1.0;
        m.percolationCoefficient = 0.0;
        m.baseflowResidenceTime = 1e12; // suppress baseflow
        m.etSoilCoefficient = 0.0; // suppress ET

        m.initialSoilWater = new double[] { 0.0 };
        m.initialGroundWater = new double[] { 0.0 };

        m.timeStep = 1.0;
        m.precipitation = 10.0;
        m.potentialET = 0.0;

        m.initialize();
        m.execute();

        // P=10, infil limited to 5 => quickflow=5, storage increase=5
        assertEquals(5.0, m.quickflow, 1e-12);
        assertEquals(0.0, m.baseflow, 1e-12);
        assertEquals(5.0, m.soilWater[0], 1e-12);
        assertEquals(0.0, m.groundWater[0], 1e-12);

        // Closure residual should be ~0
        assertEquals(0.0, m.waterBalanceError, 1e-12);
        assertTrue(m.discharge >= 0.0);
    }
}
