package itmo.semyonh.lab8;

import com.sun.javafx.scene.control.InputField;
import itmo.semyonh.lab8.commands.*;
import itmo.semyonh.lab8.helpers.Converter;
import itmo.semyonh.lab8.net.*;
import itmo.semyonh.lab8.types.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.*;

import java.io.IOException;
import java.rmi.dgc.Lease;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.UnaryOperator;

// TODO:
// spcify host & port in gui
// add owner field
public class GUICLient extends Application {
    interface CommandResultCallback {
        void run(Response r);
    }

    private Client client;
    private volatile long lastRequestID;
    private volatile long lastRetrieveID;
    private ArrayList<StudyGroupWrapper> pollingData;
    private ObservableList<StudyGroupWrapper> data;
    private volatile int pollInterval = 1000;
    private Credentials credentials = new Credentials("test", "test2");
    private HashMap<Long, Long> waitingForResponse = new HashMap<>();
    private HashMap<Long, CommandResultCallback> commandsCallbacks = new HashMap<>();

//    private TableView tableView;
    private Stage modalWindowStage;
    private Stage notificationWindowStage;
    private Canvas canvas;
    private final List<javafx.scene.paint.Color> ALL_COLORS = Arrays.asList(
            javafx.scene.paint.Color.ALICEBLUE,
            javafx.scene.paint.Color.ANTIQUEWHITE,
            javafx.scene.paint.Color.AQUA,
            javafx.scene.paint.Color.AQUAMARINE,
            javafx.scene.paint.Color.AZURE,
            javafx.scene.paint.Color.BEIGE,
            javafx.scene.paint.Color.BISQUE,
            javafx.scene.paint.Color.BLACK,
            javafx.scene.paint.Color.BLANCHEDALMOND,
            javafx.scene.paint.Color.BLUE,
            javafx.scene.paint.Color.BLUEVIOLET,
            javafx.scene.paint.Color.BROWN,
            javafx.scene.paint.Color.BURLYWOOD,
            javafx.scene.paint.Color.CADETBLUE,
            javafx.scene.paint.Color.CHARTREUSE,
            javafx.scene.paint.Color.CHOCOLATE,
            javafx.scene.paint.Color.CORAL,
            javafx.scene.paint.Color.CORNFLOWERBLUE,
            javafx.scene.paint.Color.CORNSILK,
            javafx.scene.paint.Color.CRIMSON,
            javafx.scene.paint.Color.CYAN,
            javafx.scene.paint.Color.DARKBLUE,
            javafx.scene.paint.Color.DARKCYAN,
            javafx.scene.paint.Color.DARKGOLDENROD,
            javafx.scene.paint.Color.DARKGRAY,
            javafx.scene.paint.Color.DARKGREEN,
            javafx.scene.paint.Color.DARKGREY,
            javafx.scene.paint.Color.DARKKHAKI,
            javafx.scene.paint.Color.DARKMAGENTA,
            javafx.scene.paint.Color.DARKOLIVEGREEN,
            javafx.scene.paint.Color.DARKORANGE,
            javafx.scene.paint.Color.DARKORCHID,
            javafx.scene.paint.Color.DARKRED,
            javafx.scene.paint.Color.DARKSALMON,
            javafx.scene.paint.Color.DARKSEAGREEN,
            javafx.scene.paint.Color.DARKSLATEBLUE,
            javafx.scene.paint.Color.DARKSLATEGRAY,
            javafx.scene.paint.Color.DARKSLATEGREY,
            javafx.scene.paint.Color.DARKTURQUOISE,
            javafx.scene.paint.Color.DARKVIOLET,
            javafx.scene.paint.Color.DEEPPINK,
            javafx.scene.paint.Color.DEEPSKYBLUE,
            javafx.scene.paint.Color.DIMGRAY,
            javafx.scene.paint.Color.DIMGREY,
            javafx.scene.paint.Color.DODGERBLUE,
            javafx.scene.paint.Color.FIREBRICK,
            javafx.scene.paint.Color.FLORALWHITE,
            javafx.scene.paint.Color.FORESTGREEN,
            javafx.scene.paint.Color.FUCHSIA,
            javafx.scene.paint.Color.GAINSBORO,
            javafx.scene.paint.Color.GHOSTWHITE,
            javafx.scene.paint.Color.GOLD,
            javafx.scene.paint.Color.GOLDENROD,
            javafx.scene.paint.Color.GRAY,
            javafx.scene.paint.Color.GREEN,
            javafx.scene.paint.Color.GREENYELLOW,
            javafx.scene.paint.Color.GREY,
            javafx.scene.paint.Color.HONEYDEW,
            javafx.scene.paint.Color.HOTPINK,
            javafx.scene.paint.Color.INDIANRED,
            javafx.scene.paint.Color.INDIGO,
            javafx.scene.paint.Color.IVORY,
            javafx.scene.paint.Color.KHAKI,
            javafx.scene.paint.Color.LAVENDER,
            javafx.scene.paint.Color.LAVENDERBLUSH,
            javafx.scene.paint.Color.LAWNGREEN,
            javafx.scene.paint.Color.LEMONCHIFFON,
            javafx.scene.paint.Color.LIGHTBLUE,
            javafx.scene.paint.Color.LIGHTCORAL,
            javafx.scene.paint.Color.LIGHTCYAN,
            javafx.scene.paint.Color.LIGHTGOLDENRODYELLOW,
            javafx.scene.paint.Color.LIGHTGRAY,
            javafx.scene.paint.Color.LIGHTGREEN,
            javafx.scene.paint.Color.LIGHTGREY,
            javafx.scene.paint.Color.LIGHTPINK,
            javafx.scene.paint.Color.LIGHTSALMON,
            javafx.scene.paint.Color.LIGHTSEAGREEN,
            javafx.scene.paint.Color.LIGHTSKYBLUE,
            javafx.scene.paint.Color.LIGHTSLATEGRAY,
            javafx.scene.paint.Color.LIGHTSLATEGREY,
            javafx.scene.paint.Color.LIGHTSTEELBLUE,
            javafx.scene.paint.Color.LIGHTYELLOW,
            javafx.scene.paint.Color.LIME,
            javafx.scene.paint.Color.LIMEGREEN,
            javafx.scene.paint.Color.LINEN,
            javafx.scene.paint.Color.MAGENTA,
            javafx.scene.paint.Color.MAROON,
            javafx.scene.paint.Color.MEDIUMAQUAMARINE,
            javafx.scene.paint.Color.MEDIUMBLUE,
            javafx.scene.paint.Color.MEDIUMORCHID,
            javafx.scene.paint.Color.MEDIUMPURPLE,
            javafx.scene.paint.Color.MEDIUMSEAGREEN,
            javafx.scene.paint.Color.MEDIUMSLATEBLUE,
            javafx.scene.paint.Color.MEDIUMSPRINGGREEN,
            javafx.scene.paint.Color.MEDIUMTURQUOISE,
            javafx.scene.paint.Color.MEDIUMVIOLETRED,
            javafx.scene.paint.Color.MIDNIGHTBLUE,
            javafx.scene.paint.Color.MINTCREAM,
            javafx.scene.paint.Color.MISTYROSE,
            javafx.scene.paint.Color.MOCCASIN,
            javafx.scene.paint.Color.NAVAJOWHITE,
            javafx.scene.paint.Color.NAVY,
            javafx.scene.paint.Color.OLDLACE,
            javafx.scene.paint.Color.OLIVE,
            javafx.scene.paint.Color.OLIVEDRAB,
            javafx.scene.paint.Color.ORANGE,
            javafx.scene.paint.Color.ORANGERED,
            javafx.scene.paint.Color.ORCHID,
            javafx.scene.paint.Color.PALEGOLDENROD,
            javafx.scene.paint.Color.PALEGREEN,
            javafx.scene.paint.Color.PALETURQUOISE,
            javafx.scene.paint.Color.PALEVIOLETRED,
            javafx.scene.paint.Color.PAPAYAWHIP,
            javafx.scene.paint.Color.PEACHPUFF,
            javafx.scene.paint.Color.PERU,
            javafx.scene.paint.Color.PINK,
            javafx.scene.paint.Color.PLUM,
            javafx.scene.paint.Color.POWDERBLUE,
            javafx.scene.paint.Color.PURPLE,
            javafx.scene.paint.Color.RED,
            javafx.scene.paint.Color.ROSYBROWN,
            javafx.scene.paint.Color.ROYALBLUE,
            javafx.scene.paint.Color.SADDLEBROWN,
            javafx.scene.paint.Color.SALMON,
            javafx.scene.paint.Color.SANDYBROWN,
            javafx.scene.paint.Color.SEAGREEN,
            javafx.scene.paint.Color.SEASHELL,
            javafx.scene.paint.Color.SIENNA,
            javafx.scene.paint.Color.SILVER,
            javafx.scene.paint.Color.SKYBLUE,
            javafx.scene.paint.Color.SLATEBLUE,
            javafx.scene.paint.Color.SLATEGRAY,
            javafx.scene.paint.Color.SLATEGREY,
            javafx.scene.paint.Color.SNOW,
            javafx.scene.paint.Color.SPRINGGREEN,
            javafx.scene.paint.Color.STEELBLUE,
            javafx.scene.paint.Color.TAN,
            javafx.scene.paint.Color.TEAL,
            javafx.scene.paint.Color.THISTLE,
            javafx.scene.paint.Color.TOMATO,
            javafx.scene.paint.Color.TURQUOISE,
            javafx.scene.paint.Color.VIOLET,
            javafx.scene.paint.Color.WHEAT,
            javafx.scene.paint.Color.WHITE,
            javafx.scene.paint.Color.WHITESMOKE,
            javafx.scene.paint.Color.YELLOW,
            javafx.scene.paint.Color.YELLOWGREEN
    );

