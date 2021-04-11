package game;

import java.util.*;

import game.game1.Game1Actor1;

//��mode=2����
class game2 extends game1{
	int ActCount = 3;
	static int mode = 2;
	
	game2(){
		super(mode);
	}
	
	//������������ɫ
	public class Game2Actor extends game1.Game1Actor1{
		//���캯��
		Game2Actor(int id,int blood,String name,int state,int attacknum,int defensefactor){
			super(id,blood,name,state,attacknum,defensefactor);
		}
		
	}
	//�����������
	void printout() {
		//�������
		if(a1.name.equals("Predictor")) {
			System.out.print("choose Attack(a) or Defense(d) or Predict(p):");
		}else {
			System.out.print("choose Attack(a) or Defense(d):");
		}
	}
	//���������ö�������
	//��Ϊģʽ1��ģʽ2
	void setoperator_2(String acttype) {
		//��Сд������
		//��������Ƿ�Ϸ�
		while(!acttype.equals("Attack")&&!acttype.equals("attack")&&!acttype.equals("Defense")&&!acttype.equals("Predict")&&!acttype.equals("p")&&
				!acttype.equals("defense")&&!acttype.equals("a")&&!acttype.equals("d")) {
				System.out.print("Wrong type! Please choose again!"+"\n");
				System.out.print("choose Attack(a) or Defense(d) or Predict(p):");
				Scanner sc3 = new Scanner(System.in);
				acttype = sc3.next();
			}
				if(acttype.equals("Attack")||acttype.equals("attack")||acttype.equals("a")) {
						a1.state = 1;
				}else { 
					if(acttype.equals("Defense")||acttype.equals("defense")||acttype.equals("d")) {
					a1.state = 0;
					}else {
						//Ԥ��״̬
						if(acttype.equals("Predict")||acttype.equals("predict")||acttype.equals("p")) {
							a1.state = 2;
						}
					}
				}
		}
	//������ɲ���
	void setoperator() {
		printout();
		Scanner sc2 = new Scanner(System.in);
		String acttype = sc2.next();
		
		//�ı���Ҳ���״̬:
		//�����ж�a1��״̬�Ƿ�û������
		if(a1.state!=-1) {
			a1.state=-1;
		}
		
		//Ԥ�Լ�ģʽ������Ԥ�Բ���
		if(a1.name.equals("Predictor")) {
			setoperator_2(acttype);
		}else {
		setoperator_1(acttype);
		}
		
		//ϵͳ�����������
		Game1Actor1 ga1 = new game1.Game1Actor1(a2.id, a2.blood, a2.name, a2.state, a2.attacknum, a2.defensefactor);
		ga1.randomAct(a2);
	}
	//���ý�ɫ���ͣ����������ɫ��ϵͳ��ɫ��
	Actor randomActor(boolean human) {
	//��ȡ�����
	Random r = new Random();
	int i = 0;
	if(human==true) {
	//����0��1�������
	i= r.nextInt(3);
	}else {
	//�������ܴ���Ԥ�Լ�
	i = r.nextInt(2);
	}
			
	if(i==0) {
		return new Master(this);
	}else {
		if(i==1) {
		return new Warrior(this);
		}else {
			return new predictor(this);
		}
	}
	}
	//�����������������ɫ
	Actor setActor(String type) {
	//��֤��ȡ��ȷ�Ľ�ɫ����
	while(!type.equals("Master")&&!type.equals("Warrior")&&!type.equals("Predictor")&&!type.equals("m")&&!type.equals("M")&&!type.equals("w")&&!type.equals("W")&&!type.equals("p")&&!type.equals("P")) {
		System.out.print("Wrong type! Please enter again");
		Scanner sc = new Scanner(System.in);
		type = sc.next();
		}
		//���ݽ�ɫ���ִ����ض���ɫ
		switch(type) {
		case "Master":return new Master(this);
		case "m":return new Master(this);
		case "M":return new Master(this);
		case "Predictor":return new predictor(this);
		case "p":return new predictor(this);
		case"P":return new predictor(this);
		default:return new Warrior(this);//Ĭ�����ɷ�ʦ
		}
			
	}
    //�ж���Ӯ
	void successprint() {
		//��ȡ
		int a1blood = a1.getBlood();
		int a2blood = a2.getBlood();
		
		//ģʽ������һ����ѪС��0Ϊֹ�����ж���Ӯ��
		if(a1blood<=0) {
		System.out.print("You lose!\n");
		}else {
		if(a2blood<=0) {
		System.out.print("You are the winner!\n");
		}
		}
		//���Ѫ��
		System.out.print(a1.name+String.valueOf(a1.id)+"Blood:"+String.valueOf(a1blood)+"\n");
		System.out.print(a2.name+String.valueOf(a2.id)+"Blood:"+String.valueOf(a2blood)+"\n");
	}

