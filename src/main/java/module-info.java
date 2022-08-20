module com.study.ics {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;

    opens com.study.ics to javafx.fxml;
    exports com.study.ics;
}