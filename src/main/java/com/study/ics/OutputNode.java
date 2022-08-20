package com.study.ics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.google.gson.*;

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

        this.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        this.getStyleClass().add("output");

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() throws IOException {

        Gson gson = new Gson();



        Reader reader = Files.newBufferedReader(Paths.get("errorCode.json"));


        Result[] data = gson.fromJson(reader, Result[].class);
        System.out.println("data: "+data);
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



                                for(Result result: data){
                                    VBox innerVbox = new VBox();

                                    innerVbox.setSpacing(10.0);
                                    innerVbox.setPadding(new Insets(5,5,15,5));

                                    Label title = new Label(result.getCode());
                                    title.setWrapText(true);
                                    title.getStyleClass().add("title");
                                    Label description = new Label(result.getDescription());
                                    description.setWrapText(true);

                                    description.getStyleClass().add("description");
                                    Label recommendation = new Label(result.getRecommendation());
                                    recommendation.setWrapText(true);
                                    recommendation.getStyleClass().add("recommendation");

                                    innerVbox.getChildren().add(title);
                                    innerVbox.getChildren().add(description);
                                    innerVbox.getChildren().add(recommendation);

                                    vBox.getChildren().add(innerVbox);
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

        results.add( new Result("E01","https://www.facebook.com","recommendation"));
        results.add( new Result("E02","https://www.facebook.com","recommendation"));
        results.add( new Result("E03","https://www.facebook.com","recommendation"));
        results.add( new Result("E04","https://www.facebook.com","recommendation"));
        results.add( new Result("E05","https://www.facebook.com","recommendation"));
        results.add( new Result("E06","https://www.facebook.com","recommendation"));
        return results;
    }


}