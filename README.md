# ticket_app

TicketApplication
TicketApplication developed based on Spring Boot Version (2.1.2.RELEASE) and uses in memory data base H2
Ticket application provide rest services to view reservation, hold seats for a certain duration and auto free up seats which are on hold for a certain duration.
Self runnable application can be executed on any IDE where maven dependencies are configured. This application required Java 8 as the minimum requirement to run.
Ticket services are created on JPA over H2 due to its ACID properties

Table creation 

create table FACILITY 
(
   id long not null primary key,
   EVENT_NAME varchar(25) not null,
   TOTAL_ROWS INT,
   TOTAL_COLUMNS INT,
   primary key(id)
);
create table BOOKING
(
 
 EMAIL varchar(100) not null primary key,
 EVENT_ID long,
 STATUS VARCHAR(10),
 NUMBER_OF_SEATS int,
 SEATS varchar(1000),
 TIME long,
 primary key(EMAIL)
);

REST CALL

Request 
http://localhost:8080/displaySeats?eventId=1549655231920
Response
[
    "----------[[ STAGE ]]------------",
    "---------------------------------",
    "RRsssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss"
]
Holding Seats 

Request http://localhost:8080/findAndHoldSeats?eventId=1549655231920&numberOfSeats=2&email=ff@test.com

Response

{
    "numSeats": 2,
    "customerEmail": "aa@test.com",
    "bookingStatus": true,
    "eventId": 1549655231920,
    "seatHoldId": 1549923825543
}

Reserving seats

http://localhost:8080/reserveSeats?eventId=1549655231920&seatHoldId=1549923825543&email=aa@test.com


{
    "message": "Seat reservation successfull",
    "status": true
}

http://localhost:8080/displaySeats?eventId=1549655231920

[
    "----------[[ STAGE ]]------------",
    "---------------------------------",
    "RRRRRRRRsssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss",
    "sssssssssssssssssssssssssssssssss"
]

Scheduler will free up seats which are on hold after 10 mins (Configurable property )

H2 Console can be opened on below URL

http://localhost:8080/h2-console/

 
