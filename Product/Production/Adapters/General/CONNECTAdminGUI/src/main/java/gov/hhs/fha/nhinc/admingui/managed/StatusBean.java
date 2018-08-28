/*
 * Copyright (c) 2009-2018, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.admingui.managed;

import gov.hhs.fha.nhinc.admingui.model.AvailableService;
import gov.hhs.fha.nhinc.admingui.model.StatusSnapshot;
import gov.hhs.fha.nhinc.admingui.services.PingService;
import gov.hhs.fha.nhinc.admingui.services.impl.DashboardStatusServiceImpl;
import gov.hhs.fha.nhinc.admingui.services.impl.PingServiceImpl;
import gov.hhs.fha.nhinc.event.model.EventCount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author jassmit
 */
@ManagedBean(name = "statusBean")
@ViewScoped
public class StatusBean {

    private final DashboardStatusServiceImpl sService = new DashboardStatusServiceImpl();
    private final PingService pingService = new PingServiceImpl();
    private List<AvailableService> services = new ArrayList<>();
    private HorizontalBarChartModel eventBarChart;
    private PieChartModel eventPieChart;
    private StatusSnapshot snapshot;

    @PostConstruct
    public void initServices() {
        snapshot = sService.getStatus();
        services = pingService.buildServices();
        initBarChart();
        initPieChart();
    }

    public void refresh(boolean refreshMessages) {
        snapshot = sService.getStatus(refreshMessages);
        if (refreshMessages) {
            initPieChart();
            initBarChart();
        }
    }

    public String getOs() {
        return snapshot.getOs();
    }

    public String getJava() {
        return snapshot.getJavaVersion();
    }

    public String getMemory() {
        return snapshot.getMemory();
    }

    public String getAppServer() {
        return snapshot.getServerVersion();
    }

    private Map<String, Long> getInboundEventCounts() {

        HashMap<String, Long> map = new HashMap<>();
        for (EventCount event : snapshot.getEvents().values()) {
            map.put(event.getEvent(), event.getInbound());
        }
        return map;
    }

    public long getTotalInboundRequests() {
        long total = 0;
        for (EventCount event : snapshot.getEvents().values()) {
            total += event.getInbound();
        }
        return total;
    }

    private Map<String, Long> getOutboundEventCounts() {

        HashMap<String, Long> map = new HashMap<>();
        for (EventCount event : snapshot.getEvents().values()) {
            map.put(event.getEvent(), event.getOutbound());
        }
        return map;
    }

    public long getTotalOutboundRequests() {
        long total = 0;
        for (EventCount event : snapshot.getEvents().values()) {
            total += event.getOutbound();
        }
        return total;
    }

    public List<AvailableService> getServices() {
        return services;
    }

    public HorizontalBarChartModel getEventBarChart() {
        return eventBarChart;
    }

    public PieChartModel getEventPieChart() {
        if (eventPieChart != null) {
            refreshPieChart();
        }
        return eventPieChart;
    }

    private void refreshPieChart() {

        for ( EventCount event : snapshot.getEvents().values()) {
            eventPieChart.getData().put(event.getEvent(), event.getTotal());
        }

        eventPieChart.setFill(false);
        eventPieChart.setSeriesColors("10253F, CC0000, 33D6FF, FFCC00, 98FB98, FFA500, 00CCFF");
        eventPieChart.setShowDataLabels(true);
        eventPieChart.setSliceMargin(5);
        eventPieChart.setLegendPosition("se");
    }

    private void initBarChart() {
        eventBarChart = new HorizontalBarChartModel();

        ChartSeries inboundSeries = createChart("Inbound", getInboundEventCounts());
        ChartSeries outboundSeries = createChart("Outbound", getOutboundEventCounts());

        eventBarChart.addSeries(inboundSeries);
        eventBarChart.addSeries(outboundSeries);
        eventBarChart.setSeriesColors("10253F, CC0000");
        eventBarChart.setStacked(true);
        eventBarChart.setLegendPosition("se");
    }

    private static ChartSeries createChart(String label, Map<String, Long> eventCounts) {
        ChartSeries series = new ChartSeries();
        series.setLabel(label);
        for (Entry<String, Long> serviceEntry : eventCounts.entrySet()) {
            series.set(serviceEntry.getKey(), serviceEntry.getValue());
        }
        return series;
    }

    private void initPieChart() {
        eventPieChart = new PieChartModel();
        refreshPieChart();
    }

}
