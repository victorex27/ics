package com.study.ics;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.WindowEvent;


public class DraggableNode extends AnchorPane {

    @FXML
    AnchorPane root_pane;

    static int numberOfTimesClicked = 0;

    private static DraggableNode[] connectingNodes = new DraggableNode[2];


    private EventHandler<DragEvent> mContextDragOver;
    private EventHandler<DragEvent> mContextDragDropped;

    private DragIconType mType = null;

    private Point2D mDragOffset = new Point2D(0.0, 0.0);

    @FXML
    private Label title_bar;
    @FXML
    private Label close_button;

    @FXML
    private Button connectorTop;
    @FXML
    private Button connectorBottom;
    @FXML
    private Button connectorLeft;
    @FXML
    private Button connectorRight;

    @FXML
    private ImageView imageView;

    AnchorPane parentPane;


    private enum ConnectorType {TOP, RIGHT, BOTTOM, LEFT}

    private ConnectorType connectorType;


    private Button activeButton;


    private final DraggableNode self;

    private DraggableImageview draggableImageview;

    private CustomContextMenu contextMenu;

    private Label plantNumber;

    private ArrayList<DraggableNode> connectedNodes = new ArrayList<>();


    public ArrayList<DraggableNode> getConnectedNodes(){
        return connectedNodes;
    }

    public void addToConnectedNodes(DraggableNode node){
        connectedNodes.add(node);
    }

    public void removeFromConnectedNode(DraggableNode node){
        connectedNodes.remove(node);
    }

    public DraggableNode(AnchorPane _parentPane) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DraggableNode.fxml"));
        parentPane = _parentPane;
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        self = this;


