CREATE TRIGGER trig_add_seats
    AFTER INSERT ON events
    REFERENCING NEW AS newEvent
    FOR EACH ROW
    INSERT INTO seats (seatNumber, seatType, locationId, eventId)
    SELECT seatNumber, seatType, locationId, newEvent.id
    FROM seats
    WHERE locationId = newEvent.locationId AND seats.eventId IS NULL;