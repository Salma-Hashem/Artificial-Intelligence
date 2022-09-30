package BestVertexCover.model;

public class Vertex{
    private char name;
    private int cost;

    public char getName(){
        return name;
    }
    public void setName(char name){
        this.name=name;
    }
    public int getCost(){
        return cost;
    }
    public void setCost(int cost){
        this.cost=cost;
    }
    public Vertex(char name, int cost){
        this.name=name;
        this.cost=cost;
    }
}
