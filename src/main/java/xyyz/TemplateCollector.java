package xyyz;

import org.ow2.jasmine.probe.JasmineIndicator;
import org.ow2.jasmine.probe.JasmineIndicatorValue;
import org.ow2.jasmine.probe.JasmineSingleNumberResult;
import org.ow2.jasmine.probe.JasmineSingleResult;
import org.ow2.jasmine.probe.collector.JasmineCollectorException;
import org.ow2.jasmine.probe.collectors.JCollector;

import java.util.Map;

/**
 */
public class TemplateCollector extends JCollector {

    /**
     * Pass stopped to true if the probe using this collector is stopped.
     */
    private boolean stopped = false;

    /**
     * Constructor
     * @param indicator definition
     */
    public TemplateCollector(String probeId, JasmineIndicator indicator, int period) {
        super(probeId, indicator, period);
        // specific part of JasmineIndicator (property list)
        Map<String, String> props = indicator.getProperties();
    }

    // ----------------------------------------------------------
    // JasmineCollector implementation
    // ----------------------------------------------------------

    public long count = 1;

    /**
     * Retrieve the last results for this indicator
     * This method return a List of results in case indicator represents
     * actually a list of value
     * @return List of JasmineIndicatorValue
     */
    @Override
    public JasmineIndicatorValue getLastResult() throws JasmineCollectorException {
        logger.debug("");
        JasmineIndicatorValue jiv = new JasmineIndicatorValue();
        // TODO change all this
        jiv.setName(indicator.getName());
        JasmineSingleResult jsr = new JasmineSingleNumberResult();
        jsr.setName("Template Result");
        jsr.setTimestamp(System.currentTimeMillis());
        jsr.setValue(count++);
        jiv.addValue(jsr);

        return jiv;
    }

    /**
     * Stop polling
     */
    public void stopPolling() {
        logger.debug("Stop polling");
        stopped = true;
    }

    /**
     * Restart polling
     */
    public void startPolling() {
        logger.debug("Start polling");
        stopped = false;
    }

}
