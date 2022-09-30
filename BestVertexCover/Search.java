package BestVertexCover;
import java.util.*;

import BestVertexCover.model.*;

public class Search{

    private Character output;
    private int budget;
    private int numRandomRestarts;
    protected Map<Character, Vertex> vertexMap=new LinkedHashMap<>();
    protected List<Vertex> vertexList=new ArrayList<>();
    protected List<Edge> edgeList=new ArrayList<>();

    public void createGraph(Scanner scanner){
        String newVertex="";
        while(scanner.hasNextLine()){
            newVertex=scanner.next();
            if(!scanner.hasNextInt()){
                break;
            }
            vertexMap.put(newVertex.charAt(0), new Vertex(newVertex.charAt(0), scanner.nextInt()));
        }
    }
    public Character getVerboseOrCompactOutput(){
        return output;
    }
    public void setVerboseOrCompactOutput(char output){
        if(output=='V'){
            this.output='V';
        }
        else{
            this.output='C';
        }

    }
    public int getBudget(){
        return budget;
    }
    public void setBudget(int budget){
        this.budget=budget;
    }
    public int getNumRandomRestarts(){
        return numRandomRestarts;
    }
    public void setNumRandomRestarts(int numRandomRestarts){
        this.numRandomRestarts=numRandomRestarts;
    }
}