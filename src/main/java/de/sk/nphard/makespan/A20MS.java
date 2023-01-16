package de.sk.nphard.makespan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.nphard.NpHardInjectionModule;
import de.sk.nphard.makespan.schedule.Job;
import de.sk.nphard.makespan.schedule.Machine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class A20MS {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NpHardInjectionModule());
        MakeSpanSolver mss = injector.getInstance(MakeSpanSolver.class);

        List<Job> jobs = createJobsFromQuiz20_2();
        List<Machine> machines = createMachines(5);
        mss.determineMinimumMakeSpan(jobs, machines);
        machines.forEach(machine -> System.out.println(machine.getLoad()));
    }

    private static @NotNull List<Job> createJobsFromQuiz20_2() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Job job = new Job(1);
            jobs.add(job);
        }
        Job job = new Job(5);
        jobs.add(job);
        return jobs;
    }

    private static @NotNull List<Machine> createMachines(int numberOfMachines) {
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < numberOfMachines; i++) {
            Machine machine = new Machine();
            machines.add(machine);
        }
        return machines;
    }
}
