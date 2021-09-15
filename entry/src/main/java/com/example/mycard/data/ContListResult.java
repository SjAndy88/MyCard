package com.example.mycard.data;

import java.util.ArrayList;

public class ContListResult extends BaseInfo {
    private ArrayList<ContObject> contList;

    public ArrayList<ContObject> getContList() {
        return contList;
    }

    public void setContList(ArrayList<ContObject> contList) {
        this.contList = contList;
    }
}
