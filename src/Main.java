public class Main{

    public static void main(String[] args){

        Process[] processes ={

                new Process(1, 0, 7),
                new Process(2, 1, 4),
                new Process(3, 3, 6),
                new Process(4, 3, 2),
                new Process(5, 5, 3),
                new Process(6, 5, 8),
                new Process(7, 8, 5),
                new Process(8, 8, 1)
        };

        Scheduler scheduler = new Scheduler(processes);

        scheduler.runScheduler();

        scheduler.printGanttChart();

        System.out.println();
        printResults(processes);
    }

    public static void printResults(Process[] processes){

        System.out.println("PROCESS RESULTS");

        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s %-8s%n",
                "Process",
                "AT",
                "BT",
                "CT",
                "TAT",
                "WT",
                "RT"
        );

        int totalWaitingTime = 0;
        int totalResponseTime = 0;

        for (Process process : processes){

            System.out.printf("%-8s %-8d %-8d %-8d %-8d %-8d %-8d%n",
                    "P" + process.getId(),
                    process.getArrivalTime(),
                    process.getBurstTime(),
                    process.getCompletionTime(),
                    process.getTurnaroundTime(),
                    process.getWaitingTime(),
                    process.getResponseTime()
            );

            totalWaitingTime += process.getWaitingTime();
            totalResponseTime += process.getResponseTime();
        }

        double averageWaitingTime = (double) totalWaitingTime / processes.length;

        double averageResponseTime = (double) totalResponseTime / processes.length;

        System.out.printf("%nAverage Waiting Time : %.3f ms%n", averageWaitingTime);

        System.out.printf("Average Response Time: %.3f ms%n", averageResponseTime);

        int totalExecutionTime = 0;

        for (Process process : processes){
            totalExecutionTime += process.getBurstTime();
        }

        System.out.println("Total Execution Time : " + totalExecutionTime + " ms");
    }
}