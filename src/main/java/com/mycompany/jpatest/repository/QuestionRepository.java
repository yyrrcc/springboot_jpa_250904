package com.mycompany.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.jpatest.entity.Questiontbl;

public interface QuestionRepository extends JpaRepository<Questiontbl, Long> {
	
	// 0. QuestionRepository : dao 클래스의 역할을 함
	// 1. JpaRepository 인터페이스를 상속 받아야 한다 
	// 2. T : 엔티티 클래스의 이름
	// 3. ID : 엔티티 클래스에서 기본키로 설정한 필드의 타입
}
