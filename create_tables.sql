CREATE TABLE clients (
                         id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         name VARCHAR(50) NOT NULL,
                         email VARCHAR(50),
                         phone VARCHAR(50),
                         userType VARCHAR(15) NOT NULL CHECK ( userType IN ('guest', 'registered', 'disabled') ),
                         nrPoints INT
);

CREATE TABLE organizers (
                            id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                            name VARCHAR(50) NOT NULL,
                            address VARCHAR(50)
);

CREATE TABLE locations (
                           id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           name VARCHAR(50) NOT NULL,
                           address VARCHAR(50) NOT NULL
);

CREATE TABLE events (
                        id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        name VARCHAR(50) NOT NULL,
                        description VARCHAR(200) NOT NULL,
                        locationId INT NOT NULL REFERENCES locations(id),
                        organizerId INT NOT NULL REFERENCES organizers(id),
                        startDate TIMESTAMP NOT NULL,
                        endDate TIMESTAMP NOT NULL,
                        seatPrice DOUBLE NOT NULL
);

CREATE TABLE seats (
                       id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       seatNumber INT NOT NULL,
                       seatType VARCHAR(50) NOT NULL CHECK (seatType IN ('exclusive', 'disabled', 'normal') ),
                       locationId INT NOT NULL REFERENCES locations(id),
                       eventId INT REFERENCES events(id)
);


CREATE TABLE tickets (
                         id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         seatId INT NOT NULL REFERENCES seats(id),
                         clientId INT NOT NULL REFERENCES clients(id),
                         eventsId INT NOT NULL REFERENCES events(id),
                         qrPath VARCHAR(60)
);

CREATE TABLE historical_tickets (
                                    id INT PRIMARY KEY,
                                    seatId INT NOT NULL REFERENCES seats(id),
                                    clientId INT NOT NULL REFERENCES clients(id),
                                    eventsId INT NOT NULL REFERENCES events(id),
                                    qrPath VARCHAR(60)
)