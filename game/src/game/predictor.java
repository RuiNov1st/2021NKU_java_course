package game;

class predictor extends game2.Game2Actor {
	//��������
	private static int blood = 200;
	public static int id = 1;
	public static int state = -1;//Ĭ��״̬�����ѡ��
	private static int attacknum = 250;//Ԥ�ԼҵĹ���ָ��
	private static int defensefactor = 15;//��ʦ�ķ���ָ��
	public static String name = "Predictor";
	
	//���캯��
	predictor(game2 g2){
			//������ʿ��ɫ
		g2.super(id, blood, name, state, attacknum, defensefactor);
	}
	//��������
	public void attack(Actor a) {
		//ֻ����ͨ����
		this.generalattack(a);
		System.out.print("Predictor"+String.valueOf(this.id)+' '+"General Attack!"+"\n");
	}
	void printoutstate(Actor a) {
		if(a.state==0) {
		System.out.print(a.getname()+String.valueOf(a.id)+"Defense!\n");
		}else {
			System.out.print(a.getname()+String.valueOf(a.id)+"Attack!\n");
		}
	}
	//Ԥ�Լң��鿴���˵Ĳ���״̬
	public void predict(Actor a) {
		this.state = 2;//Ԥ��״̬
		//�鿴���ֵĲ���״̬��
		printoutstate(a);
	}
	void setid() {
		id = id+1;
	}
	
			
			
		
}
