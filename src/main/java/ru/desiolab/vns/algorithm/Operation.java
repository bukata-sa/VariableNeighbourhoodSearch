package ru.desiolab.vns.algorithm;

import ru.desiolab.vns.dto.Solution;

public interface Operation {

    Solution perform(Solution solution);
}