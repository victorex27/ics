package com.study.ics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OutputNode extends AnchorPane {



    @FXML
    private Button scan;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private VBox vBox;

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

//    final WebView browser = new WebView();
//    final WebEngine webEngine = browser.getEngine();


    public OutputNode() {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("OutputNode.fxml")
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

        scan.setOnAction(e -> {

            scan.setDisable(true);

            progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);



            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                progressBar.setProgress(0);
                                scan.setDisable(false);

                                ArrayList<Result> results = OutputNode.getResult();

                                for(Result result: results){
//                                    Pane pane = new Pane();

                                    Hyperlink a = new Hyperlink(result.getErrorCode());




                                    a.setOnAction( e->{

//                                            webEngine.load(result.getDescriptionLink());
                                    });

                                    vBox.getChildren().add(a);
                                }


                            });

                        }
                    },
                    10000
            );

        });


    }


    public static ArrayList<Result> getResult(){
        ArrayList<Result> results = new ArrayList<Result>();

        results.add( new Result("E01","https://www.facebook.com"));
        results.add( new Result("E02","https://www.facebook.com"));
        results.add( new Result("E03","https://www.facebook.com"));
        results.add( new Result("E04","https://www.facebook.com"));
        results.add( new Result("E05","https://www.facebook.com"));
        results.add( new Result("E06","https://www.facebook.com"));
        return results;
    }


}