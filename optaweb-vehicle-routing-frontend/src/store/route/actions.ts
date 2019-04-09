/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

import { ActionCreator } from 'redux';
import {
  ActionType,
  AddLocationAction,
  ClearRouteAction,
  DeleteLocationAction,
  LatLng,
  RoutingPlan,
  UpdateRouteAction,
} from './types';

export const addLocation: ActionCreator<AddLocationAction> = (location: LatLng) => ({
  type: ActionType.ADD_LOCATION,
  value: location,
});

export const deleteLocation: ActionCreator<DeleteLocationAction> = (id: number) => ({
  type: ActionType.DELETE_LOCATION,
  value: id,
});

export const clearRoute: ActionCreator<ClearRouteAction> = () => ({
  type: ActionType.CLEAR_SOLUTION,
});

export const updateRoute: ActionCreator<UpdateRouteAction> = (
  plan: RoutingPlan,
) => ({
  plan,
  type: ActionType.UPDATE_ROUTING_PLAN,
});
