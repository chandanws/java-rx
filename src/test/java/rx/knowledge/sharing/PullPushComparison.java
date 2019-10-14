package rx.knowledge.sharing;

import rx.utility.TimeDelayer;

/**
 * Depicts difference between push and pull approaches in writing code.
 */
public class PullPushComparison {

    private static final int WATER_BOILING_POINT = 100;

    private static class Stove {

    }

    private static class NonWhistlingKettle extends Kettle {

        private NonWhistlingKettle(Water water) {
            super(water);
        }

        private void boilWater() {
            while (getWater().isNotBoiling()) {
                heatWater();
            }
        }
    }

    private static class WhistlingKettle extends Kettle {

        private WhistlingKettle(Water water) {
            super(water);
        }

        private void boilWater() {
            while (getWater().isNotBoiling()) {
                heatWater();
            }
            whistle();
        }

        private void whistle() {

        }
    }

    private static abstract class Kettle {

        private Water water;

        Kettle(Water water) {
            this.water = water;
        }

        Water getWater() {
            return this.water;
        }

        void heatWater() {
            TimeDelayer.sleepForOneSecond();
            this.water.heatWater(20);
        }

        boolean isWaterBoiling() {
            return this.water.isBoiling();
        }
    }

    private static class Water {

        private int temperature;

        private Water(int temperature) {
            if (temperature > WATER_BOILING_POINT) {
                throw new IllegalArgumentException();
            }
            this.temperature = temperature;
        }

        private int getTemperature() {
            return temperature;
        }

        private void heatWater(int degreesToAdd) {
            int targetTemperature = this.temperature + degreesToAdd;
            if (targetTemperature >= WATER_BOILING_POINT) {
                this.temperature = WATER_BOILING_POINT;
                return;
            }
            this.temperature += degreesToAdd;
        }

        private boolean isBoiling() {
            return getTemperature() == WATER_BOILING_POINT;
        }

        private boolean isNotBoiling() {
            return getTemperature() < WATER_BOILING_POINT;
        }
    }
}