    private volatile boolean executing = true;
    private Thread threadPoll;
    private Thread threadRetrieve;


    //App runtime config
    private SimpleBooleanProperty useVisualisationViewProperty = new SimpleBooleanProperty(false);

    private void showNotification(String text) {
        var p = new VBox();
        var l = new Label(text);
        var b = new Button("close");
        b.setOnAction(e -> {
            notificationWindowStage.hide();
        });
        p.getChildren().addAll(l, b);
        Scene s = new Scene(p, 600, 100);
        notificationWindowStage.setTitle("Notification");
        notificationWindowStage.setScene(s);
        notificationWindowStage.show();
    }

    interface GUICommandCallback<V> {
        void run(V v);
    }
    private Pane createStudyGroupInput(StudyGroupWrapper base, GUICommandCallback<StudyGroupWrapper> callback) {
        if (base == null) {
            base = new StudyGroupWrapper(new StudyGroup(), "");
            base.id.setValue(0);
        }

        var p = new VBox();

        var id = new HBox();
        var idLabel = new Label("ID: ");
        var idValue = new TextField();
        idValue.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        idValue.setText(base.id.getValue().toString());
        id.getChildren().addAll(idLabel, idValue);

        var name = new HBox();
        var nameLabel = new Label("Name: ");
        var nameValue = new TextField(base.name.getValue());
        name.getChildren().addAll(nameLabel, nameValue);

        var coordX = new HBox();
        var coordXLabel = new Label("Coordinates.x: ");
        var coordXValue = new TextField();
        coordXValue.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));
        coordXValue.setText(base.coordinatesX.getValue().toString());
        coordX.getChildren().addAll(coordXLabel, coordXValue);

        var coordY = new HBox();
        var coordYLabel = new Label("Coordinates.y: ");
        var coordYValue = new TextField();
        coordYValue.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        coordYValue.setText(base.coordinatesY.getValue().toString());
        coordY.getChildren().addAll(coordYLabel, coordYValue);

        var date = new HBox();
        var dateLabel = new Label("Date: ");
        var dateValue = new Label(base.creationDate.getValue());
        date.getChildren().addAll(dateLabel, dateValue);

        var studentsCount = new HBox();
        var studentsCountLabel = new Label("Student count: ");
        var studentsCountValue = new TextField();
        studentsCountValue.setTextFormatter(new TextFormatter<>(new LongStringConverter()));
        studentsCountValue.setText(base.studentsCount.getValue().toString());
        studentsCount.getChildren().addAll(studentsCountLabel, studentsCountValue);

        var shouldBeExpelled = new HBox();
        var shouldBeExpelledLabel = new Label("Should be expelled: ");
        var shouldBeExpelledValue = new TextField();
        shouldBeExpelledValue.setTextFormatter(new TextFormatter<>(new LongStringConverter()));
        shouldBeExpelledValue.setText(base.shouldBeExpelled.getValue().toString());
        shouldBeExpelled.getChildren().addAll(shouldBeExpelledLabel, shouldBeExpelledValue);

        var formOfEducation = new HBox();
        var formOfEducationLabel = new Label("Form of Education: ");
        var formOfEducationValue = new ComboBox<FormOfEducation>();
        formOfEducationValue.getItems().addAll(FormOfEducation.values());
        formOfEducationValue.setValue(base.formOfEducation.getValue().isEmpty() ? null : FormOfEducation.valueOf(base.formOfEducation.getValue()));
        formOfEducation.getChildren().addAll(formOfEducationLabel, formOfEducationValue);

        var semester = new HBox();
        var semesterLabel = new Label("Semester: ");
        var semesterValue = new ComboBox<Semester>();
        semesterValue.getItems().addAll(Semester.values());
        semesterValue.setValue(base.semester.getValue().isEmpty() ? null : Semester.valueOf(base.semester.getValue()));
        semester.getChildren().addAll(semesterLabel, semesterValue);


        var groupAdminLabel = new Label("Group admin: ");

        var gaName = new HBox();
        var gaNameLabel = new Label("Name: ");
        var gaNameValue = new TextField(base.groupAdmin.name.getValue());
        gaName.getChildren().addAll(gaNameLabel, gaNameValue);

        var gaWeight = new HBox();
        var gaWeightLabel = new Label("Weight: ");
        var gaWeightValue = new TextField();
        gaWeightValue.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        gaWeightValue.setText(base.groupAdmin.weight.getValue().toString());
        gaWeight.getChildren().addAll(gaWeightLabel, gaWeightValue);

        var gaEyeColor = new HBox();
        var gaEyeColorLabel = new Label("EyeColor: ");
        var gaEyeColorValue = new ComboBox<Color>();
        gaEyeColorValue.getItems().addAll(Color.values());
        gaEyeColorValue.setValue(base.groupAdmin.eyeColor.getValue().isEmpty() ? null : Color.valueOf(base.groupAdmin.eyeColor.getValue()));
        gaEyeColor.getChildren().addAll(gaEyeColorLabel, gaEyeColorValue);

        var gaHairColor = new HBox();
        var gaHairColorLabel = new Label("HairColor: ");
        var gaHairColorValue = new ComboBox<Color>();
        gaHairColorValue.getItems().addAll(Color.values());
        gaHairColorValue.getItems().add(null);
        gaHairColorValue.setValue(base.groupAdmin.hairColor.getValue().isEmpty() ? null : Color.valueOf(base.groupAdmin.hairColor.getValue()));
        gaHairColor.getChildren().addAll(gaHairColorLabel, gaHairColorValue);

        var gaNationality = new HBox();
        var gaNationalityLabel = new Label("Nationality: ");
        var gaNationalityValue = new ComboBox<Country>();
        gaNationalityValue.getItems().addAll(Country.values());
        gaNationalityValue.setValue(base.groupAdmin.nationality.getValue().isEmpty() ? null : Country.valueOf(base.groupAdmin.nationality.getValue()));
        gaNationality.getChildren().addAll(gaNationalityLabel, gaNationalityValue);


        var spacer = new Region();
        spacer.setPrefHeight(20);

        p.getChildren().addAll(id, name, coordX, coordY, date,
                    studentsCount, shouldBeExpelled,
                    formOfEducation, semester, spacer, groupAdminLabel,
                gaName, gaWeight, gaEyeColor, gaHairColor, gaNationality);

        for (var ch : p.getChildren()) {
            if (ch instanceof HBox) {
                for (var chch : ((HBox) ch).getChildren()) {
                    if (chch instanceof Control) {
                        ((Control) chch).setMinWidth(200);
                    }
                }
            }
        }

        var execute = new Button("execute");
        p.getChildren().addAll(execute);

        var originDate = Date.from(ZonedDateTime.parse(base.creationDate.getValue(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy")).toInstant());

        execute.setOnAction((e) -> {
            StudyGroupWrapper w = null;
            try {
                var g = new StudyGroup();
                g.setId(Integer.parseInt(idValue.getText()));
                g.setName(nameValue.getText());
                var c = new Coordinates();
                c.setX(Float.parseFloat(coordXValue.getText()));
                c.setY(Double.parseDouble(coordYValue.getText()));
                g.setCoordinates(c);
                g.setCreationDate(originDate);
                g.setStudentsCount(Integer.parseInt(studentsCountValue.getText()));
                g.setShouldBeExpelled(Long.parseLong(shouldBeExpelledValue.getText()));
                g.setFormOfEducation(formOfEducationValue.getValue());
                g.setSemester(semesterValue.getValue());
                var pers = new Person();
                pers.setName(gaNameValue.getText());
                pers.setWeight(Double.parseDouble(gaWeightValue.getText()));
                pers.setEyeColor(gaEyeColorValue.getValue());
                pers.setHairColor(gaHairColorValue.getValue());
                pers.setNationality(gaNationalityValue.getValue());
                g.setGroupAdmin(pers);
                w = new StudyGroupWrapper(g, "");
            } catch (IllegalArgumentException ex) {
                showNotification("Bad value: " + ex.getMessage()); // trn
                return;
            }

            callback.run(w);
            modalWindowStage.hide();
        });

        return p;
    }

    private void initMenuBarCommands(Menu menu) {
        var add = new MenuItem("add");
        add.setOnAction(e -> {
            var p = createStudyGroupInput(null, (v) -> {
                try {
                    send(new AddCommand(v.convert()), (eee) -> {
                        System.out.println("sdhfkjsdfhksjdhfkjds");
                    });
                } catch (IllegalArgumentException ex) {
                    showNotification("Error: " + ex.getMessage());
                }
            });
            var scene = new Scene(p, 400, 600);
            modalWindowStage.setTitle("command -> add");
            modalWindowStage.setScene(scene);
            modalWindowStage.show();
        });

        var addIfMin = new MenuItem("add_if_min");
        addIfMin.setOnAction(e -> {
            var p = createStudyGroupInput(null, (v) -> {
                try {
                    send(new AddIfMinCommand(v.convert()));
                } catch (IllegalArgumentException ex) {
                    showNotification("Error: " + ex.getMessage());
                }
            });
            var scene = new Scene(p, 400, 600);
            modalWindowStage.setTitle("command -> add_if_min");
            modalWindowStage.setScene(scene);
            modalWindowStage.show();
        });

        var clear = new MenuItem("clear");
        clear.setOnAction(e -> {
            send(new ClearCommand());
        });

        var help = new MenuItem("help");
        help.setOnAction(e -> {
            var p = new VBox();
            p.getChildren().add(new Label(new AddCommand((StudyGroup) null).getDescription()));
            p.getChildren().add(new Label(new AddIfMinCommand((StudyGroup) null).getDescription()));
            p.getChildren().add(new Label(new ClearCommand().getDescription()));
            p.getChildren().add(new Label(new HelpCommand(null).getDescription()));
            p.getChildren().add(new Label(new InfoCommand(null).getDescription()));
            p.getChildren().add(new Label(new MaxByCoordsCommand().getDescription()));
            p.getChildren().add(new Label(new PrintFieldDescFormOfEducationCommand().getDescription()));
            p.getChildren().add(new Label(new PrintUniqueShouldBeExpelledCommand().getDescription()));
            p.getChildren().add(new Label(new RemoveByIdCommand().getDescription()));
            p.getChildren().add(new Label(new RemoveGreaterCommand((StudyGroup) null).getDescription()));
            p.getChildren().add(new Label(new UpdateCommand((StudyGroup) null).getDescription()));
            var s = new Scene(p, 600, 400);
            modalWindowStage.setTitle("command -> help");
            modalWindowStage.setScene(s);
            modalWindowStage.show();
        });


        var info = new MenuItem("info");
        info.setOnAction(e -> {
            send(new InfoCommand(null), (r) -> {
                if (r.status() == Status.PROCESSED) {
                    showNotification(r.response());
                } else {
                    showNotification("info error: " + r.response());
                }
            });
        });

        var maxByCoords = new MenuItem("max_by_coords");
        maxByCoords.setOnAction(e -> {
            send(new MaxByCoordsCommand(), (r) -> {
                if (r.status() == Status.PROCESSED) {
                    updateCommand(Converter.studyGroupFromJson(r.response()).getId());
                } else {
                    showNotification("error: " + r.response());
                }
            });
        });

        var pfdfoe = new MenuItem("field_desc_form");
        pfdfoe.setOnAction(e -> {
            send(new PrintFieldDescFormOfEducationCommand(), (r) -> {
                if (r.status() == Status.PROCESSED) {
                    showNotification(r.response());
                } else {
                    showNotification("error: " + r.response());
                }
            });
        });

        var pusbe = new MenuItem("unique_should_be_expelled");
        pusbe.setOnAction(e -> {
            send(new PrintUniqueShouldBeExpelledCommand(), r -> {
                if (r.status() == Status.PROCESSED) {
                    showNotification(r.response());
                } else {
                    showNotification("error: " + r.response());
                }
            });
        });

        var removeByID = new MenuItem("remove_by_id");
        removeByID.setOnAction(e -> {
            var p = new VBox();
            var pp = new HBox();
            var l = new Label("ID: ");
            var input = new TextField("0");
            input.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
            pp.getChildren().addAll(l, input);
            var btn = new Button("remove");
            btn.setOnAction((ee) -> {
                    send(new RemoveByIdCommand().with(new String[]{input.getText()}), (eee) -> {
                        if (eee.status() == Status.FAILED) {
                            showNotification("remove by id failed:" + eee.response());
                        }
                    });
                    modalWindowStage.hide();
                }
            );
            p.getChildren().addAll(pp, btn);
            var s = new Scene(p, 200, 100);
            modalWindowStage.setTitle("command -> remove_by_id");
            modalWindowStage.setScene(s);
            modalWindowStage.show();
        });

        var removeGreater = new MenuItem("remove_greater");
        removeGreater.setOnAction(e -> {
            var p = createStudyGroupInput(null, (v) -> {
                try {
                    send(new RemoveGreaterCommand(v.convert()));
                } catch (IllegalArgumentException ex) {
                    showNotification("Error: " + ex.getMessage());
                }
            });
            var scene = new Scene(p, 400, 600);
            modalWindowStage.setTitle("command -> remove_greater");
            modalWindowStage.setScene(scene);
            modalWindowStage.show();
        });

        var update = new MenuItem("update");
        update.setOnAction(e -> {
            updateCommand(0);
        });


        menu.getItems().addAll(add, addIfMin, clear, help, info, maxByCoords, pfdfoe, pusbe, removeByID, removeGreater, update);
    }

    private void updateCommand(int id) {
        StudyGroupWrapper base = null;
        if (id > 0) {
            for (var d : data) {
                if (d.id.get() == id) {
                    base = d;
                }
            }
        }
        var p = createStudyGroupInput(base, (v) -> {
            try {
                send(new UpdateCommand(v.convert()).with(new String[] { v.id.getValue().toString() }));
            } catch (IllegalArgumentException ex) {
                showNotification("Error: " + ex.getMessage());
            }
        });
        var scene = new Scene(p, 400, 600);
        modalWindowStage.setTitle("command -> update");
        modalWindowStage.setScene(scene);
        modalWindowStage.show();
    }

    private void initMenuBar(Pane pane) {

        var view = new Menu("view");
        var command = new Menu("command");
        var configuration = new Menu("configuration");

        {//view
            var tv = new MenuItem("use table view");
            tv.setOnAction((e) -> { useVisualisationViewProperty.setValue(false); });
            var vv = new MenuItem("use visualisation view");
            vv.setOnAction((e) -> { useVisualisationViewProperty.setValue(true); });
            view.getItems().addAll(tv, vv);
        }
        {//command
            initMenuBarCommands(command);
        }
        {//configuration
            var acc = new MenuItem("account");
            {
                var p = new VBox();

                var login = new TextField(new String(credentials.login()));
                var pass = new PasswordField();
                pass.setText(new String(credentials.password()));

                var save = new Button("save");
                save.setOnAction((e) -> {
                    credentials = new Credentials(login.getText(), pass.getText());
                });

                var registerAccount = new Button("register-account");
                registerAccount.setOnAction((e) -> {
                });

                p.getChildren().addAll(login, pass, save);

                var accScene = new Scene(p, 300, 200);

                acc.setOnAction((e) -> {
                    modalWindowStage.setTitle("configuration -> account");
                    modalWindowStage.setScene(accScene);
                    modalWindowStage.show();
                });
            }
            configuration.getItems().add(acc);
        }

        var mb = new MenuBar(view, command, configuration);
        pane.getChildren().add(mb);

        mb.setUseSystemMenuBar(true);
    }

    private void initTableView(Pane pane) {
        var tv = new TableView<StudyGroupWrapper>();

        tv.setRowFactory(r -> {
            TableRow<StudyGroupWrapper> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    if (row.getItem().owner.getValue().equals(credentials.login())) {
                        updateCommand(row.getItem().id.get());
                    }
                }
            });
            return row;
        });

        var owner = new TableColumn<StudyGroupWrapper, String>("owner");
        owner.setCellValueFactory(cd -> cd.getValue().owner);

        var id = new TableColumn<StudyGroupWrapper, Integer>("id");
        id.setCellValueFactory((cd) -> cd.getValue().id.asObject());
        var name = new TableColumn<StudyGroupWrapper, String>("name");
        name.setCellValueFactory((cd) -> cd.getValue().name);
        var coordinatesX = new TableColumn<StudyGroupWrapper, Float>("coordinates.x");
        var coordinatesY = new TableColumn<StudyGroupWrapper, Double>("coordinates.y");
        coordinatesX.setCellValueFactory(cd -> cd.getValue().coordinatesX.asObject());
        coordinatesY.setCellValueFactory(cd -> cd.getValue().coordinatesY.asObject());
        var creationDate = new TableColumn<StudyGroupWrapper, String>("creation_date");
        creationDate.setCellValueFactory((cd) -> cd.getValue().creationDate);
        var studentsCount = new TableColumn<StudyGroupWrapper, Long>("students_count");
        studentsCount.setCellValueFactory(cd -> cd.getValue().studentsCount.asObject());
        var shouldBeExpelled = new TableColumn<StudyGroupWrapper, Long>("should_be_expelled");
        shouldBeExpelled.setCellValueFactory(cd -> cd.getValue().shouldBeExpelled.asObject());
        var formOfEducation = new TableColumn<StudyGroupWrapper, String>("form_of_education");
        formOfEducation.setCellValueFactory(cd -> cd.getValue().formOfEducation);
        var semester = new TableColumn<StudyGroupWrapper, String>("semester");
        semester.setCellValueFactory(cd -> cd.getValue().semester);

        var admin_name = new TableColumn<StudyGroupWrapper, String>("admin_name");
        admin_name.setCellValueFactory(cd -> cd.getValue().groupAdmin.name);
        var admin_weight = new TableColumn<StudyGroupWrapper, Double>("admin_weight");
        admin_weight.setCellValueFactory(cd -> cd.getValue().groupAdmin.weight.asObject());
        var admin_eye_color = new TableColumn<StudyGroupWrapper, String>("admin_eye_color");
        admin_eye_color.setCellValueFactory(cd -> cd.getValue().groupAdmin.eyeColor);
        var admin_hair_color = new TableColumn<StudyGroupWrapper, String>("admin_hair_color");
        admin_hair_color.setCellValueFactory(cd -> cd.getValue().groupAdmin.hairColor);
        var admin_nationality = new TableColumn<StudyGroupWrapper, String>("admin_nationality");
        admin_nationality.setCellValueFactory(cd -> cd.getValue().groupAdmin.nationality);

        tv.getColumns().addAll(owner, id, name, coordinatesX, coordinatesY, creationDate, studentsCount, shouldBeExpelled, formOfEducation,
                                    admin_name, admin_weight, admin_hair_color, admin_nationality);
        tv.visibleProperty().bind(useVisualisationViewProperty.not());
        tv.setItems(data);
        pane.getChildren().add(tv);
