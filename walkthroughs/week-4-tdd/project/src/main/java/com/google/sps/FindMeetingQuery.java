// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class FindMeetingQuery {
    private ArrayList<TimeRange> possibleMeetingTimes = new ArrayList<>();
    
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        Collection<String> attendees = request.getAttendees();
        long requestedDuration = request.getDuration();
        
        // handle case of meeting request with no attendees
        if (attendees.isEmpty()) {
            return Arrays.asList(TimeRange.WHOLE_DAY);
        }
        
        // handle case of meeting request with a duration that is longer than a day
        if (requestedDuration > TimeRange.WHOLE_DAY.duration()) {
            return Arrays.asList();
        }
        
        possibleMeetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, TimeRange.END_OF_DAY, true));
        Set<String> eventAttendees;
        
        for (Event event : events) {
            eventAttendees = new HashSet<>(event.getAttendees());
            eventAttendees.retainAll(attendees);
            if (!eventAttendees.isEmpty()) {
                setPossibleMeetingTimes(removeUnavailableTimeSlot(possibleMeetingTimes, event.getWhen(), requestedDuration));
            }
        }
        return possibleMeetingTimes;
    }
    
    private ArrayList<TimeRange> removeUnavailableTimeSlot(ArrayList<TimeRange> possibleMeetingTimes, TimeRange eventTime, long requestedDuration) {
        ArrayList<TimeRange> availableTimeSlots = (ArrayList<TimeRange>) possibleMeetingTimes.clone();
        for (TimeRange timeSlot : possibleMeetingTimes) {
            if (timeSlot.overlaps(eventTime)) {

                if (timeSlot.equals(eventTime)) {
                    availableTimeSlots.remove(eventTime);
                    return availableTimeSlots;
                }

                if (timeSlot.contains(eventTime)) {
                    // divide timeslot into 2 possible segments (before and after the scheduled event)
                    availableTimeSlots.remove(timeSlot);
                    TimeRange timeSlotBefore = TimeRange.fromStartEnd(timeSlot.start(), eventTime.start(), false);
                    TimeRange timeSlotAfter = TimeRange.fromStartEnd(eventTime.end(), timeSlot.end(), false);
                    // ensure the new time slots are of the desired duration
                    if (timeSlotBefore.duration() >= requestedDuration) {
                        availableTimeSlots.add(timeSlotBefore);
                    }
                    if (timeSlotAfter.duration() >= requestedDuration) {
                        availableTimeSlots.add(timeSlotAfter);
                    }
                    return availableTimeSlots;
                }
                
                // handle cases where event time overlaps with available meeting times (but is not contained in other time slots)
                if (eventTime.start() <= timeSlot.start() && eventTime.end() <= timeSlot.end()) {
                    availableTimeSlots.remove(timeSlot);
                    availableTimeSlots.add(TimeRange.fromStartEnd(eventTime.end(), timeSlot.end(), false));
                    return availableTimeSlots;
                }
                if (timeSlot.start() < eventTime.start() && timeSlot.end() <= eventTime.end()){
                    availableTimeSlots.remove(timeSlot);
                    availableTimeSlots.add(TimeRange.fromStartEnd(timeSlot.start(), eventTime.start(), false));
                    return availableTimeSlots;
                }
            }
        }
        return availableTimeSlots;
    }
    
    public void setPossibleMeetingTimes(ArrayList<TimeRange> possibleMeetingTimes) {
        this.possibleMeetingTimes = (ArrayList<TimeRange>) possibleMeetingTimes.clone();
    }   
}

