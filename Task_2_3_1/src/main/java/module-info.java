module ru.nsu.svirsky.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.svirsky.task_2_3_1.controllers to javafx.fxml;
    opens ru.nsu.svirsky.task_2_3_1 to javafx.fxml;
    exports ru.nsu.svirsky.task_2_3_1;
}