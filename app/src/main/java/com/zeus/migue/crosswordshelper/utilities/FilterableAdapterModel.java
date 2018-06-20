package com.zeus.migue.crosswordshelper.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 79812 on 11/06/2018.
 */

public abstract class FilterableAdapterModel<DataModel extends MyFilterable<DataModel>> implements FilterableAdapterModelContract<DataModel>{
    protected List<DataModel> currentData;
    private final List<DataModel> originalData;

    public FilterableAdapterModel(List<DataModel> originalData){
        this.originalData = originalData;
        currentData = new ArrayList<>(originalData);
    }

    @Override
    public List<DataModel> getCurrentData(){
        return currentData;
    }

    @Override
    public void addItem(DataModel item) {
        currentData.add(item);
        originalData.add(item);

        //Notify changes to adapter through presenter
    }

    @Override
    public void deleteItem(int position) {
        //Removed of original list using the object (Word)
        originalData.remove(currentData.get(position));
        //Removed of the current data using the current position gotten from the click listener
        currentData.remove(position);

        //Notify changes to adapter through presenter
    }

    @Override
    public void updateItem(DataModel newItem, int position) {
        //This won't work on the originalList because we don't know the real position of the item that is being modded
        //Could create a search method through id, or a way to modify the item in the original list through setters
        //Something like implement a method in DataModel to copy from a src DataModel the content of the new DataModel
        //currentData.set(position, newItem);
        //currentData.get(position).updateContents(newItem);
        //Notify changes to adapter through presenter

        deleteItem(position);
        originalData.add(0, newItem);
        currentData.add(0, newItem);
    }

    @Override
    public int getItemCount() {
        return currentData.size();
    }

    @Override
    public void filterResults(String filter){
        currentData = new ArrayList<>();
        if (filter.isEmpty()){
            currentData.addAll(originalData);
            return;
        }
        for (DataModel dataModel : originalData){
            if (dataModel.passFilter(filter)){
                currentData.add(dataModel);
            }
        }
    }
}
