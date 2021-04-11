package game;
import java.util.*;


public class play {
	static Scanner sc = new Scanner(System.in);
	public static void main(String arg[]) {
		//选择是否再玩一局
		String choice = "";
		//启动游戏
		do {
		
		int mode = 0;
		
		//非法字符和游戏模式合法性判断：
		boolean judge = true;
		mylabels:
		while(true) {
			//模式选择
			System.out.print("Choose game mode:1-one game;2-multiple games:");
			judge = sc.hasNextInt();
			if(!judge) {
				System.out.print("Wrong type!"+"\n");
				sc.nextLine();
				continue mylabels;
			}
			mode = sc.nextInt();
			if(mode==1||mode==2) {
				break mylabels;
			}
			System.out.print("Wrong mode!"+"\n");
			sc.nextLine();
		}
		
		//生成游戏
		Game G = new game1(mode);
		if(mode==1) {
			G = new game1(mode);
		}else {
				G = new game2();
		}
			
		G.play();
		//选择是否再玩一局
		System.out.print("One more time?-Yes(y) or No(n)");
		Scanner sc = new Scanner(System.in);
		choice = sc.next();
		}while(choice.equals("Yes")||choice.equals("yes")||choice.equals("y")||choice.equals("Y"));//大小写不敏感
		
		sc.close();
	}
}
