package helloworld;


import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.concurrent.locks.*;

//���߳�
public class CDshop {
	public static void main(String []args) {
		//ʹ����
		Lock l = new ReentrantLock();
		//���ý�������������
		Condition salecondition;
		Condition getincondition;
		getincondition = l.newCondition();
		salecondition = l.newCondition();
		
		//������־
		Logger mylogger = Logger.getLogger("CDshop");
		//���뵽�ļ���
		FileHandler f;
		try {
			f = new FileHandler("D:\\java_file\\HelloWorld\\cdshop.txt");
			SimpleFormatter sf = new SimpleFormatter();
			f.setFormatter(sf);
			mylogger.addHandler(f);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//����CD�б���ʮ��
		CD_collect cc = new CD_collect(mylogger,l,salecondition,getincondition);
		//����CD�б�10��
		CD_pro []cdrentlist = new CD_pro[10];
		for(int i = 0;i<10;i++) {
			cdrentlist[i] = new CD_pro();
		}
		//�����ۺ�����CD���������
		for(int i = 0;i<10;i++) {
			cdrentlist[i].name = "CD_rent"+String.valueOf(i+1);
			cdrentlist[i].num = 1;//ÿ��CDֻ��һ��
		}
		//�����߳���
		ControlThread_pro control = new ControlThread_pro(cc,cdrentlist,mylogger, l,getincondition , salecondition);
		control.start();
	}

}
//�ѽ��������ۿ��ɼ��ϲ���
class CD_collect{
	Lock l;
	private Condition getincondition;
	private Condition salecondition;
	int type = 10;
	Logger lg;
	//����CD�б���ʮ��
	CD_pro []cdlist =new CD_pro[10];
	CD_collect(Logger lg,Lock l, Condition salecondition, Condition getincondition) {
		this.l= l;
		this.getincondition = getincondition;
		this.salecondition = salecondition;
		this.lg = lg;
		for(int i = 0;i<10;i++) {
		cdlist[i] = new CD_pro();
		cdlist[i].name = "CD_saled"+String.valueOf(i+1);
		}
	}
	//����
	void getin() throws InterruptedException {
		l.lock();
		for(int i = 0;i<10;i++) {
			cdlist[i].num = 10;
			//lg.info(cdlist[i].name+" GetIn Start\n"+cdlist[i].name+" Current Storage:"+cdlist[i].num+"\n");
			System.out.print(cdlist[i].name+" GetIn Start\n");
			System.out.print(cdlist[i].name+" Current Storage:"+cdlist[i].num+"\n");
		}
		salecondition.signalAll();
		long remain = getincondition.awaitNanos(1000000000);
		if(remain<=0) {
			System.out.print("Normal GetIn!\n");
		}
		l.unlock();
	}
	