        try {
            fxmlLoader.load();

            plantNumber = new Label();

            contextMenu = new CustomContextMenu(title_bar, plantNumber);


            connectorLeft.setOnAction(e -> {
                connectorType = ConnectorType.LEFT;
                drawConnector(e);

            });

            connectorTop.setOnAction(e -> {
                connectorType = ConnectorType.TOP;
                drawConnector(e);

            });

            connectorBottom.setOnAction(e -> {
                connectorType = ConnectorType.BOTTOM;
                drawConnector(e);

            });

            connectorRight.setOnAction(e -> {
                connectorType = ConnectorType.RIGHT;
                drawConnector(e);

            });

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    private void initialize() {
        buildNodeDragHandlers();
    }

    @FXML
    private void setOnContextMenuRequested(ContextMenuEvent event) {
        System.out.println("requested");
        contextMenu.show(self, event.getScreenX(), event.getScreenY());
//        contextMenu.show(self, parentPane.getLayoutX() + self.getLayoutX(), parentPane.getLayoutY() + self.getLayoutY());
    }

    ;


    public void relocateToPoint(Point2D p) {

        //relocates the object to a point that has been converted to
        //scene coordinates
        Point2D localCoords = getParent().sceneToLocal(p);

        relocate((int) (localCoords.getX() - mDragOffset.getX()), (int) (localCoords.getY() - mDragOffset.getY()));
    }

    public DragIconType getType() {
        return mType;
    }

    public void setType(DragIconType type) {

        mType = type;

        draggableImageview = new DraggableImageview(getClass(), mType);


        imageView.setImage(draggableImageview.getImage());
        title_bar.setText(draggableImageview.getLabel());
    }

    public void buildNodeDragHandlers() {

        mContextDragOver = new EventHandler<DragEvent>() {

            //dragover to handle node dragging in the right pane view
            @Override
            public void handle(DragEvent event) {

                event.acceptTransferModes(TransferMode.ANY);
                relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                event.consume();
            }
        };

        //dragdrop for node dragging
        mContextDragDropped = new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {

                getParent().setOnDragOver(null);
                getParent().setOnDragDropped(null);

                event.setDropCompleted(true);

                event.consume();
            }
        };
        //close button click
        close_button.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                AnchorPane parent = (AnchorPane) self.getParent();
                parent.getChildren().remove(self);
            }

        });


        //drag detection for node dragging
        title_bar.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                getParent().setOnDragOver(null);
                getParent().setOnDragDropped(null);

                getParent().setOnDragOver(mContextDragOver);
                getParent().setOnDragDropped(mContextDragDropped);

                //begin drag ops
                mDragOffset = new Point2D(event.getX(), event.getY());

                relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();

                container.addData("type", mType.toString());
                content.put(DragContainer.AddNode, container);

                startDragAndDrop(TransferMode.ANY).setContent(content);

                event.consume();
            }

        });
    }

    @FXML
    public void drawConnector(ActionEvent event) {

        Button button = (Button) event.getSource();

        activeButton = button;


        if (numberOfTimesClicked < 2) {
            connectingNodes[numberOfTimesClicked] = this;
            numberOfTimesClicked++;
        }


        if (numberOfTimesClicked == 2) {

            numberOfTimesClicked = 0;

            DraggableNode beginningNode = connectingNodes[0];
            DraggableNode endingNode = connectingNodes[1];

            if (beginningNode.equals(endingNode)) {
                return;
            }


            CubicCurve line = new CubicCurve();
            line.setStroke(Color.BLACK);
            line.setFill(null);
            line.setStrokeWidth(2);


            Button beginningNodeActiveButton = beginningNode.getActiveButton();
            Button endingNodeActiveButton = endingNode.getActiveButton();


            ArrayList<ObservableValue> startPoints = getPositions(beginningNode, beginningNodeActiveButton);
            ArrayList<ObservableValue> endPoints = getPositions(endingNode, endingNodeActiveButton);

            ObservableValue<? extends Number> xStart = startPoints.get(0);
            ObservableValue<? extends Number> yStart = startPoints.get(1);
            ObservableValue<? extends Number> xEnd = endPoints.get(0);
            ObservableValue<? extends Number> yEnd = endPoints.get(1);


            line.startXProperty().bind(xStart);
            line.startYProperty().bind(yStart);

            line.endXProperty().bind(xEnd);
            line.endYProperty().bind(yEnd);


            double xStartValue = xStart.getValue().doubleValue();
            double xEndValue = xEnd.getValue().doubleValue();
            double yStartValue = yStart.getValue().doubleValue();
            double yEndValue = yEnd.getValue().doubleValue();

            double xDiff = (double) (Math.abs((xStartValue - xEndValue) / 2) + xStartValue);
            double yDiff = (double) (Math.abs((yStartValue - yEndValue) / 2) + yStartValue);

            if (xStartValue > xEndValue) {
                xDiff = (double) (xStartValue - Math.abs((xStartValue - xEndValue) / 2));
            }

            if (yStartValue > yEndValue) {
                yDiff = (double) (yStartValue - Math.abs((yStartValue - yEndValue) / 2));
            }


            line.setControlX1(xDiff);
            line.setControlX2(xDiff);
            line.setControlY1(yDiff);
            line.setControlY2(yDiff);


            Anchor control1 = new Anchor(Color.GOLD, line.controlX1Property(), line.controlY1Property());
            Anchor control2 = new Anchor(Color.GOLDENROD, line.controlX2Property(), line.controlY2Property());

            parentPane.getChildren().add(new Group(line, control1, control2));


        }

    }


    public ArrayList<ObservableValue> getPositions(DraggableNode node, Button button) {
        ObservableValue<? extends Number> xStart, yStart;

        Node parent = node.getParent();


        switch (node.connectorType) {

            case TOP:

                xStart = node.layoutXProperty().add(node.translateXProperty()).add(node.widthProperty().divide(2));
                yStart = node.layoutYProperty().add(node.translateYProperty());
                break;
            case LEFT:
                xStart = node.layoutXProperty().add(node.translateXProperty());
                yStart = node.layoutYProperty().add(node.translateYProperty()).add(node.heightProperty().divide(2));
                break;
            case RIGHT:
                xStart = node.layoutXProperty().add(node.translateXProperty()).add(node.widthProperty());
                yStart = node.layoutYProperty().add(node.translateYProperty()).add(node.heightProperty().divide(2));
                break;
            case BOTTOM:

                xStart = node.layoutXProperty().add(node.translateXProperty()).add(node.widthProperty().divide(2));

                yStart = node.layoutYProperty().subtract(node.translateYProperty()).add(node.translateYProperty()).add(node.heightProperty());
                break;
            default:
                throw new RuntimeException("Invalid Connector Type");
        }

        ArrayList<ObservableValue> arrayList = new ArrayList<>();

        arrayList.add(xStart);
        arrayList.add(yStart);

        return arrayList;
    }


    public String getTile(){
        return title_bar.getText();
    }

    public String getPlantNumber(){
        return  plantNumber.getText();
    }

    public Button getActiveButton() {
        return activeButton;
    }

    class Anchor extends Circle {
        Anchor(Color color, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 5);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        // make a node movable by dragging it around with the mouse.
        private void enableDrag() {
            final Delta dragDelta = new Delta();
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // record a delta distance for the drag and drop operation.
                    dragDelta.x = getCenterX() - mouseEvent.getX();
                    dragDelta.y = getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                }
            });
            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getX() + dragDelta.x;
                    if (newX > 0 && newX < getScene().getWidth()) {
                        setCenterX(newX);
                    }
                    double newY = mouseEvent.getY() + dragDelta.y;
                    if (newY > 0 && newY < getScene().getHeight()) {
                        setCenterY(newY);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.HAND);
                    }
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            });
        }

        // records relative x and y co-ordinates.
        private class Delta {
            double x, y;
        }
    }

}