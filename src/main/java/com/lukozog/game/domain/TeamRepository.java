package com.lukozog.game.domain;

import java.util.List;
import java.util.Map;

interface TeamRepository {
    Map<Long, Team> findByIds(List<Long> teamIds);
}
