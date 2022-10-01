package BestVertexCover;

import java.io.*;
import java.util.*;

import BestVertexCover.model.State;
import BestVertexCover.model.Vertex;
import BestVertexCover.model.Edge;

public class IterativeDeepening extends Search{

    public void iterativeDeepeningSearch(int budget, Character output){
        
        // Maximal depth of Best Vertex Cover Problem is n-1 (n if there are cycles) & branching factor is n
        int depth = vertexMap.size();
        System.out.print("\nOutput");
        for(int limit=1; limit<=depth; limit++){
            // If no solution found within depth limit increment depth limit by 1
            System.out.println();
            if(output=='V') System.out.println("Depth=" + limit);
            State startSpace = new State();
            List<Vertex> vertexList = new ArrayList<Vertex>();
            startSpace.setVertexList(vertexList);

            // Depth Limited Search Expansion
            depthLimitedSearch(budget, 'V', 0, startSpace, new ArrayList<Edge>(), limit, 0);
        }
        System.out.println("\nNo Solution Found");

    }

    public State depthLimitedSearch(int budget, Character output, int cost, State stateSpace, List<Edge> edgeCoverage, int limit, int currentDepth){
        /* 
        Depth Limited Search: (References: Artificial Intelligence Textbook)
        @param: 
            budget: int Total Cost
            output: char V (Verbose) or C (Compact)
            stateSpace: current state Space intial empty
            edgeList: list of edges covered 
            limit: depth limit
            currentDepth: current depth of node
        output: Statespace with success, cutoff, or failure 
        */
        
        if(output=='V') printStateSpaceIDS(stateSpace.getVertexList());

        // Is Goal State? :all edges covered and cost<= budget
        if(edgeCoverage.size()==edgeList.size() && cost<=budget){
            System.out.print("\nFound solution ");
            printStateSpaceIDS(stateSpace.getVertexList());
            System.exit(0);
            //return stateSpace;
        }
        
        // Do not traverse node if depth limit reached, proceed to BFS expansion
        if(currentDepth<limit){

            boolean edgeCovered = false;

            for(Vertex v: vertexList){

                // Check Node cost, DNE in stateSpace, and alphabetically later than vertices in S to guarantee systematic search 
                if(!stateSpace.getVertexList().contains(v) && cost+ v.getCost()<=budget 
                    && (stateSpace.getVertexList().size()==0
                    || (stateSpace.getVertexList().size()!=0 && v.getName() > stateSpace.getVertexList().get(stateSpace.getVertexList().size()-1).getName()))) {
                    State vStateSpace= new State();
                    List<Vertex> newList= new ArrayList<Vertex>(stateSpace.getVertexList());
                    newList.add(v);
                    vStateSpace.setVertexList(newList);

                    // update edge Coverage add all edges where the node is a vertex of the edge
                    for (Edge edge : edgeList){
                        if(!edgeCoverage.contains(edge) && ((v.getName()== edge.getVertex1().getName() 
                            || v.getName()== edge.getVertex2().getName()))){

                            edgeCoverage.add(edge);
                            edgeCovered = true;
                        }
                    }
                    
                    State state =null;
                    // begin searching children nodes
                    if(edgeCovered && currentDepth< limit){
                         // Depth Limited Search Expansion
                        state= depthLimitedSearch(budget, 'V', cost+v.getCost(), vStateSpace, edgeCoverage, limit, currentDepth+1);
                    }

                    if(state!= null) return state;
                    // removse edges that most recently added vertex to statspace
                    // if edge has another end in the stateSpace do not delete
                    for(Edge edge: edgeList){
                        if((v.getName() == edge.getVertex1().getName()) && !stateSpace.getVertexList().contains(edge.getVertex2()) 
                            || (v.getName() == edge.getVertex2().getName() && !stateSpace.getVertexList().contains(edge.getVertex1()))) {
                                
                                edgeCoverage.remove(edge);
                        }
                    }
                }
            }
        }

        // stateSpace is empty if depth limit is hit
        // or if depth limit not reached, but the current node is the last child node for that sequence
        stateSpace=null;
        return stateSpace;
    }

    public void printStateSpaceIDS(List<Vertex> vertices) {
		int cost = 0;
		if(vertices.size() > 0) {
			for(Vertex v : vertices) {
				cost += v.getCost();
				System.out.print(v.getName()+" ");
			}
			System.out.printf("%10s","Cost="+ cost);
			System.out.println();
		}
	}

    public static void main(String[] args){
        File input = new File("BestVertexCover/input_files/IterativeDeepeningInput.txt");
		IterativeDeepening iterativeDeepening = new IterativeDeepening();
		try {
			Scanner iscanner = new Scanner(input);
			iterativeDeepening.setBudget(iscanner.nextInt());
			iterativeDeepening.setVerboseOrCompactOutput(iscanner.next().charAt(0));
			iterativeDeepening.createGraph(iscanner);
			iscanner.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("File was not found; Please check file path!");
            System.exit(0);
		} catch (NoSuchElementException ne){
            System.out.println("File is missing some or all input information!");
            System.exit(0);
        }
        
		iterativeDeepening.iterativeDeepeningSearch(iterativeDeepening.getBudget(), iterativeDeepening.getVerboseOrCompactOutput());
    }
}
