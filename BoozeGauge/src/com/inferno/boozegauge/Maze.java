package com.inferno.boozegauge;

public class Maze {
	private boolean[][] vLines;
	private boolean[][] hLines;
	
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
