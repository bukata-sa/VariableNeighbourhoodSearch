package ru.desiolab.vns.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
public class Cluster implements Cloneable {

    private final List<Integer> machines;
    private final List<Integer> parts;

    public Cluster(List<Integer> machines, List<Integer> parts) {
        this.machines = machines;
        this.parts = parts;
    }

    @Override
    public Cluster clone() {
        return new Cluster(new ArrayList<>(machines), new ArrayList<>(parts));
    }
}
