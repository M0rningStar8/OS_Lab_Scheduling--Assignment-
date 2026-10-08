import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Scheduler{
    static final int TIME_QUANTUM = 3;

    PriorityQueue<Process> processQueue;
    PriorityQueue<Process> readyQueue;

    GlobalTimer globalTimer;

    List<GanttEntry> ganttChart;

    public Scheduler(Process[] processes){
        globalTimer = new GlobalTimer(0);
        ganttChart = new ArrayList<>();

        //waiting to arrive.
        processQueue = new PriorityQueue<>(
                new Comparator<Process>(){
                    @Override
                    public int compare(
                            Process process1,
                            Process process2){
                        if (process1.getArrivalTime() != process2.getArrivalTime()){
                            return process1.getArrivalTime() - process2.getArrivalTime();
                        }

                        return process1.getId() - process2.getId();
                    }
                }
        );

        //Lowest burst time priority.
        readyQueue = new PriorityQueue<>(
                new Comparator<Process>(){
                    @Override
                    public int compare(
                            Process process1,
                            Process process2){
                        if (process1.getRemainingTime() != process2.getRemainingTime()){

                            return process1.getRemainingTime() - process2.getRemainingTime();
                        }

                        //If remaining burst time is equal, the earlier arrival time has priority.
                        if (process1.getArrivalTime() != process2.getArrivalTime()){

                            return process1.getArrivalTime() - process2.getArrivalTime();
                        }

                        //If arrival time also equal, lower process number priority.
                        return process1.getId() - process2.getId();
                    }
                }
        );

        // Add to the process queue.
        for (Process process : processes){
            processQueue.add(process);
        }
    }

    public void runScheduler(){

        while (!processQueue.isEmpty() || !readyQueue.isEmpty()){

            addNewProcesses();

            if (!readyQueue.isEmpty()){

                runProcessInCPU();

            } else {
                globalTimer.increaseTime(1);
            }
        }
    }

    public void addNewProcesses(){

        while (!processQueue.isEmpty() && processQueue.peek().getArrivalTime() <= globalTimer.getTime()){
            readyQueue.add(processQueue.poll());
        }
    }

    public void runProcessInCPU(){
        Process process = readyQueue.poll();

        int startTime = globalTimer.getTime();

        System.out.println("Process P" + process.getId() + " started at time " + startTime);

        int executionTime = Math.min(
                TIME_QUANTUM,
                process.getRemainingTime()
        );

        //Record response time
        if (!process.isStarted()){

            process.started = true;

            process.responseTime = globalTimer.getTime() - process.getArrivalTime();
        }

        process.setRemainingTime(process.getRemainingTime() - executionTime);

        globalTimer.increaseTime(executionTime);

        int endTime = globalTimer.getTime();

        //Add execution interval
        ganttChart.add(
                new GanttEntry(process.getId(),
                        startTime,
                        endTime
                )
        );

        System.out.println("Process P" + process.getId() + " executed until time " + endTime);

        //New processes are at the next scheduling decision.
        addNewProcesses();

        if (process.getRemainingTime() == 0){

            process.completionTime = globalTimer.getTime();

            process.turnaroundTime = process.getCompletionTime() - process.getArrivalTime();

            process.waitingTime = process.getTurnaroundTime() - process.getBurstTime();

            System.out.println("Process P" + process.getId() + " completed ");

        } else {

            //Time quantum expired.
            //Put process back into the ready queue.
            readyQueue.add(process);

            System.out.println("Process P" + process.getId() + " returned to Ready Queue " + "with remaining burst time" + process.getRemainingTime());
        }

        System.out.println();
    }

    public void printGanttChart(){

        System.out.println();
        System.out.println("Gantt Chart");

        for (GanttEntry entry : ganttChart){

            System.out.printf("|  P%-2d ", entry.processId);
        }

        System.out.println("|");

        System.out.printf("%-6d", ganttChart.get(0).startTime);

        for (GanttEntry entry : ganttChart){

            System.out.printf("%-7d", entry.endTime);
        }

        System.out.println();
    }

    static class GanttEntry {

        int processId;
        int startTime;
        int endTime;

        GanttEntry(
                int processId,
                int startTime,
                int endTime) {

            this.processId = processId;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}