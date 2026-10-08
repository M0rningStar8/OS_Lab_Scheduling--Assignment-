public class GlobalTimer{

    int time;

    public GlobalTimer(int initialTime){
        this.time = initialTime;
    }

    public int getTime(){
        return time;
    }

    public void increaseTime(int amount){
        time += amount;
    }
}