package io.github.coachluck.utils;

@SuppressWarnings("unused")
public class Stopwatch {

    private static long startTime = 0;
    private static long endTime = 0;
    private static boolean running = false;

    public static void start(){
        startTime = System.currentTimeMillis();
        running = true;
    }

    public static void stop(){
        endTime = System.currentTimeMillis();
        running = false;
    }

    public static long getElapsedTime(){
        long elapsed;
        if(running){
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (endTime = startTime);
        }
        return elapsed;
    }

    public static long getElapedTimeInSeconds(){
        long elapsed;
        if(running){
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        } else {
            elapsed = ((endTime - startTime) / 1000);
        }
        return elapsed;
    }
}