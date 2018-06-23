import com.sun.xml.internal.bind.v2.runtime.Coordinator;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends ArrayList{
    protected Integer number;
    protected Integer Team;
    protected Coodinates coordinates;
    protected Integer DistanceToBall;

    public Player() {
    }

    public Player(Integer number, Integer team) {
        this.number = number;
        Team = team;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTeam() {
        return Team;
    }

    public void setTeam(Integer team) {
        Team = team;
    }

    public Integer getDistanceToBall() {
        return DistanceToBall;
    }

    public void setDistanceToBall(Integer distanceToBall) {
        DistanceToBall = distanceToBall;
    }

    public void calculateDistanceToBall(Integer playerNumber, Integer[] coordinates){


    }

}