	//��������������
	boolean operate() {
	//ģʽ����
	boolean flag1 = false;//�ж�a1�Ƿ�ᴥ����������
	boolean flag2 = false;//�ж�a2�Ƿ�ᴥ����������
	//�������Ԥ��״̬��
	if(a1.isPredict()) {
		a1.predict(a2);
		//�ٴν��в���ѡ��
		setoperator();
	}
		if(a1.isDefense()) {
			if(a1.juddefense()) {
				flag1 = true;
			}
			if(a2.isAttack()) {
				a2.judattack(a1);
			}else {
				if(a2.juddefense()) {
					flag2 = true;
				}
			}
			//�����Ǵ������������������ָ����Ҫ�ظ���
			if(!flag1) {
			a1.redefense();
			}
			if(!flag2) {
			a2.redefense();
			}
		}else {
			//������ҽ�����ϵͳ��ҿɽ����ɷ���
			//ϵͳ��ҷ���
			if(a2.isDefense()) {
				if(a2.juddefense()) {
					flag2 = true;
				}
				a1.judattack(a2);
				if(!flag2) {
					a2.redefense();
				}
			}else {
			//ϵͳ��ҽ������������������
				a1.judattack(a2);
				if(a2.getBlood()<=0) {
					successprint();
					return true;
				}
				a2.judattack(a1);
			}
		}
		return false;
	}

	//���������ɫ��
	void printoutactor() {
		System.out.print("choose Master(m) or Warrior(w) or Predictor(p)");
	}
	//ģʽ2��ֻҪû�ﵽ˫��ûѪ�����Լ�������ȥ
	void play_plus(int rank) {
		int countmatch = 1;
		mylabels:
		while(a1.getBlood()>0&&a2.getBlood()>0) {
			if(countmatch%5==0) {
				System.out.print("Would you like to give up? -Y(y)/N(n)\n");
				Scanner sc8 = new Scanner(System.in);
				String choice = sc8.next();
				if(choice.equals("Y")||choice.equals("y")) {
					a1.blood = 0;
					successprint();
					continue mylabels;
				}
			}
		setoperator();
		if(!operate()) {
		successprint();
		}
		if(a1.getBlood()>0&&a2.getBlood()>0) {
		//�ͼ�ģʽ�����ϵͳ�����Ѫ
		a1.Bloodoperate();
		a2.BloodoperateSys(rank);
		//�鿴��ǰѪ��
		System.out.print("Your blood is:"+String.valueOf(a1.getBlood())+"\n");
	}
		//����ѡ�����������
		countmatch++;
		
		}
		
	}
	//�Ѷȵȼ�ѡ��
	int rank_choose() {
		//ѡ���Ѷȵȼ�
		System.out.print("choose level you wanna challenge:\n0-easy(System player would not add blood automatically),\n1-middle(System player would add blood randomly),\n2-advance(System player would add blood to win):\n");
		Scanner sc5 = new Scanner(System.in);
		while(!sc5.hasNextInt()) {
			System.out.print("Wrong range!\n");
			sc5.nextLine();
		}
		int rank = sc5.nextInt();
		return rank;
	}
	//��������
	void play() {
		int rank = rank_choose();
		play_1(rank);
	}
	
	
}