package com.zeus.migue.crosswordshelper.utilities;

import java.util.List;

/**
 * Created by 79812 on 11/06/2018.
 */

public interface FilterableAdapterModelContract<DataModel extends MyFilterable> {
    void filterResults(String filter);
    int getItemCount();
    List<DataModel> getCurrentData();
    void addItem(DataModel item);
    void deleteItem(int position);
    void updateItem(DataModel newItem, int position);
}
