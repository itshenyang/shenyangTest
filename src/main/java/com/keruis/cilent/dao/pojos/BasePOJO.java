package com.keruis.cilent.dao.pojos;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Huan on 2017/2/8.
 */
@Alias("basepojo")
public class BasePOJO implements Serializable {
    private String tokenuserid;

    private String tag;

    public String getTokenuserid() {
        return tokenuserid;
    }

    public void setTokenuserid(String tokenuserid) {
        this.tokenuserid = tokenuserid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "BasePOJO{" +
                "tokenuserid='" + tokenuserid + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}