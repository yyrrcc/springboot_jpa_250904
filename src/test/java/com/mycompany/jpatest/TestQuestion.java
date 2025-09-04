package com.mycompany.jpatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.jpatest.entity.Questiontbl;
import com.mycompany.jpatest.repository.QuestionRepository;

@SpringBootTest
public class TestQuestion {
	
	// 0. dao 역할을 해줌 (mapper .xml 즉 sql문 없이도 동작하는 dao)
	
	// 1. sqlsession을 안 써도 됨 repos를 바로 가져옴
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	@DisplayName ("질문 등록 테스트")
	public void writeQuestion() {
		// sql문을 안 쓰고 db에 값 넣기
		Questiontbl question = new Questiontbl();
		question.setQtitle("두번째 질문 입니다.");
		question.setQcontent("코딩 공부 재밌나요?");
		
		// JpaRepository가 이미 sql문과 비슷한 메서드들을 가지고 있음 (find = select, delete, save = insert, update는 없음 = delete 후 save 하기) 
		questionRepository.save(question); // insert문 쿼리가 실행됨
		
	}

}
