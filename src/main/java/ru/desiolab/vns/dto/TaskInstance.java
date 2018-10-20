package ru.desiolab.vns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Accessors(fluent = true)
public class TaskInstance {
    private Integer machinesNumber;
    private Integer partsNumber;
    private int[][] machineToPart;
}
