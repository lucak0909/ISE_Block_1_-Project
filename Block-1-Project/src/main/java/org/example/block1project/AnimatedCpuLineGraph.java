package org.example.block1project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class AnimatedCpuLineGraph extends Application {

    private XYChart.Series<Number, Number> cpuSeries;  // Series to hold CPU usage data
    private int timeInMilliseconds = 0;  // Track elapsed time in milliseconds
    private static final int MAX_TIME_RANGE = 10000;  // Show the last 10 "graph seconds"
    private static final int UPDATE_INTERVAL = 100;   // Update interval (100ms)
    private static final double TIME_SCALE = 0.7;     // Faster time scale (70% of real-time speed)

    @Override
    public void start(Stage primaryStage) {
        // Create the X and Y axes
        NumberAxis xAxis = new NumberAxis(0, MAX_TIME_RANGE / 1000, 1);  // Slowed down last 10 seconds (graph time)
        NumberAxis yAxis = new NumberAxis(0, 100, 10);  // Y-axis range between 0 and 100 (percentage)

        // Hide the axes and tick marks for a clean look
        xAxis.setVisible(false);
        yAxis.setVisible(false);

        // Create a LineChart to display CPU usage over time
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("CPU Usage Over Time");

        // Remove grid lines for a cleaner look
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);

        // Create a Series to hold the data
        cpuSeries = new XYChart.Series<>();
        cpuSeries.setName("CPU Usage");

        // Add the series to the LineChart
        lineChart.getData().add(cpuSeries);
        lineChart.setCreateSymbols(false);  // Disable symbols on data points (just the line)

        // Style the chart to remove background and make it cleaner
        lineChart.setStyle("-fx-background-color: transparent;");

        // Create a Scene and add it to the Stage
        Scene scene = new Scene(lineChart, 600, 400);
        primaryStage.setTitle("Animated CPU Usage Line Graph");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start a Timeline to update the CPU usage regularly (every 0.1 seconds for smooth transitions)
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(UPDATE_INTERVAL), event -> updateCpuUsage())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // Run indefinitely
        timeline.play();  // Start the animation
    }

    // Method to update the CPU usage and add data to the graph
    private void updateCpuUsage() {
        // Get CPU usage using OperatingSystemMXBean
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuUsage = osBean.getSystemCpuLoad() * 100;

        // Ensure CPU usage is between 0 and 100
        cpuUsage = Math.max(0, Math.min(100, cpuUsage));

        // Add the CPU usage to the series (with time on the X-axis, adjusted by TIME_SCALE)
        cpuSeries.getData().add(new XYChart.Data<>((timeInMilliseconds * TIME_SCALE / 1000.0), cpuUsage));

        // Increment the time counter
        timeInMilliseconds += UPDATE_INTERVAL;

        // Remove old data points to keep the X-axis range constant (last 10 seconds in graph time)
        if (timeInMilliseconds * TIME_SCALE > MAX_TIME_RANGE) {
            cpuSeries.getData().remove(0);  // Remove the oldest data point
        }

        // Update the X-axis range to keep showing the last 10 seconds (in graph time)
        ((NumberAxis) cpuSeries.getChart().getXAxis()).setLowerBound((timeInMilliseconds * TIME_SCALE - MAX_TIME_RANGE) / 1000.0);
        ((NumberAxis) cpuSeries.getChart().getXAxis()).setUpperBound((timeInMilliseconds * TIME_SCALE) / 1000.0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
