package com.study.ics;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DragIcon extends AnchorPane {

    @FXML
    AnchorPane root_pane;

    @FXML
    ImageView imageView;

    @FXML
    private Label label;

    private DragIconType mType = null;

    private DraggableImageview draggableImageview;

    public DragIcon() {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("DragIcon.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
    }

    public void relocateToPoint(Point2D p) {

        //relocates the object to a point that has been converted to
        //scene coordinates
        Point2D localCoords = getParent().sceneToLocal(p);

        relocate(
                (int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
                (int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
        );
    }

    public DragIconType getType() {
        return mType;
    }

    public void setType(DragIconType type) {

        mType = type;
        Image image;
//
//		getStyleClass().clear();
//		getStyleClass().add("dragicon");

        draggableImageview = new DraggableImageview(getClass(), mType);


        imageView.setImage(draggableImageview.getImage());
        label.setText(draggableImageview.getLabel());


    }
}
