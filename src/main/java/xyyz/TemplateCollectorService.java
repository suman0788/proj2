package xyyz;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.apache.felix.ipojo.annotations.Validate;

import org.ow2.jasmine.probe.JasmineCollectorInfo;
import org.ow2.jasmine.probe.JasmineIndicator;
import org.ow2.jasmine.probe.JasmineProbe;
import org.ow2.jasmine.probe.JasminePropertyInfo;
import org.ow2.jasmine.probe.collector.JasmineCollector;
import org.ow2.jasmine.probe.collector.JasmineCollectorException;
import org.ow2.jasmine.probe.collectors.JCollector;
import org.ow2.jasmine.probe.collectors.JCollectorService;
import org.ow2.jasmine.probe.probemanager.ProbeManager;

import java.util.ArrayList;

/**
 */
@Component(name="TemplateCollectorService")
@Provides
public class TemplateCollectorService extends JCollectorService {

    /**
     * Description
     */
    public final static String SHORT_DESC =
            "Short description";
    public final static String LONG_DESC =
            "Long Description";

    /**
     * Sources
     */
    public final static String SOURCE_DESC =
            "Description of source indicators, if any.";
    public final static int SOURCE_MIN = 0;
    public final static int SOURCE_MAX = 0;

    /**
     * Properties description
     */
    public final static String PROP_ONE = "one";
    public final static String PROP_ONE_DESC =
            "Description of property one";
    public final static String PROP_TWO = "two";
    public final static String PROP_TWO_DESC =
            "Description of property two";

    // --------------------------------------------------------------------------------
    // ipojo management
    // --------------------------------------------------------------------------------

    @ServiceProperty(name="indicator.type", value="123")
    private String indicatorType;

    @Validate
    public void start() {
        // Build the CollectorInfo
        collectorInfo = new JasmineCollectorInfo();
        collectorInfo.setType(indicatorType);
        collectorInfo.setShortDesc(SHORT_DESC);
        collectorInfo.setLongDesc(LONG_DESC);
        collectorInfo.setSourceDesc(SOURCE_DESC);
        collectorInfo.setSourceMin(SOURCE_MIN);
        collectorInfo.setSourceMax(SOURCE_MAX);
        // init the list of properties
        // Set true for properties that are mandatory.
        properties = new ArrayList<JasminePropertyInfo>();
        properties.add(new JasminePropertyInfo(PROP_ONE, PROP_ONE_DESC, true));
        properties.add(new JasminePropertyInfo(PROP_TWO, PROP_TWO_DESC, false));
        collectorInfo.setPropertyInfos(properties);
    }

    @Invalidate
    public void stop() {
        // Mark as removed all the collectors corresponding to this indicator-type
        removeCollectors(null, null);
    }

    @Requires
    ProbeManager probeManager = null;

    // --------------------------------------------------------------------------------
    // JasmineCollectorService implementation
    // --------------------------------------------------------------------------------

    /**
     * Create a Collector for this JasmineIndicator.
     * Called by the JasmineProbeManager when first starting a probe
     * (at the probe creation).
     * @param indicator contains the indicator's parameters.
     * @param probe the probe that gets the collector
     * @return the Collector
     * @throws JasmineCollectorException operation failed
     */
    @Override
    public synchronized JasmineCollector getCollector(JasmineIndicator indicator, JasmineProbe probe) throws JasmineCollectorException {
        JCollector collector = new TemplateCollector(probe.getId(), indicator, probe.getPeriod());
        collector.setProbeManager(probeManager);
        addCollector(indicator.getName(), collector);
        return collector;
    }

}
