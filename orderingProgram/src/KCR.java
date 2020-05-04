/* 
 ���� ppt�����δ� ���α׷� ���� ���� ���� �����ϴ� �������� ���� �� �� ���Ƽ� �������� ���� �����غ��� ��� ������ ���ؼ� ������ �غ��ҽ��ϴ�:) 
 cf.  �ʿ��� ������ �ּ��� ���ؼ� �κкκ� �����Ͽ����ϴ� 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.io.*;

public class KCR extends JFrame{
	JLabel product, amount,price, total_price;
	static JTable table; //�ϴ� ���̺�
	static JTextField amount_many, price_much, price_total_much;
	JButton add_order;
	JPanel info1, info2, info3, info4, info5;
	JComboBox combo;
	static String[][] content;
	int n = 0;
	
		public KCR(){
			
			setTitle("�ֹ� ���� ���α׷�");
			this.setLayout(new BorderLayout());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			product = new JLabel("��ǰ��");
			String[] productName = {"ġ��", "ĩ��", "��", "��Ǫ", "����"}; //�ܰ� ����: ġ�� 1000, ĩ�� 500, �� 2000, ��Ǫ 2000, ���� 500
			JComboBox combo = new JComboBox(productName);
			amount = new JLabel("����");
			JTextField amount_many = new JTextField(10);
			price = new JLabel("�ܰ�");
			JTextField price_much = new JTextField(10);
			JButton add_order = new JButton("�߰�");
			total_price = new JLabel("�հ� (��)");
			JTextField price_total_much = new JTextField(10);
		
			//table���ѱ� 
			String[] header = {"��ȣ","��ǰ��","����","�ܰ� (��)","�Ұ� (��)"};
			String[][] content = new String[5][5];
			DefaultTableModel model = new DefaultTableModel(header,0) { //ó������ �ֹ� �����ϱ� ����� ������
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			JTable table = new JTable(model);
			JScrollPane scrollPane = new JScrollPane(table);
			table.setFillsViewportHeight(true);
			
			info1 = new JPanel();
			info2 = new JPanel();
			info3 = new JPanel();
			info4 = new JPanel();
			info5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
			info1.add(product); 
			info1.add(combo); 
			info2.add(amount); 
			info2.add(amount_many); 
			info3.add(price); 
			info3.add(price_much);
			info4.add(add_order);
			info5.add(total_price);
			info5.add(price_total_much);
		
			JPanel p = new JPanel(new GridLayout(1,4));
			p.add(info1); p.add(info2); p.add(info3); p.add(info4);
			add(p,BorderLayout.NORTH);	
			add(scrollPane, BorderLayout.CENTER);
			add(info5, BorderLayout.SOUTH);
			
			setVisible(true);
			setBounds(100, 100, 700, 203);
			setResizable(false);
		
			//��� �ֹ����� �޴�
			JMenuBar menuBar = new JMenuBar();
			JMenu tabMenu = new JMenu("�ֹ�����");
			JMenuItem checkItem = new JMenuItem("�ֹ� ��� ��ȸ"); 
			JMenuItem saveItem = new JMenuItem("�ֹ� ����"); 
			JMenuItem terminateItem = new JMenuItem("����"); 
			
			setJMenuBar(menuBar); //�޴��ٸ� JFrame�� ����
			menuBar.add(tabMenu); //tabMenu�� �޴��ٿ� ����
			tabMenu.add(checkItem); //�޴� �������� tabMenu�� ����
			tabMenu.add(saveItem);
			tabMenu.addSeparator(); //�޴� �и��� �߰�
			tabMenu.add(terminateItem);
			
			/*
			<�ֹ����� �ǿ��� �� �޴� ����> : ������ �ֹ� ��� ��ȸ -> �ֹ� ���� -> ����� �׻� �����δ�
			 �ֳ��ϸ� ó���� txt�� ���� �ֹ��� �ֳ� "�ֹ� ��� ��ȸ"�� ���ؼ� Ȯ���ϰ�, �ֹ��� �߰��Ϸ��� "�߰�"�ϰ�, �߰��� ���� Ȯ���Ϸ��� "�ֹ� ����"�� �ϰ�, �������� ������
			 ���� �ֹ� ��� ��ȸ�� ���� �ʰ� �߰��� �ϴ� ���, ���� �̸� �ֹ� ���� ���� ���������� �𸣰� �� ������ 10�� �̻� �Ѿ���� �ִ� �� ������ ����
			 ���� �׻� "�ֹ� ��� ��ȸ" -> "�߰�" -> "�ֹ� ����" -> "����" �̷� ������ ���� ����ڰ� �� ���α׷��� ����Ѵٰ� ������ �����ϰ� ������
			cf. �̸� �־����� order.txt���Ͽ��� ������ 10�� �Ѱ� ����ڰ� �Ҵ����� �ʴ´ٰ� ������
			*/
			
			//�ֹ� ��� ��ȸ �����ϸ� �̸� ����� �ֹ� ��� ���÷���
			checkItem.addActionListener(new ActionListener() { 
				/* 
				 �߰��ϰ� �ֹ� ��� ��ȸ(order.txt display)�� ����ڰ� �� �ϴ� �ɷ� ������
				 �ֳ��ϸ� �ֹ� ��� ��ȸ�� ���� ���� "�̸� ����� txt"������ ���÷����ϴ� ���̱� ������ �߰� �� txt�� ������Ʈ�ϴ� ����  �ʱ� ������ �ٲٴ� �̻��� ��
				 ������ �߰��ϸ� ǥ�� ������Ʈ �Ǳ� ������ �ٽ� �ֹ� ��� ��ȸ�� Ȯ���� ���� ����
				 ���� �߰� �� ������ Ȯ���ϰ� ������ �ֹ� ���� �޴��� ���ؼ� txt������ ������Ʈ�� �� ���� -> �� �� ��ȸ ���� 
				 */
				public void actionPerformed(ActionEvent arg0) {
					try {
						//2byte�� ����
						FileReader fReader;
						fReader = new FileReader("file/order.txt"); //�����
						//������ �� ������ ����
						BufferedReader bReader = new BufferedReader(fReader);
						String[][] input = new String[5][5]; //�Է¹ް� �۰� �ٽ� �����
						String str = null;
						int ctrl = 0;
						
						DefaultTableModel model= (DefaultTableModel)table.getModel();
						
						//EOF�� null ��ȯ
						while((str = bReader.readLine()) != null) {
							if(ctrl == 1) {
								// �հ� ����
								String[] block2 = str.split(",");
								price_total_much.setText(block2[1]);
								ctrl = 0;
							} 
								else {
									if(str.contains(",")) {// Jtable�� �迭�� �Է��ϱ�: table  
										String[] block = str.split(",");
										model.insertRow(n, new Object[] {(n+1)+"",block[0],block[1],block[2],block[3]});
										table.updateUI();
										n++;
									}
									else ctrl = 1; //�հ� ���� �غ�
									}
							}
					
						bReader.close();
						fReader.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				});
			
				//��ǰ�� �����ϸ� �ܰ��� �����Ǿ��ְ�
				combo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox)e.getSource(); //�޺��ڽ� �˾Ƴ���
						int index = cb.getSelectedIndex(); //���õ� �������� �ε��� , 1st: 0
						if(index==0) price_much.setText("1000"); //ġ�� 1000��
						else if(index==1) price_much.setText("500"); //ĩ�� 500��
						else if(index==2) price_much.setText("2000"); //�� 2000��							
						else if(index==3) price_much.setText("2000"); //��Ǫ 2000��
						else price_much.setText("500"); //���� 500��	
 						}
				});
				//�߰� ��ư ������ ������Ʈ
				add_order.addActionListener(new ActionListener() {
					// ���� ������ 10���̹Ƿ� 10�� �̻��� �ֹ��� ���ؼ��� 10���� �ֹ� �ǰ� ��Ȳ ����
						public void actionPerformed(ActionEvent arg0) {
							// ���� �о�ͼ� �迭�� �����ؼ� �� -> 1. �̹� �ֹ� ��Ͽ� �߰� ��ǰ�� ���� ��: �� <=10 ����üũ  2. �ش� ��ǰ�� ó�� �߰��ϴ� ���� ��: ���� <=���� üũ
							String[][] inputupdate = new String[5][5]; 
							String which = null;
							// �ֹ� ��� ��ȸ �� ���̺� ���÷��̵� �ֹ� ���� �� ������Ʈ�� �迭�� �о����
							int rowNumber = table.getRowCount();
							int inputupdate_row = rowNumber;
							if(rowNumber==0) { //  1. order.txt empty  -> �߰������� ���̺� addrow
								//��ǰ�� �б�
								JComboBox cb = (JComboBox)combo; //�޺��ڽ� �˾Ƴ���
								int index = cb.getSelectedIndex(); //���õ� �������� �ε��� , 1st: 0
								if(index==0) which = "ġ��"; //ġ�� 
								else if(index==1) which = "ĩ��"; //ĩ�� 
								else if(index==2) which = "��"; //�� 						
								else if(index==3) which = "��Ǫ"; //��Ǫ 
								else which = "����"; //���� 
								//���� ���� <=10, �Ұ� ���, �հ� ������Ʈ
								try {
									String str = (String)amount_many.getText();
									int num = Integer.parseInt(str);
									String str1 = (String)price_much.getText();
									int cost = Integer.parseInt(str1);
									if (num>10)  num = 10;
									String[][] inputupdate_ = new String[1][5];
									inputupdate_[0][0] = "1";
									inputupdate_[0][1] = which;
									inputupdate_[0][2] = Integer.toString(num);
									inputupdate_[0][3] = str1;
									inputupdate_[0][4] = Integer.toString(num*cost);
									
									//���̺� �߰�
									DefaultTableModel model= new DefaultTableModel(inputupdate_, header);
									table.setModel(model);
									inputupdate_row = 1;
									price_total_much.setText(inputupdate_[0][4]);
									
									// �߰� ��ư ���� �� ��ǰ�� ���� �ܰ� �ʱ�ȭ
									combo.setSelectedIndex(0); //��ǰ�� �ʱ�ȭ
									amount_many.setText(""); // ���� �ʱ�ȭ
									price_much.setText(""); // �ܰ� �ʱ�ȭ
								} 
								catch (NumberFormatException e) {} 
								catch (Exception e) {}
							}
							else { // 2. order.txt not empty  -> inputupdate�� ���ؼ� ������ ������Ʈ, tableupdate, ������ addrow+inputupdate update
								for(int i=0; i<rowNumber; i++) { // �ֹ� ��� ��ȸ �� n�� �� ��ȣ 
									for(int j=0; j<5; j++) { // ��ȣ ��ǰ�� ���� �ܰ� �Ұ�
										inputupdate[i][j] = (String)table.getValueAt(i, j); //String���� �������ֱ� ���ؼ�
									}
								}
								int totalTemp = 0;
								for(int i=0; i<rowNumber; i++) {
									String temp = (String)(inputupdate[i][4]);
									totalTemp += Integer.parseInt(temp);
								}
								JComboBox cb = (JComboBox)combo; //�޺��ڽ� �˾Ƴ���
								int index = cb.getSelectedIndex(); //���õ� �������� �ε��� , 1st: 0
								if(index==0) which = "ġ��"; //ġ�� 
								else if(index==1) which = "ĩ��"; //ĩ�� 
								else if(index==2) which = "��"; //�� 						
								else if(index==3) which = "��Ǫ"; //��Ǫ 
								else which = "����"; //���� 
								String str = (String)amount_many.getText();
								int num = Integer.parseInt(str);
								String str1 = (String)price_much.getText();
								int cost = Integer.parseInt(str1);
								int where = 0;
								int exist =0; //�̹� �ֹ��� ��ǰ�� �߰� �ֹ��� ��� -> 1, ���ο� ��ǰ �ֹ��� ��� -> 0
								for(int i=0; i<rowNumber; i++) {
									if(which.equals(inputupdate[i][1])) 
										{exist=1; 
									     where=i;}
								}
								if(exist==1) { // �̹� �ֹ��� ��ǰ�� �߰� �ֹ��� ���: �� �� 10����������, ���̺� ����&�Ұ� ������Ʈ, �հ� ������Ʈ, �ʱ�ȭ
									
									// ���� ����
									int numNew = num;
									int numBef = Integer.parseInt(inputupdate[where][2]); 
									int totalCost = 0;
									int numTemp = 0;
									if((numNew+numBef)>10) {
										numTemp = 10;
										totalCost = 10*cost;
									} else {
										numTemp = num+Integer.parseInt(inputupdate[where][2]);
										totalCost = cost*numTemp;
									}
								
										// ���� ������Ʈ
									inputupdate[where][2] = Integer.toString(numTemp);
										// �Ұ� ������Ʈ
									inputupdate[where][4] = Integer.toString(numTemp*cost);
										// ���̺� ������Ʈ
									table.setValueAt(Integer.toString(numTemp), where, 2);
									table.setValueAt(Integer.toString(numTemp*cost), where, 4);
									table.updateUI();

									// �հ� ������Ʈ
									int totalTemp2=0;
									for(int i = 0; i< rowNumber; i++) {
										totalTemp2 += Integer.parseInt(inputupdate[i][4]);
									}
									price_total_much.setText(Integer.toString(totalTemp2));

									// �߰� ��ư ���� �� ��ǰ�� ���� �ܰ� �ʱ�ȭ
									combo.setSelectedIndex(0); //��ǰ�� �ʱ�ȭ
									amount_many.setText(""); // ���� �ʱ�ȭ
									price_much.setText(""); // �ܰ� �ʱ�ȭ
									exist = 0;
									
								}
								else { // ���ο� ��ǰ �ֹ��� ���: 10����������, addrow-���̺� �߰�-��ȣ=rowNumber+1, �հ� ������Ʈ, �ʱ�ȭ
									
									//���� ����
									if (num>10)  num = 10;
									
									//���̺� �߰�
									model.insertRow(rowNumber, new Object[] {Integer.toString(rowNumber+1), which, Integer.toString(num), str1, Integer.toString(num*cost)});
									
									//�հ� ������Ʈ
									price_total_much.setText(Integer.toString(num*cost+totalTemp));
									table.updateUI();
									
									// �߰� ��ư ���� �� ��ǰ�� ���� �ܰ� �ʱ�ȭ
									combo.setSelectedIndex(0); //��ǰ�� �ʱ�ȭ
									amount_many.setText(""); // ���� �ʱ�ȭ
									price_much.setText(""); // �ܰ� �ʱ�ȭ
							
								}
							}
						}
					});	
		
					
			//�ֹ� ���� �����ϸ�(�ֹ���ȸ -> �߰� -> �ֹ� ����) �޸��� �����ؼ� ����, �հ� ����
			saveItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// file �ʱ�ȭ -> ���̺� ���÷��� �� ��� �Է�
					File order = new File("file/order.txt");
					order.delete();
					File orderNew = new File("file/order.txt");
					
					// ���̺� �� ��������
					int rowNumber = table.getRowCount();
					String[][] toWrite = new String[rowNumber][4];
					for(int i=0; i<rowNumber; i++) { // �ֹ� ��� ��ȸ �� n�� �� ��ȣ 
						for(int j=0; j<4; j++) { // ��ȣ ��ǰ�� ���� �ܰ� �Ұ�
							toWrite[i][j] = (String)table.getValueAt(i, j+1); //String���� �������ֱ� ���ؼ�
						}
					}
					
					// ���̺� ���� ����
					try {
						FileWriter fw = new FileWriter(orderNew);
						// �ֹ� ��� ����
						for(int i =0 ;i<rowNumber; i++) {
							fw.write(toWrite[i][0]+","+toWrite[i][1]+","+toWrite[i][2]+","+toWrite[i][3]+"\n");
						}
						// �հ� ����
						String division = "---------------------";
						fw.write(division+"\n"+"�հ�,"+(String)price_total_much.getText());
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
				});
				
							
			//���� �����ϸ� ���α׷� ���� 
			terminateItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
	
	public static void main(String[] args) {
		KCR app = new KCR();
	}

}