//        tableView = tv;
    }

    private void initVisualisationView(Pane pane) {
        var pp = new Pane();
        var c = new Canvas();
        canvas = c;
        c.setWidth(1200);
        c.setHeight(600);
        pp.getChildren().add(c);

        pane.getChildren().add(pp);
        pp.visibleProperty().bind(useVisualisationViewProperty);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var host = System.getenv("HOST");
        var portString = System.getenv("PORT");
        var port = Integer.parseInt(portString);
        modalWindowStage = new Stage();
        notificationWindowStage = new Stage();
        client = new Client(host, port);
        client.init();
        pollingData = new ArrayList<>();
        data = FXCollections.observableArrayList();

        threadPoll = new Thread(() -> {
            while (true) {
                if (!executing) { return; }
//                System.out.println("send");

                migrateData();

                send(new RetrieveCommand());
                lastRetrieveID = lastRequestID - 1;
                try {
                    Thread.sleep(pollInterval);
                } catch (InterruptedException ignored) {
                }
            }
        });
        threadRetrieve = new Thread(() -> {
            //todo:
            while (true) {
                if (!executing) { return; }
//                System.out.println("receive wait");
                var r = client.recieve();
                if (r != null) {
                    receive(r);
                }
            }
        });

        var p1 = new VBox();
        var p2 = new VBox();
        initMenuBar(p1);
        initMenuBar(p2);
        initTableView(p1);
        initVisualisationView(p2);

        var sceneTable = new Scene(p1, 1200, 600);
        var sceneVisual = new Scene(p2, 1200, 600);

        useVisualisationViewProperty.addListener(e -> {
            if (useVisualisationViewProperty.get()) {
                stage.setScene(sceneVisual);
            } else {
                stage.setScene(sceneTable);
            }
        });

        stage.setTitle("Collection Manager - GUI Client");
        stage.setScene(sceneTable);
        stage.show();

        threadPoll.start();
        threadRetrieve.start();

        drawCanvas();
    }

    @Override
    public void stop() throws Exception {
        executing = false;
        threadPoll.join();
        threadRetrieve.join();
    }

    public static void launch_gui() {
        launch(GUICLient.class);
    }

    private void migrateData() {
        var s = new HashSet<Integer>();
        for (var d : data) {
            s.add(d.id.get());
        }
        for (var i : pollingData) {
            if (!s.contains(i.id.get())) {
                data.add(i);
            } else {
                for (var d : data) {
                    if (d.id.get() == i.id.get()) {
                        d.migrate(i);
                    }
                }
                s.remove(i.id.get());
            }
        }
        data.removeIf((i) -> s.contains(i.id.get()));
        pollingData.clear();
    }

    private HashMap<Long, List<Double>> drawCurrent = new HashMap<>();
    private double drawSpeed = 2;
    private void drawCanvas() {
//        if (drawing) { return; }
//        drawing = true;

        var context = canvas.getGraphicsContext2D();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                context.setFill(javafx.scene.paint.Color.WHITE);
                context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                for (var i : data) {
                    if (!drawCurrent.containsKey((long)i.id.get())) {
                        drawCurrent.put((long)i.id.get(), new ArrayList<>(Arrays.asList(0.0, 0.0)));
                    }

                    var vals = drawCurrent.get((long)i.id.get());
                    vals.set(0, Math.min(vals.get(0) + drawSpeed, i.coordinatesX.getValue()));
                    vals.set(1, Math.min(vals.get(1) + drawSpeed, -i.coordinatesY.getValue()));
                    drawCurrent.put((long)i.id.get(), vals);

                    context.setFill(ALL_COLORS.get(i.owner.getValue().hashCode() % ALL_COLORS.size()));
//                    context.fillOval(i.coordinatesX.getValue(), -i.coordinatesY.getValue(), 10, 10);
                    context.fillOval(vals.get(0), vals.get(1), 10, 10);

                    if (Math.abs(vals.get(0) -i.coordinatesX.getValue() ) < 0.1 && Math.abs(vals.get(1) - (-i.coordinatesY.getValue()) ) < 0.1) {
                        context.fillText(i.owner.getValue(), i.coordinatesX.getValue(), -i.coordinatesY.getValue() - 20);
                    }
                }
            }
        };
        timer.start();

        canvas.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();

            for (var i : data) {
                double dx = mouseX - (i.coordinatesX.getValue() + 5);
                double dy = mouseY - (-i.coordinatesY.getValue() + 5);
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= 5) {
                    updateCommand(i.id.get());
                }
            }

        });

    }

    private void send(Command c) {
        long id = lastRequestID;
        client.send(new Request(lastRequestID++, c.getName(), Converter.commandToJson(c), credentials));
        waitingForResponse.put(lastRequestID, Instant.now().getEpochSecond());
    }
    private void send(Command c, CommandResultCallback callback) {
        long id = lastRequestID;
        client.send(new Request(lastRequestID++, c.getName(), Converter.commandToJson(c), credentials));
        waitingForResponse.put(id, Instant.now().getEpochSecond());
        commandsCallbacks.put(id, callback);
    }

    private void receive(Response r) {
        if (!waitingForResponse.containsKey(r.requestID())) return;

        if (commandsCallbacks.containsKey(r.requestID())) {
            var callback = commandsCallbacks.get(r.requestID());
            Platform.runLater(() -> {
                callback.run(r);
            });
            commandsCallbacks.remove(r.requestID());
            waitingForResponse.remove(r.requestID());
        } else {
            if (r.type() == ResponseType.ItemStream) {
                if (r.status() == Status.FAILED) {
                    System.out.println("ItemStream failed package: " + r.response());
                } else if (r.status() == Status.PROCESSED && r.requestID() == lastRetrieveID) {
                    var item = Converter.studyGroupFromJson(r.response());
                    pollingData.add(new StudyGroupWrapper(item, r.objectOwnerLogin() != null ? r.objectOwnerLogin() : "<unknown>"));
                }
            }
        }
    }
}
