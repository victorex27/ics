package com.study.ics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Component extends AnchorPane {

    private ComponentIconType iconType;

    public Component() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("component.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);

        }
    }

    @FXML
    private void initialize() {
    }

    public ComponentIconType getIconType() {
        return iconType;
    }

    public void setIconType(ComponentIconType type) {
        iconType = type;

        getStyleClass().clear();
        getStyleClass().add("component-icon");

        switch (iconType) {
            case red:
                getStyleClass().add("icon-red");
                break;
            case blue:
                getStyleClass().add("icon-blue");
                break;
            case grey:
                getStyleClass().add("icon-grey");
                break;
            case black:
                getStyleClass().add("icon-black");
                break;
            case green:
                getStyleClass().add("icon-green");
                break;
            case purple:
                getStyleClass().add("icon-purple");
                break;
            case yellow:
                getStyleClass().add("icon-yellow");
                break;
            default:
                break;
        }
    }

    public void relocateToPoint (Point2D p) {

        Point2D localCoordinates = getParent().sceneToLocal(p);

        relocate (
                (int) (localCoordinates.getX() -
                        (getBoundsInLocal().getWidth() / 2)),
                (int) (localCoordinates.getY() -
                        (getBoundsInLocal().getHeight() / 2))
        );
    }
}
