package com.david.tmall_springboot_2023.util;

import org.springframework.data.domain.Page;

import java.util.List;

public class Page4Navigator<T> {
    private Page pageFromJpa;// Page instance returned from JPA。
    private int navigatePages; //How many pages we want to show in page navigator. for example, if we show
    //pages like [3,4,5,6,7,8,9], the navigatePages is 7。
    private int totalPages; //the number of total pages。
    private Long totalElements; //total amount of elements。
    private int number; //PageNumber 页码。The number of current page; the number of the first page is 0。
    private int size; //the size of a page when this page is full。
    private int numberOfElements; //the number of elements currently on this page。
    private List<T> contents; //List of page content。
    private boolean first; //whether the current page is the first page。
    private boolean last; //whether the current page is the last page。
    private boolean hasPrevious; //是否有前一页。
    private boolean hasNext; //是否有后一页。
    private int[] navigatePageNums; //显示的页面数码。 比如 [3, 4, 5, 6, 7, 8, 9]

    /*
    Page 的实现类里面， 首页（即第一个页面）的 number = 0.
    Page4Navigator 里面，number 直接从 Page 里面取得，首页（即第一个页面）的 number = 0， 但是此时如果显示到页面上，对应的页码应该显示为 1。
    所以此时 navigatePageNums 数组的值应该是从至少为 1 开始的， 当然数组的下标都是从 0 开始的。
     */

    //Constructor with parameters.
    public Page4Navigator(Page<T> pageFromJpa, int navigatePages ){
        this.pageFromJpa = pageFromJpa;
        this.navigatePages = navigatePages;
        this.totalPages = pageFromJpa.getTotalPages();
        this.totalElements = pageFromJpa.getTotalElements();
        this.number = pageFromJpa.getNumber();
        this.size = pageFromJpa.getSize();
        this.numberOfElements = pageFromJpa.getNumberOfElements();
        this.contents = pageFromJpa.getContent();
        this.first = pageFromJpa.isFirst();
        this.last = pageFromJpa.isLast();
        this.hasNext = pageFromJpa.hasNext();

        calcNavigatePageNums();

    }

    private void calcNavigatePageNums(){

        int totalPages = getTotalPages(); //所有页面数目。
        System.out.println("总的页面数量 totalPage: " + totalPages);
        int number = getNumber(); //当前页面数。
        System.out.println("当前页面数码 number: " + number);
        int navigatePages = getNavigatePages();//显示的导航页面数目。
        System.out.println("导航页面数目 navigatePages : " + navigatePages);
        int[] pages = null;

        //总的页面数量 大于 导航页面数目
        if(totalPages >= navigatePages){
            pages = new int[navigatePages];

            int startNum = number - navigatePages/2;
            int endNum = number + navigatePages/2;

            if(startNum < 0){
                startNum = 0;
                pages[0] = startNum + 1;
                for (int index = 1; index < navigatePages; index ++){
                    pages[index] = pages[index-1] + 1;
                }
            } else if(endNum >= totalPages){
                endNum = totalPages;
                pages[navigatePages - 1] = endNum;

                for (int index = 2 ; index <= navigatePages; index ++){
                    pages[navigatePages - index] = pages[navigatePages - index + 1] - 1;
                }
            } else {
                startNum ++;
                for (int index = 0; index < navigatePages; index ++){
                    pages[index] = startNum ++;
                }
            }

        }else {
            pages = new int[totalPages];

            for(int index = 0; index < totalPages; index ++) {
                pages[index] = index + 1;
            }

        }

        this.navigatePageNums = pages;
        System.out.println("导航页面数组 : " );
        for (int index = 0; index < pages.length; index++){
            System.out.print(pages[index]);
        }
    }

    public Page getPageFromJpa() {
        return pageFromJpa;
    }

    public void setPageFromJpa(Page pageFromJpa) {
        this.pageFromJpa = pageFromJpa;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasnext() {
        return hasNext;
    }

    public void setHasnext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int[] getNavigatePageNums() {
        return navigatePageNums;
    }

    public void setNavigatePageNums(int[] navigatePageNums) {
        this.navigatePageNums = navigatePageNums;
    }
}
