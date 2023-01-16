package de.sk.nphard.makespan.schedule;

import org.jetbrains.annotations.NotNull;

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
}
