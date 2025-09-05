package com.mycompany.jpatest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.jpatest.dto.QuestionDto;
import com.mycompany.jpatest.entity.Questiontbl;
import com.mycompany.jpatest.repository.QuestionRepository;

@SpringBootTest
@TestMethodOrder (MethodOrderer.OrderAnnotation.class) // 10. 메서드 순서 정할 수도 있음 (추천 안함)
public class TestQuestion {
	
	// 0. dao 역할을 해줌 (mapper .xml 즉 sql문 없이도 동작하는 dao)
	
	// 1. sqlsession을 안 써도 됨 repos를 바로 가져옴
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	@DisplayName ("질문 등록 테스트")
	@Order(2) // 11. 애노테이션에 괄호로 순서 정하기
	public void writeQuestion() {
		// 방법1. sql문을 안 쓰고 db에 값 넣기
//		Questiontbl question = new Questiontbl();
//		question.setQtitle("두번째 질문 입니다.");
//		question.setQcontent("코딩 공부 재밌나요?");
		
		// JpaRepository가 이미 sql문과 비슷한 메서드들을 가지고 있음 (find = select, delete, save = insert, update는 없음 = delete 후 save 하기) 
//		questionRepository.save(question); // insert문 쿼리가 실행됨
		
		
		
		// 방법2. dto 만들어서 해보기 -> questionRepository는 entity 애노테이션의 값만 가져올 수 있음 -> 변환 과정 필요
		// JPA 메서드는 엔티티 객체만 인자값으로 받을 수 있다!!
		QuestionDto questionDto = new QuestionDto();
		questionDto.setQtitle("김민지");
		questionDto.setQcontent("저는 김민지입니다.");
		Questiontbl question = questionDto.ChangeToEntity();
		questionRepository.save(question);
	}
	
	
	
	
	@Test
	@DisplayName ("질문 삭제 테스트")
	@Order(1)
	public void deleteQuestion() {
//		questionRepository.deleteAll(); // 모든 데이터 삭제 메서드
		
//		List<Questiontbl> questiontbls = new ArrayList<>(); // entity 리스트도 삭제 가능 (원하는 레코드만 삭제 가능)
//		questionRepository.deleteAll(questiontbls);
		
		questionRepository.deleteById(3L); // 기본키 이용해서 지우기, Long 타입은 숫자 뒤에 L 작성 해줘야 함
		
		// repos에서 만든 메서드 사용
		questionRepository.deleteAllByQtitle("홍길동");
	}
	
	
	
	
	@Test
	@DisplayName ("질문 조회 테스트")
	@Order(3)
	public void searchQuestion() {
		 List<Questiontbl> questionList = questionRepository.findAll(); // 모든 레코드 가져오기
		 for (Questiontbl questiontbl : questionList) {
			 System.out.println(questiontbl.getQnum());
			 System.out.println(questiontbl.getQtitle());
 	 		 System.out.println(questiontbl.getQcontent());
   			 System.out.println(questiontbl.getQdate());
			 System.out.println("-----------------------------");
		}
		 
		 List<Questiontbl> questionOrderList = questionRepository.findAllByOrderByQdateDesc(); // 최근 질문이 위로 오도록 정렬하여 출력
		 for (Questiontbl questiontbl : questionOrderList) {
			 System.out.println(questiontbl.getQnum());
			 System.out.println(questiontbl.getQtitle());
			 System.out.println(questiontbl.getQcontent());
			 System.out.println(questiontbl.getQdate());
			 System.out.println("-----------------------------");
		}		 
	}
	
	
	
	
	
	@Test
	@DisplayName ("특정 질문 검색")
	@Order(4)
	public void searchQuestionByField() {
		// 기본키 id를 이용해서 1개 또는 0개 record 불러옴
		// 이때 null 존재 할 수도 -> 반환타입 optional -> boolean isPresent() 로 확인
		Optional<Questiontbl> questionOption = questionRepository.findById(2L);
		if (questionOption.isPresent()) {
			Questiontbl question = questionOption.get(); // 어차피 값 1개니까 get메서드로 해당 값 가져오기
			System.out.println(question.getQnum());
			System.out.println(question.getQtitle());
		} else {
			System.out.println("해당 번호의 레코드는 존재하지 않습니다.");
		}
		
		// 2가지 조건이 일치한 결과를 가져와라 (예. 로그인 성공 시 아이디 비밀번호 일치)
		Questiontbl questionTwoOption = questionRepository.findByQnumAndQtitle(8L, "이수연");
		System.out.println(questionTwoOption);
		System.out.println("================================");
		
		// 제목이 정확히 일치하는 조건
		List<Questiontbl> questionQtitle = questionRepository.findAllByQtitle("김민지");
		for (Questiontbl questiontbl : questionQtitle) {
			System.out.println(questiontbl.getQtitle());
			System.out.println("**************************");
		}
		
		
		// 특정 문자로 제목 레코드 조회 (like) + 최근 글이 위로 오도록 정렬
		List<Questiontbl> questionLike = questionRepository.findAllByQtitleLikeOrderByQdateDesc("%질문%");
		for (Questiontbl questiontbl : questionLike) {
			System.out.println(questiontbl.getQnum());
			System.out.println(questiontbl.getQtitle());
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
		
		
		
		
//		// 직접 작성한 SQL 사용 @Query 사용
		Questiontbl questionSql = questionRepository.findQuestionByQnum(10L);
		System.out.println(questionSql.getQnum());
		System.out.println(questionSql.getQtitle());
		System.out.println("-----------");
		
		
		
		
		
		
		
	}
	

}
