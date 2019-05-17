package com.cgy.hupu.bean;

import java.util.List;

/**
 * @author cgy
 * @description
 * @date 2019/5/17 14:00
 */
public class SearchResult {

    public int count;
    public int hasNextPage;
    public String search_title;
    public List<Search> data;
}
