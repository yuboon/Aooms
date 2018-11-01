package net.aooms.core.record;

import java.util.List;

/**
 * Record数据集
 * Created by 风象南(yuboon) on 2018/8/23
 */
public class PagingRecord {

    private int page;
    private int limit;
    private List<? extends Record> list;
    private int currentTotal;
    private int total;
    private int totalPage;
    private boolean isPaging;

    public PagingRecord(){}

    public PagingRecord(int page, int limit, List<? extends Record> list, int total, boolean isPaging) {
        this.page = page;
        this.limit = limit;
        this.list = list;
        this.total = total;
        this.currentTotal = list.size();
        this.isPaging = isPaging;

        if(limit > 0){
            int mod = total % limit;
            this.totalPage = (mod == 0? total / limit : total / limit + 1);
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<? extends Record> getList() {
        return list;
    }

    public void setList(List<Record> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean isPaging() {
        return isPaging;
    }

    public void setPaging(boolean paging) {
        isPaging = paging;
    }

    public int getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(int currentTotal) {
        this.currentTotal = currentTotal;
    }
}
