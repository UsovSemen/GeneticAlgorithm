package controller;

import entity.Chromosome;
import entity.Details;
import entity.Operations;
import entity.Wrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import view.Procecc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntryCtrl {

    @FXML
    Button Start;

    @FXML
    TextField Power;
    @FXML
    TextField Steps;
    @FXML
    TextField PC;
    @FXML
    TextField PM;


    @FXML
    TableView<Chromosome> ishTable;
    @FXML
    TableColumn ishPos;
    @FXML
    TableColumn ishBin;
    @FXML
    TableColumn ishVal;
    @FXML
    TableColumn ishFunc;
    @FXML
    TableColumn ishProb;

    @FXML
    TableView<Chromosome> repTable;
    @FXML
    TableColumn repBin;
    @FXML
    TableColumn repVal;
    @FXML
    TableColumn repFunc;


    @FXML
    TableView<Wrapper> operationTable;
    @FXML
    TableColumn befCross;
    @FXML
    TableColumn afterCross;
    @FXML
    TableColumn afterVal;
    @FXML
    TableColumn mut;
    @FXML
    TableColumn mutVal;


    @FXML
    TableView<Chromosome> allTable;
    @FXML
    TableColumn allPos;
    @FXML
    TableColumn allBin;
    @FXML
    TableColumn allVal;
    @FXML
    TableColumn allFunc;

    @FXML
    Label maxVal;
    @FXML
    ComboBox<Integer> comboBox;

    private Integer power;
    private Integer steps;
    private Double pc;
    private Double pm;

    private ObservableList<Chromosome> ishData;
    private ObservableList<Chromosome> repData;
    private ObservableList<Wrapper> opData;
    private ObservableList<Chromosome> allData;
    private ObservableList<Integer> comboFill;

    private Details details;
    private String MAX_VALUE = "Максимальное значение - ";

    public static String text = "";

    public EntryCtrl() {
    }



    @FXML
    public void initialize() {

        ishPos.setMinWidth(100);
        ishPos.setCellValueFactory(new PropertyValueFactory<Chromosome, Integer>("position"));
        ishBin.setMinWidth(100);
        ishBin.setCellValueFactory(new PropertyValueFactory<Chromosome, String>("binary"));
        ishVal.setMinWidth(100);
        ishVal.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("value"));
        ishFunc.setMinWidth(100);
        ishFunc.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("funcValue"));
        ishProb.setMinWidth(100);
        ishProb.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("probability"));

        repBin.setMinWidth(100);
        repBin.setCellValueFactory(new PropertyValueFactory<Chromosome, String>("binary"));
        repVal.setMinWidth(100);
        repVal.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("value"));
        repFunc.setMinWidth(100);
        repFunc.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("funcValue"));


        befCross.setMinWidth(100);
        befCross.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("befCross"));
        afterCross.setMinWidth(100);
        afterCross.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("afterCross"));
        afterVal.setMinWidth(100);
        afterVal.setCellValueFactory(new PropertyValueFactory<Wrapper, Double>("afterVal"));
        mut.setMinWidth(100);
        mut.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("mut"));
        mutVal.setMinWidth(100);
        mutVal.setCellValueFactory(new PropertyValueFactory<Wrapper, Double>("mutVal"));


        allPos.setMinWidth(100);
        allPos.setCellValueFactory(new PropertyValueFactory<Chromosome, Integer>("position"));
        allBin.setMinWidth(100);
        allBin.setCellValueFactory(new PropertyValueFactory<Chromosome, String>("binary"));
        allVal.setMinWidth(100);
        allVal.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("value"));
        allFunc.setMinWidth(100);
        allFunc.setCellValueFactory(new PropertyValueFactory<Chromosome, Double>("funcValue"));


        comboBox.setOnAction(event -> {
            int n = comboBox.getSelectionModel().getSelectedItem() - 1;
            try {
                updateData(details, n);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Start.setOnAction(event -> {
           // EntryCtrl.text = "Шаг: 11";
            power = Integer.parseInt(Power.getText());
            steps = Integer.parseInt(Steps.getText());
            pc = Double.parseDouble(PC.getText());
            pm = Double.parseDouble(PM.getText());

            details = Procecc.proc(power, steps, pc, pm);
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 1; i <= steps; i++) {
                list.add(i);
            }
            // comboBox.getItems().clear();
            comboFill = FXCollections.observableArrayList(list);
            comboBox.setItems(comboFill);
            maxVal.setText(MAX_VALUE + Operations.sortList(Procecc.clone(details.afterAll.get(steps - 1))).get(0).getFuncValue() + "  " + text);

            try {
                updateData(details, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }


    private void updateData(Details details, int n) throws SQLException {

        ishTable.getItems().clear();
        repTable.getItems().clear();
        operationTable.getItems().clear();
        allTable.getItems().clear();


        ishData = FXCollections.observableArrayList(details.ishList.get(n));
        repData = FXCollections.observableArrayList(details.afterRep.get(n));
        allData = FXCollections.observableArrayList(details.afterAll.get(n));

        opData = FXCollections.observableArrayList(
                getWrapper(
                        details.afterRep.get(n),
                        details.afterCros.get(n),
                        details.afterMut.get(n),
                        details.indexMut.get(n)
                )
        );

        ishTable.setItems(ishData);
        repTable.setItems(repData);
        operationTable.setItems(opData);
        allTable.setItems(allData);
    }

    private List<Wrapper> getWrapper(List<Chromosome> rep, List<Chromosome> cross, List<Chromosome> mut, List<Integer> integers) {
        List<Wrapper> list = new ArrayList<>();

        for (int i = 0; i < cross.size(); i++) {
            Wrapper wrapper = new Wrapper();
            wrapper.setBefCross(rep.get(i).getBinary());
            wrapper.setAfterCross(cross.get(i).getBinary());
            wrapper.setAfterVal(cross.get(i).getFuncValue());

            for (int j = 0; j < integers.size(); j++) {
                if (integers.get(j) == i) {
                    wrapper.setMut(mut.get(j).getBinary());
                    wrapper.setMutVal(mut.get(j).getFuncValue());
                }
            }

            list.add(wrapper);
        }

        return list;
    }

}
