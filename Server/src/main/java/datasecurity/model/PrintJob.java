package datasecurity.model;

public class PrintJob {
    private int job;
    private String fileName;

    public PrintJob(int job, String fileName) {
        this.job = job;
        this.fileName = fileName;
    }

    public int getJob() {
        return job;
    }

    public String getFileName() {
      return fileName;
    }

}
