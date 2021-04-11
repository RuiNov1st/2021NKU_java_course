package game;

import java.util.Scanner;
import java.util.*;

//Canplay:
//���û����淨�Ľӿ�
 interface Canplay{
	void attack(Actor a);
	void defense();
}
	
 //Medican��
 //����ҩ������Ĳ�ͬ�Ӳ�ͬ��Ѫ��
 //��ͬ��ҩ�����Ų�ͬ������
 class Medicine{
	 //ҩ������������
	 int general = 3;
	 int median = 2;
	 int advance = 1;
	 
	 //��Ա��������
	 
	 //���ַ���ת��Ϊ����
	 int toInteger(String type) {
		 switch(type) {
		 case "general":return 1;
		 case "median":return 2;
		 case "advance":return 3;
		 }
		 return 0;
	 }
	 //������ת��Ϊ�ַ���
	 String toString_pro(int type) {
		 switch(type) {
		 case 1: return "general";
		 case 2:return "median";
		 default:return "advance";
		 }
	 }
	 
	 //��ȡҩ����
	 boolean getMedicine(String type) {
		int medicinelabel = 0;
		medicinelabel = this.toInteger(type);
		 //���Ƿ���
		 if(havemedican(medicinelabel)) {
			 switch(medicinelabel) {
			 case 1:general--;break;
			 case 2:median--;break;
			 case 3:advance--;break;
			 }
			 return true;
		 }else {
			 return false;
		 }
	 }
	 
	 //�ж�ĳ��ҩ�Ƿ���ҩ����
	 boolean havemedican(int medicinelabel) {
		 boolean flag = true;
		 switch(medicinelabel) {
		 case 1: if(general==0)flag = false;break;
		 case 2: if(median==0)flag = false;break;
		 case 3: if(advance==0)flag = false;break;
		 }
		 return flag;
	 }
	 //�ж����е�ҩ�Ƿ���ҩ��
	 boolean havemedicine() {
		 boolean flag = true;
		 if(general==0&&median==0&&advance==0) {
			 flag = false;
		 }
		 return flag;
	 }
	 //�����ǰҩ���Ĵ���
	 void getcurrentmedicine() {
		 System.out.print("current medican:\n");
		 System.out.print("general:"+general+"\n");
		 System.out.print("median:"+median+"\n");
		 System.out.print("advance:"+advance+"\n");
	 }
	 
 }
 
 
 

//��ɫ����:
//1. ʵ��CanPlay�ӿ�
//2. �����������
//3. �����������
//4. Ҫ��Game1Actor�̳�
class Actor implements Canplay {
	//��Ա����
	String name;//��ɫ����
	int id;//��ɫid
	int blood;//��ɫѪ��
	int state;//��ɫ״̬����������أ��������Ϊ1������Ϊ0
	int attacknum;//����ָ��
	int defensefactor;//����ָ��
	Medicine blood_bag;//Ѫ����������ÿһ����ɫ
	int count = 3;
	
	//���캯��
	Actor(int id,int blood,String name,int state,int attacknum,int defensefactor){
		this.id = id;
		this.blood = blood;
		this.name = name;
		this.state = state;
		this.defensefactor = defensefactor;
		this.attacknum = attacknum;
		blood_bag = new Medicine();//ÿ����ɫ�ڸ���ֵʱ�������Լ���Ѫ��
	}
	//��Ա����
	
