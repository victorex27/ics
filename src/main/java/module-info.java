module com.study.ics {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.study.ics to javafx.fxml;
    exports com.study.ics;
}