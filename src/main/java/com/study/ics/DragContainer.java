package com.study.ics;

import javafx.scene.input.DataFormat;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DragContainer implements Serializable {
    private static final long serialVersionUID = -1458406119115196098L;
    public static final DataFormat AddNode =
            new DataFormat("application.Component.add");

    private final List<Pair<String, Object>> mDataPairs = new ArrayList<Pair<String, Object> >();

    public static final DataFormat Binding =
            new DataFormat("com.study.ics.filesystem.view.FileSystemBinding");

    public static final DataFormat Node =
//            new DataFormat("com.study.ics.filesystem.view.FileSystemNode");
    new DataFormat("com.study.ics.Component");


    public DragContainer () {
    }

    public void addData (String key, Object value) {
        mDataPairs.add(new Pair<String, Object>(key, value));
    }

    public <T> T getValue (String key) {

        for (Pair<String, Object> data: mDataPairs) {

            if (data.getKey().equals(key))
                return (T) data.getValue();

        }

        return null;
    }

    public List <Pair<String, Object> > getData () { return mDataPairs; }
}
