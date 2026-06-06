# SALTO seed model (assumptions)

This is **not** a full reproduction of the paper.
It is a small, self-contained “SALTO-style” seed: a distributed (HRU-based) catchment model where a small set of **parameters** controls how information from **conventional catchment descriptors** affects runoff generation.

## Conceptual scheme (per HRU)

Each HRU has two storages:

- Soil water storage `S [mm]`
- Groundwater storage `G [mm]`

At each timestep (piecewise-constant forcing), the model computes:

1) actual ET `AET` limited by available soil water
2) infiltration input limited by an infiltration capacity scaled by descriptors
3) quickflow as the remainder of effective precipitation after infiltration
4) percolation from soil to groundwater proportional to soil water
5) baseflow as a linear reservoir from groundwater

Catchment outputs are **area-weighted averages** of HRU fluxes.

## Required inputs

### Forcing (uniform across HRUs in this seed)

- `precipitation` [mm/d]
- `potentialET` [mm/d]
- `timeStep` [d]

### Conventional descriptors (per HRU)

- `hruArea` [m2]
- `slope` [-] (e.g. tan(gradient) or dimensionless slope proxy)
- `soilDepth` [mm]
- `drainageDensity` [1/m]

## Parameters

All parameters are scalar and applied to all HRUs.

- `infiltrationCapacity` [mm/d]: baseline infiltration capacity.
- `slopeInfiltrationSensitivity` [-]: increases infiltration capacity with slope using `1 + aSlope * slope`.
- `soilDepthInfiltrationSensitivity` [-]: increases infiltration capacity with soil depth using `1 + aDepth * (soilDepth / 1000)`.
- `drainageDensityInfiltrationSensitivity` [-]: decreases infiltration capacity with drainage density using `1 / (1 + aDd * drainageDensity)`.

- `quickflowCoefficient` [-]: controls the share of non-infiltrated water routed to quickflow. (In this seed, it is applied as a simple multiplier and then limited by available water.)

- `percolationCoefficient` [1/d]: percolation fraction rate from soil to groundwater (`perc = kPerc * S * dt`).
- `baseflowResidenceTime` [d]: linear reservoir residence time (`Qb = G / kBase`).

- `etSoilCoefficient` [1/d]: limits ET by available soil water (`AET = min(PET, etSoilCoefficient * S)`), applied each timestep.

## Units & conventions

- Storages are in water depth [mm] per HRU.
- Fluxes are in [mm/d].
- Negative forcing is clamped to zero.
- All “capacity/denominator” parameters are bounded away from zero internally to avoid division-by-zero.

## Notes / limitations

- Forcing is uniform across HRUs (no spatial meteorology in this seed).
- Descriptor scaling is linear/rational and meant only as a placeholder for “parameter controls”.
- No snow, routing, channel network, or timestep aggregation beyond a single lumped output.
