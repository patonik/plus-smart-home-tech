package ru.yandex.practicum.infra.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.infra.analyzer.processing.HubEventProcessor;
import ru.yandex.practicum.infra.analyzer.processing.SnapshotProcessor;

import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class AnalyzerApp {
    private static Thread hubEventThread;
    private static Thread snapshotThread;

    public static void main(String[] args) {
        // Start the Spring application context
        ConfigurableApplicationContext context = SpringApplication.run(AnalyzerApp.class, args);
        log.info("AnalyzerApp started.");

        // Initialize and start the HubEventProcessor
        HubEventProcessor hubEventProcessor = context.getBean(HubEventProcessor.class);
        hubEventThread = new Thread(hubEventProcessor, "HubEventHandlerThread");
        hubEventThread.start();
        log.info("HubEventProcessor thread started.");

        // Initialize and start the SnapshotProcessor
        SnapshotProcessor snapshotProcessor = context.getBean(SnapshotProcessor.class);
        snapshotThread = new Thread(snapshotProcessor::start, "SnapshotProcessorThread");
        snapshotThread.start();
        log.info("SnapshotProcessor thread started.");

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(AnalyzerApp::shutdown));
    }

    /**
     * Clean up and stop all threads gracefully during shutdown.
     */
    @PreDestroy
    public static void shutdown() {
        log.info("Shutting down AnalyzerApp...");

        // Interrupt and join HubEventProcessor thread
        if (hubEventThread != null) {
            hubEventThread.interrupt();
            try {
                hubEventThread.join();
                log.info("HubEventProcessor thread stopped.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted while waiting for HubEventProcessor thread to stop.", e);
            }
        }

        // Interrupt and join SnapshotProcessor thread
        if (snapshotThread != null) {
            snapshotThread.interrupt();
            try {
                snapshotThread.join();
                log.info("SnapshotProcessor thread stopped.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted while waiting for SnapshotProcessor thread to stop.", e);
            }
        }

        log.info("AnalyzerApp shutdown complete.");
    }
}
