package tech.ya.hh.constant;

public enum Processes {
    MULTIPROCESS("MULTIPROCESS"),
    SINGLEPROCESS("SINGLEPROCESS");
    private final String process;

    Processes(String process)
    {
        this.process = process;

    }

    public String getProcess()
    {
        return process;
    }
}
