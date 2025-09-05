package com.mycompany.jpatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.jpatest.entity.Questiontbl;

public interface QuestionRepository extends JpaRepository<Questiontbl, Long> {
	
	// 0. QuestionRepository : dao 클래스의 역할을 함
	// 1. JpaRepository 인터페이스를 상속 받아야 한다 
	// 2. T : 엔티티 클래스의 이름
	// 3. ID : 엔티티 클래스에서 기본키로 설정한 필드의 타입
	
	// 원하는 메서드가 없다면 여기서 만들고 실행하면 된다 (기본 제공 jpa는 한계가 있음)
	// delete 지운다 all 모든 걸 by 조건 Qtitle 제목
	// 메서드 이름은 무조건 카멜표기법으로 쓰기
	// (select로 값 먼저 찾고 delete 하는 순서임, 따라서 트랜젝션이 되어야 함)
	@Transactional
	public void deleteAllByQtitle(String qtitle); // 질문 제목으로 삭제, delete에서는 LIKE %% 조건 사용 안 됨 
	
	
	// 가장 최근 질문이 위로 오도록 정렬해 모든 레코드 반환 (이름을 외우거나 지피티 이용..)
	public List<Questiontbl> findAllByOrderByQdateDesc();
	
	// 두개의 조건이 일치하는 레코드 조회
	public Questiontbl findByQnumAndQtitle(Long qnum, String qtitle);
	
	// 제목이 정확히 일치하는 레코드 조회
	public List<Questiontbl> findAllByQtitle(String qtitle);
	
	// 특정 문자로 제목 레코드 조회 (like) + 최근 글이 위로 오도록 정렬
	public List<Questiontbl> findAllByQtitleLikeOrderByQdateDesc(String keyword);
	
}
