package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xml.internal.utils.StopParseException;

import model.Model2048;

public class alphaBeta {
	
	public enum Player {       
        COMPUTER, 
        USER
    }
    
	public static Map<String, Object> alphabeta(Model2048 model, int depth, int alpha, int beta, Player player) throws CloneNotSupportedException {        
		Map<String, Object> result = new HashMap<String, Object>();                
        String bestDirection = null;
        String[] directions = {"Up","Down","Right","Left"};
        int bestScore = 0;        
        if (model.isGameOver()) {
        	System.out.println("Finito");        	
        	if(model.isGameWon())    {
        		System.out.println("win");
        		bestScore=Integer.MAX_VALUE; //highest possible score
        		System.exit(0);
        	}
        	else 
        		bestScore=Math.min(model.getScore(), 1); //lowest possible score
        }else if(depth==0) 
            bestScore=heuristicScore(model.getScore(),model.getFreeStates().size(),calculateClusteringScore(model.getBoard()));
        else {
            if(player == Player.USER) {
                for(String direction : directions) {
                	if (!model.move(direction, true))
                		continue;
                    Model2048 newModel = (Model2048) model.clone();
                    if (newModel.move(direction,false) ) {
                    	//int points=newModel.getScore();                    
                    	Map<String, Object> currentResult = alphabeta(newModel, depth-1, alpha, beta, Player.COMPUTER);
                    	int currentScore=((Number)currentResult.get("Score")).intValue();                                        
                    	if(currentScore>alpha) { //maximize score
                    		alpha=currentScore;
                    		bestDirection=direction;
                    	}                    
                    	if((beta<=alpha) || model.isGameOver() || model.isGameWon()) {
                    		break; //beta cutoff
                    	}
                    }                
                    bestScore = alpha;
                }           
            }else {
                List<Integer> moves = model.getEmptyCellIds();
                int[] possibleValues = {2, 4};

                int i,j;
                abloop: for(Integer cellId : moves) {
                    i = cellId/model.getBoard().length;
                    j = cellId%model.getBoard().length;;
                    for(int value : possibleValues) {
                        Model2048 newModel = (Model2048) model.clone();
                        newModel.setEmptyCell(i, j, value);

                        Map<String, Object> currentResult = alphabeta(newModel, depth-1, alpha, beta, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<beta) { //minimize best score
                            beta=currentScore;
                        }
                        
                        if((beta<=alpha) || model.isGameOver() || model.isGameWon()) {
                            break abloop; //alpha cutoff
                        }
                    }
                }
                
                bestScore = beta;             
                if(moves.isEmpty()) {
                    bestScore=0;
                }
            }        
        }
        
        result.put("Score", bestScore);
        result.put("Direction", bestDirection);        
        return result;
    }
        
    
	
    
    /**
     * Estimates a heuristic score by taking into account the real score, the
     * number of empty cells and the clustering score of the board.
     * 
     * @param actualScore
     * @param numberOfEmptyCells
     * @param clusteringScore
     * @return 
     */
    private static int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }
    
    /**
     * Calculates a heuristic variance-like score that measures how clustered the
     * board is.
     * 
     * @param boardArray
     * @return 
     */
    private static int calculateClusteringScore(int[][] boardArray) {
        int clusteringScore=0;
        
        int[] neighbors = {-1,0,1};
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray.length;++j) {
                if(boardArray[i][j]==0) {
                    continue; //ignore empty cells
                }
                
                //clusteringScore-=boardArray[i][j];
                
                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=boardArray.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=boardArray.length) {
                            continue;
                        }
                        
                        if(boardArray[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(boardArray[i][j]-boardArray[x][y]);
                        }
                        
                    }
                }
                
                clusteringScore+=sum/numOfNeighbors;
            }
        }
        
        return clusteringScore;
    }


}
