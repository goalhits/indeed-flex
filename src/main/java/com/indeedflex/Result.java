package com.indeedflex;

public class Result {
    private String worker;
    private Integer result;

    Result(String worker,Integer result) {
        this.worker = worker;
        this.result = result;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
