package ru.practicum.explorewm.event.service;

import ru.practicum.explorewm.event.dto.*;
import ru.practicum.explorewm.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;


public interface EventService {

    Collection<EventShort> getByParamPublic(PublicGetEventRequest request, HttpServletRequest req);

    EventFull getBtIdPublic(long eventId, HttpServletRequest req);

    Collection<EventShort> getUserEvents(Long userId, int from, int size);

    EventFull getByIdUserEvent(Long userId, Long eventId);


    EventFull userUpdate(Long userId, EventUpdate updateEvent);

    EventFull add(Long userId, Event event);

    EventFull adminUpdate(Long eventId, AdminEventUpdate updateEvent);

    EventFull cancel(Long userId, Long eventId);

    Collection<EventFull> getByParamAdmin(AdminGetEventRequest request);

    EventFull publish(Long eventId);

    EventFull reject(Long eventId);

}
