public class Process{

    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int responseTime;

    boolean started;

    public Process(int id, int arrivalTime, int burstTime){

        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.responseTime = 0;
        this.started = false;
    }

    public int getId(){
        return id;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getBurstTime(){
        return burstTime;
    }

    public int getRemainingTime(){
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime){
        this.remainingTime = remainingTime;
    }

    public int getCompletionTime(){
        return completionTime;
    }

    public int getTurnaroundTime(){
        return turnaroundTime;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public int getResponseTime(){
        return responseTime;
    }

    public boolean isStarted(){
        return started;
    }
}