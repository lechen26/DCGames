package server;

import java.awt.Point;
import java.util.ArrayList;

import common.Constants;

import maze.Maze;
import maze.MazeDomain;
import maze.MazeHeuristicDistance;
import maze.MazeStandardDistance;
import model.algorithms.Action;
import model.algorithms.Searcher;
import model.algorithms.a_star.Astar;

public class aStar {

	public ArrayList<Action>  runAstar(int[][] boardForMaze,Point start, Point exit) {			
		//Change Cheeze value to be as implementd on Astar			
		for (int i=0;i<boardForMaze.length;++i){
			for(int j=0;j<boardForMaze[0].length;++j) {
				if ( boardForMaze[i][j] == Constants.mini)
					boardForMaze[i][j]=2;
			}
		}		
		Maze maze = new Maze(boardForMaze,start,exit);
		Searcher as = new Astar(new MazeDomain(maze),new MazeHeuristicDistance(), new MazeStandardDistance());		
		ArrayList<Action> actions  = as.search(maze.getStart(),maze.getGoal());		
		return actions;
	}

}
