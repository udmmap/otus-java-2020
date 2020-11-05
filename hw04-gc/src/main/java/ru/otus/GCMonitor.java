package ru.otus;
//-Xms1024m -Xmx1024m -XX:+UseZGC

import com.sun.management.GarbageCollectionNotificationInfo;
import ru.otus.benchmark.Consumer;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCMonitor {
    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        switchOnMonitoring();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Consumer");

        Consumer mbean = new Consumer();
        mbs.registerMBean(mbean, name);

        beginTime = System.currentTimeMillis();
        mbean.run();

    }

    private static long totalDuration;
    private static long beginTime;

    private static NotificationListener listener = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            String gcName = info.getGcName();
            String gcAction = info.getGcAction();
            String gcCause = info.getGcCause();

            long startTime = info.getGcInfo().getStartTime();
            long duration = info.getGcInfo().getDuration();
            totalDuration += duration;
            System.out.println(
                    "time:" + (System.currentTimeMillis() - beginTime) / 1000
                    + " totalGC:" + totalDuration
                    + " Name:" + gcName
                    + " action:" + gcAction
                    + " gcCause:" + gcCause
                    + "(" + duration + " ms)");
            info.getGcInfo().getMemoryUsageBeforeGc().forEach((key,memUsed)->{
                System.out.print(String.format(" %s:%d",key,memUsed.getUsed()));
            });
            System.out.println("");
            info.getGcInfo().getMemoryUsageAfterGc().forEach((key,memUsed)->{
                System.out.print(String.format(" %s:%d",key,memUsed.getUsed()));
            });
            System.out.println("");
            System.out.println("---------------------------------------------------------------------------------");

        }
    };

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcBeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("GC name:" + gcBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            totalDuration = 0;
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static void switchOffMonitoring() {
        List<GarbageCollectorMXBean> gcBeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("GC name:" + gcBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcBean;

            try {
                emitter.removeNotificationListener(listener, null, null);
            } catch (ListenerNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}