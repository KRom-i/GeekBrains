package sample;

import FilesMeneger.DirFiles;
import WorkFiles.FileKBKFormate;
import WorkFiles.ReadFileXML;
import WorkFiles.ReadFilesXLS;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

public class Controller {




    public void initialize (){

        homedir();
        startTableViewKBK();

    }




    @FXML
    private TableColumn<FileKBKFormate, Button> delKBK;
    @FXML
    private TableColumn<FileKBKFormate, Integer> numberCodeIncomeColumn;
    @FXML
    private TableColumn<FileKBKFormate, String> nameKBKColumn;
    @FXML
    private TableColumn<FileKBKFormate, String> numberKBKColumn;
    @FXML
    private TableColumn<FileKBKFormate, Integer> viewKBKColumn;
    @FXML
    private TableView<FileKBKFormate> tableViewKBK;

    private  void startTableViewKBK(){

        numberCodeIncomeColumn.setCellValueFactory(new PropertyValueFactory<FileKBKFormate, Integer>("numcodeKBK"));
        nameKBKColumn.setCellValueFactory(new PropertyValueFactory<FileKBKFormate, String>("name"));
        numberKBKColumn.setCellValueFactory(new PropertyValueFactory<FileKBKFormate, String>("codeKBK"));
        viewKBKColumn.setCellValueFactory(new PropertyValueFactory<FileKBKFormate, Integer>("incomeCode"));
        delKBK.setCellValueFactory(new PropertyValueFactory<FileKBKFormate, Button>("buttonKBK"));

        for(FileKBKFormate ff: ReadFilesXLS.readFileNumberIncome()
            ) {
            tableViewKBK.getItems().add(ff);
        }

    }

    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button button;
    @FXML
    private ListView listViewFilesAddUsers;
    private boolean startUserDirTab = false;

    @FXML
    private TreeView<File> treeViewFilesUsers;

    private Semaphore semaphore = new Semaphore(1);

    private void homedir(){
//
//        for(File f: File.listRoots()
//            ) {
//            TreeItem<File> item = new TreeItem<File>(f);
//            treeViewFilesUsers.setRoot(item);
//            new newDirFiles(f, item, semaphore);
//
//        }




    }

    public void goToDir (MouseEvent mouseEvent){

        if (mouseEvent.getClickCount() == 2) {


        }
    }

    public void actionDirFiles(ActionEvent actionEvent) {

        new Thread (()->{

            progressBar.setManaged (true);
            progressBar.setVisible (true);
            button.setManaged (false);
            button.setVisible (false);
            listView.setManaged (false);
            listView.setVisible (false);

            List<File> fileList = new ArrayList<> ();

            new DirFiles (new File ("C:\\OZAC"), fileList);

            for (int i = 0; i < fileList.size (); i++) {
                listView.getItems ().add (fileList.get (i).getAbsolutePath ());
            }

            progressBar.setManaged (false);
            progressBar.setVisible (false);
            button.setManaged (true);
            button.setVisible (true);
            listView.setManaged (true);
            listView.setVisible (true);


        }).start ();

    }


    public void clickedTableView (MouseEvent mouseEvent){
        nodeVisibleFalse(buttonAddNewKBK);
        nodeVisibleTrue(vBoxEditTable);

        if (mouseEvent.getClickCount() == 2){
            textFieldnumberCodeIncomeColumn.setText(String.valueOf(tableViewKBK.getSelectionModel().getSelectedItem().getNumcodeKBK()));
            textFieldnameKBKColumn.setText(tableViewKBK.getSelectionModel().getSelectedItem().getName());
            textFieldnumberKBKColumn.setText(tableViewKBK.getSelectionModel().getSelectedItem().getCodeKBK());
            textFieldviewKBKColumn.setText(String.valueOf(tableViewKBK.getSelectionModel().getSelectedItem().getIncomeCode()));

        }


    }


    @FXML
    private Button buttonAddNewKBK;
    @FXML
    private VBox vBoxEditTable;
    @FXML
    private TextField textFieldnumberCodeIncomeColumn;
    @FXML
    private TextField textFieldnameKBKColumn;
    @FXML
    private TextField textFieldnumberKBKColumn;
    @FXML
    private TextField textFieldviewKBKColumn;
    @FXML
    private Button buttonSaveKBK;
    @FXML
    private Button buttonSaveKBKClose;

    public void methodAddnewKBK (ActionEvent actionEvent){
        nodeVisibleFalse(buttonAddNewKBK);
        nodeVisibleTrue(vBoxEditTable);

    }


    private static void nodeVisibleFalse(Node node){
        node.setVisible(false);
        node.setManaged(false);

    }

    private static void nodeVisibleTrue(Node node){
        node.setVisible(true);
        node.setManaged(true);

    }

    public void vBoxEditTableClose (ActionEvent actionEvent){

        nodeVisibleFalse(vBoxEditTable);
        nodeVisibleTrue(buttonAddNewKBK);

    }

    public void vBoxEditTableSave (ActionEvent actionEvent){
        nodeVisibleTrue(vBoxEditTable);
        nodeVisibleFalse(buttonAddNewKBK);

        FileKBKFormate fileKBKFormate = new FileKBKFormate();
        fileKBKFormate.setNumcodeKBK(Integer.valueOf(textFieldnumberCodeIncomeColumn.getText()));
        fileKBKFormate.setName(textFieldnameKBKColumn.getText());
        fileKBKFormate.setCodeKBK(textFieldnumberKBKColumn.getText());
        fileKBKFormate.setIncomeCode(Integer.valueOf(textFieldviewKBKColumn.getText()));

        tableViewKBK.getItems().add(fileKBKFormate);
    }
}
