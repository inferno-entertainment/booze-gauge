package com.inferno.boozegauge;

//Representation of the maze used directly by the UI
public class Maze {
	//true means i line is present, false means no line
	private boolean[][] vLines;  //represents the vertical lines in the maze
	private boolean[][] hLines;  //represents the horizontal lines in the maze
	
	public Maze() {
		vLines = hLines = null;
	}
	
	public Maze(boolean[][] vL, boolean[][] hL) {
		vLines = vL;
		hLines = hL;
	}
	
	public boolean[][] getVLines() {
		return vLines;
	}
	
	public boolean[][] getHLines() {
		return hLines;
	}
}
