package WorkDataBase.Clients;

import Bot.BotTelegram;
import Format.RandomCod;
import Format.TextFormat;
import GUIMain.CustomStage.ErrorStage;
import GUIMain.CustomStage.InfoStage;
import GUIMain.CustomStage.WarningStage;
import GUIMain.Styles.CssUrl;
import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;

public class NewClientStage {


        private ClientClass client;
        private VBox vBoxMain;
        private Button buttonStageCod;
        private Button buttonNewReg;
        private Button buttonDeleteReg;
        private boolean save;

        public NewClientStage (Node node, String... stringsNameClients) {
            save = false;
            this.client = new ClientClass();

            client.getRegTelegramInfo();
            Stage mainStage = (Stage) node.getScene ().getWindow ();
            Stage clientStage = new Stage();
            clientStage.setTitle("Добавить нового клиента");
            vBoxMain = new VBox();
            vBoxMain.setAlignment(Pos.CENTER);
            vBoxMain.getStylesheets().add(new CssUrl().get ());

            VBox vBox = new VBox();

            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(5);
            vBox.setMaxSize(600,800);

            Label firstNameLab = new Label("Фамилия");
            TextField firstNameText = new TextField();
            firstNameText.setPromptText("Фамилия");
            firstNameText.setMinHeight(40);


            Label lastNameLab  = new Label("Имя");
            TextField lastNameText = new TextField();
            lastNameText.setPromptText("Имя");
            lastNameText.setMinHeight(40);

            Label patronymicNameLab  = new Label("Отчество");
            TextField patronymicNameText = new TextField();
            patronymicNameText.setPromptText("Отчество");
            patronymicNameText.setMinHeight(40);


            Label telephoneLab  = new Label("Телефон");
            telephoneLab.setPadding (new Insets(15, 0, 0, 0));
            TextField telephoneText = new TextField();
            telephoneText.setPromptText("Телефон");
            telephoneText.setMinHeight(40);

            Label birthDayLab  = new Label("Дата рождения");
            birthDayLab.setPadding (new Insets (15, 0,0,0));
            TextField birthDayText = new TextField();
            birthDayText.setPromptText("Дата рождения");
            birthDayText.setMinHeight(40);

            Label infoLab  = new Label("Дополнительаня информация по клиенту");
            infoLab.setPadding (new Insets (15, 0,0,0));
            TextArea infoText = new TextArea();
            infoText.setMaxHeight(300);

            HBox hBoxEmail = new HBox ();
            hBoxEmail.setSpacing (10);
            hBoxEmail.setAlignment (Pos.BOTTOM_LEFT);
            ImageView logoEmail = null;
            try {
                logoEmail = new ImageView (new Image(new File("img/email/emailLogo.png").toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            }
            logoEmail.setFitHeight(35);
            logoEmail.setFitWidth(35);
            Label emailLab  = new Label("E-mail");
            hBoxEmail.getChildren().addAll(logoEmail, emailLab);
            hBoxEmail.setPadding (new Insets (15, 0,0,0));
            TextField emailText = new TextField();
            emailText.setMinHeight(40);


            HBox hBoxTelegram = new HBox ();
            hBoxTelegram.setSpacing (10);
            hBoxTelegram.setAlignment (Pos.BOTTOM_LEFT);
            Label labelTelegram = new Label("Telegram");
            hBoxTelegram.setPadding (new Insets (15, 0,15,0));
            ImageView logoTelegram = null;
            try {
                logoTelegram = new ImageView (new Image (new File ("img/telegram/lelLogo.png").toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            }
            logoTelegram.setFitHeight(35);
            logoTelegram.setFitWidth(35);

            buttonNewReg = new Button("Регистрация");
            buttonNewReg.setManaged(false);
            buttonNewReg.setVisible(false);
            buttonNewReg.setMinWidth(170);

            buttonDeleteReg = new Button("Отмена регистрации");
            buttonDeleteReg.setManaged(false);
            buttonDeleteReg.setVisible(false);
            buttonDeleteReg.setMinWidth(170);

            buttonStageCod = new Button("Код регистрации");
            buttonStageCod.setManaged(false);
            buttonStageCod.setVisible(false);
            buttonStageCod.setMinWidth(170);


            hBoxTelegram.setVisible(false);
            hBoxTelegram.setManaged(false);
            hBoxTelegram.getChildren ().addAll (logoTelegram, labelTelegram, buttonNewReg, buttonDeleteReg, buttonStageCod);

            if (client.isTelegramReg()){
                if (client.getCodRegTelegram() != null){
                    buttonStageCod.setManaged(true);
                    buttonStageCod.setVisible(true);
                    buttonDeleteReg.setManaged(true);
                    buttonDeleteReg.setVisible(true);
                } else {
                    buttonDeleteReg.setManaged(true);
                    buttonDeleteReg.setVisible(true);
                }
            } else {
                buttonNewReg.setManaged(true);
                buttonNewReg.setVisible(true);
            }

            buttonNewReg.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle (ActionEvent event) {

                    if (firstNameText.getText().length() > 2 && lastNameText.getText().length() > 2){

                        if (!save) {
                            save = true;
                            client.setLastName(new TextFormat().upperCaseOneChar(firstNameText.getText()));
                            client.setFirstName(new TextFormat().upperCaseOneChar(lastNameText.getText()));
                            client.setPatronymicName(new TextFormat().upperCaseOneChar(patronymicNameText.getText()));
                            client.setEmail(emailText.getText());
                            client.setDateBirth(birthDayText.getText());
                            client.setTelephone(telephoneText.getText());
                            client.setInfoClient(infoText.getText());
                            ClientDataBase.addNewClientDateBase(client);
                        }
                        initNewCodReg();
                        buttonStageCod.setManaged(true);
                        buttonStageCod.setVisible(true);
                        buttonDeleteReg.setManaged(true);
                        buttonDeleteReg.setVisible(true);
                        buttonNewReg.setManaged(false);
                        buttonNewReg.setVisible(false);
                    } else {
                        firstNameText.setStyle ("-fx-background-color: #FF6347");
                        lastNameText.setStyle ("-fx-background-color: #FF6347");
                        new ErrorStage("Ошибка поле (Фамилия, Имя)");
                    }


                }
            });

            buttonDeleteReg.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle (ActionEvent event) {
                    BotTelegram.deleteClient(client.getId());
                    buttonStageCod.setManaged(false);
                    buttonStageCod.setVisible(false);
                    buttonDeleteReg.setManaged(false);
                    buttonDeleteReg.setVisible(false);
                    buttonNewReg.setManaged(true);
                    buttonNewReg.setVisible(true);
                }
            });

            buttonStageCod.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle (ActionEvent event) {
                    stageInfoReg();
                }
            });



            Button buttonSave = new Button("Сохранить");
            buttonSave.setMinSize(600, 40);
            buttonSave.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle (ActionEvent event) {

                    if (firstNameText.getText().length() > 2 && lastNameText.getText().length() > 2){

                            client.setLastName(new TextFormat().upperCaseOneChar(firstNameText.getText()));
                            client.setFirstName(new TextFormat().upperCaseOneChar(lastNameText.getText()));
                            client.setPatronymicName(new TextFormat().upperCaseOneChar(patronymicNameText.getText()));
                            client.setEmail(emailText.getText());
                            client.setDateBirth(birthDayText.getText());
                            client.setTelephone(telephoneText.getText());
                            client.setInfoClient(infoText.getText());
                        if (!save) {
                            save = true;
                            ClientDataBase.addNewClientDateBase(client);
                        } else {
                            ClientDataBase.editClientDateBase(client);
                        }
                        new InfoStage("Сохранение операции.");
                        clientStage.close();
                    } else {
                        firstNameText.setStyle ("-fx-background-color: #FF6347");
                        lastNameText.setStyle ("-fx-background-color: #FF6347");
                        new ErrorStage("Ошибка поле (Фамилия, Имя)");
                    }

                }
            });

