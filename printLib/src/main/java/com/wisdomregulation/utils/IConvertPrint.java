package com.wisdomregulation.utils;

import com.wisdomregulation.data.entitybase.Base_Entity;

import java.util.List;

/**
 * 文书转换类
 */
public interface IConvertPrint {

    /**
     * 将bookentity转化为网络端的webobject
     * @param bookentity 用户app端打印展现用的实体类
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public  Object bookentity2webobject(Base_Entity bookentity) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    /**
     * 将网络端的webobject转化为bookentity
     * @param webobject  从web端解析到的实体类
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public  Base_Entity webobject2bookentity(Object webobject) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

    /**
     * list转化为打印类的string
     * @param list
     * @return
     */
    public String list2string(List list);

    /**
     * 打印类的string转成list
     * @param string
     * @param webobjectname
     * @return
     */
    public List stringlist(String string, String webobjectname);
}


