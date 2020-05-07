package com.tryingpfq.job;

import com.tryingpfq.jobqueue.dto.AbstractJob;
import com.tryingpfq.jobqueue.enums.JobType;
import com.tryingpfq.jobqueue.handler.JobHandler;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
public class JobTest {
    private static ApplicationContext applicationContext;

    static{
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void jobTest() throws Exception{
        JobHandler jobHandler = applicationContext.getBean(JobHandler.class);
        for (int i = 0; i < 10; i++) {
            jobHandler.addJob(new MyJob(i));

            Thread.sleep(new Random().nextInt(20));
        }
        System.in.read();
    }

    public static class MyJob extends AbstractJob{


        public MyJob(int id) {
            super();
            setJobId(id);
        }

        @Override
        public long getDelayTime() {
            return new Random().nextInt(20000);
        }

        @Override
        public JobType jobType() {
            return JobType.Order;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
