package de.sk.nphard.makespan.schedule;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Representation of a machine in the realm of the make span minimization problem.
 */
public class Machine implements Comparable<Machine> {

    private final Set<Job> assignedJobs;
    private int load;

    public Machine() {
        this.assignedJobs = new LinkedHashSet<>();
        this.load = 0;
    }

    /**
     * Returns the jobs which are assigned to this machine.
     *
     * @return list of jobs assigned to the machine.
     */
    public @NotNull List<Job> getJobs() {
        return new ArrayList<>(this.assignedJobs);
    }

    /**
     * Returns the load of the machine. The load is calculated as sum of the jobs assigned to this machine.
     *
     * @return load of the machine
     */
    public int getLoad() {
        return this.load;
    }

    /**
     * Assigns a new job to this machine.
     *
     * @param job job to assign
     */
    public void assignJob(@NotNull Job job) {
        this.assignedJobs.add(job);
        this.load += job.length();
    }

    /**
     * Removes the given {@code job} from the machine (if {@code job} is assigned). The load of the machine is adjusted
     * accordingly.
     *
     * @param job job to remove from the machine
     */
    public void removeJob(@NotNull Job job) {
        this.assignedJobs.remove(job);
        this.load -= job.length();
    }

    @Override
    public int compareTo(@NotNull Machine other) {
        return Integer.compare(this.load, other.load);
    }
}
