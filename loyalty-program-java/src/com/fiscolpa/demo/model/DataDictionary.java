package com.fiscolpa.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class DataDictionary {
	
    @Id
    @Column(name = "dictionary_id")
    private Integer dictionaryId;
    
    private String dictionaryName;
    
    private String dictionaryValue;
    
    private String describes;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    


	public Integer getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getDictionaryValue() {
		return dictionaryValue;
	}

	public void setDictionaryValue(String dictionaryValue) {
		this.dictionaryValue = dictionaryValue;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
    
    public DataDictionary(){}
    
    public DataDictionary(String dictionaryName){
    	this.dictionaryName = dictionaryName;
    }
    
}