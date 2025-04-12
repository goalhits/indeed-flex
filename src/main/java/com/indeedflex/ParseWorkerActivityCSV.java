package com.indeedflex;

import com.opencsv.CSVWriter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.indeedflex.ConstantUtil.*;

public class ParseWorkerActivityCSV {

    public static void main(String[] args) {
        if(args.length < 2) {
            throw new InputMismatchException("Please provide source and result csv file names");
        }
        ParseWorkerActivityCSV parseWorkerActivityCSV = new ParseWorkerActivityCSV();
        parseWorkerActivityCSV.createCSVFile(args[0], args[1]);
    }

    public void createCSVFile(String sourceFile, String resultCSV) {
        List<WorkerActivity> inputList = prepareInputWorkerList(sourceFile);
        List<String[]> csvData = prepareResultCSVRows(inputList);
        try (CSVWriter writer = new CSVWriter(new FileWriter(resultCSV),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END)) {
            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<WorkerActivity> prepareInputWorkerList(String csvFile) {
        Function<String, WorkerActivity> mapToItem = (line) -> {
            String[] p = line.split(COMMA);
            WorkerActivity workerActivity = new WorkerActivity();
            workerActivity.setWorker(p[ZERO]);
            workerActivity.setEmployer(Integer.parseInt(p[ONE]));
            workerActivity.setRole(Integer.parseInt(p[TWO]));
            String date = p[THREE].replaceAll(SPACE, TEMPORAL);
            LocalDateTime localDateTime = LocalDateTime.parse(date);
            workerActivity.setDate(localDateTime);
            return workerActivity;
        };

        List<WorkerActivity> inputList = new ArrayList<>();
        try {
            File inputF = new File(csvFile);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {

        }
        return inputList;
    }



    public List<String[]> prepareResultCSVRows(List<WorkerActivity> inputList) {
        LocalDateTime reportingDateTime = LocalDateTime.of(REPORTING_YEAR,
                Month.DECEMBER, DAY_OF_MONTH, ZERO_HOUR, ZERO_MINUTE, ZERO_SECOND);
        List<String[]> csvData = new ArrayList<>();
        String[] header = {WORKER_HEADER, CONTINUITY_HEADER};
        csvData.add(header);
        inputList.stream().collect(
                Collectors.groupingBy(input -> input.getWorker()))
                .forEach((worker,workerActivities) -> {
                    WorkerActivity previousActivity = null;
                    int continuity = 0;
                    workerActivities.sort(Comparator.comparing(o -> o.getDate()));
                    for(WorkerActivity workerActivity : workerActivities) {
                        if(previousActivity != null) {
                            Period period = Period.between(previousActivity.getDate().toLocalDate(), workerActivity.getDate().toLocalDate());
                            if(period.getDays() > 6) {
                                continuity = 1;
                            } else if(!workerActivity.getEmployer().equals(previousActivity.getEmployer())) {
                                continuity = 1;
                            } else if(!workerActivity.getRole().equals(previousActivity.getRole())) {
                                continuity = 1;
                            } else {
                                continuity++;
                            }
                        } else {
                            continuity++;
                        }
                        previousActivity = workerActivity;
                    }
                    Period periodReporting = Period.between(workerActivities.get(workerActivities.size() - 1).getDate().toLocalDate(),
                            reportingDateTime.toLocalDate());
                    if(periodReporting.getDays() > 6) {
                        continuity = 0;
                    }
                    createCsvDataSimple(csvData,worker,continuity);
                });
        return csvData;
    }

    public void createCsvDataSimple(List<String[]> csvData,String worker,Integer continuity) {
        String[] record = {worker,String.valueOf(continuity)};
        csvData.add(record);
    }
}

