package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.GanttBlock;
import model.Process;
import model.Result;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ResultController {


    @FXML private Label leftTitle;
    @FXML private Label rightTitle;
    @FXML private TableView<Process> leftTable;
    @FXML private TableView<Process> rightTable;
    @FXML private TableColumn<Process, String> l_id;
    @FXML private TableColumn<Process, Integer> l_at;
    @FXML private TableColumn<Process, Integer> l_bt;
    @FXML private TableColumn<Process, Integer> l_wt;
    @FXML private TableColumn<Process, Integer> l_tat;
    @FXML private TableColumn<Process, Integer> l_rt;
    @FXML private TableColumn<Process, String> r_id;
    @FXML private TableColumn<Process, Integer> r_at;
    @FXML private TableColumn<Process, Integer> r_bt;
    @FXML private TableColumn<Process, Integer> r_wt;
    @FXML private TableColumn<Process, Integer> r_tat;
    @FXML private TableColumn<Process, Integer> r_rt;
    @FXML private HBox leftGantt;
    @FXML private HBox rightGantt;
    @FXML private HBox leftTimeline;
    @FXML private HBox rightTimeline;
    @FXML private Label leftAvgWT, leftAvgTAT, leftAvgRT;
    @FXML private Label rightAvgWT, rightAvgTAT, rightAvgRT;
    @FXML private Label leftConclusion;
    @FXML private Label rightConclusion;



    public void setData(Result left, Result right) {

        leftTitle.setText(left.algorithmName);
        rightTitle.setText(right.algorithmName);

        fillTable(leftTable, left.processes, l_id, l_at, l_bt, l_wt, l_tat, l_rt);
        fillTable(rightTable, right.processes, r_id, r_at, r_bt, r_wt, r_tat, r_rt);

        drawGantt(leftGantt, leftTimeline, left.ganttBlocks);
        drawGantt(rightGantt, rightTimeline, right.ganttBlocks);

        leftAvgWT.setText("Avg WT: " + left.avgWaitingTime);
        leftAvgTAT.setText("Avg TAT: " + left.avgTurnaroundTime);
        leftAvgRT.setText("Avg RT: " + left.avgResponseTime);

        rightAvgWT.setText("Avg WT: " + right.avgWaitingTime);
        rightAvgTAT.setText("Avg TAT: " + right.avgTurnaroundTime);
        rightAvgRT.setText("Avg RT: " + right.avgResponseTime);

        generateConclusions(left, right);
        leftConclusion.setText(left.conclusion);
        rightConclusion.setText(right.conclusion);
    }



    private void fillTable(TableView<Process> table,
                           List<Process> processes,
                           TableColumn<Process, String> id,
                           TableColumn<Process, Integer> at,
                           TableColumn<Process, Integer> bt,
                           TableColumn<Process, Integer> wt,
                           TableColumn<Process, Integer> tat,
                           TableColumn<Process, Integer> rt) {

        id.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().id));
        at.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().arrivalTime));
        bt.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().burstTime));
        wt.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().waitingTime));
        tat.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().turnAroundTime));
        rt.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().responseTime));

        table.getItems().setAll(processes);
    }



    private void drawGantt(HBox ganttBox, HBox timelineBox, List<GanttBlock> blocks) {

        ganttBox.getChildren().clear();
        timelineBox.getChildren().clear();

        if (blocks == null || blocks.isEmpty()) return;

        int totalTime = blocks.get(blocks.size() - 1).end;

        for (GanttBlock b : blocks) {

            int duration = b.end - b.start;
            double width = (duration * 500.0) / totalTime;


            StackPane block = new StackPane();
            block.setPrefWidth(width);
            block.setPrefHeight(60);
            block.setStyle("-fx-border-color: black;");
            Label name;
            name = new Label(b.myProcess.id);
            block.getChildren().add(name);
            StackPane.setAlignment(name, Pos.CENTER);
            ganttBox.getChildren().add(block);
            StackPane tick = new StackPane();
            tick.setPrefWidth(width);
            tick.setPrefHeight(20);

            Label time = new Label(String.valueOf(b.start));
            tick.getChildren().add(time);
            StackPane.setAlignment(time, Pos.CENTER_LEFT);

            timelineBox.getChildren().add(tick);
        }


        GanttBlock last = blocks.get(blocks.size() - 1);
        StackPane lastTick = new StackPane();
        lastTick.setPrefWidth(30);
        lastTick.setPrefHeight(20);

        Label end = new Label(String.valueOf(last.end));
        lastTick.getChildren().add(end);
        StackPane.setAlignment(end, Pos.CENTER_LEFT);

        timelineBox.getChildren().add(lastTick);
    }
    public static void showResults(Result r1, Result r2) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    ResultController.class.getResource("/view/result.fxml"));

            Parent root = loader.load();

            ResultController controller = loader.getController();
            controller.setData(r1, r2);

            Stage stage = new Stage();
            stage.setTitle("Results Comparison");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generateConclusions(Result left, Result right) {
        StringBuilder leftScore = new StringBuilder();
        StringBuilder rightScore = new StringBuilder();


        if (left.avgWaitingTime < right.avgWaitingTime) {
            leftScore.append("• Better waiting time (saves " + String.format("%.2f", right.avgWaitingTime - left.avgWaitingTime) + " units).\n");
        } else if (right.avgWaitingTime < left.avgWaitingTime) {
            rightScore.append("• Better waiting time (saves " + String.format("%.2f", left.avgWaitingTime - right.avgWaitingTime) + " units).\n");
        } else {
            leftScore.append("• Equal waiting time efficiency.\n");
            rightScore.append("• Equal waiting time efficiency.\n");
        }


        if (left.avgTurnaroundTime < right.avgTurnaroundTime) {
            leftScore.append("• Faster turnaround time (saves " + String.format("%.2f", right.avgTurnaroundTime - left.avgTurnaroundTime) + " units).\n");
        } else if (right.avgTurnaroundTime < left.avgTurnaroundTime) {
            rightScore.append("• Faster turnaround time (saves " + String.format("%.2f", left.avgTurnaroundTime - right.avgTurnaroundTime) + " units).\n");
        } else {
            leftScore.append("• Equal turnaround time efficiency.\n");
            rightScore.append("• Equal turnaround time efficiency.\n");
        }


        if (left.avgResponseTime < right.avgResponseTime) {
            leftScore.append("• Quickest response time (faster by " + String.format("%.2f", right.avgResponseTime - left.avgResponseTime) + " units).\n");
        } else if (right.avgResponseTime < left.avgResponseTime) {
            rightScore.append("• Quickest response time (faster by " + String.format("%.2f", left.avgResponseTime - right.avgResponseTime) + " units).\n");
        } else {
            leftScore.append("• Equal response time efficiency.\n");
            rightScore.append("• Equal response time efficiency.\n");
        }


        int leftPoints = (left.avgWaitingTime <= right.avgWaitingTime ? 1 : 0) + (left.avgTurnaroundTime <= right.avgTurnaroundTime ? 1 : 0);
        int rightPoints = (right.avgWaitingTime <= left.avgWaitingTime ? 1 : 0) + (right.avgTurnaroundTime <= left.avgTurnaroundTime ? 1 : 0);

        if (leftPoints > rightPoints) {
            leftScore.append("\n Overall: Most optimal choice for this workload.");
            rightScore.append("\n Less optimal compared to " + left.algorithmName);
        } else if (rightPoints > leftPoints) {
            rightScore.append("\n Overall: Most optimal choice for this workload.");
            leftScore.append("\n Less optimal compared to " + right.algorithmName);
        } else {
            leftScore.append("\n Overall: Both perform equally under these metrics.");
            rightScore.append("\n Overall: Both perform equally under these metrics.");
        }

        left.conclusion = leftScore.toString();
        right.conclusion = rightScore.toString();
    }
}
