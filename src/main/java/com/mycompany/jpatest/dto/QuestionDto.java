package com.mycompany.jpatest.dto;

import java.time.LocalDateTime;

import com.mycompany.jpatest.entity.Questiontbl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

	private Long qnum; 
	private String qtitle;
	private String qcontent;
	private LocalDateTime qdate;
	private LocalDateTime udate;
	
	// dto 객체에 들어온 값으로 entity 만들어서 반환 (dto -> entity)
	public Questiontbl ChangeToEntity() {
		Questiontbl question = new Questiontbl();
		question.setQtitle(this.qtitle);
		question.setQcontent(this.qcontent);
		return question;
	}
}
