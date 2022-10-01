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
        String newVertex= "";
        while(scanner.hasNextLine()){
            newVertex = scanner.next();
            if(!scanner.hasNextInt()){
                break;
            }
            vertexMap.put(newVertex.charAt(0), new Vertex(newVertex.charAt(0), scanner.nextInt()));
        }
        vertexList = new ArrayList<Vertex>(vertexMap.values());
		edgeList.add(new Edge(vertexMap.get(newVertex.charAt(0)), vertexMap.get(scanner.next().charAt(0))));
		while(scanner.hasNextLine()) {
			edgeList.add(new Edge(vertexMap.get(scanner.next().charAt(0)), vertexMap.get(scanner.next().charAt(0))));
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