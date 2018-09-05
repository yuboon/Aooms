
package net.aooms.core.module.metric;

import com.codahale.metrics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/metric")
public class MetricController {

    @Autowired
    private Meter requestMeter;

    @Autowired
    private Histogram responseSizes;

    @Autowired
    private Counter pendingJobs;

    @Autowired
    private Timer responses;

    @Autowired
    private ConsoleReporter consoleReporter;

    @RequestMapping("/show")
    @ResponseBody
    public String helloWorld() {

        requestMeter.mark();
        pendingJobs.inc();
        responseSizes.update(new Random().nextInt(10));

        final Timer.Context context = responses.time();
        try {


            //consoleReporter.report();
            consoleReporter.report();

            return "Hello World";

        } finally {
            context.stop();
        }
    }
}

