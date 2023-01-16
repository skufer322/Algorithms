package de.sk.nphard.makespan;

import de.sk.nphard.makespan.schedule.Job;
import de.sk.nphard.makespan.schedule.Machine;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for implementations solving the make span minimization problem. The solutions determined
 * by the implementations might be approximate solutions since solving the make span minimization problem is NP-hard.
 * <br>
 * For the make span minimization problem, the make span is minimized for a given set of n jobs to be assigned to a set of m machines.
 * The make span is the maximum load for any of the machines.
 */
public interface MakeSpanSolver {

    /**
     * Determines a solution for the make span minimization problem for the given {@code jobs} and {@code machines}. The determined
     * solution might only be an approximate solution.
     *
     * @param jobs     jobs which are to be assigned to machines
     * @param machines machines to which the jobs are to assign
     * @return solution for the make span minimization problem for the given {@code jobs} and {@code machines} (might be an approximate solution)
     */
    @NotNull List<Machine> determineMinimumMakeSpan(@NotNull List<Job> jobs, @NotNull List<Machine> machines);
}
