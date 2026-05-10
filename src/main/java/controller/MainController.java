package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Process;
import model.Result;
import util.Validator;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private TextField idField;
    @FXML private TextField arrivalField;
    @FXML private TextField burstField;
    @FXML private TextField quantumField;
    @FXML private TextArea outputArea;

    private List<Process> processes = new ArrayList<>();

    @FXML
    public void addProcess() {
        try {
            String id = idField.getText().trim().toUpperCase().replaceAll("\\s+", "");
            int arrival = Integer.parseInt(arrivalField.getText());
            int burst = Integer.parseInt(burstField.getText());

            Process newProcess = new Process(id, arrival, burst);

            String validationMsg = Validator.validateProcess(newProcess, processes);

            if (validationMsg != null) {
                outputArea.appendText(validationMsg + "\n");
                return;
            }

            processes.add(newProcess);
            outputArea.appendText(id + " added\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Arrival/Burst must be numbers\n");
        } catch (Exception e) {
            outputArea.appendText("Invalid input\n");
        }
    }

    @FXML
    public void runSimulation() {
        try {
            int quantum = Integer.parseInt(quantumField.getText());

            String validationMsg = Validator.validateRun(processes, quantum);

            if (validationMsg != null) {
                outputArea.appendText(validationMsg + "\n");
                return;
            }

            List<Process> rrProcesses = new ArrayList<>();
            List<Process> srtfProcesses = new ArrayList<>();

            for (Process p : processes) {
                rrProcesses.add(new Process(p.id, p.arrivalTime, p.burstTime));
                srtfProcesses.add(new Process(p.id, p.arrivalTime, p.burstTime));
            }

            RRController rr = new RRController(rrProcesses, quantum);
            SRTFController srtf = new SRTFController(srtfProcesses);

            Result rrResult = rr.buildResult();
            Result srtfResult = srtf.buildResult();

            ResultController.showResults(rrResult, srtfResult);

        }catch (NumberFormatException e) {
            outputArea.appendText("quantum must be numbers\n");
        }
        catch (Exception e) {
            outputArea.appendText("Error running simulation\n");
        }
    }
}