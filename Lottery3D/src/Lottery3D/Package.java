package Lottery3D;

//��ѡ
//�ڲ�����ж�̬�Լ̳�
public class Package extends Lottery3D {
	int label_pro = 0;//��¼�����ֻ񽱷�ʽ��Ĭ��ʲô������
	//��ѡ3ȫ�У�1
	//��ѡ3���У�2
	//��ѡ6ȫ�У�3
	//��ѡ6����: 4
	
	void print_sen() {
		System.out.print("������0~999֮�������\n");
	}
	
	//һ����̳�Single��
	//��λȫ����ͬ
	class PackSing extends Single{
		
	}
	//һ����̳�group��
	//���Բ���˳��
	class PackGroup extends group{
		
	}
	//�ж��Ƿ��
	boolean judwin() {
		int count = 1;//ÿ����������ͬ
		//�������
		this.numbercount();
		//ȷ������
		for(int i = 0;i<length;i++) {
			if(this.countwin[this.winNumber[i]]>=2) {
				count = 2;//������������ͬ
			}
		}
		boolean flag1 = true;
		//���ж��Ƿ�ȫ��
		PackSing ps = new PackSing();
		flag1 = ps.judwin(this.winNumber,this.userNumber);
		if(flag1) {
			if(count==2) {
				label_pro = 1;
			}else {
				label_pro =3;
		    }
		}else {
			//���ж��Ƿ�����
			PackGroup pg = new PackGroup();
			flag1 = pg.judwin(this.winNumber,this.userNumber);
			if(flag1) {
				if(count==2) {
					label_pro = 2;
				}else {
					label_pro = 4;
				}
		}
		}
		return flag1;
	}
	//��ȡ����
	int getWins() {
		int bouns = 0;
		switch(label_pro) {
		case 1:bouns = 693;break;
		case 2:bouns = 173;break;
		case 3:bouns = 606;break;
		case 4:bouns = 86;break;
		}
		return bouns;
	}
}
