package com.mycompany.jpatest;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.jpatest.entity.Questiontbl;
import com.mycompany.jpatest.repository.QuestionRepository;

@SpringBootTest
public class TestUpdateQuestion {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	@DisplayName ("특정 질문 내용 업데이트 - sql 방식")
	public void updateQuestion() {
		int updateResult = questionRepository.updateQcontentByQnum("내용을 변경하였습니다!", 16L);
		Optional<Questiontbl> updateOptional = questionRepository.findById(16L);
		if(updateOptional.isPresent()) {
			Questiontbl questiontbl = updateOptional.get();
			System.out.println(questiontbl.getQnum());
			System.out.println(questiontbl.getQcontent());
		} else {
			System.out.println("조회되지 않는 질문입니다.");
		}
		
		

		questionRepository.updateNativeQcontentByQnum("내용 변경", 17L);
		Optional<Questiontbl> updateNativeOptional = questionRepository.findById(17L);
		if(updateNativeOptional.isPresent()) {
			Questiontbl questionNativetbl = updateNativeOptional.get();
			System.out.println(questionNativetbl.getQnum());
			System.out.println(questionNativetbl.getQcontent());
		} else {
			System.out.println("조회되지 않는 질문입니다.");
		}
	}
	
	
	

	
	@Test
	@DisplayName ("update문을 jpa 방법으로")
	@Transactional
	@Rollback(false)
	public void updateJpaQuestion() {
		Optional<Questiontbl> jpaOptional = questionRepository.findById(20L);
		Questiontbl jpaQuestion = jpaOptional.get();
		System.out.println(jpaQuestion.getQnum());
		System.out.println(jpaQuestion.getQcontent());
		
		jpaQuestion.setQcontent("내용 수정 2020!!!"); // 업데이트
		
		Optional<Questiontbl> jpaOptional2 = questionRepository.findById(20L);
		Questiontbl jpaQuestion2 = jpaOptional2.get();
		System.out.println(jpaQuestion2.getQnum());
		System.out.println(jpaQuestion2.getQcontent());
		// 테스트 끝나면 커밋 안하고 롤백을 하기 때문에 db에 반영은 안 됨 -> @rollback 애노테이션 이용
	}
	
	
	
	
}

