package com.xiaoy.model;

/**
 * ID Name ParentId ShortName LevelType CityCode ZipCode MergerName lng Lat Pinyin
 * 
 * @author XiaoY
 * @date: 2015年9月5日 下午8:20:44
 */
public class ZipModel {
	private String id;// 行政码
	private String name;// 全称
	private String parentId;// 上级行政码
	private String shortName;// 简称
	private String levelType;// 等级
	private String cityCode;// 城市Code
	private String zipCode;// 邮编
	private String mergerName;// 完整名称
	private String lng;// 经度
	private String lat;// 纬度
	private String pinyin;// 名称拼音

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMergerName() {
		return mergerName;
	}

	public void setMergerName(String mergerName) {
		this.mergerName = mergerName;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
