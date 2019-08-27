package xyz.gray.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gray on 2019-08-27 上午 09:05
 */
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showFirstPage;
    private boolean showPrevious;
    private boolean showEndPage;
    private boolean showNext;
    private Integer page;
    private List<Integer> pages;
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.page = page;
        this.totalPage = totalPage;

        pages = new ArrayList<>();
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        showPrevious = page > 1;
        showNext = page < totalPage;
        showFirstPage = !pages.contains(1)  && showPrevious;
        showEndPage = !pages.contains(totalPage) && showNext;
    }
}