	//����
	void sale(int n,int id,int type,GetInThread_pro gt) {
			l.lock();
			//��治��
			while(this.cdlist[type].num-n<0) {
				//lg.warning(cdlist[type].name+"'s stock is not enough now\n"+cdlist[type].name+" wait to GetIn\n");
				//System.out.print("current Storage: "+this.cdlist[type].num+"\n");
				System.out.print(cdlist[type].name+"'s stock is not enough now\n");
				//System.out.print(cdlist[type].name+" wait to GetIn\n");
				System.out.print(cdlist[type].name+" wait to GetIn\n");
				try {
					System.out.print(new Date()+" Force to get in\n");
					getincondition.signal();
					salecondition.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//�����㣬��������
			cdlist[type].num = cdlist[type].num-n;
			//lg.info(new Date()+"Thread"+id+" "+"Sale "+n+"copies "+cdlist[type].name+"\n"+cdlist[type].name+" Current Storage: "+cdlist[type].num+"\n");
			System.out.print(new Date()+" "+"Thread"+id+" "+"Sale "+n+"copies "+cdlist[type].name+"\n");
			System.out.print(cdlist[type].name+" Current Storage: "+cdlist[type].num+"\n");
			
			l.unlock();
			//���������߳�
			try {
				//���������ִ�е��߳���������CPU�������߳�ִ��
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}




//CD����
class CD_pro{
	String name;
	int num=10;
	
	//������
	synchronized void rent(int id,CD_pro cd,Logger lg) {
		//Ҫ���CDû��
		while(this.num==0) {
			//lg.warning(name+"'s stock is not enough now\n"+name+" wait others to return \n");
			System.out.print(name+"isn't avaliable\n");
			System.out.print(name+" wait others to return \n");
			notifyAll();
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//�赽��
		this.num = 0;
		//lg.info(new Date()+"Thread"+id+" "+"Rent "+name+"\n");
		System.out.print(new Date()+" "+"Thread"+id+" "+"Rent "+name+"\n");
		Random time = new Random();
		try {
			//����Ⱥ�200~300msȻ��黹
			notifyAll();
			wait(time.nextInt(100)+200);
			//Thread.sleep(time.nextInt(100)+200);
			this.num = 1;//�黹CD
			//lg.info(name+" has been returned by"+" Thread"+id+"\n");
			System.out.print(new Date()+" "+name+" has been returned by"+" Thread"+id+"\n");
			notifyAll();//����������Ҫ��ͬһ��CD���߳�
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���������߳�
		try {
			//���������ִ�е��߳���������CPU�������߳�ִ��
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//�����߳�
//ÿ�β������CD�б�
class GetInThread_pro extends Thread{
	Lock l;
	//����CD
	CD_pro []cdlist;
	CD_collect cc;
	Logger lg;
	Condition salecondition;
	Condition getincondition;
	//���캯��
	GetInThread_pro(CD_collect cc, Lock l, Condition getincondition, Condition salecondition){
		this.salecondition = salecondition;
		this.getincondition = getincondition;
		this.l = l;
		this.cc = cc;
		this.setDaemon(true);//һֱ����ѭ��״̬���߳�һ��Ҫ����Ϊdaemon�������߳��޷�����
	}
	
	//ͬ�����ǵ�ǰ��CD�б����
	public void run(){
		while(true) {
			//lg.info("GetIn Start:\n");
			System.out.print(new Date()+" "+"GetIn Start:\n");
			try {
				cc.getin();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//�����߳�
class saleThread_pro extends Thread{
		Lock l;
		Condition salecondition;
		Condition getincondition;
		GetInThread_pro gt;
		//����CD�б�
		CD_pro [] cd_saled;
		CD_collect cc;
		//���ֲ�ͬ���߳�
		static int ids;
		int id;
		Logger lg;
		//���캯��
		saleThread_pro(CD_collect cc,GetInThread_pro gt,Logger lg,Lock l,Condition getincondition,Condition salecondition){
			this.salecondition = salecondition;
			this.getincondition = getincondition;
			this.l = l;
			this.gt = gt;
			this.cc = cc;
			this.setDaemon(true);//һֱ����ѭ��״̬���߳�һ��Ҫ����Ϊdaemon�������߳��޷�����
			id = ++ids;
			this.lg = lg;
		}
		//��ֹ����Ĵ���ƴ����
		//��֤��������д����
		@Override
		public void run() {
			//����
			Random t = new Random();
			//����
			Random r = new Random();
			while(true) {
				try {
					Thread.sleep(r.nextInt(200));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//200���ڵ�����ʱ��
				
				int saletype = t.nextInt(10);//����ʮ���ڵ��������
				int salenum = r.nextInt(5)+1;//���������ڵ��������
				
					//idΪx�������̳߳�������ĳ��CD������
					//lg.info("saledThread"+id+" Try Sale "+cc.cdlist[saletype].name+" "+salenum+"\n");
					System.out.print("saledThread"+id+" Try Sale "+cc.cdlist[saletype].name+" "+salenum+"\n");
					//Ҫ���CD�Ŀ�治��
					if(cc.cdlist[saletype].num<salenum) {
						//���ѡ��Ⱥ��Ƿ���
						if(r.nextBoolean()) {
							cc.sale(salenum, id,saletype,gt);
						}else {
							//lg.info("saledThread"+id+" give up"+" "+cc.cdlist[saletype].name+"\n");
							System.out.print("saledThread"+id+" give up"+" "+cc.cdlist[saletype].name+"\n");
						}
					}else {
						//�����㣬���Թ���
						cc.sale(salenum, id,saletype,gt);
					}
					
				}
		}
		
}

//������
//�����ǵ��ţ���˲�����CD���ϵķ�ʽͬ��
class RentThread extends Thread{
	CD_pro []cdrentlist;
	//���ֲ�ͬ���߳�
	static int ids;
	int id;
	Logger lg;
	//���캯��
	RentThread(CD_pro[] cd,Logger lg){
		this.cdrentlist = cd;
		this.setDaemon(true);//һֱ����ѭ��״̬���߳�һ��Ҫ����Ϊdaemon�������߳��޷�����
		id = ++ids;
		this.lg = lg;
	}

	@Override
	public void run() {
		//����
		Random t = new Random();
		//ʱ��
		Random r = new Random();
		while(true) {
			//����ʱ��Ϊ200ms���ڵ������
			try {
				Thread.sleep(r.nextInt(200));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//200���ڵ�����ʱ��
			
			int renttype = t.nextInt(10)+1;//����ʮ���ڵ��������
			//��ȡ��ͬ������
			synchronized(cdrentlist[renttype-1]) {
				//idΪx�������̳߳�������ĳ��CD������
				//lg.info("RentThread"+id+" Try Rent "+cdrentlist[renttype-1].name+"\n");
				System.out.print("RentThread"+id+" Try Rent "+cdrentlist[renttype-1].name+"\n");
				//Ҫ���CD�Ŀ�治��
				if(cdrentlist[renttype-1].num==0) {
					//���ѡ��Ⱥ��Ƿ���
					if(r.nextBoolean()) {
						cdrentlist[renttype-1].rent(id,cdrentlist[renttype-1],lg);
					}else {
						//lg.info("RentThread"+id+" give up"+" "+cdrentlist[renttype-1].name+"\n");
						System.out.print("RentThread"+id+" give up"+" "+cdrentlist[renttype-1].name+"\n");
					}
				}else {
					//�����㣬�������
					cdrentlist[renttype-1].rent(id,cdrentlist[renttype-1],lg);
				}
				
			}
		}
	}
}

//���������̵߳�����������
class ControlThread_pro extends Thread{
		//����CD
		CD_pro []cd_saled;
		CD_pro []cdrentlist;
		CD_collect cc;
		Logger lg;
		Lock l;
		Condition getincondition;
		Condition salecondition;
		
		ControlThread_pro(CD_collect cc,CD_pro []cdrentlist,Logger lg,Lock l,Condition getincondition,Condition salecondition){
			this.cc = cc; 
			this.cdrentlist =cdrentlist;
			this.lg = lg;
			this.l = l;
			this.salecondition = salecondition;
			this.getincondition = getincondition;
		}
		
		@Override
		//ȥ�����������е��߳�
		public void run() {
			//ֱ�����������߳�
			GetInThread_pro gt= new GetInThread_pro(cc,l,getincondition,salecondition);
			gt.start();
			//�����������������ϵ������߳�
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			
			//������
			new RentThread(cdrentlist,lg).start();
			new RentThread(cdrentlist,lg).start();
			new RentThread(cdrentlist,lg).start();
			new RentThread(cdrentlist,lg).start();
			
			//�����߳�˯��
			//�����߳̽����������߳̽���
			try {
				Thread.sleep(120*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}


