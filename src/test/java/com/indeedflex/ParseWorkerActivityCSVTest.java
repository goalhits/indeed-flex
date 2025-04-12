package com.indeedflex;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParseWorkerActivityCSVTest {

    String WORKER_ACTIVITY_TEST_CSV = "worker_activity_test.csv";
    ParseWorkerActivityCSV parseWorkerActivityCSV;

    @Before
    public void setup() {
        parseWorkerActivityCSV = new ParseWorkerActivityCSV();
    }

    @Test
    public void testPrepareWorkerList() {
        List<WorkerActivity> workerActivities = parseWorkerActivityCSV.prepareInputWorkerList(WORKER_ACTIVITY_TEST_CSV);
        assertEquals(workerActivities.size(), 8);
        assertEquals(workerActivities.get(workerActivities.size()-1).getWorker(), "456");
    }

    @Test
    public void testPrepareResultCSVRows() {
        List<WorkerActivity> workerActivities = parseWorkerActivityCSV.prepareInputWorkerList(WORKER_ACTIVITY_TEST_CSV);
        List<String[]> resultList = parseWorkerActivityCSV.prepareResultCSVRows(workerActivities);
        assertEquals(resultList.size(), 4);
        assertEquals(resultList.get(1)[1], "0");
    }

}
