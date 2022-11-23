package de.sk.graphs.datastructure;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the general methods for vertex implementations (both for directed and undirected graphs).
 */
public interface Vertex {

    String BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT = "The name passed for the vertex is blank: %s";

    @NotNull String getName();

    boolean isExplored();

    void setExplored(boolean explored);

    int getCc();

    void setCc(int cc);
}
