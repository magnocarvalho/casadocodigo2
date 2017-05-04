package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.format.annotation.NumberFormat;

@Embeddable
public class Price {
	
	@Column(scale = 2)
	@NumberFormat(pattern="#,##0.00")
	private BigDecimal value;
	
	private BookType bookType;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

}
