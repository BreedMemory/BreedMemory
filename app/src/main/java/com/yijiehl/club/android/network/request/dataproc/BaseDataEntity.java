/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: BaseDataEntity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.request.dataproc;

import android.text.TextUtils;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: BaseDataEntity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 谌珂 <br/>
 */
public abstract class BaseDataEntity {

    /** 必填, 一次只能对同一类数据进行操作，如用户，讨论组/群组，讨论组/群组成员，常用联系人等 */
    protected String dataModel = this.getDataModle();

    /** 必填, create新增，update修改，delete删除，cancel取消 */
    protected String opType = getOperateType().getName();

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    public String getDataModel() {
        return dataModel;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    /**
     * 描 述：设置数据模型<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     */
    protected abstract String getDataModle();

    /**
     * 描 述：设置操作类型<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     */
    protected abstract OperateType getOperateType();

    public enum OperateType {
        CREATE(0, "create"),
        UPDATE(1, "update"),
        DELETE(2, "delete"),
        CANCEL(3, "cancel"),
        UPLOAD(4, "upload");

        private int value;
        private String name;

        OperateType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static BaseDataEntity.OperateType setValue(String name) {
            OperateType[] arr$ = values();
            for (OperateType c : arr$) {
                if (TextUtils.equals(c.getName(), name)) {
                    return c;
                }
            }
            return CANCEL;
        }
    }
}
