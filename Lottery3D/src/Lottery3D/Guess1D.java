package Lottery3D;

import java.util.Scanner;

class Guess1D extends Lottery3D {
	//��¼�ǲ���1��2��3
	int label = 0;//Ĭ��û����
	int standcount = 1;
	
	void print_sen() {
		System.out.print("������һ������");
	}
	int getstancount() {
		return standcount;
	}
	
	//ֻ����һ������
	boolean isAvailable(String s) {
		//���ȼ��
		if(s.length()!=getstancount()) {return false;}
		//�ж������Ƿ�Ϊ����
		for(int i = 0;i<getstancount();i++) {
			char a = s.charAt(i);
			if(a<48||a>57) {
			return false;
		}
		}
		return true;
	}
	//�ж���Ӯ��ȷ�����м�
	boolean judwin() {
		boolean flag = true;
		//λ�ô���
		numbercount();
		label = this.countwin[this.userNumber[0]];
		if(label == 0) {
			flag = false;
		}
		return flag;
	}
	//��������
	int getWins() {
		switch(label) {
		case 1:return 2;
		case 2:return 12;
		default:return 230;
		}
	}
	
	
}

class Guess2D extends Guess1D{
	int standcount = 2;
	int label = 1 ;//�ж�����ͬ�Ż�������ͬ�ţ�1-Ĭ��Ϊ����ͬ�ţ�2-Ϊ��ͬ��
	//����
	void print_sen() {
		System.out.print("��������������");
	}
	int getstancount() {
		return standcount;
	}
	//�ж���Ӯ��ȷ�����м�
	boolean judwin() {
		boolean flag = true;
		//��������
		numbercount();
		for(int i = 0;i<this.standcount;i++) {
			if(this.countinput[this.userNumber[i]]==this.countwin[this.userNumber[i]]) {
				if(this.countinput[this.userNumber[i]]>=2) {
					label = 2;
				}
			}else {
				flag = false;
			}
		}
		
		return flag;
	}
	//��ȡ����
	int getWins() {
		if(label==1) {
			return 19;
		}else {
			return 37;
		}
	}
}

//����ͬ
class ThreeSame extends Lottery3D{
	int label = 0;//��¼�Ƿ񿪳���������Ĭ��Ϊ0-������1-��
	//���
	void print_sen() {
		System.out.print("�����롰Yes�����ߡ�No��");
	}
	//�ж������Ƿ���ȷ
	boolean isAvailable(String s) {
		choice[0] = "Yes";
		choice[1] = "No";
		return CharaLegal(s);
		
	}
	//�ж��Ƿ��
	boolean judwin() {
		//�������
		this.numbercount();
		//������������
		if(this.countwin[this.winNumber[0]]==3) {
			if(label_high==1) {
				return true;
			}
		}else {
			//û����������
			if(label_high==0) {
				return true;
			}
		}
		return false;
	}
	//ȡ�ý���
	int getWins() {
		return 104;
	}
	
}
