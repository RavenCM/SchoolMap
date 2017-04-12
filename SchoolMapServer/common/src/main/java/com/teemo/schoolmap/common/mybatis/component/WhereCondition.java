package com.teemo.schoolmap.common.mybatis.component;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/12 13:47
 * @version 1.0
 * @name schoolmap-server
 * @description 查询条件
 */
public class WhereCondition {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 比较操作
     */
    private String op;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "WhereCondition{" +
                "name='" + name + '\'' +
                ", op='" + op + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof WhereCondition)) return false;
        WhereCondition that = (WhereCondition) object;
        return getName().equals(that.getName()) && getOp().equals(that.getOp());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getOp().hashCode();
        return result;
    }
}
