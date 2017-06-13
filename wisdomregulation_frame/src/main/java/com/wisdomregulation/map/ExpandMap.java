package com.wisdomregulation.map;

import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.entityfile.EntityS_File;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据树
 * @author King2016s
 *
 */
public class ExpandMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 显示的名字
	 */
	private String name;
	/**
	 * 包含的一个实体
	 */
	private Base_Entity baseentity;
	/**
	 * 文件操作的类
	 */
	private EntityS_File entityvalue;
	private String id;
	private int viewtype;

	public int getViewtype() {
		return viewtype;
	}

	public ExpandMap setViewtype(int viewtype) {
		this.viewtype = viewtype;
		return this;
	}

	private List<ExpandMap> expandList=new ArrayList<ExpandMap>();
	
	public ExpandMap(String name) {
		super();
		this.name = name;
	}
	
	public EntityS_File getEntityvalue() {
		return entityvalue;
	}
	
	public Base_Entity getBaseentity() {
		return baseentity;
	}

	public ExpandMap setBaseentity(Base_Entity baseentity) {
		this.baseentity = baseentity;
		return this;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Integer.valueOf(name);
	}

	@Override
	public boolean equals(Object o) {
		if(this.name.equals((String)o)){
			return true;
		}
		else{
			return false;
		}
	}

	public ExpandMap setEntityvalue(EntityS_File entityvalue) {
		this.entityvalue = entityvalue;
		return this;
	}
	public ExpandMap get(int child){
		return expandList.get(child);
	}
	public int size() {
		// TODO Auto-generated method stub
		return expandList.size();
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getId() {
		return id;
	}

	public ExpandMap setId(String id) {
		this.id = id;
		return this;
	}

	public ExpandMap add(ExpandMap expandMap) {
		expandList.add(expandMap);
		return this;
	}
	public ExpandMap remove(int index) {
		expandList.remove(index);
		return this;
	}
	

}
