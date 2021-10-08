package org.wayne.api.entity;


import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class RespPageBeanQ {
    private long total;
    private List<?> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
