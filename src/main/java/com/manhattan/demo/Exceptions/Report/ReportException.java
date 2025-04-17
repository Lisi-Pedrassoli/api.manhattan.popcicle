package com.manhattan.demo.Exceptions.Report;

public class ReportException extends RuntimeException {
    public ReportException() {
        super("Não foi possível gerar o relatório");
    }

    public ReportException(String message) {
        super(message);
    }
}
