package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private double tempBefore;
    private double minDiff;
    private double[] toReturn;
    private double[] result;
    private int seriesIndex;

    public TemperatureSeriesAnalysis() {
        throw new InputMismatchException();
    }

    private void checkLength() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        int EXCLUDE = -273;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (temperatureSeries[i] < EXCLUDE) {
                throw new InputMismatchException();
            }
        }
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
    }

    public double average() {
        checkLength();
        double sum = 0;
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            sum += this.temperatureSeries[i];
        }
        return sum / this.temperatureSeries.length;
    }

    public double deviation() {
        checkLength();
        double toReturn = 0;
        double average = average();
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            toReturn += (this.temperatureSeries[i] - average) *
                    (this.temperatureSeries[i] - average);
        }
        return Math.sqrt(toReturn / this.temperatureSeries.length);
    }

    public double min() {
        checkLength();
        double min = this.temperatureSeries[0];
        for (int i = 1; i < this.temperatureSeries.length; i++) {
            if (this.temperatureSeries[i] < min) {
                min = this.temperatureSeries[i];
            }
        }
        return min;
    }

    public double max() {
        checkLength();
        double max = this.temperatureSeries[0];
        for (int i = 1; i < this.temperatureSeries.length; i++) {
            if (this.temperatureSeries[i] > max) {
                max = this.temperatureSeries[i];
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        checkLength();
        double closest = this.temperatureSeries[0];
        Arrays.sort(this.temperatureSeries);
        for (int i = 1; i < this.temperatureSeries.length; i++) {
            tempBefore = this.temperatureSeries[i - 1];
            if (this.temperatureSeries[i] >= 0 && tempBefore <= 0) {
//                check if the found values are equal
                if (this.temperatureSeries[i] == Math.abs(tempBefore)) {
                    closest = Math.abs(tempBefore);
                }
//                else closest is minimum and check whether it is less than 0
                else {
                    closest = Math.min(this.temperatureSeries[i], Math.abs(tempBefore));
                    if (closest == Math.abs(tempBefore)) {
                        closest = tempBefore;
                    }

                }
                break;
            }
        }
        return closest;
    }


    public double findTempClosestToValue(double tempValue) {
        checkLength();
        minDiff = Math.abs(tempValue - this.temperatureSeries[0]);
        double closest = this.temperatureSeries[0];
        for (int i = 1; i < this.temperatureSeries.length; i++) {
            if (Math.abs(tempValue - this.temperatureSeries[i]) < minDiff) {
                minDiff = Math.abs(tempValue - this.temperatureSeries[i]);
                closest = this.temperatureSeries[i];
            }
        }
        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        toReturn = new double[0];
        int index = 0;
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            if (this.temperatureSeries[i] < tempValue) {
                double[] temp = Arrays.copyOf(toReturn, toReturn.length + 1);
                toReturn = Arrays.copyOf(temp, temp.length);
                toReturn[index] = this.temperatureSeries[i];
                index += 1;
            }
        }
        return toReturn;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        result = new double[0];
        int index = 0;
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            if (this.temperatureSeries[i] >= tempValue) {
                double[] temp = Arrays.copyOf(result, result.length + 1);
                result = Arrays.copyOf(temp, temp.length);
                result[index] = this.temperatureSeries[i];
                index += 1;
            }
        }
        return result;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkLength();
        TempSummaryStatistics statistics = new
                TempSummaryStatistics(average(), deviation(), min(), max());
        return statistics;
    }

    public int addTemps(double... temps) {
        seriesIndex = this.temperatureSeries.length - 1;
        int amount = this.temperatureSeries.length;

        for (int i = 0; i < temps.length; i++) {
            if (seriesIndex == this.temperatureSeries.length - 1) {
                double[] temp = Arrays.copyOf(this.temperatureSeries,
                        this.temperatureSeries.length * 2);
                this.temperatureSeries = Arrays.copyOf(temp, temp.length);
            }
            seriesIndex += 1;
            this.temperatureSeries[seriesIndex] = temps[i];
            amount += 1;
        }
        return amount;

    }
}



