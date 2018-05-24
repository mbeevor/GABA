package com.bignerdranch.android.gaba;

import java.util.List;

/**
 * Created by Matthew on 22/05/2018.
 */

public interface AsyncTaskListener {

    void onTaskComplete(List<Recipe> list);
}
