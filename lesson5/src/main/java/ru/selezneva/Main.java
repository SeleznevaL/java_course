package ru.selezneva;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

//-Xmx32m
//-XX:+UseSerialGC
//-XX:+UseParallelGC
//-XX:+UseConcMarkSweepGC

public class Main {
    static long totalDuration = 0;
    public static void main(String[] args) throws Exception {
        System.out.println( "Starting pid: " + ManagementFactory.getRuntimeMXBean().getName() );
        switchOnMonitoring();

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        My my = new My();
        ObjectName name = new ObjectName("ru.selezneva:type=My");
        mBeanServer.registerMBean(my, name);
        my.setCount(1_000_000_00);
        my.run();
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for ( GarbageCollectorMXBean gcbean : gcbeans ) {
            System.out.println( "GC name:" + gcbean.getName() );
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = ( notification, handback ) -> {
                if ( notification.getType().equals( GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION ) ) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from( (CompositeData) notification.getUserData() );
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    totalDuration += duration;

                    System.out.println( "start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)" );
                    System.out.println("Total duration " + totalDuration);
                }
            };
            emitter.addNotificationListener( listener, null, null );
        }
    }
}
