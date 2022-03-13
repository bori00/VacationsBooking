---------------- User table

create table user
(
    Id       bigint auto_increment
        primary key,
    password varchar(255) not null,
    userName varchar(255) not null,
    userType varchar(255) not null,
    constraint UK_4bakctviobmdk6ddh2nwg08c2
        unique (userName)
);

INSERT INTO vacations.user (Id, password, userName, userType) VALUES (1, 'annapass', 'anna', 'V');
INSERT INTO vacations.user (Id, password, userName, userType) VALUES (17, 'boripass', 'bori', 'V');
INSERT INTO vacations.user (Id, password, userName, userType) VALUES (22, 'adminpass', 'admin', 'A');
INSERT INTO vacations.user (Id, password, userName, userType) VALUES (25, 'peterpass', 'peter', 'V');
INSERT INTO vacations.user (Id, password, userName, userType) VALUES (26, 'mariapass', 'maria', 'V');
INSERT INTO vacations.user (Id, password, userName, userType) VALUES (27, 'simonpass', 'simon', 'V');

---------------- Destination table

create table destination
(
    Id   bigint auto_increment
        primary key,
    name varchar(255) not null,
    constraint UK_kw349sqcyo1k39xa0wn3k3q2r
        unique (name)
);

INSERT INTO vacations.destination (Id, name) VALUES (20, 'Amsterdam');
INSERT INTO vacations.destination (Id, name) VALUES (17, 'Lake Como');
INSERT INTO vacations.destination (Id, name) VALUES (15, 'London');
INSERT INTO vacations.destination (Id, name) VALUES (18, 'Mont Blanc');
INSERT INTO vacations.destination (Id, name) VALUES (19, 'Naples');
INSERT INTO vacations.destination (Id, name) VALUES (14, 'Paris');
INSERT INTO vacations.destination (Id, name) VALUES (22, 'Stockholm');
INSERT INTO vacations.destination (Id, name) VALUES (21, 'Vienna');

---------------- VacationPackage table
create table vacation_package
(
    Id             bigint auto_increment
        primary key,
    name           varchar(255) not null,
    price          float        null,
    endDate        date         not null,
    extraDetails   varchar(255) null,
    nrPlaces       int          not null,
    startDate      date         not null,
    destination_id bigint       not null,
    constraint UK_n2rsnvkhj0htw07rn9mxn3j6
        unique (name),
    constraint FKdxupp03nu120q45ihtophscyw
        foreign key (destination_id) references destination (Id)
);

INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (15, 'Sunny Como', 10, '2022-03-17', '', 2, '2022-03-14', 17);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (16, 'Romantic Paris', 1200, '2022-03-30', '', 100, '2022-03-23', 14);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (17, 'Anne Frank Tour', 565.5, '2022-04-04', '', 12, '2022-04-01', 20);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (18, 'Cheese Tour', 200, '2022-03-19', 'For Gourmets!', 25, '2022-03-19', 20);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (19, 'All Museums in London', 110, '2022-03-20', '', 10, '2022-03-19', 15);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (20, 'Pub Tour', 135, '2022-03-26', '', 15, '2022-03-26', 15);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (21, 'Pub Tour 2', 145, '2022-04-02', '', 3, '2022-04-02', 15);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (24, 'Royal Tour', 1200, '2022-03-18', '', 18, '2022-03-15', 15);
INSERT INTO vacations.vacation_package (Id, name, price, endDate, extraDetails, nrPlaces, startDate, destination_id) VALUES (25, 'Royal Tour (short)', 600, '2022-03-16', '', 18, '2022-03-15', 15);

----------------- Booking table
create table booking
(
    Id          bigint auto_increment
        primary key,
    user_id     bigint not null,
    vacation_id bigint not null,
    constraint FKewphskwi2nj7o4oj3jc0bqc33
        foreign key (vacation_id) references vacation_package (Id),
    constraint FKkgseyy7t56x7lkjgu3wah5s3t
        foreign key (user_id) references user (Id)
);

INSERT INTO vacations.booking (Id, user_id, vacation_id) VALUES (29, 17, 21);
INSERT INTO vacations.booking (Id, user_id, vacation_id) VALUES (30, 17, 15);
INSERT INTO vacations.booking (Id, user_id, vacation_id) VALUES (31, 17, 21);
INSERT INTO vacations.booking (Id, user_id, vacation_id) VALUES (34, 27, 15);
INSERT INTO vacations.booking (Id, user_id, vacation_id) VALUES (36, 17, 18);
INSERT INTO vacations.booking (Id, user_id, vacation_id) VALUES (37, 25, 21);