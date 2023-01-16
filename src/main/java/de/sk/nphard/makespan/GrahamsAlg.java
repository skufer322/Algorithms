package de.sk.nphard.makespan;

import de.sk.nphard.makespan.schedule.Job;
import de.sk.nphard.makespan.schedule.Machine;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * Implementation of {@link MakeSpanSolver} utilizing Graham's algorithm.
 * <br>
 * Time complexity: O(n log m) (by using a heap; n = number of jobs, m = number of machines).
 */
public class GrahamsAlg implements MakeSpanSolver {

    static final String HEAP_ELEMENT_ISNULL_EXCEPTION_MSG = "Internal error! One of the elements in the machines' heap is null!";

    private final PriorityQueue<Machine> machinesHeap = new PriorityQueue<>();

    @Override
    public @NotNull List<Machine> determineMinimumMakeSpan(@NotNull List<Job> jobs, @NotNull List<Machine> machines) {
        this.clearDatastructures();
        this.machinesHeap.addAll(machines);
        for (Job job : jobs) {
            Machine lowestLoadMachine = Optional.ofNullable(this.machinesHeap.poll()).orElseThrow(() -> new IllegalStateException(HEAP_ELEMENT_ISNULL_EXCEPTION_MSG));
            lowestLoadMachine.assignJob(job);
            this.machinesHeap.add(lowestLoadMachine);
        }
        return machines;
    }

    private void clearDatastructures() {
        this.machinesHeap.clear();
    }
}
