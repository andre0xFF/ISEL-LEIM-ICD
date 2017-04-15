package create_and_manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateFormatterExample {

    public static class DateFormatterThreadLocal extends ThreadLocal<SimpleDateFormat> {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("EEE MMM d, hh:mm:ss");
        }
    }

    public static DateFormatterThreadLocal dateFormatterVar = new DateFormatterThreadLocal();

    public static void main(String[] args) {

        Thread t1 = new Thread(new DatePrinter("Formatter 1"), "Formatter 1");
        Thread t2 = new Thread(new DatePrinter("Formatter 2"), "Formatter 2");

        t1.start();
        t2.start();
    }
}

class DatePrinter implements Runnable {

        private String name;

        public DatePrinter(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            if (name.equalsIgnoreCase("Formatter 1")) {
                System.out.println(name + " is setting the formatting pattern");
                DateFormatterExample.dateFormatterVar.get().applyPattern("hh:mm:ss");
            }

            while (true) {
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e) { }

                Date now = new Date();

                System.out.println(name + ": " + DateFormatterExample.dateFormatterVar.get().format(now));
            }
        }
}