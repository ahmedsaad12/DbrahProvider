package com.apps.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class StatisticsDataModel extends StatusResponse implements Serializable {
    private StatisticsModel data;

    public StatisticsModel getData() {
        return data;
    }
}
