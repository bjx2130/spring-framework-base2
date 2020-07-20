package com.sino.vo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;


public class Product {
	private int pdId;
	private String pdTitle;
	private String pdIntro;
	private String pdNotes;
	private int pd_expir;
	private int pdIndex;
	private String pdCode;
	private String productId;
	private String pcode;
	private String belong;
	private int pdType;
	private String pdTypeExt;
	private BigDecimal pdPrice;
	
	private String pdName;
	private String pdNameSms;
	
	@TableField(exist = false)
	private int ctgId;
	
	public int getPdId() {
		return pdId;
	}
	public void setPdId(int pdId) {
		this.pdId = pdId;
	}
	public int getCtgId() {
		return ctgId;
	}
	public void setCtgId(int ctgId) {
		this.ctgId = ctgId;
	}
	public String getPdTitle() {
		return pdTitle;
	}
	public void setPdTitle(String pdTitle) {
		this.pdTitle = pdTitle;
	}
	public String getPdIntro() {
		return pdIntro;
	}
	public void setPdIntro(String pdIntro) {
		this.pdIntro = pdIntro;
	}
	public String getPdNotes() {
		return pdNotes;
	}
	public void setPdNotes(String pdNotes) {
		this.pdNotes = pdNotes;
	}
	public int getPd_expir() {
		return pd_expir;
	}
	public void setPd_expir(int pd_expir) {
		this.pd_expir = pd_expir;
	}
	public int getPdIndex() {
		return pdIndex;
	}
	public void setPdIndex(int pdIndex) {
		this.pdIndex = pdIndex;
	}
	public String getPdCode() {
		return pdCode;
	}
	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public int getPdType() {
		return pdType;
	}
	public void setPdType(int pdType) {
		this.pdType = pdType;
	}
	public String getPdTypeExt() {
		return pdTypeExt;
	}
	public void setPdTypeExt(String pdTypeExt) {
		this.pdTypeExt = pdTypeExt;
	}
	public BigDecimal getPdPrice() {
		return pdPrice;
	}
	public void setPdPrice(BigDecimal pdPrice) {
		this.pdPrice = pdPrice;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public String getPdNameSms() {
		return pdNameSms;
	}
	public void setPdNameSms(String pdNameSms) {
		this.pdNameSms = pdNameSms;
	}
	
	
	
}
