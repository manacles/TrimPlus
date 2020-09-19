package com.tbs.trimplus.module.history.view;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.main.bean.Bible;

public interface IgetHistoryRecordView {
    /**
     * 获取历史记录
     */
    void getHistoryRecord(BaseList<Bible> bibleBaseList);
}
