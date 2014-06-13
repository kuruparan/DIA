package org.yarlithub.dia.util;

/**
 * Created by nirajh on 6/12/14.
 */
public class Data {
    public static int temperatureValue(String sensorData){
        String[] tem=sensorData.split(";");
        return Integer.parseInt(tem[0].replace("T:",""));
    }

    public static int moistureValue(String sensorData){
        String[] tem=sensorData.split(";");
        return Integer.parseInt(tem[1].replace("M:",""));
    }
}
