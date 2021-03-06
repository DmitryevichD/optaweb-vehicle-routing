/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.plugin.persistence;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.optaweb.vehiclerouting.domain.LatLng;
import org.optaweb.vehiclerouting.domain.Location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DistanceRepositoryImplTest {

    @Mock
    private DistanceCrudRepository crudRepository;
    @InjectMocks
    private DistanceRepositoryImpl repository;
    @Captor
    private ArgumentCaptor<DistanceEntity> distanceEntityArgumentCaptor;
    @Mock
    private DistanceEntity distanceEntity;

    private Location from = new Location(1, LatLng.valueOf(7, -4.0));
    private Location to = new Location(2, LatLng.valueOf(5, 9.0));

    @Test
    public void should_save_distance() {
        double distance = 95676.6417;
        repository.saveDistance(from, to, distance);
        verify(crudRepository).save(distanceEntityArgumentCaptor.capture());
        DistanceEntity distanceEntity = distanceEntityArgumentCaptor.getValue();
        assertThat(distanceEntity.getDistance()).isEqualTo(distance);
        assertThat(distanceEntity.getKey().getFromId()).isEqualTo(from.getId());
        assertThat(distanceEntity.getKey().getToId()).isEqualTo(to.getId());
    }

    @Test
    public void should_return_distance_when_entity_is_found() {
        DistanceKey distanceKey = new DistanceKey(from.getId(), to.getId());
        when(crudRepository.findById(distanceKey)).thenReturn(Optional.of(distanceEntity));
        final double distance = 1.0305;
        when(distanceEntity.getDistance()).thenReturn(distance);
        assertThat(repository.getDistance(from, to)).isEqualTo(distance);
    }

    @Test
    public void should_return_negative_number_when_distance_not_found() {
        when(crudRepository.findById(any(DistanceKey.class))).thenReturn(Optional.empty());
        assertThat(repository.getDistance(from, to))
                .isNegative()
                // Shouldn't be necessary but improves mutation coverage report because Pitest does -(x + 1) mutation,
                // which turns -1 into -0, so this test wouldn't kill that mutation without the following:
                .isNotZero();
    }
}
