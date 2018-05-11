/**
 *
 */
package gov.hhs.fha.nhinc.adapter.deferred.queue;

import gov.hhs.fha.nhinc.properties.PropertyAccessException;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

@EnableScheduling
@Configuration
@ComponentScan(basePackages = { "gov.hhs.fha.nhinc" })
public class DeferredQueueScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(DeferredQueueScheduler.class);
    private static final String GATEWAY_PROPERTY_FILE = "gateway";
    private static final String DEFERRED_QUEUE_REFRESH_DURATION_PROPERTY = "DeferredQueueRefreshDuration";
    private static final int DEFERRED_QUEUE_REFRESH_DURATION_DEFAULT = 600; // (10 minutes)

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }


    @Bean
    @SuppressWarnings("unchecked")
    public ScheduledFuture<DeferredQueueTimerTask> deferredTaskManager(ThreadPoolTaskScheduler scheduler) {
        // 10 minutes by default.
        int intervalSeconds = DEFERRED_QUEUE_REFRESH_DURATION_DEFAULT;
        try {
            String sDuration = PropertyAccessor.getInstance().getProperty(GATEWAY_PROPERTY_FILE,
                DEFERRED_QUEUE_REFRESH_DURATION_PROPERTY);

            intervalSeconds = Integer.parseInt(sDuration);

        } catch (PropertyAccessException | NumberFormatException e) {
            LOG.error("Could not set interval rate. Defaulting to {} seconds by default. Error is : {}",
                DEFERRED_QUEUE_REFRESH_DURATION_DEFAULT, e.getMessage());
        }

        PeriodicTrigger periodicTrigger = new PeriodicTrigger(intervalSeconds, TimeUnit.SECONDS);
        periodicTrigger.setInitialDelay(intervalSeconds);

        return (ScheduledFuture<DeferredQueueTimerTask>) scheduler.schedule(new DeferredQueueTimerTask(),
            periodicTrigger);

    }
}