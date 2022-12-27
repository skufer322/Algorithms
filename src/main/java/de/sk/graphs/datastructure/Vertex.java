package de.sk.graphs.datastructure;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the general methods for vertex implementations (for both directed and undirected graphs).
 */
public interface Vertex {

    String BLANK_NAME_PASSED_EXCEPTION_MSG_TF = "The name passed for the vertex is blank: %s";

    /**
     * Returns the name of the vertex.
     *
     * @return the vertex's name
     */
    @NotNull String getName();

    /**
     * Returns whether the vertex has been explored, yet.
     *
     * @return true if the vertex has been explored, false else
     */
    boolean isExplored();

    /**
     * Sets the information if the vertex has been explored.
     *
     * @param isExplored boolean if the vertex has been explored, or not
     */
    void setExplored(boolean isExplored);

    /**
     * Returns the number/id of the connected component the vertex is located in.
     *
     * @return number/id of the connected component the vertex is located in, -1 if the vertex was not involved in a cc analysis
     */
    int getCc();

    /**
     * Sets the number/id/{@code cc} of the connected component the vertex is located in.
     *
     * @param cc the number/id of the connected component the vertex is located in
     */
    void setCc(int cc);
}
