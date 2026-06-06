package org.geoframe.linearreservoir;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LinearReservoirTest {

    @Test
    void executeUpdatesStorageAndOutflow() {
        LinearReservoir reservoir = new LinearReservoir();
        reservoir.initialStorage = 10.0;
        reservoir.residenceTime = 2.0;
        reservoir.timeStep = 1.0;
        reservoir.inflow = 4.0;

        reservoir.initialize();
        reservoir.execute();

        double decay = Math.exp(-0.5);
        double expectedStorage = 10.0 * decay + 4.0 * 2.0 * (1.0 - decay);
        double expectedOutflow = expectedStorage / 2.0;

        assertEquals(expectedStorage, reservoir.storage, 1e-12);
        assertEquals(expectedOutflow, reservoir.outflow, 1e-12);
        assertEquals(decay, reservoir.decayFactor, 1e-12);
    }

    @Test
    void initializeClampsNegativeStorageToZero() {
        LinearReservoir reservoir = new LinearReservoir();
        reservoir.initialStorage = -5.0;
        reservoir.residenceTime = 3.0;

        reservoir.initialize();

        assertEquals(0.0, reservoir.storage, 1e-12);
        assertEquals(0.0, reservoir.outflow, 1e-12);
    }

    @Test
    void executeWithZeroTimeStepKeepsState() {
        LinearReservoir reservoir = new LinearReservoir();
        reservoir.initialStorage = 8.0;
        reservoir.residenceTime = 4.0;
        reservoir.timeStep = 0.0;
        reservoir.inflow = 6.0;

        reservoir.initialize();
        reservoir.execute();

        assertEquals(8.0, reservoir.storage, 1e-12);
        assertEquals(2.0, reservoir.outflow, 1e-12);
        assertEquals(1.0, reservoir.decayFactor, 1e-12);
    }
}
