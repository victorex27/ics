package com.study.ics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Objects;

public class DraggableImageview {

    private Class callingClass;
    private DragIconType mType;

    public DraggableImageview(Class callingClass, DragIconType type) {
        this.callingClass = callingClass;
        mType = type;

    }

    public String getLabel() {
        String label;

        switch (mType) {

            case COMPUTER:

                label = "Computer";

                break;

            case FLOW_SENSOR:
                label = "Flow Sensor";
                break;

            case LEVEL_SENSOR:
                label = "Level Sensor";
                break;

            case MODEM:
                label = "Modem";
                break;

            case PLC:
                label = "PLC";
                break;


            case PRESSURE_SENSOR:
                label = "Pressure Sensor";
                break;

            case PUMP:
                label = "Pump";
                break;

            case SCADA:
                label = "SCADA";
                break;
            case VAVLE:
                label = "Valve";
                break;
            case RTU:
                label = "RTU";
                break;
            case PLU:
                label = "PLU";
                break;
            case DCS:
                label = "DCS";
                break;

            default:
                throw new RuntimeException("An Icon type must be selected");


        }

        return label;
    }

    public Image getImage() {


        Image image;

        switch (mType) {

            case COMPUTER:

                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/computer.png")));
                break;

            case FLOW_SENSOR:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/flow_sensor.png")));
                break;

            case LEVEL_SENSOR:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/level_sensor.png")));
                break;

            case MODEM:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/modem.png")));
                break;

            case PLC:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/plc.png")));
                break;


            case PRESSURE_SENSOR:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/pressure_sensor.png")));
                break;

            case PUMP:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/pump.png")));
                break;

            case SCADA:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/scada.png")));
                break;
            case VAVLE:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/valve.png")));
                break;
            case RTU:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/rtu.png")));
                break;
            case PLU:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/plu.png")));
                break;
            case DCS:
                image = new Image(Objects.requireNonNull(callingClass.getResourceAsStream("/images/dcs.png")));
                break;

            default:
                throw new RuntimeException("An Icon type must be selected");


        }
        return image;
    }
}
