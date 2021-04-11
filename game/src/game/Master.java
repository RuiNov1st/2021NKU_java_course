package game;

//�̳�gam1Actor��
class Master extends game1.Game1Actor1 {
	//��������
	private static int blood = 100;
	public static int id = 1;
	public static int state = -1;//Ĭ��״̬�����ѡ��
	private static int attacknum = 300;//��ʦ�Ĺ���ָ��
	private static int defensefactor = 15;//��ʦ�ķ���ָ��
	public static String name = "Master";
	//���캯��
	Master(game1 g1){
		//���ɷ�ʦ��ɫ
		g1.super(id, blood, name, state, attacknum, defensefactor);
	}
	//��������
	public void attack(Actor a) {
		//���ñ�ǩ��������ʽ
		int label = 0;//Ĭ��Ϊgeneral attack
		
		//ͬ�࣬һ�����
		if(this.getname().equals(a.getname())) {
			this.generalattack(a);
			
		}else {
			//��ͬ�࣬double����
			this.doubleattack(a);
			label = 1;
		}
		//�����ǰ״̬
		if(label==0) {
			System.out.print("Master"+String.valueOf(this.id)+' '+"General Attack!"+"\n");
		}else {
			System.out.print("Master"+String.valueOf(this.id)+' '+"Double Attack!!"+"\n");
		}
		
	}
	//�����������ض�����������Ѫ���½���һ���̶�ʱ��
	//���������������������ָ�����Ҳ���ظ�
	public void superdefense() {
		defensefactor=30;
	}
	
	public boolean juddefense() {
		//�ж��Ƿ�ᴥ����������
		boolean flag = false;
		if(this.getBlood()<30) {
			this.superdefense();
			this.defense();
			flag = true;
		}else {
			this.defense();
			flag = false;
		}
		return flag;
	}

}
