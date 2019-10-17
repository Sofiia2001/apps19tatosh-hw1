package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.lang.Math;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;

    public TemperatureSeriesAnalysis() {

    }

    private void checkLength() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (temperatureSeries[i] < -273) throw new InputMismatchException();
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
        double to_return = 0;
        double average = average();
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            to_return += Math.pow(this.temperatureSeries[i] - average, 2);
        }
        return Math.sqrt(to_return / this.temperatureSeries.length);
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
        double temp_before;
        double closest = this.temperatureSeries[0];
        Arrays.sort(this.temperatureSeries);
        for (int i = 1; i < this.temperatureSeries.length; i++) {
            temp_before = this.temperatureSeries[i - 1];
            if (this.temperatureSeries[i] >= 0 && temp_before <= 0) {
//                check if the found values are equal
                if (this.temperatureSeries[i] == Math.abs(temp_before)) closest = Math.abs(temp_before);
//                else closest is minimum and check whether it is less than 0
                else {
                    closest = Math.min(this.temperatureSeries[i], Math.abs(temp_before));
                    if (closest == Math.abs(temp_before)) closest = temp_before;
                }
                break;
            }
        }
        return closest;
    }


    public double findTempClosestToValue(double tempValue) {
        checkLength();
        double min_diff = Math.abs(tempValue - this.temperatureSeries[0]);
        double closest = this.temperatureSeries[0];
        for (int i = 1; i < this.temperatureSeries.length; i++) {
            if (Math.abs(tempValue - this.temperatureSeries[i]) < min_diff) {
                min_diff = Math.abs(tempValue - this.temperatureSeries[i]);
                closest = this.temperatureSeries[i];
            }
        }
        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] to_return = new double[0];
        int index = 0;
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            if (this.temperatureSeries[i] < tempValue) {
                double[] temp = Arrays.copyOf(to_return, to_return.length + 1);
                to_return = Arrays.copyOf(temp, temp.length);
                to_return[index] = this.temperatureSeries[i];
                index += 1;
            }
        }
        return to_return;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] to_return = new double[0];
        int index = 0;
        for (int i = 0; i < this.temperatureSeries.length; i++) {
            if (this.temperatureSeries[i] >= tempValue) {
                double[] temp = Arrays.copyOf(to_return, to_return.length + 1);
                to_return = Arrays.copyOf(temp, temp.length);
                to_return[index] = this.temperatureSeries[i];
                index += 1;
            }
        }
        return to_return;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkLength();
        TempSummaryStatistics statistics = new TempSummaryStatistics(average(), deviation(), min(), max());
        return statistics;
    }

    public int addTemps(double... temps) {
        int series_index = this.temperatureSeries.length - 1;
        int amount = this.temperatureSeries.length;

        for (int i = 0; i < temps.length; i++) {
            if (series_index == this.temperatureSeries.length - 1) {
                double[] temp = Arrays.copyOf(this.temperatureSeries, this.temperatureSeries.length * 2);
                this.temperatureSeries = Arrays.copyOf(temp, temp.length);
            }
            series_index += 1;
            this.temperatureSeries[series_index] = temps[i];
            amount += 1;
        }
//        for (int j = 0; j < this.temperatureSeries.length; j++) {
//            if (this.temperatureSeries[j] != )
//            sum += this.temperatureSeries[j];
        return amount;

    }
}



