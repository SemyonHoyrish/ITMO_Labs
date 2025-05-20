package itmo.semyonh.lab8;

import itmo.semyonh.lab8.types.StudyGroup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUICLient extends Application {
    private Client client;

    //App runtime config
    private boolean useVisualisationView = false;

    private void initMenuBar(Pane pane) {

        var view = new Menu("view");
        var command = new Menu("command");
        var configuration = new Menu("command");

        {//view
            var tv = new MenuItem("use table view");
            tv.setOnAction((e) -> { useVisualisationView = false; });
            var vv = new MenuItem("use visualisation view");
            vv.setOnAction((e) -> { useVisualisationView = true; });
            view.getItems().addAll(tv, vv);
        }
        {//command

        }
        {//configuration

        }

        var mb = new MenuBar(view, command, configuration);
        pane.getChildren().add(mb);

        mb.setUseSystemMenuBar(true);
    }

    private void initTableView(Pane pane) {

    }

    private void initVisualisationView(Pane pane) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        var host = System.getenv("HOST");
        var portString = System.getenv("PORT");
        var port = Integer.parseInt(portString);
        client = new Client(host, port);

        var p = new Pane();
        initMenuBar(p);

        var scene = new Scene(p, 800, 600);

        var t = new Label();
        t.setText("hello");
        p.getChildren().add(t);

        stage.setTitle("Collection Manager - GUI Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void launch_gui() {
        launch(GUICLient.class);
    }
}
