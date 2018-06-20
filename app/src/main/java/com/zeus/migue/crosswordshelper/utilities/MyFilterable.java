package com.zeus.migue.crosswordshelper.utilities;

/**
 * Created by 79812 on 11/06/2018.
 */

public interface MyFilterable<DataModel> {
    boolean passFilter(String filter);
    void updateContents(DataModel dataModel);
}
