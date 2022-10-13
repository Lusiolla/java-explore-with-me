package ru.practicum.explorewm.event.service;

import ru.practicum.explorewm.event.dto.*;
import ru.practicum.explorewm.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;


public interface EventService {

    Collection<EventShort> getByParamPublic(PublicGetEventRequest request, HttpServletRequest req);

    EventFull getBtIdPublic(long eventId, HttpServletRequest req);

    Collection<EventShort> getUserEvents(Long userId, int from, int size);

    EventDto getByIdUserEvent(Long userId, Long eventId);


    EventDto userUpdate(Long userId, EventUpdate updateEvent);

    EventDto add(Long userId, Event event);

    EventDto adminUpdate(Long eventId, AdminEventUpdate updateEvent);

    EventDto cancel(Long userId, Long eventId);

    Collection<EventFull> getByParamAdmin(AdminGetEventRequest request);

    EventPublished publish(Long eventId);

    EventDto reject(Long eventId);

}
