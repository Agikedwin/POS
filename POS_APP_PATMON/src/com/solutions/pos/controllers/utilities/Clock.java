/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers.utilities;

import java.util.Calendar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author RESEARCH2
 */
public class Clock {

    /**
     * Creates a digital clock display as a simple label. Format of the clock
     * display is hh:mm:ss aa, where: hh Hour in am/pm (1-12) mm Minute in hour
     * ss Second in minute aa Am/pm marker Time is the system time for the local
     * timezone.
     *
     * @param lbl
     * @return
     */
    public Label bindToTime(Label lbl) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), (ActionEvent actionEvent) -> {
                    Calendar time = Calendar.getInstance();
                    String hourString = pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                    String minuteString = pad(2, '0', time.get(Calendar.MINUTE) + "");
                    String secondString = pad(2, '0', time.get(Calendar.SECOND) + "");
                    String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                    lbl.setText(hourString + " : " + minuteString + " : " + secondString + " " + ampmString);
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return lbl;
    }

    /**
     *
     * @param lbl
     * @return
     */
    public Label bindToDate(Label lbl) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), (ActionEvent actionEvent) -> {
                    Calendar time = Calendar.getInstance();
                    lbl.setText(String.valueOf(dayOfTheWeek(time.get(Calendar.DAY_OF_WEEK)) + " " + time.get(Calendar.DAY_OF_MONTH) +" "+ time.get(Calendar.YEAR)));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return lbl;
    }

    /**
     *
     * @param day
     * @return
     */
    private String dayOfTheWeek(int day) {
        switch (day) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THUR";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                return null;
        }
    }

    /**
     * Creates a string left padded to the specified width with the supplied
     * padding character.
     *
     * @param fieldWidth the length of the resultant padded string.
     * @param padChar a character to use for padding the string.
     * @param s the string to be padded.
     * @return the padded string.
     */
    private String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }

}
