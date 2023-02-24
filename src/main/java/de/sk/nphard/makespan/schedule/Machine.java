package de.sk.nphard.makespan.schedule;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Representation of a machine in the realm of the make span minimization problem.
 */
public class Machine implements Comparable<Machine> {

    private final Set<Job> assignedJobs;
    private int load;
    private final UUID uuid;

    public Machine() {
        this.assignedJobs = new LinkedHashSet<>();
        this.load = 0;
        this.uuid = UUID.randomUUID();
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
     * Returns the UUID of the machine.
     *
     * @return uuid of the machine
     */
    public @NotNull UUID getUuid() {
        return this.uuid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return this.load == machine.load && this.assignedJobs.equals(machine.assignedJobs) && this.uuid.equals(machine.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.assignedJobs, this.load, this.uuid);
    }
}
