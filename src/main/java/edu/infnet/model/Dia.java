package edu.infnet.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Dia {

	private LocalDate date;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private BigDecimal adjClose;
	private BigDecimal volume;
	
	public Dia(LocalDate date, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close,
			BigDecimal adjClose, BigDecimal volume) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adjClose = adjClose;
		this.volume = volume;
	}
	public Dia() {
	}
	
	public Dia(LocalDate date, BigDecimal close) {
		this.date = date;
		this.close = close;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getAdjClose() {
		return adjClose;
	}
	public void setAdjClose(BigDecimal adjClose) {
		this.adjClose = adjClose;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return "Grafico [date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close
				+ ", adjClose=" + adjClose + ", volume=" + volume + "]";
	}
	
}
