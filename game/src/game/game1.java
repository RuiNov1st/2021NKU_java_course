package game;

import java.util.*;

//������Ϸ1���࣬�̳���game��
// 1. ʵ��game���м̳еĶ��������ɫ��������ɫ�ľ��崴��������Warrior��Master��
//2.����play������a1��a2�Կ���ѡ����ص��ȳ��У����blood�ȽϸߵĽ�ɫ
class game1 extends Game {
	int ActorCount = 2;
	int actionCount = 2;
	int mode = 0;
	//���캯���޲�
		game1(int mode){
			this.mode = mode;
		}
		
	//�����ڲ���
	public class Game1Actor1 extends Actor{
		//���캯��
		//ͨ�����๹�����������Actor�������
		Game1Actor1(int id,int blood,String name,int state,int attacknum,int defensefactor){
		//����Actor���캯��
			super(id,blood,name,state,attacknum,defensefactor);
		}
		//��Ա����
			
		//�������
		void randomAct(Actor a){
			//��ȡ�����
			Random r = new Random();
			//����0��1����������޸�a��״̬
			//ϵͳ״̬�������ΪԤ�Լ�
			a.state = r.nextInt(2);
			}
		}
	//�����������
	void printout() {
		//���������
		System.out.print("choose Attack(a) or Defense(d):");
	}
	//���������ö�������
	//��Ϊģʽ1��ģʽ2
	void setoperator_1(String acttype) {
		//��Сд������
		//��������Ƿ�Ϸ�
	while(!acttype.equals("Attack")&&!acttype.equals("attack")&&!acttype.equals("Defense")&&!acttype.equals("defense")&&!acttype.equals("a")&&!acttype.equals("d")) {
			System.out.print("Wrong type! Please choose again!"+"\n");
			System.out.print("choose Attack(a) or Defense:");
			Scanner sc3 = new Scanner(System.in);
			acttype = sc3.next();
		}
			if(acttype.equals("Attack")||acttype.equals("attack")||acttype.equals("a")) {
					a1.state = 1;
			}else { 
				if(acttype.equals("Defense")||acttype.equals("defense")||acttype.equals("d")) {
				a1.state = 0;
				}
			}
	}
	void setoperator() {
		printout();
		Scanner sc2 = new Scanner(System.in);
		String acttype = sc2.next();
		
		//�ı���Ҳ���״̬:
		//�����ж�a1��״̬�Ƿ�û������
		if(a1.state!=-1) {
			a1.state=-1;
		}
		
		setoperator_1(acttype);
		
		//ϵͳ�����������
		Game1Actor1 ga1 = new game1.Game1Actor1(a2.id, a2.blood, a2.name, a2.state, a2.attacknum, a2.defensefactor);
		ga1.randomAct(a2);
		
	}
	
	
	//���ý�ɫ���ͣ����������ɫ��ϵͳ��ɫ��
	Actor randomActor(boolean human) {
		//��ȡ�����
		Random r = new Random();
		//����0��1�������
		int i= r.nextInt(2);
		
		if(i==0) {
			return new Master(this);
		}else {
			return new Warrior(this);
		}
	}
	//�����������������ɫ
	Actor setActor(String type) {
		//��֤��ȡ��ȷ�Ľ�ɫ����
		while(!type.equals("Master")&&!type.equals("Warrior")&&!type.equals("m")&&!type.equals("M")&&!type.equals("w")&&!type.equals("W")) {
			System.out.print("Wrong type! Please enter again");
			Scanner sc = new Scanner(System.in);
			type = sc.next();
			//sc.close();
		}
		//���ݽ�ɫ���ִ����ض���ɫ
		switch(type) {
		case "Master":return new Master(this);
		case "m":return new Master(this);
		case "M":return new Master(this);
		default:return new Warrior(this);//Ĭ�����ɷ�ʦ
		}
		
	}
	
	//�ж���Ӯ
	void successprint() {
		//��ȡ
		int a1blood = a1.getBlood();
		int a2blood = a2.getBlood();
		//ģʽһ��ֻ��һ�֣�˭��Ѫ���˭Ӯ
		//�ж�ʤ��
		if(a1blood==a2blood) {
			System.out.print("Drew!\n");
		}else {
			if(a1blood>a2blood) {
				System.out.print("You are the winner!\n");
			}else {
				System.out.print("You lose!\n");
			}
		}
		
		//���Ѫ��
		System.out.print(a1.name+String.valueOf(a1.id)+"Blood:"+String.valueOf(a1blood)+"\n");
		System.out.print(a2.name+String.valueOf(a2.id)+"Blood:"+String.valueOf(a2blood)+"\n");
	}
	
	//��������������
	boolean operate() {
		//�����ߵĲ�����ͬʱ
		//�����߽�Ϊ���أ���������ҷ��أ�ϵͳ��ҽ�����ģʽһ��Ϊ����߹����ԣ�
			if(a1.isDefense()) {
				a1.defense();
				a2.attack(a1);
				a1.redefense();
			}
			else {
				//������ҽ�����ϵͳ��ҿɽ����ɷ���
				//ϵͳ��ҷ���
				if(a2.isDefense()) {
					a2.defense();
					a1.attack(a2);
					a2.redefense();
				}else {
				//ϵͳ��ҽ������������������
					a1.attack(a2);
					a2.attack(a1);
				}
			}
		return true;
}
	
	//���������ɫ��
	void printoutactor() {
		System.out.print("choose Master(m) or Warrior(w)");
	}
	
	void play_plus(int rank) {
		//����
		setoperator();
		operate();
		//�ж���Ӯ
		successprint();
	}
	
	//������Ϸ����
	void play() {
		play_1(0);
	}
	void play_1(int rank) {
		//�������
		boolean human = false;
		mylabels:
		while(true) {
			System.out.print("choose actor or produced randomly:\n");
			System.out.print("1-choose yourself; 2-produced randomly");
			Scanner sc = new Scanner(System.in);
			int i = 0;
			i = sc.nextInt();
			
			//���ñ�����¼�Ƿ������Զ�������ɫ
			
			//�ֶ�����
			if(i==1) {
				printoutactor();
				Scanner sc1 = new Scanner(System.in);
				String actortype = sc1.next();
				//�������
				a1 = this.setActor(actortype);
				break mylabels;
			}else {
				if(i==2) {
				//�����漴����
				human = true;
				a1 = this.randomActor(human);
				human = false;
				break mylabels;
				}else {
					System.out.print("Wrong range!\n");
					continue mylabels;
				}
			}
		}
			
			//ϵͳ���
			a2 = this.randomActor(human);

			//��ϵͳ��ҽ�ɫ�����������ͬ����Ҫ����id��
			if(a1.getname().equals(a2.getname())) {
				a2.setid();
			}
			
			//�����Һ�ϵͳ��ɫ
			System.out.print("You are"+" "+a1.getname()+String.valueOf(a1.id)+"\n");
			System.out.print("System is"+" "+a2.getname()+String.valueOf(a2.id)+"\n");
			
			//���ֹ���
			
			//��Ϊģʽһ����ÿ��ֻ��һ��
			play_plus(rank);
			
		}
}
		
			



		