	//�жϵ�ǰ�Ƿ�Ϊ����״̬
	boolean isDefense() {
		if(this.state==0) {
			return true;
		}else {
			return false;
		}
	}
	//�жϵ�ǰ�Ƿ�Ϊ����״̬
	boolean isAttack() {
		if(this.state==1) {
			return true;
		}else {
			return false;
		}
	}
	//�Ƿ񷢶�Ԥ��״̬
	boolean isPredict() {
		if(this.state==2) {
			return true;
		}else {
			return false;
			}
	}
	//��Ѫ
	//rui:����ҩ������Ĳ�ͬ�Ӳ�ͬ��Ѫ��
	void addBlood(String type) {
		switch(type) {
		case "general":this.blood = this.blood+40;break;
		case "median":this.blood= this.blood+80;break;
		case "advance":this.blood = this.blood+120;break;
		}
		
	}
	//��Ѫ����Ҳ�������
	void Bloodoperate() {
		String jud = "";
		//�ж��Ƿ���Ѫ��
		//���е�Ѫ����û��
		if(!blood_bag.havemedicine()) {
			System.out.print("There is no any blood bags left.\n");
		}else {
		//����Ѫ��
		System.out.print("If you want to add your Blood?-Yes(y)/No(n)"+"\n");
		Scanner sc4 = new Scanner(System.in);
		//�ж�����ĺϷ��ԣ������Ϸ���ֱ���˳���Ѫ����˭��������Ѫ�����
		if(sc4.hasNext()) {
			jud = sc4.next();
		}else {
			System.out.print("Wrong Input!"+"\n");
		}
	}
		//��Сд������
		if(jud.equals("Yes")||jud.equals("yes")||jud.equals("y")||jud.equals("Y")) {
		//�����ǰѪ��������
		System.out.print("Your medicine storage:\n");
		blood_bag.getcurrentmedicine();
		System.out.print("Choose blood bags type:g/m/a\n");
		Scanner sc5 = new Scanner(System.in);
		String type = sc5.next();
		//�ж��Ƿ��и����͵�Ѫ��
		//���У������;û�У������ʧ��
		switch(type) {
		case "g":type = "general";break;
		case "m":type = "median";break;
		case "a":type = "advance";break;
		default:break;
		}
		 if(blood_bag.getMedicine(type)) {
				this.addBlood(type);
		}else {
				System.out.print("Failure add!");
			 }
		}
}
	
	//ϵͳ��Ҽ�Ѫ
	//�ͼ��淨������Ѫ��
	//�м��淨��������������Ƿ��Ѫ����ѪҲֻ�Ǽ���ͼ���Ѫ����Ѫֻ�ܼ����Ρ�
	//�߼��淨��Ϊ��Ӯ��������Ŀ�ʼ���
	void BloodoperateSys(int rank) {
		if(rank==1) {
	    //�м��淨��
	    Random r = new Random();
	    int type = r.nextInt(2);
	    
	    //��type = 0ʱ����Ѫ
	    if(type!=0) {
	    	if(count>0) {
	    	this.addBlood(blood_bag.toString_pro(1));
	    	count = count-1;
	    }
	}
	}else {
		if(rank==2) {
	//�߼��淨������߿�ʼ�ӡ�
	//����Ѫ��
	if(blood_bag.havemedicine()) {
		if(blood_bag.havemedican(3)) {
			this.addBlood("advance");
		}else {
			if(blood_bag.havemedican(2)) {
				this.addBlood("median");
			}else {
				if(blood_bag.havemedican(1))
				this.addBlood("general");
			}
		}
	}
		}
		
}
}
	
	//һ��������������ڹ���ָ��/����ָ��
	void generalattack(Actor a) {
		int attackindex = this.attacknum/a.defensefactor;
		a.blood = a.blood-attackindex;
	}
	//double�������������ڹ���ָ��*2/����ָ��
	void doubleattack(Actor a) {
		int attackindex = this.attacknum*2/a.defensefactor;
		a.blood = a.blood-attackindex;
	}
	
	//��ȡ��ǰѪ��
	int getBlood() {
		return this.blood;
	}
	//���ݽ�ɫ�Ĳ�ͬ���ò�ͬ��Ѫ��
	void setBlood(int blood) {
		this.blood = blood;
	}
	//��ȡ��ɫ����
	String getname() {
		return this.name;
	}
	//���ý�ɫ����
	void setName(String name) {
		this.name = name;
	}
	
	
	//�ӿڵ�ʵ�֣��������ɫ��д
	//attack���������Ľ���״̬��
	public void attack(Actor a) {
	}
	
		
	//defense���������ķ���״̬	
	public void defense() {
		//����״̬�·���ָ�����ӡ�
		this.defensefactor +=10;
		this.state = 0;
		//�����ʱ״̬]
		System.out.print(this.name+String.valueOf(this.id)+' '+"Defense!"+"\n");
		
	}
	public void judattack(Actor a) {
		this.attack(a);
	}
	public boolean juddefense() {
		this.defense();
		return false;
	}
	//����ָ��״̬�Ļָ�
	public void redefense() {
		this.defensefactor-=10;
	}
	//��Ԥ�Լ�������д
	public void predict(Actor a) {
		
	}
	void setid() {
		this.id+=1;
		
	}
	

}
