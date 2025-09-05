package com.mycompany.jpatest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 1. 엔티티 클래스 (dto로도 사용 가능 하지만 안전성 생각하면 dto 따로 만드는 게 좋음)
@Table (name = "jpaquestiontbl") // 2. 실제로 매핑 될 데이터베이스 테이블 이름 (생략하면 클래스 이름으로 만들어짐)
@SequenceGenerator( // 5. 시퀀스 애노테이션
		name = "QUESTION_SEQ_GENERATOR", // JPA 내부 시퀀스 이름 (기존 시퀀스 이름 + generator 붙이는 게 관례)
		sequenceName = "QUESTION_SEQ", // DB의 실제 시퀀스 이름
		initialValue = 1, // 시퀀스 시작값
		allocationSize = 1 // 시퀀스 증가치
		)
@Data // 6. dto로도 사용 할 예정이라 롬복 추가
@AllArgsConstructor
@NoArgsConstructor
public class Questiontbl {
	
	// 0. 필드값은 무조건 소문자로 쓰기 왜냐면 카멜표기법 사용하면 db에 _ 언더바 이용해서 값이 들어가짐
	
	@Id // 3. 해당 테이블의 해당 필드를 기본키로 설정하겠다는 뜻
	@Column (name = "qnum") // 8. 실제로 db 테이블에 만들어질 필드 이름 (똑같으면 생략 가능)
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ_GENERATOR")
	// 7. 기본키 전략 (db에 따라 자동증가가 다르기 때문에 알맞게 전략 세우기. 시퀀스를 만들어줬으니 기본키에 설정해주는 것임. 오라클을 쓰고 있으니까 sequence인거고 mysql은 auto increment .. 이런 식으로 정하는 거. identity는 둘 다 사용 가능)
	private Long qnum; // 질문 번호 (기본키, 시퀀스로 자동 증가), null 값을 설정할 수 있는 Integer로 설정, 길이 때문에 Long
	// 4. 오라클은 develop에서 직접 시퀀스를 만들어야 함
	
	// 9. 컬럼의 속성 지정해주기
	@Column (name = "qtitle", length = 20, nullable = false)
	private String qtitle; // 질문 제목
	
	@Column (name = "qcontent", length = 200, nullable = false)
	private String qcontent; // 질문 내용
	
	@CreationTimestamp // 10. 자동으로 입력 (sysdate)
	private LocalDateTime qdate; // 질문 최초 등록일 (sysdate)
	
	@UpdateTimestamp // 11. 자동으로 입력됨
	private LocalDateTime udate; // 질문 수정된 시간 (sysdate)
}
