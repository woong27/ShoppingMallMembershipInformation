package musinsa;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MusinsaExample {
	public static final int INPUT = 1, OUTPUT = 2, SEARCH = 3, UPDATE = 4, DELETE = 5, SORT = 6, STATS = 7, EXIT = 8;
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		//데이터베이스 연결
		DBConnection dbConn = new DBConnection();
		
		dbConn.connect();
		boolean flag = false;
		while (!flag) {
			int num = displayMenu();
			switch (num) {
			case INPUT:
				musinsaInput();
				break;
			case OUTPUT:
				musinsaOutput();
				break;
			case SEARCH:
				musinsaSearch();
				break;
			case UPDATE:
				musinsaUpdate();
				break;
			case DELETE:
				musinsaDelete();
				break;
			case SORT:
				musinsaSort();
				break;
			case STATS:
				musinsaStats();
				break;
			case EXIT:
				flag = true;
				break;
			default:
				System.out.println("1~8번에서 입력해주세요.");
				break;

			}
		}
		System.out.println("종료");

	}

	// 통계
	public static void musinsaStats() {
		List<Musinsa> list = new ArrayList<Musinsa>();
		try {
			// 검색 방식
			System.out.println("|1.TOP|2.bottom|");
			int type = sc.nextInt();
			boolean value = checkputPattern(String.valueOf(type), 4);
			if (!value) {
				return;
			}
			// 데이터베이스 연결
			DBConnection dbConn = new DBConnection();

			dbConn.connect();

			list = dbConn.selectMaxMin(type);
			if (list.size() <= 0) {
				System.out.println("검색한 회원 정보가 없습니다. " + list.size());
				return;
			}
			
			for (Musinsa musinsa : list) {
				System.out.println(musinsa);
			}
			dbConn.close();
		} catch (InputMismatchException e) {
			System.out.println("InputMismatchException error" + e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println("database Stats error" + e.getMessage());
		}

	}

	// 정렬
	public static void musinsaSort() {
		List<Musinsa> list = new ArrayList<Musinsa>();
		try {
			// 데이터 베이스 연결
			DBConnection dbConn = new DBConnection();
			
			dbConn.connect();
			// 정렬방식 입력
			System.out.println("|1.회원번호|2.이름|3.총구매가격|");
			int type = sc.nextInt();
			// 패턴번호 입력
			boolean value = checkputPattern(String.valueOf(type), 4);
			if (!value) {
				return;
			}
			list = dbConn.selectOrderBy(type);
			if (list.size() <= 0) {
				System.out.println("보여줄 리스트가 없습니다." + list.size());
				return;
			}
			// 리스트 내용 보여주기
			for (Musinsa musinsa : list) {
				System.out.println(musinsa);
			}
			dbConn.close();
		} catch (Exception e) {
			System.out.println("database error" + e.getMessage());
		}
		return;

	}

	// 삭제
	public static void musinsaDelete() {
		try {
			System.out.println("삭제할 회원번호 입력 >>");
			String no = sc.nextLine();

			// 패턴검색
			boolean value = checkputPattern(no, 1);
			if (!value) {
				return;
			}
			// 데이터베이스 연결
			DBConnection dbConn = new DBConnection();

			dbConn.connect();
			int deleteReturnValue = dbConn.delete(no);
			if (deleteReturnValue == -1) {
				System.out.println("삭제 실패입니다." + deleteReturnValue);
				return;
			}
			if (deleteReturnValue == 0) {
				System.out.println("삭제할 번호가 없습니다." + deleteReturnValue);
			} else {
				System.out.println("삭제 성공 리턴값 = " + deleteReturnValue);
			}
			dbConn.close();
		} catch (InputMismatchException e) {
			System.out.println("입력 타입이 맞지않습니다.");
			return;
		}

	}

	// 수정
	public static void musinsaUpdate() {
		List<Musinsa> list = new ArrayList<Musinsa>();
		try {
			// 수정할 회원번호 입력
			System.out.println("수정할 회원번호 입력 >>");
			String no = sc.nextLine();

			boolean value = checkputPattern(no, 1);
			if (!value) {
				return;
			}
			// 데이터베이스 연결
			DBConnection dbConn = new DBConnection();
			
			dbConn.connect();
			list = dbConn.selectSearch(no, 1);
			if (list.size() <= 0) {
				System.out.println("입력된 정보가 없습니다.");
				return;
			}
			// 리스트의 내용을 보여준다
			for (Musinsa musinsa : list) {
				System.out.println(musinsa);
			}

			// 수정할 리스트를 보여줘야 한다
			Musinsa imsiMusinsa = list.get(0);
			System.out.println("의류 구매가격 >>");
			int clothing = sc.nextInt();
			value = checkputPattern(String.valueOf(clothing), 3);
			if (!value)
				return;
			imsiMusinsa.setClothing(clothing);
			

			System.out.println("액세서리 구매가격 >>");
			int acc = sc.nextInt();
			value = checkputPattern(String.valueOf(acc), 3);
			if (!value)
				return;
			imsiMusinsa.setAcc(acc);
			

			System.out.println("스포츠용품 구매가격 >>");
			int sport = sc.nextInt();
			value = checkputPattern(String.valueOf(sport), 3);
			if (!value)
				return;
			imsiMusinsa.setSport(sport);
			

			System.out.println("애완용품 구매가격 >>");
			int pet = sc.nextInt();
			value = checkputPattern(String.valueOf(pet), 3);
			if (!value)
				return;
			imsiMusinsa.setPet(pet);
			
//			imsiMusinsa.calTotal();
			
			
			//데이터베이스 수정할 부분을 UPDATE 진행
			int returnUpdateValue = dbConn.update(imsiMusinsa);
			if(returnUpdateValue == -1) {
				System.out.println("수정할 정보가 없습니다.");
				return;
			}
			System.out.println("수정이 완료되었습니다.");
			dbConn.close();

		} catch (InputMismatchException e) {
			System.out.println("입력 타입이 맞지않습니다.");
			sc.nextLine();
			return;
		}catch(Exception e) {
			System.out.println("Exception error");
			return;
		}

	}

	// 검색
	public static void musinsaSearch() {
		List<Musinsa> list = new ArrayList<Musinsa>();
		try {
			System.out.println("검색할 회원이름 입력 >>");
			String name = sc.nextLine();
			// 패턴 검색
			boolean value = checkputPattern(name, 2);
			if (!value) {
				return;
			}
			//데이터베이스 연결
			DBConnection dbConn = new DBConnection();
			
			dbConn.connect();
			list = dbConn.selectSearch(name, 2);
			if (list.size() <= 0) {
				System.out.println("검색할 정보가 없습니다." + list.size());
				return;
			}
			// 리스트 내용을 보여준다
			for (Musinsa musinsa : list) {
				System.out.println(musinsa);
			}
			dbConn.close();

		} catch (InputMismatchException e) {
			System.out.println("입력 타입이 맞지않아요" + e.getStackTrace());
			return;
		} catch (Exception e) {
			System.out.println("database search error " + e.getStackTrace());
		}
	}

	// 출력
	public static void musinsaOutput() {
		List<Musinsa> list = new ArrayList<Musinsa>();
		try {
			DBConnection dbConn = new DBConnection();
			// 데이터베이스 연결
			dbConn.connect();
			list = dbConn.select();
			if (list.size() <= 0) {
				System.out.println("보여줄 리스트가 없습니다." + list.size());
				return;
			}
			// 리스트내용을 보여준다
			for (Musinsa musinsa : list) {
				System.out.println(musinsa);
			}
			dbConn.close();
		} catch (Exception e) {
			System.out.println("Exception error" + e.getMessage());
		}
		return;

	}

	// 입력
	public static void musinsaInput() {
		try {
			System.out.println("회원번호 입력 >>");
			String no = sc.nextLine();
			boolean value = checkputPattern(no, 1);
			if (!value) {
				return;
			}
			// 이름
			System.out.println("이름 입력 >>");
			String name = sc.nextLine();
			value = checkputPattern(name, 2);
			if (!value) {
				return;
			}
			// 나이
			System.out.println("나이 입력 >>");
			int age = sc.nextInt();
			value = checkputPattern(String.valueOf(age), 6);
			if (!value) {
				return;
			}
			// 전화번호
			sc.nextLine();
			System.out.println("전화번호 입력 >>");
			String phone = sc.nextLine();
			value = checkputPattern(phone, 7);
			if (!value) {
				return;
			}
			// 의류구매가격
			System.out.println("의류 거래금액 입력 >>");
			int clothing = sc.nextInt();
			value = checkputPattern(String.valueOf(clothing), 3);
			if (!value) {
				return;
			}
			// 액세서리 구매가격
			System.out.println("액세서리 거래금액 입력 >>");
			int acc = sc.nextInt();
			value = checkputPattern(String.valueOf(acc), 3);
			if (!value) {
				return;
			}
			// 스포츠 구매가격
			System.out.println("스포츠 거래금액 입력 >>");
			int sport = sc.nextInt();
			value = checkputPattern(String.valueOf(sport), 3);
			if (!value) {
				return;
			}
			// 애완용품 구매가격
			System.out.println("애완용품 거래금액 입력 >>");
			int pet = sc.nextInt();
			value = checkputPattern(String.valueOf(pet), 3);
			if (!value) {
				return;
			}

			// 데이터 베이스 입력
			Musinsa musinsa = new Musinsa(no, name, age, phone, clothing, acc, sport, pet);

			DBConnection dbConn = new DBConnection();
			// 데이터베이스 연결
			dbConn.connect();
			int insertReturnValue = dbConn.insert(musinsa);
			if (insertReturnValue == -1) {
				System.out.println("삽입 실패입니다.");
			} else {
				System.out.println("삽입성공 리턴값 = " + insertReturnValue);
			}

		} catch (InputMismatchException e) {
			System.out.println("입력타입이 맞지 않습니다.");
			return;
		} finally {
			sc.nextLine();
		}

	}

	// 메뉴
	public static int displayMenu() {
		int num = -1;
		try {
			System.out.println("|1.입력|2.출력|3.검색|4.수정|5.삭제|6.정렬|7.통계|8.종료|\n입력>>");
			num = sc.nextInt();
			// 패턴검색
			String pattern = "^[1-8]*$";
			boolean regex = Pattern.matches(pattern, String.valueOf(num));
			System.out.println(regex);
		} catch (InputMismatchException e) {
			System.out.println("잘못 입력되었습니다.");
			num = -1;
		} finally {
			sc.nextLine();
		}
		return num;
	}

	// 문자패턴 검색
	public static boolean checkputPattern(String data, int patternType) {
		String pattern = null;
		boolean regex = true;
		String message = null;
		switch (patternType) {
		case 1:
			pattern = "^[0-9][0-9][0-9][0-9]$";
			message = "회원번호 재입력 (예 : 0000~9999)";
			break;
		case 2:
			pattern = "^[가-힣]{2,4}$";
			message = "이름 재입력 : (예 : 홍길동)";
			break;
		case 3:
			pattern = "^[0-9]{1,20}$";
			message = "거래금액 재입력";
			break;
		case 4:
			pattern = "^[1-3]$";
			message = "정렬타입 재입력요망(예 : 1~3)";
			break;
		case 5:
			pattern = "^[1-2]$";
			message = "통계타입 재입력요망 (예 : 1~2)";
			break;
		case 6:
			pattern = "^[0-9]{1,2}$";
			message = "나이 재입력 요망 (예 : 29)";
			break;
		case 7:
			pattern = "^010-[0-9]{4}-[0-9]{4}$";
			message = "정화번호 재입력 요망(예 : 010-1111-1111)";
			break;
		}

		regex = Pattern.matches(pattern, data);
		if (patternType == 3) {

		}
		if (!regex) {
			System.out.println(message);
			return false;

		}
		return regex;
	}

}
