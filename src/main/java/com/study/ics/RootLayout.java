package com.study.ics;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RootLayout extends AnchorPane {

    @FXML
    SplitPane base_pane;
    @FXML
    AnchorPane right_pane;
    @FXML
    VBox left_pane;

    private Component component = null;

    private EventHandler componentDragOverRoot = null;
    private EventHandler componentDragDropped = null;
    private EventHandler componentDragOverRightPane = null;

    public RootLayout() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("root-layout.fxml"));
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

        component = new Component();
        component.setVisible(false);
        component.setOpacity(0.65);
        getChildren().add(component);


        for (int i = 0; i < 7; i++) {
            Component com = new Component();

            addDragDetection(com);
            com.setIconType(ComponentIconType.values()[i]);
            left_pane.getChildren().add(com);
        }

        buildDragHandlers();
    }

    private void buildDragHandlers() {

        //drag over transition to move widget form left pane to right pane
        addDragDetection(component);
        componentDragOverRoot = new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {

                Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

                if (!right_pane.boundsInLocalProperty().get().contains(p)) {
                    component.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                    return;
                }

                event.consume();
            }
        };

        componentDragOverRightPane = new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {

                event.acceptTransferModes(TransferMode.ANY);

                component.relocateToPoint(
                        new Point2D(event.getSceneX(), event.getSceneY())
                );


                event.consume();
            }
        };

        componentDragDropped = new EventHandler<DragEvent>  () {



            @Override
            public void handle(DragEvent event) {

                DragContainer container =
                        (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

                container.addData("scene_coords",
                        new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                content.put(DragContainer.AddNode, container);

                event.getDragboard().setContent(content);
                event.setDropCompleted(true);
            }
        };


        this.setOnDragDone (new EventHandler <DragEvent> (){

            @Override
            public void handle (DragEvent event) {

                right_pane.removeEventHandler(DragEvent.DRAG_OVER, componentDragOverRightPane);
                right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, componentDragDropped);
                base_pane.removeEventHandler(DragEvent.DRAG_OVER, componentDragOverRoot);

                component.setVisible(false);

                DragContainer container =
                        (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

                if (container != null) {
                    if (container.getValue("scene_coords") != null) {

                        Component droppedIcon = new Component();

                        droppedIcon.setIconType(ComponentIconType.valueOf(container.getValue("type")));
                        right_pane.getChildren().add(droppedIcon);

                        Point2D cursorPoint = container.getValue("scene_coords");

                        droppedIcon.relocateToPoint(
                                new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                        );
                    }
                }
                event.consume();
            }
        });
    }


    private void addDragDetection(Component comp) {

        comp.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                // set the other drag event handles on their respective objects
                base_pane.setOnDragOver(componentDragOverRoot);
                right_pane.setOnDragOver(componentDragOverRightPane);
                right_pane.setOnDragDropped(componentDragDropped);

                // get a reference to the clicked DragIcon object
                Component currentComponent = (Component) event.getSource();

                //begin drag ops
                component.setIconType(currentComponent.getIconType());
//                component.relocate(new Point2D(event.getSceneX(), event.getSceneY()));
                component.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();
                container.addData("type", component.getIconType().toString());
                content.put(DragContainer.AddNode, container);
//                content.putString(currentComponent.getIconType().toString());

                component.startDragAndDrop(TransferMode.ANY).setContent(content);
                component.setVisible(true);
                component.setMouseTransparent(true);
                event.consume();

            }
        });
    }
}
