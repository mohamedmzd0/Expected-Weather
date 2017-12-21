package com.example.mohamedabdelaziz.myapplication;

/**
 * Created by Mohamed Abd Elaziz on 12/6/2017.
 */

public class ExpertSystem {
    public String detect_wind(double speed) {
        if (speed <= 1)
            return "calm";
        else if (speed <= 3)
            return "Light air";
        else if (speed <= 6)
            return "Light breeze";
        else if (speed <= 10)
            return "Gentle breeze";
        else if (speed <= 16)
            return "Moderate breeze";
        else if (speed <= 21)
            return "Fresh breeze";
        else if (speed <= 27)
            return "Strong breeze";
        else if (speed <= 33)
            return "Moderate gale";
        else if (speed <= 40)
            return "Fresh gale";
        else if (speed <= 47)
            return "Strong gale";
        else if (speed <= 55)
            return "Whole gale";
        else if (speed <= 63)
            return "Storm";
        else
            return "Hurricane";

    }

    public String THI(double temp, double humidity) {
        double thi = temp - ((1 - humidity) * (temp - 14.5));
        if (thi >= 28)
            return "Very uncomfortable due to excessive heat or humidity";
        else if (thi >= 27)
            return "uncomfortable";
        else if (thi >= 25)
            return "My transition between rest and absence";
        else if (thi >= 17)
            return "comfortable";
        else if (thi > 15)
            return "My transition between comfort and non-existence as a result of cold";
        else
            return "Restlessness due to coldness";
    }

    public String K(double temp, double speed) {
        double k = (33 - temp) * (10 * Math.sqrt(speed) - speed + 10.5);
        if (k < 50)
            return "hot";
        else if (k < 100)
            return "warm";

        else if (k < 200)
            return "nice";
        else if (k < 400)
            return "Italic to cold";
        else if (k < 600)
            return "seems cold";
        else if (k < 800)
            return "cold";

        else if (k < 1000)
            return "so cold";
        else if (k < 1200)
            return "Cold chilling";
        else
            return "Freezing of exposed skin";
    }
}
