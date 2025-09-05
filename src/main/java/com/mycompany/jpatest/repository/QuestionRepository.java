package com.mycompany.jpatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	
	// findBy 와 findAllBy 의 차이
	// findAllBy : 반환값이 여러 개. 반환타입 리스트
	// findBy : 실제 반환값이 리스트면 List로 반환되긴 함
	
	
	// JPA-SQL문 직접 쓰기 ( * 못 써서 테이블이름(여기선 엔티티이름) 적고 네이밍 해준 후 그걸 가지고 와야 함, ? 대신 : 사용)
	// Query 애노테이션, 엔티티 테이블 이름, * 사용 안됨, : 세미콜론 및 Param 애노테이션 
	@Query ("SELECT q FROM Questiontbl q WHERE q.qnum = :qnum")
	public Questiontbl findQuestionByQnum(@Param("qnum") Long qnum);

	// JPA-SQL문 직접 쓰기 - LIKE 사용 시
	@Query ("SELECT q FROM Questiontbl q WHERE q.qtitle LIKE %:qtitle%")
	public List<Questiontbl> findAllQuestionByQtitle(@Param("qtitle") String qtitle);

	// JPA-SQL문 직접 쓰기 - 부등호
	@Query ("SELECT q FROM Questiontbl q WHERE q.qnum >= :number")
	public List<Questiontbl> findAllQuestionByQnumber(@Param("number") Long number);

	
	// Native SQL문 (오리지널, 테이블은 db테이블 이름으로, * 사용 가능, : 사용은 똑같음, value와 nativeQuery 값 두개)
	@Query (value = "SELECT * FROM jpaquestiontbl WHERE qnum = :qnum", nativeQuery = true)
	public Questiontbl findQuestionNativeByQnum(@Param("qnum") Long qnum);	
	
	
	
	
	// 기타 JPA 문법 : exists - 해당 레코드 존재 여부 확인(qnum이 존재하는 번호면 true 반환)
	public boolean existsByQnum(Long qnum);
	
	// 기타 JPA 문법 : GreaterThan - 초과, GreaterThanEqual - 이상(>=)
	public List<Questiontbl> findByQnumGreaterThanEqual(Long qnum);
}
