//http://rosettacode.org/wiki/Maze#Java
package com.inferno.boozegauge;

import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
 
/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator {
	private final int x;
	private final int y;
	private final int[][] maze;
	private static final Random rand = new Random(System.currentTimeMillis());
 
	public MazeGenerator(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		generateMaze(0, 0);
	}
 
	/*
	 * converts the bit map type maze into a pair of 2 dimensional boolean arrays
	 * one for vertical lines, one for horizontal lines
	 */
	public Maze display() {
		boolean[][] hLines = new boolean[x+1][y];
		boolean[][] vLines = new boolean[x][y+1];

		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				hLines[i][j] = (maze[j][i] & 1) == 0 ? true : false;
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				vLines[i][j] = (maze[j][i] & 8) == 0 ? true : false;
			}
			System.out.println("|");
			vLines[i][y] = true;
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			hLines[x][j] = true;
		}
		
		return new Maze(vLines, hLines);
	}
 
	/*
	 * recursive algorithm follows a depth first search pattern to create a maze
	 */
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}
 
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
 
	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
}