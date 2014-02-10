import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {

	private String fileName, bufferLine;
	private int init_X, init_Y;
	private int dimention, lineCounter = 0;
	private Position[][] elements_maze;
	private String[] elements_line;

	// main函数
	public static void main(String[] args) throws IOException {
		Maze maze = new Maze(); // 新建Maze类变量
		maze.run(args); // 调用Maze类中的run（）函数并传入参数
	}

	// 程序大体思路如下
	// 1.检查用户输入是否合法：参数长度，参数类型
	// 2.从文件中读取迷宫数据，存入建立的数据结构中
	// 3.检查用户输入的起始点是否合法：是否为P
	// 4.递归寻找deadend，通过查看每个P位置周围4个位置属性来判断，如果三面环“X”即为deadend，
	// 并标记为“X”，直到没有变化为止
	// 5.然后就可得出路径所通过的位置
	private void run(String[] args) throws IOException {
		if (args.length != 3) {
			System.err.println("No arguments input!");
			System.exit(0);
		}
		fileName = args[0];
		try {
			init_X = Integer.parseInt(args[1]) - 1; // 直接把输入的x和y减一，以方便计算，
			init_Y = Integer.parseInt(args[2]) - 1; // 因为数组从0开始计数
		} catch (Exception e) {
			System.err
					.println("The second and third arguments have to be integers!");
			System.exit(0);
		}

		this.loadFile(fileName); // 调用loadFile函数，前面的this指当前class，也就是Maze了
		if (this.checkStart(init_X, init_Y) == true) { // 检查用户输入的起始位置是否为p
			elements_maze[init_X][init_Y].setNature("S"); // 如果为p则将其改为S
			while (this.locateDeadEnd() == true) { // 定位deadend位置並判斷是否有新的deadend
				@SuppressWarnings("unused")
				boolean tmp = this.locateDeadEnd(); // 不停的找出deadend直到沒有新的出現
			}
		} else {
			System.err.println("The coordinate you provided is not valid!");
			System.exit(0);
		}
		this.printResult();
	}

	private void loadFile(String fileName) throws IOException {
		// 建立reader以讀取文件中的數據
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		bufferLine = br.readLine(); // 讀取單個行
		dimention = Integer.parseInt(bufferLine); // 由於第一行是維數，之間轉化爲整數類型
		elements_maze = new Position[dimention][dimention]; // 給二維數組分配內存
		// 遞歸讀取每行並存入二維數組
		for (int j = 0; j < dimention; j++) {
			bufferLine = br.readLine();
			elements_line = bufferLine.split(" ");

			for (int i = 0; i < dimention; i++) {
				Position tmp = new Position(elements_line[i].toUpperCase(), // 這裏一律改爲大寫
						lineCounter, i);
				elements_maze[lineCounter][i] = tmp;
			}
			lineCounter++;
		}
	}

	private boolean checkStart(int initX, int initY) {
		boolean result;
		if (elements_maze[initX][initY].getNature().equals("P"))
			result = true;
		else
			result = false;
		return result;
	}

	private boolean locateDeadEnd() {
		boolean result = false;
		for (int i = 0; i < dimention; i++) {
			for (int j = 0; j < dimention; j++) {
				// 判斷該位置是否爲p以及搜索其周圍4個相鄰位置p的個數，如果爲1則是deandend
				if ((elements_maze[i][j].getNature().equals("P") && this
						.searchPathAround(i, j) == 1)) {
					elements_maze[i][j].setNature("X");
					result = true;
				}
			}
		}
		return result; // 返回是否找到新的deadend
	}

	private int searchPathAround(int X, int Y) {
		int count = 0;
		if ((elements_maze[X - 1][Y].getNature().equals("P"))
				|| (elements_maze[X - 1][Y].getNature().equals("S"))
				|| (elements_maze[X - 1][Y].getNature().equals("G")))
			count++;
		if ((elements_maze[X + 1][Y].getNature().equals("P"))
				|| (elements_maze[X + 1][Y].getNature().equals("S"))
				|| (elements_maze[X + 1][Y].getNature().equals("G")))
			count++;
		if ((elements_maze[X][Y - 1].getNature().equals("P"))
				|| (elements_maze[X][Y - 1].getNature().equals("S"))
				|| (elements_maze[X][Y - 1].getNature().equals("G")))
			count++;
		if ((elements_maze[X][Y + 1].getNature().equals("P"))
				|| (elements_maze[X][Y + 1].getNature().equals("S"))
				|| (elements_maze[X][Y + 1].getNature().equals("G")))
			count++;
		return count;
	}

	private void printResult() {
		for (int i = 0; i < dimention; i++) {
			for (int j = 0; j < dimention; j++) {
				System.out.print(elements_maze[i][j].getNature() + " ");
			}
			System.out.print("\n");
		}
		System.out.println("The path goes like:");

		// 把迷宮中的X改爲空格以方便觀察
		for (int i = 0; i < dimention; i++) {
			for (int j = 0; j < dimention; j++) {
				if (elements_maze[i][j].getNature().equals("X"))
					elements_maze[i][j].setNature(" ");
			}
		}
		for (int i = 0; i < dimention; i++) {
			for (int j = 0; j < dimention; j++) {
				System.out.print(elements_maze[i][j].getNature() + " ");
			}
			System.out.print("\n");
		}
		// 输出最后的path顺序坐标
		System.out.println("The locations of the path in order are:");
		ArrayList<Position> steps = findPath();
		for (int i = 0; i < steps.size(); i++) {
			// 为啥要加1呢，因为计算机坐标是从0算起，而要输出的坐标是从1算起的
			System.out.println("(" + (steps.get(i).getCoodinateX() + 1) + ","
					+ (steps.get(i).getCoodinateY() + 1) + ")");
		}
	}

	// 主要增加的就是一下两个函数了
	private ArrayList<Position> findPath() {
		ArrayList<Position> pathOrder = new ArrayList<Position>();
		Position shifter = new Position("S", init_X, init_Y);
		pathOrder.add(shifter);
		// 循环调用findStep函数，直到没有下一步，每一步把所走下的step加入ArrayList中，以供
		// 之后输出调用
		while (findStep(shifter.getCoodinateX(), shifter.getCoodinateY()) != null) 
		{
			// 这个地方shifter的作用相当与一个磁头，找到下一步并移动磁头
			shifter = findStep(shifter.getCoodinateX(), shifter.getCoodinateY());
			// 保存所走过的坐标
			pathOrder.add(shifter);
		}
		return pathOrder;
	}

	// 从给入的其实点开始算起，扫描其周围的位置，找到P，并输出返回那个位置，
	// 于此同时把当前的位置改为X,以免重复扫描造成无限循环
	private Position findStep(int x, int y) {
		Position tmp = null;
		if (elements_maze[x][y + 1].getNature().equals("P")
				|| elements_maze[x][y + 1].getNature().equals("G")) {
			tmp = new Position("P", x, y + 1);
			elements_maze[x][y].setNature("X");
		}
		if (elements_maze[x][y - 1].getNature().equals("P")
				|| elements_maze[x][y - 1].getNature().equals("G")) {
			tmp = new Position("P", x, y - 1);
			elements_maze[x][y].setNature("X");
		}
		if (elements_maze[x + 1][y].getNature().equals("P")
				|| elements_maze[x + 1][y].getNature().equals("G")) {
			tmp = new Position("P", x + 1, y);
			elements_maze[x][y].setNature("X");
		}
		if (elements_maze[x - 1][y].getNature().equals("P")
				|| elements_maze[x - 1][y].getNature().equals("G")) {
			tmp = new Position("P", x - 1, y);
			elements_maze[x][y].setNature("X");
		}
		return tmp;
	}
}



