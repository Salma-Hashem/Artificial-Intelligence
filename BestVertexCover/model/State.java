package BestVertexCover.model;
import java.util.*;

public class State{
    private List<Vertex> vertexList=new ArrayList<>();
    private int error;

    public List<Vertex> getVertexList(){
        return vertexList;
    }
    public void setVertexList(List<Vertex> vertexList){
        this.vertexList=vertexList;
    }
    public int getError(){
        return error;
    }
    public void setError(int error){
        this.error=error;
    }
}