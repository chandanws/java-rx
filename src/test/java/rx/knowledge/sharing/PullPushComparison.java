package rx.knowledge.sharing;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observable;
import rx.utility.TimeDelayer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Depicts difference between push and pull approaches in writing code.
 */
@Slf4j
public class PullPushComparison {

    private static final int WATER_BOILING_POINT = 100;

    @Test
    public void oldLadyShouldDrinkTea() {
        new SmartOldLady().drinkTea();
    }

    @Test
    public void notSoSmartOldLadyShouldDrinkTea() {
        new NotSoSmartOldLady().drinkTea();
    }

    private static class SmartOldLady {

        private final WhistlingKettle whistlingKettle = WhistlingKettle.fullOfWater();

        private void drinkTea() {
            log.debug("Set the kettle");
            Observable.fromCallable(whistlingKettle::boilWater)
                      .subscribe(onWaterBoiling -> makeTea());
        }

        private void makeTea() {
            TimeDelayer.sleepForAWhile();
            log.debug("Old lady is making tea");
        }
    }

    private static class NotSoSmartOldLady {

        private final NonWhistlingKettle nonWhistlingKettle = NonWhistlingKettle.fullOfWater();

        private void drinkTea() {
            log.debug("Set the kettle");

            nonWhistlingKettle.boilWater();
            while (!nonWhistlingKettle.isWaterBoiling()) {
                log.debug("Check if water boils");
                TimeDelayer.sleepForAWhile();
            }

            makeTea();
        }

        private void makeTea() {
            TimeDelayer.sleepForAWhile();
            log.debug("Old lady is making tea");
        }
    }

    private static class NonWhistlingKettle extends Kettle {

        private static NonWhistlingKettle fullOfWater() {
            return new NonWhistlingKettle(new Water(40));
        }

        private NonWhistlingKettle(Water water) {
            super(water);
        }

        private void boilWater() {
            new Thread(() -> {
                while (getWater().isNotBoiling()) {
                    heatWater();
                }
            }).start();
        }
    }

    private static class WhistlingKettle extends Kettle {

        private static WhistlingKettle fullOfWater() {
            return new WhistlingKettle(new Water(40));
        }

        private WhistlingKettle(Water water) {
            super(water);
        }

        private boolean boilWater() {
            while (getWater().isNotBoiling()) {
                heatWater();
            }
            return whistle();
        }

        private boolean whistle() {
            TimeDelayer.sleepForAWhile();
            log.debug("Kettle is whistling");
            return true;
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

        private AtomicInteger temperature = new AtomicInteger();

        private Water(int temperature) {
            if (temperature > WATER_BOILING_POINT) {
                throw new IllegalArgumentException();
            }
            this.temperature.set(temperature);
        }

        private int getTemperature() {
            return this.temperature.get();
        }

        private void heatWater(int degreesToAdd) {
            int targetTemperature = getTemperature() + degreesToAdd;
            if (targetTemperature >= WATER_BOILING_POINT) {
                this.temperature.set(WATER_BOILING_POINT);
                log.debug("Water is boiling");
                return;
            }
            this.temperature.addAndGet(degreesToAdd);
        }

        private boolean isBoiling() {
            return getTemperature() == WATER_BOILING_POINT;
        }

        private boolean isNotBoiling() {
            return getTemperature() < WATER_BOILING_POINT;
        }
    }
}
