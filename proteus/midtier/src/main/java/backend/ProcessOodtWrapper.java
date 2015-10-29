package backend;

import java.io.IOException;

/**
 * Created by stevenfrancus on 10/13/15.
 */
public class ProcessOodtWrapper extends GenericProcess implements AbstractOodtWrapper {
    private static final String OODT = FileConstants.OODT_PATH;
    public ProcessOodtWrapper() {
        super(OODT);
    }
    public void run() throws IOException {
        super.createProcess("start", true);
    }

    public void reset() throws IOException {
        final String RM = "rm",
                    RF_OPTIONAL_PARAMS = "-rf";
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/data/workflow"), RF_OPTIONAL_PARAMS);
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/filemgr/catalog"), RF_OPTIONAL_PARAMS);
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/solr/drat/data"), RF_OPTIONAL_PARAMS);
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/data/archive/*"), RF_OPTIONAL_PARAMS);
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/workflow/run/cas.workflow.pid"), null);
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/filemgr/run/cas.filemgr.pid"), null);
        initResetProcess(RM, FileConstants.buildDratSubdirectoryPath("/deploy/resmgr/run/cas.resmgr.pid"), null);
    }

    public void stop() throws IOException {
        super.createProcess("stop", true);
    }

    public boolean isRunning() {
        return true;
    }

    private void initResetProcess(String command, String path, String optionalParams) throws IOException {
        boolean hasOptionalParams = optionalParams != null;
        ProcessBuilder process = (hasOptionalParams) ? new ProcessBuilder(command, optionalParams, path) :
                new ProcessBuilder(command, path);
        process.environment().putAll(Utils.getEnvironment());
        process.redirectErrorStream(true);
        process.start();
    }
}
