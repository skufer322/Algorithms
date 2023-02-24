package de.sk.nphard.makespan;

import de.sk.TestUtils;
import de.sk.nphard.makespan.schedule.Job;
import de.sk.nphard.makespan.schedule.Machine;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for {@link LongestProcessingTimeFirstAlg}.
 */
@ExtendWith(MockitoExtension.class)
class LongestProcessingTimeFirstAlgTest {

    private AutoCloseable mockHandler;

    private static final long SEED_FOR_RANDOM_GENERATOR = 5786551034901171591L;
    private RandomGenerator randomGenerator;

    @Captor
    private ArgumentCaptor<List<Job>> jobsCaptor;
    @Captor
    private ArgumentCaptor<List<Machine>> machinesCaptor;

    @Mock
    private GrahamsAlg grahamsAlgMock;

    @InjectMocks
    private LongestProcessingTimeFirstAlg underTest;

    @BeforeEach
    void setUp() {
        this.randomGenerator = new Random(SEED_FOR_RANDOM_GENERATOR);
        this.mockHandler = MockitoAnnotations.openMocks(this);
    }

    private @NotNull List<Job> generateJobsForTest(int n) {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Job job = new Job(this.randomGenerator.nextInt(1, 100));
            jobs.add(job);
        }
        return jobs;
    }

    @Test
    void shouldExactlyOnceCallMethod_determineMinimumMakeSpan_ofGrahamsAlg() {
        this.underTest.determineMinimumMakeSpan(Collections.emptyList(), Collections.emptyList());
        verify(this.grahamsAlgMock).determineMinimumMakeSpan(anyList(), anyList());
    }

    @ParameterizedTest(name = "[{index}] number of jobs={0}")
    @ValueSource(ints = {0, 1, 10, 100, 1_000})
    void shouldPassJobs_sortedByLengthInDescendingOrder_toGrahamsAlg(int numberOfJobs) {
        // arrange
        List<Job> jobs = this.generateJobsForTest(numberOfJobs);
        List<Machine> machineMocks = TestUtils.createMocks(5, Machine.class);

        // act
        this.underTest.determineMinimumMakeSpan(jobs, machineMocks);

        // assert
        verify(this.grahamsAlgMock).determineMinimumMakeSpan(this.jobsCaptor.capture(), eq(machineMocks));
        List<Job> jobsPassedToGrahamsAlg = this.jobsCaptor.getValue();
        assertThat(jobsPassedToGrahamsAlg).containsExactlyInAnyOrderElementsOf(jobs);
        assertThat(jobsPassedToGrahamsAlg).isSortedAccordingTo(LongestProcessingTimeFirstAlg.LPTF_JOB_SORTING_CRITERION);
    }

    @ParameterizedTest(name = "[{index}] number of machines={0}")
    @ValueSource(ints = {0, 1, 10, 100, 1_000})
    void shouldPassMachines_unchanged_toGrahamsAlg(int numberOfMachines) {
        // arrange
        List<Job> jobs = Collections.emptyList();
        List<Machine> machineMocks = TestUtils.createMocks(numberOfMachines, Machine.class);
        List<Machine> machineMocksInOriginalOrder = new ArrayList<>(machineMocks);

        // act
        this.underTest.determineMinimumMakeSpan(jobs, machineMocks);

        // assert
        verify(this.grahamsAlgMock).determineMinimumMakeSpan(anyList(), this.machinesCaptor.capture());
        List<Machine> machinesPassedToGrahamsAlg = this.machinesCaptor.getValue();
        assertThat(machinesPassedToGrahamsAlg).isEqualTo(machineMocksInOriginalOrder);
    }

    @ParameterizedTest(name = "[{index}] expected machines result={0}")
    @MethodSource("createExpectedMachineResultsForTestIfResultsFromGrahamsAlgAreReturned")
    void shouldReturnResult_whichIsReturned_fromGrahamsAlg(List<Machine> expectedMachinesResult) {
        // arrange
        when(this.grahamsAlgMock.determineMinimumMakeSpan(anyList(), anyList())).thenReturn(expectedMachinesResult);
        // act
        List<Machine> machines = this.underTest.determineMinimumMakeSpan(Collections.emptyList(), Collections.emptyList());
        // assert
        assertThat(machines).isEqualTo(expectedMachinesResult);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mockHandler.close();
    }

    private static Stream<Arguments> createExpectedMachineResultsForTestIfResultsFromGrahamsAlgAreReturned() {
        List<Machine> machines1 = List.of(new Machine(), new Machine());
        List<Machine> machines2 = List.of(new Machine());
        List<Machine> machines3 = Collections.emptyList();
        return Stream.of(
                Arguments.of(machines1),
                Arguments.of(machines2),
                Arguments.of(machines3)
        );
    }
}