//            Button buttonDelete = new Button("Удалить");
//            buttonDelete.setMinSize(600, 40);
//            buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle (ActionEvent event) {
//                    ClientDataBase.delClient(client);
//                    new WarningStage("Удаление клиента");
//                    clientStage.close();
//                }
//            });

            if (stringsNameClients.length > 0){
                String[] name = stringsNameClients[0].split(" ");
                for(int i = 0; i < name.length; i++) {
                    if (i == 0){
                        firstNameText.setText(new TextFormat().upperCaseOneChar(name[i]));
                    } else if (i == 1){
                        lastNameText.setText(new TextFormat().upperCaseOneChar(name[i]));
                    } else if (i == 2){
                        patronymicNameText.setText(new TextFormat().upperCaseOneChar(name[i]));
                    }
                }
            }

            vBox.getChildren().addAll(firstNameLab, firstNameText, lastNameLab, lastNameText, patronymicNameLab, patronymicNameText,
                                      telephoneLab, telephoneText, birthDayLab, birthDayText,  infoLab, infoText, hBoxEmail, emailText , hBoxTelegram,  buttonSave);


            vBoxMain.getChildren().add(vBox);

            Scene scene = new Scene (vBoxMain, 620, 840);
            clientStage.setScene (scene);
            clientStage.initModality (Modality.WINDOW_MODAL);
            clientStage.initOwner (mainStage);
            clientStage.initStyle (StageStyle.UTILITY);
            clientStage.show ();
        }


        private void initNewCodReg(){
            String cod = new RandomCod().get();
            client.setCodRegTelegram(cod);
            BotTelegram.addNewCodeRegClient(client.getId(), cod);
            client.getRegTelegramInfo();
            buttonStageCod.setManaged(true);
            buttonStageCod.setVisible(true);
            stageInfoReg();
        }

        private void stageInfoReg() {
            String infoCod = "Reg-" + client.getId() + "-" + client.getCodRegTelegram();

            Stage mainStage = (Stage) vBoxMain.getScene ().getWindow ();
            Stage stageInfoReg = new Stage();
            stageInfoReg.setTitle("Регистрация Telegram");
            VBox vBoxInfoReg= new VBox();
            vBoxInfoReg.setStyle ("-fx-background-color: #153f6d");
            vBoxInfoReg.setAlignment(Pos.CENTER);
            Scene scene = new Scene (vBoxInfoReg, 1250, 950);
            scene.getStylesheets().add(new CssUrl ().get ());

            VBox vBox = new VBox();
            vBox.setStyle ("-fx-background-color: #153f6d");
            vBox.setSpacing(5);
            vBox.setMaxSize(1240,920);
            vBox.setAlignment (Pos.CENTER);

            HBox hBoxCodReg = new HBox();
            hBoxCodReg.setAlignment (Pos.CENTER);
            hBoxCodReg.setSpacing(5);
            hBoxCodReg.setPadding(new Insets(30,0,30,0));

            Label labelCodReg = new Label("Код регистрации : " );
            labelCodReg.setStyle ("-fx-text-fill: #bb4d00;" +
                                  "-fx-font-size: 25pt;" );

            TextField textFieldCodReg = new TextField(infoCod);
            textFieldCodReg.setMinSize(400,70);
            textFieldCodReg.setMaxSize(400,70);
            textFieldCodReg.setEditable(false);
            textFieldCodReg.setStyle ("-fx-text-fill: #bb4d00;" +
                                      "-fx-background-color: #0c0042;"+
                                      "-fx-font-size: 25pt;"+
                                      "-fx-background-radius: 20;" +
                                      "-fx-opacity: 0.9;");
            textFieldCodReg.setAlignment(Pos.CENTER);
            textFieldCodReg.setFocusTraversable(false);

            hBoxCodReg.getChildren().addAll(labelCodReg, textFieldCodReg);

            HBox imgHBox = new HBox();

            try {
                imgHBox.setSpacing(15);
                ImageView imageView1 = new ImageView(new Image(new File("img/telegram/tel1.jpg").toURI().toURL().toString()));
                imageView1.setFitHeight(800);
                imageView1.setFitWidth(400);
                ImageView imageView2 = new ImageView(new Image(new File("img/telegram/tel2.jpg").toURI().toURL().toString()));
                imageView2.setFitHeight(800);
                imageView2.setFitWidth(400);
                ImageView imageView3 = new ImageView(new Image(new File("img/telegram/tel3.jpg").toURI().toURL().toString()));
                imageView3.setFitHeight(800);
                imageView3.setFitWidth(400);
                imgHBox.getChildren().addAll(imageView1, imageView2, imageView3);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            vBox.getChildren().addAll(hBoxCodReg, imgHBox);

            vBoxInfoReg.getChildren().add(vBox);

            stageInfoReg.setScene (scene);
            stageInfoReg.initModality (Modality.WINDOW_MODAL);
            stageInfoReg.initOwner (mainStage);
            stageInfoReg.initStyle (StageStyle.UTILITY);
            stageInfoReg.show ();
        }
    }


