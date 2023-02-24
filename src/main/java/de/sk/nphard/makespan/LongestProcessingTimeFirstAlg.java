package de.sk.nphard.makespan;

import de.sk.nphard.makespan.schedule.Job;
import de.sk.nphard.makespan.schedule.Machine;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of {@link MakeSpanSolver} utilizing the Longest Processing Time First (LPT) algorithm. Improves
 * {@link GrahamsAlg} by first sorting the jobs by length in descending order, and second passing this sorted list
 * of jobs to an instance {@link GrahamsAlg}.
 * <br>
 * Time complexity: O(n log n) (if n > m; n = number of jobs, m = number of machines).
 */
public class LongestProcessingTimeFirstAlg implements MakeSpanSolver {

    static final Comparator<Job> LPTF_JOB_SORTING_CRITERION = Comparator.reverseOrder();

    @Inject
    private GrahamsAlg grahamsAlg;

    @Override
    public @NotNull List<Machine> determineMinimumMakeSpan(@NotNull List<Job> jobs, @NotNull List<Machine> machines) {
        List<Job> jobsCopy = new ArrayList<>(jobs);
        jobsCopy.sort(LPTF_JOB_SORTING_CRITERION);
        return this.grahamsAlg.determineMinimumMakeSpan(jobsCopy, machines);
    }
}
