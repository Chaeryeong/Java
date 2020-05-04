/* 
 과제 ppt만으로는 프로그램 동작 순서 등을 이해하는 가짓수가 여럿 될 것 같아서 논리적으로 나름 생각해보고 몇가지 가정을 더해서 과제를 해보았습니다:) 
 cf.  필요한 설명은 주석을 통해서 부분부분 설명하였습니다 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.io.*;

public class KCR extends JFrame{
	JLabel product, amount,price, total_price;
	static JTable table; //하단 테이블
	static JTextField amount_many, price_much, price_total_much;
	JButton add_order;
	JPanel info1, info2, info3, info4, info5;
	JComboBox combo;
	static String[][] content;
	int n = 0;
	
		public KCR(){
			
			setTitle("주문 관리 프로그램");
			this.setLayout(new BorderLayout());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			product = new JLabel("상품명");
			String[] productName = {"치약", "칫솔", "비누", "샴푸", "휴지"}; //단가 지정: 치약 1000, 칫솔 500, 비누 2000, 샴푸 2000, 휴지 500
			JComboBox combo = new JComboBox(productName);
			amount = new JLabel("수량");
			JTextField amount_many = new JTextField(10);
			price = new JLabel("단가");
			JTextField price_much = new JTextField(10);
			JButton add_order = new JButton("추가");
			total_price = new JLabel("합계 (원)");
			JTextField price_total_much = new JTextField(10);
		
			//table만둘기 
			String[] header = {"번호","상품명","수량","단가 (원)","소계 (원)"};
			String[][] content = new String[5][5];
			DefaultTableModel model = new DefaultTableModel(header,0) { //처음에는 주문 없으니까 헤더만 나오게
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
		
			//상단 주문관리 메뉴
			JMenuBar menuBar = new JMenuBar();
			JMenu tabMenu = new JMenu("주문관리");
			JMenuItem checkItem = new JMenuItem("주문 목록 조회"); 
			JMenuItem saveItem = new JMenuItem("주문 저장"); 
			JMenuItem terminateItem = new JMenuItem("종료"); 
			
			setJMenuBar(menuBar); //메뉴바를 JFrame에 부착
			menuBar.add(tabMenu); //tabMenu를 메뉴바에 부착
			tabMenu.add(checkItem); //메뉴 아이템을 tabMenu에 부착
			tabMenu.add(saveItem);
			tabMenu.addSeparator(); //메뉴 분리선 추가
			tabMenu.add(terminateItem);
			
			/*
			<주문관리 탭에서 각 메뉴 실행> : 순서는 주문 목록 조회 -> 주문 저장 -> 종료로 항상 움직인다
			 왜냐하면 처음에 txt로 들어온 주문이 있나 "주문 목록 조회"를 통해서 확인하고, 주문을 추가하려면 "추가"하고, 추가한 것을 확정하려면 "주문 저장"을 하고, 마지막에 종료함
			 만약 주문 목록 조회를 하지 않고 추가를 하는 경우, 현재 미리 주문 들어온 것이 무엇인지도 모르고 총 수량이 10개 이상 넘어갈수도 있는 등 문제가 생김
			 따라서 항상 "주문 목록 조회" -> "추가" -> "주문 저장" -> "종료" 이런 순서를 따라 사용자가 이 프로그램을 사용한다고 과제를 이해하고 가정함
			cf. 미리 주어지는 order.txt파일에는 수량을 10개 넘개 사용자가 할당하지 않는다고 가정함
			*/
			
			//주문 목록 조회 선택하면 미리 저장된 주문 목록 디스플레이
			checkItem.addActionListener(new ActionListener() { 
				/* 
				 추가하고 주문 목록 조회(order.txt display)는 사용자가 안 하는 걸로 가정함
				 왜냐하면 주문 목록 조회는 과제 설명에 "미리 저장된 txt"파일을 디스플레이하는 것이기 때문에 추가 시 txt를 업데이트하는 것은  초기 저장을 바꾸는 이상한 일
				 어차피 추가하면 표가 업데이트 되기 때문에 다시 주문 목록 조회를 확인할 일이 없음
				 만약 추가 된 내용을 확정하고 싶으면 주문 저장 메뉴를 통해서 txt파일을 업데이트할 수 있음 -> 그 후 조회 가능 
				 */
				public void actionPerformed(ActionEvent arg0) {
					try {
						//2byte씩 읽음
						FileReader fReader;
						fReader = new FileReader("file/order.txt"); //상대경로
						//파일을 행 단위로 읽음
						BufferedReader bReader = new BufferedReader(fReader);
						String[][] input = new String[5][5]; //입력받고 작게 다시 만들기
						String str = null;
						int ctrl = 0;
						
						DefaultTableModel model= (DefaultTableModel)table.getModel();
						
						//EOF면 null 반환
						while((str = bReader.readLine()) != null) {
							if(ctrl == 1) {
								// 합계 설정
								String[] block2 = str.split(",");
								price_total_much.setText(block2[1]);
								ctrl = 0;
							} 
								else {
									if(str.contains(",")) {// Jtable에 배열값 입력하기: table  
										String[] block = str.split(",");
										model.insertRow(n, new Object[] {(n+1)+"",block[0],block[1],block[2],block[3]});
										table.updateUI();
										n++;
									}
									else ctrl = 1; //합계 받을 준비
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
			
				//상품명 선택하면 단가는 지정되어있게
				combo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox)e.getSource(); //콤보박스 알아내기
						int index = cb.getSelectedIndex(); //선택된 아이템의 인덱스 , 1st: 0
						if(index==0) price_much.setText("1000"); //치약 1000원
						else if(index==1) price_much.setText("500"); //칫솔 500원
						else if(index==2) price_much.setText("2000"); //비누 2000원							
						else if(index==3) price_much.setText("2000"); //샴푸 2000원
						else price_much.setText("500"); //휴지 500원	
 						}
				});
				//추가 버튼 누르면 업데이트
				add_order.addActionListener(new ActionListener() {
					// 수량 제한은 10개이므로 10개 이상의 주문에 대해서는 10개로 주문 되게 상황 설정
						public void actionPerformed(ActionEvent arg0) {
							// 모델을 읽어와서 배열에 저장해서 비교 -> 1. 이미 주문 목록에 추가 상품이 있을 때: 합 <=10 수량체크  2. 해당 상품을 처음 추가하는 것일 때: 수량 <=수량 체크
							String[][] inputupdate = new String[5][5]; 
							String which = null;
							// 주문 목록 조회 후 테이블에 디스플레이된 주문 내역 모델 오브젝트를 배열에 읽어오기
							int rowNumber = table.getRowCount();
							int inputupdate_row = rowNumber;
							if(rowNumber==0) { //  1. order.txt empty  -> 추가들어오면 테이블에 addrow
								//상품명 읽기
								JComboBox cb = (JComboBox)combo; //콤보박스 알아내기
								int index = cb.getSelectedIndex(); //선택된 아이템의 인덱스 , 1st: 0
								if(index==0) which = "치약"; //치약 
								else if(index==1) which = "칫솔"; //칫솔 
								else if(index==2) which = "비누"; //비누 						
								else if(index==3) which = "샴푸"; //샴푸 
								else which = "휴지"; //휴지 
								//수량 제한 <=10, 소계 계산, 합계 업데이트
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
									
									//테이블에 추가
									DefaultTableModel model= new DefaultTableModel(inputupdate_, header);
									table.setModel(model);
									inputupdate_row = 1;
									price_total_much.setText(inputupdate_[0][4]);
									
									// 추가 버튼 누른 후 상품명 수량 단가 초기화
									combo.setSelectedIndex(0); //상품명 초기화
									amount_many.setText(""); // 수량 초기화
									price_much.setText(""); // 단가 초기화
								} 
								catch (NumberFormatException e) {} 
								catch (Exception e) {}
							}
							else { // 2. order.txt not empty  -> inputupdate에 비교해서 있으면 업데이트, tableupdate, 없으면 addrow+inputupdate update
								for(int i=0; i<rowNumber; i++) { // 주문 목록 조회 후 n은 행 번호 
									for(int j=0; j<5; j++) { // 번호 상품명 수량 단가 소계
										inputupdate[i][j] = (String)table.getValueAt(i, j); //String으로 저장해주기 위해서
									}
								}
								int totalTemp = 0;
								for(int i=0; i<rowNumber; i++) {
									String temp = (String)(inputupdate[i][4]);
									totalTemp += Integer.parseInt(temp);
								}
								JComboBox cb = (JComboBox)combo; //콤보박스 알아내기
								int index = cb.getSelectedIndex(); //선택된 아이템의 인덱스 , 1st: 0
								if(index==0) which = "치약"; //치약 
								else if(index==1) which = "칫솔"; //칫솔 
								else if(index==2) which = "비누"; //비누 						
								else if(index==3) which = "샴푸"; //샴푸 
								else which = "휴지"; //휴지 
								String str = (String)amount_many.getText();
								int num = Integer.parseInt(str);
								String str1 = (String)price_much.getText();
								int cost = Integer.parseInt(str1);
								int where = 0;
								int exist =0; //이미 주문된 상품을 추가 주문할 경우 -> 1, 새로운 상품 주문일 경우 -> 0
								for(int i=0; i<rowNumber; i++) {
									if(which.equals(inputupdate[i][1])) 
										{exist=1; 
									     where=i;}
								}
								if(exist==1) { // 이미 주문된 상품을 추가 주문할 경우: 총 합 10개이하제한, 테이블에 수량&소계 업데이트, 합계 업데이트, 초기화
									
									// 수량 제한
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
								
										// 수량 업데이트
									inputupdate[where][2] = Integer.toString(numTemp);
										// 소계 업데이트
									inputupdate[where][4] = Integer.toString(numTemp*cost);
										// 테이블 업데이트
									table.setValueAt(Integer.toString(numTemp), where, 2);
									table.setValueAt(Integer.toString(numTemp*cost), where, 4);
									table.updateUI();

									// 합계 업데이트
									int totalTemp2=0;
									for(int i = 0; i< rowNumber; i++) {
										totalTemp2 += Integer.parseInt(inputupdate[i][4]);
									}
									price_total_much.setText(Integer.toString(totalTemp2));

									// 추가 버튼 누른 후 상품명 수량 단가 초기화
									combo.setSelectedIndex(0); //상품명 초기화
									amount_many.setText(""); // 수량 초기화
									price_much.setText(""); // 단가 초기화
									exist = 0;
									
								}
								else { // 새로운 상품 주문일 경우: 10개이하제한, addrow-테이블에 추가-번호=rowNumber+1, 합계 업데이트, 초기화
									
									//수량 제한
									if (num>10)  num = 10;
									
									//테이블 추가
									model.insertRow(rowNumber, new Object[] {Integer.toString(rowNumber+1), which, Integer.toString(num), str1, Integer.toString(num*cost)});
									
									//합계 업데이트
									price_total_much.setText(Integer.toString(num*cost+totalTemp));
									table.updateUI();
									
									// 추가 버튼 누른 후 상품명 수량 단가 초기화
									combo.setSelectedIndex(0); //상품명 초기화
									amount_many.setText(""); // 수량 초기화
									price_much.setText(""); // 단가 초기화
							
								}
							}
						}
					});	
		
					
			//주문 저장 선택하면(주문죄회 -> 추가 -> 주문 저장) 콤마로 구분해서 저장, 합계 저장
			saveItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// file 초기화 -> 테이블에 디스플레이 된 목록 입력
					File order = new File("file/order.txt");
					order.delete();
					File orderNew = new File("file/order.txt");
					
					// 테이블 모델 가져오기
					int rowNumber = table.getRowCount();
					String[][] toWrite = new String[rowNumber][4];
					for(int i=0; i<rowNumber; i++) { // 주문 목록 조회 후 n은 행 번호 
						for(int j=0; j<4; j++) { // 번호 상품명 수량 단가 소계
							toWrite[i][j] = (String)table.getValueAt(i, j+1); //String으로 저장해주기 위해서
						}
					}
					
					// 테이블 내용 쓰기
					try {
						FileWriter fw = new FileWriter(orderNew);
						// 주문 목록 쓰기
						for(int i =0 ;i<rowNumber; i++) {
							fw.write(toWrite[i][0]+","+toWrite[i][1]+","+toWrite[i][2]+","+toWrite[i][3]+"\n");
						}
						// 합계 쓰기
						String division = "---------------------";
						fw.write(division+"\n"+"합계,"+(String)price_total_much.getText());
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
				});
				
							
			//종료 선택하면 프로그램 종료 
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
