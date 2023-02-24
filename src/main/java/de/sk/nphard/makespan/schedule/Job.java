package de.sk.nphard.makespan.schedule;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Representation of a job in realm of the make span minimization problem.
 * @param length length of the job
 */
public record Job(int length) implements Comparable<Job> {

    static final String JOB_LENGTH_MUST_GREATER_0_EXCEPTION_MSG_TF = "Job length must 0 at least. Given: %d.";

    public Job {
        if (length <= 0) {
            throw new IllegalArgumentException(JOB_LENGTH_MUST_GREATER_0_EXCEPTION_MSG_TF);
        }
    }

    @Override
    public int compareTo(@NotNull Job other) {
        return Integer.compare(this.length, other.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return length == job.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length);
    }
}
