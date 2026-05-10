package util;

import model.Process;
import java.util.List;

public class Validator {

    public static String validateProcess(Process newProcess, List<Process> existingProcesses) {

        if (newProcess.id == null || newProcess.id.isEmpty()) {
            return "Process ID cannot be empty";
        }

        for (Process p : existingProcesses) {
            if (p.id.equalsIgnoreCase(newProcess.id)) {
                return "Process ID already exists: " + newProcess.id;
            }
        }

        if (newProcess.arrivalTime < 0) {
            return "Arrival time cannot be negative";
        }

        if (newProcess.burstTime <= 0) {
            return "Burst time must be > 0";
        }

        return null;
    }

    public static String validateRun(List<Process> processes, int quantum) {

        if (processes == null || processes.isEmpty()) {
            return "No processes added";
        }

        if (quantum <= 0) {
            return "Quantum must be greater than 0";
        }

        return null;
    }
}