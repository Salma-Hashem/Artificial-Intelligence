package BestVertexCover;
import BestVertexCover.model.State;
import BestVertexCover.model.Vertex;
import java.util.*;
import java.io.*;

public class HillClimbing extends Search{
    
    //Goal: find a set with smallest error because if a set has an error of 0 then all edges are covered in graph. 
    public State hillClimbing(State startState){

        while(true){
            //find error of current start state 
            State localOptimum=startState;
            //initialize localoptimum error with current starting states error 
            int localOptimumError= getError(startState);

            if(getVerboseOrCompactOutput()=='V'){
                System.out.println("Neighbors");
            }

            //intialize the list of nextStates or neighbors and for each one check if its error is less than the local optimum. if its less then set local optimum to nextState
            List<State> nextStateList= traverseNext(startState);
            for(State nextState : nextStateList){
                if(getVerboseOrCompactOutput()=='V'){
                    print(nextState);
                }
                if(getError(nextState)< localOptimumError){
                    localOptimum=nextState;
                    localOptimumError=getError(nextState);
                }
            }

            if(getError(localOptimum)==0){
                return localOptimum;
            }
            if(localOptimum==startState){
                return startState;
            }
            //if we find a neighbor state that is less than current start state, set startstate to that localoptimum and then loop again to find neighbors of new start state (previously, the next state)
            startState=localOptimum;
        
            if(getVerboseOrCompactOutput()=='V'){
                System.out.print("\nMove to ");
                print(startState);
            }
            }
    }

    public void randomRestart(int nrr, Character output){
        State resultstate=null;
        while(nrr>0){

            State currentState = randomRestartState(new State());
            if(output =='V') {
                System.out.print("\nRandomly chosen starting state ");
                print(currentState);
            }
            State result= hillClimbing(currentState);
            if(getError(result)==0){
                System.out.print("\nFound Solution ");
                resultstate=result;
                print(resultstate);
                break;
            }
            else{
                // the neighbor state had lower error than current state 
               if(resultstate == null|| getError(result)<getError(resultstate)){
                    resultstate = result;
               }
               if(getVerboseOrCompactOutput()=='V'){
                System.out.println("\nSearch Failed\n");
               }
            }
            nrr--;
        }
        if(resultstate!=null && getError(resultstate)!=0){
            System.out.println("\nNo solution found.");
        }
    }
    
    
    
    public State randomRestartState(State startState){
        //randomly choose vertices from vertexlist initliazed from input file in Search.java
        Random random = new Random();
        startState = new State();
        List<Vertex> startList = new ArrayList<>();
        for(Vertex v : vertexList){
            if(random.nextBoolean()){
                startList.add(v);
            }
        }
        startState.setVertexList(startList);
        startState.setError(getError(startState));
        return startState;
    }

    // initial error of random restart set 
    public int getError(State startState){
        int error=0;
        int uncoveredEdgeError=0;
        for(int i=0; i<=startState.getVertexList().size()-1; i++){
            error+= startState.getVertexList().get(i).getCost();
        }
        for(int i=0; i<=edgeList.size()-1; i++){
            if( !startState.getVertexList().contains(edgeList.get(i).getVertex1()) 
                && !startState.getVertexList().contains(edgeList.get(i).getVertex2())){
                uncoveredEdgeError+= Math.min(edgeList.get(i).getVertex1().getCost(), edgeList.get(i).getVertex2().getCost());
            }
        }
        error = Math.max(0,(error)-getBudget()) + uncoveredEdgeError;
        return error;
    }

    //getting all the neighbors of current state, to find which to traverse next 
    //concept: add all vertices to list that are not in list and begin deleting vertices to create new neighboring sets until best vertex cover set is achieved
    public List<State> traverseNext(State currentState){ 
        //get the current State's vertex list and store it in a list to traverse
        List<Vertex> vList = currentState.getVertexList();
        //create a List to store next state we will traverse 
        List<State> nextStateList= new ArrayList<>();
        //find uncovered edges by checking if vertices are not in list 
        for(Vertex v: vertexList){
            //System.out.println("vertex v in addition"+v.getName());
            if(!vList.contains(v)){
                //System.out.println("vertex v in addition satisfies condiiton"+v.getName());

                List<Vertex> sList = new ArrayList<>(vList);
                //add v to the states vertex list in alphabetical order, find the location in array list to add V
                int i=0;
                for(i=0; i<vList.size();i++){
                    if(vList.get(i).getName() > v.getName()){
                        break;
                    }
                }
                sList.add(i,v);
                State nextState= new State();
                nextState.setVertexList(sList);
                nextStateList.add(nextState);
                if(getError(nextState)==0){
                    return nextStateList;
                }
            }
        }
        //deleting vertices in set to create new neighbors 
        // delete from end of vretexc list 
        for(int i=vList.size()-1; i>=0; i--){
            List<Vertex> sList =  new ArrayList<>(vList);
            sList.remove(i);
            State nextState= new State();
            nextState.setVertexList(sList);
            nextStateList.add(nextState);
            if(getError(nextState)==0){
                return nextStateList;
            }
        }
        return nextStateList;
    }


    public void print(State state){
        int cost =0;
        if(state.getVertexList().size()==0) System.out.println("{} ");
        for(Vertex v : state.getVertexList()){
            cost+= v.getCost();
            System.out.print(v.getName()+ " ");
        }
        System.out.println("Cost=" + cost+ " Error="+ getError(state));
    }

    public static void main(String[] args){
        File input= new File("BestVertexCover/input_files/HillClimbingInput.txt");
        HillClimbing hillclimb = new HillClimbing();

        try{
            Scanner iscanner = new Scanner(input);
            hillclimb.setBudget(iscanner.nextInt());
            hillclimb.setVerboseOrCompactOutput(iscanner.next().charAt(0));
            hillclimb.setNumRandomRestarts(iscanner.nextInt());
            hillclimb.createGraph(iscanner);
            iscanner.close();
        } catch( FileNotFoundException fnfe){
            System.out.println("Input file was not found, please check path.");
            System.exit(0);
        } catch (NoSuchElementException ne){
            System.out.println("File is missing some or all input information!");
            System.exit(0);
        }
        hillclimb.randomRestart(hillclimb.getNumRandomRestarts(), hillclimb.getVerboseOrCompactOutput());
    }
}
