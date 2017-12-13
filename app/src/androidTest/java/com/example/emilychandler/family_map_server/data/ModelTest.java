package com.example.emilychandler.family_map_server.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Model;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 12/10/17.
 */

public class ModelTest {
    private Model model;

    @Before
    public void setUp() {
        model = Model.getInstance();
        Map<String, Event> events = new HashMap<>();
        Event event = new Event("emily", "emily", -.7333, -116.7333, "USA", "Provo", "birth", "1997");
        event.setEventId("ebirth");
        events.put("ebirth", event);

        event = new Event("emily", "emily", -.7333, -116.7333, "USA", "Provo", "baptism", "2000");
        event.setEventId("ebaptism");
        events.put("ebaptism", event);

        event = new Event("emily", "emily", -.7333, -116.7333, "USA", "Provo", "marriage", "2017");
        event.setEventId("emarriage");
        events.put("emarriage", event);

        model.setEvents(events);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetPersonEvents() {
        Map<String, List<Event>> personEvents = model.getPersonEvents();
        List<Event> events = personEvents.get("emily");
        assertEquals("birth", events.get(0));
        assertEquals("baptism", events.get(1));
        assertEquals("marriage", events.get(2));
    }
}
