package game;
import java.util.*;


public class play {
	static Scanner sc = new Scanner(System.in);
	public static void main(String arg[]) {
		//ѡ���Ƿ�����һ��
		String choice = "";
		//������Ϸ
		do {
		
		int mode = 0;
		
		//�Ƿ��ַ�����Ϸģʽ�Ϸ����жϣ�
		boolean judge = true;
		mylabels:
		while(true) {
			//ģʽѡ��
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
		
		//������Ϸ
		Game G = new game1(mode);
		if(mode==1) {
			G = new game1(mode);
		}else {
				G = new game2();
		}
			
		G.play();
		//ѡ���Ƿ�����һ��
		System.out.print("One more time?-Yes(y) or No(n)");
		Scanner sc = new Scanner(System.in);
		choice = sc.next();
		}while(choice.equals("Yes")||choice.equals("yes")||choice.equals("y")||choice.equals("Y"));//��Сд������
		
		sc.close();
	}
}
