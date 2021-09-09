DROP SCHEMA IF EXISTS `workorderdb`;
CREATE SCHEMA IF NOT EXISTS `workorderdb` DEFAULT CHARACTER SET latin1;
USE `workorderdb`;

CREATE TABLE IF NOT EXISTS `workorderdb`.`cause` (
    cause_id    INT(4) NOT NULL,
    cause_code  VARCHAR(10),
    code_description    VARCHAR(30),
    PRIMARY KEY (cause_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`damage` (
    damage_id   INT(4) NOT NULL,
    damage_code VARCHAR(10),
    code_description    VARCHAR(30),
    PRIMARY KEY (damage_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`plant` (
    plant_id    INT(4) NOT NULL,
    plant_name  VARCHAR(30),
    longitude    FLOAT(6,3),
    latitude    FLOAT(6,3),
    location    VARCHAR(100),
    PRIMARY KEY (plant_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`notif_type` (
    type_id    VARCHAR(3) NOT NULL,
    type_desc  VARCHAR(30),
    PRIMARY KEY (type_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`order_type` (
    type_id    VARCHAR(3) NOT NULL,
    type_desc  VARCHAR(30),
    PRIMARY KEY (type_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`technical_object` (
    tech_obj_id     INT(8) NOT NULL,
    tech_obj_name   VARCHAR(30),
    tech_obj_type   VARCHAR(20),
    PRIMARY KEY (tech_obj_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`work_center` (
    work_center_id  VARCHAR(6) NOT NULL,
    work_center_name    VARCHAR(40),
    PRIMARY KEY (work_center_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`status` (
    status_id  INT(1) NOT NULL,
    status_desc    VARCHAR(40),
    PRIMARY KEY (status_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`notification` (
    notification_id     INT(7) NOT NULL,
    notification_type   VARCHAR(3),
    plant_id    INT(4),
    damage_id    INT(4),
    cause_id    INT(4),
    tech_obj_id    INT(8),
    created_by       INT(5) NOT NULL,
    work_center_id  VARCHAR(6),
    status      INT(1),
    PRIMARY KEY (notification_id),
    CONSTRAINT notif_plant_FK 
        FOREIGN KEY (plant_id) 
        REFERENCES plant (plant_id),
    CONSTRAINT notif_damage_FK 
        FOREIGN KEY (damage_id) 
        REFERENCES damage (damage_id),
    CONSTRAINT notif_cause_FK 
        FOREIGN KEY (cause_id) 
        REFERENCES cause (cause_id),
    CONSTRAINT notif_tech_obj_FK 
        FOREIGN KEY (tech_obj_id) 
        REFERENCES technical_object (tech_obj_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`work_order` (
    work_order_id   INT(7) NOT NULL,
    order_type  VARCHAR(4),
    description     VARCHAR(100),
    required_start_date     DATETIME,
    required_end_date   DATETIME,
    notification_id     INT(7) NOT NULL,
    status      INT(1),
    notes       VARCHAR(250),
    PRIMARY KEY(work_order_id),
    CONSTRAINT order_notification_FK 
        FOREIGN KEY (notification_id) 
        REFERENCES notification (notification_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`operations` (
    operation_id     INT NOT NULL,
    description     VARCHAR(40),
    long_text       VARCHAR(100),
    work_order_id   INT NOT NULL,
    status 		INT(1),
    person_responsible	 INT(5),
    PRIMARY KEY (operation_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`users` (
    user_id INT(5) NOT NULL,
    user_fname   VARCHAR(45),
    user_lname   VARCHAR(45),
    user_hash    VARCHAR(500),
    user_role    INT(10),
    work_center_id  VARCHAR(6),
    user_forced_reset BOOLEAN default 0,
    user_locked BOOLEAN default 0,
    PRIMARY KEY (user_id),
    CONSTRAINT user_work_center_FK
        FOREIGN KEY (work_center_id) 
        REFERENCES work_center (work_center_id));

CREATE TABLE IF NOT EXISTS `workorderdb`.`roles` (
    role_id INT NOT NULL,
    role_desc   VARCHAR(45),
    role_type   VARCHAR(45),
    PRIMARY KEY (role_id));

ALTER TABLE `workorderdb`.`notification` 
    ADD CONSTRAINT notif_type_PK
        FOREIGN KEY (notification_type)
        REFERENCES notif_type (type_id);

ALTER TABLE `workorderdb`.`notification` 
    ADD CONSTRAINT created_by_FK 
        FOREIGN KEY (created_by) 
        REFERENCES users (user_id);

ALTER TABLE `workorderdb`.`notification` 
    ADD CONSTRAINT notif_work_center_FK 
        FOREIGN KEY (work_center_id) 
        REFERENCES work_center (work_center_id);

ALTER TABLE `workorderdb`.`notification` 
    ADD CONSTRAINT notif_status_PK
        FOREIGN KEY (status)
        REFERENCES status (status_id);

ALTER TABLE `workorderdb`.`work_order` 
    ADD CONSTRAINT order_type_PK
        FOREIGN KEY (order_type)
        REFERENCES order_type (type_id);

ALTER TABLE `workorderdb`.`work_order`
    ADD CONSTRAINT order_status_PK
        FOREIGN KEY (status)
        REFERENCES status (status_id);

ALTER TABLE `workorderdb`.`operations`
    ADD CONSTRAINT oper_work_order_FK 
        FOREIGN KEY (work_order_id) 
        REFERENCES work_order (work_order_id);

ALTER TABLE `workorderdb`.`operations`
    ADD CONSTRAINT oper_responsible_FK 
        FOREIGN KEY (person_responsible) 
        REFERENCES users (user_id);

ALTER TABLE `workorderdb`.`operations`
    ADD CONSTRAINT oper_status_PK
        FOREIGN KEY (status)
        REFERENCES status (status_id);
COMMIT;

INSERT INTO `status` VALUES (1, 'ASSIGNED');
INSERT INTO `status` VALUES (2, 'OPEN');
INSERT INTO `status` VALUES (3, 'CLOSED');
INSERT INTO `status` VALUES (4, 'PAUSED');
INSERT INTO `status` VALUES (5, 'STARTED');
INSERT INTO `status` VALUES (6, 'FINISHED');
INSERT INTO `status` VALUES (7, 'CANCELLED');

INSERT INTO `cause` VALUES (0001, 'CA-00NK', 'Unknown cause');
INSERT INTO `cause` VALUES (0002, 'CA-01FM', 'Failure related to maintenance');
INSERT INTO `cause` VALUES (0003, 'CA-02FMGT', 'Failure related to management');
INSERT INTO `cause` VALUES (0004, 'CA-03EWT', 'Expected wear and tear');
INSERT INTO `cause` VALUES (0005, 'CA-04DESI', 'Design related causes');
INSERT INTO `cause` VALUES (0006, 'CA-05IE', 'Instalation Error');
INSERT INTO `cause` VALUES (0007, 'CA-06OE', 'Operating Error');

INSERT INTO `damage` VALUES (1001, 'DA-01LE', 'Leakage');
INSERT INTO `damage` VALUES (1002, 'DA-02VI', 'Vibration');
INSERT INTO `damage` VALUES (1003, 'DA-03BR', 'Breakage');
INSERT INTO `damage` VALUES (1004, 'DA-04OH', 'Overheating');

INSERT INTO `plant` VALUES (2001, 'Edmonton Gas Plant', -113.354, 53.502, '3652 8 St NW, Edmonton, AB T6P 1R8');
INSERT INTO `plant` VALUES (2002, 'Fort McMurray Gas Plant', -111.468, 56.732, 'Thickwood Blvd, Fort McMurray, AB T9H 5R6');
INSERT INTO `plant` VALUES (2003, 'Calgary Gas Plant', -113.916, 51.082, 'Monterey Park, Calgary, AB T1Y 7H3');
INSERT INTO `plant` VALUES (2004, 'Grande Prairie Gas Plant', -118.706, 55.170,'45043 Township Rd 714, Grande Prairie, AB T8X 0B5');
INSERT INTO `plant` VALUES (2005, 'Red Deer Gas Plant', -113.747, 52.289, 'Blackfalds, AB T0M 0J0');
INSERT INTO `plant` VALUES (2006, 'Lethbridge Gas Plant', -112.777, 49.724, 'North Lethbridge, Lethbridge, AB T1H 5G1');

INSERT INTO `technical_object` VALUES (00000001, 'Motor Pump', 'Equipment');
INSERT INTO `technical_object` VALUES (00000002, 'Crane', 'Equipment');
INSERT INTO `technical_object` VALUES (00000003, 'Pressure Gauge', 'Equipment');
INSERT INTO `technical_object` VALUES (00000004, 'Pressure Vessel', 'Equipment');
INSERT INTO `technical_object` VALUES (00000005, 'Transmitter', 'Equipment');
INSERT INTO `technical_object` VALUES (00000006, 'Service Truck', 'Equipment');
INSERT INTO `technical_object` VALUES (00000007, 'Gas Turbine', 'Component');
INSERT INTO `technical_object` VALUES (00000008, 'Gas Heater', 'Component');
INSERT INTO `technical_object` VALUES (00000009, 'Boiler', 'Component');
INSERT INTO `technical_object` VALUES (00000010, 'Compressor', 'Component');
INSERT INTO `technical_object` VALUES (00000011, 'Pipeline', 'Component');

INSERT INTO `work_center` VALUES ('M-ELE', 'Electrical Maintenance');
INSERT INTO `work_center` VALUES ('M-MECH', 'Mechanical Maintenance');
INSERT INTO `work_center` VALUES ('M-EQ', 'Equipment Maintenance');
INSERT INTO `work_center` VALUES ('M-PM', 'Preventive Maintenance');

INSERT INTO `roles` VALUES (30001, 'Director', 'Admin');
INSERT INTO `roles` VALUES (30002, 'Department Manager', 'Admin');
INSERT INTO `roles` VALUES (30003, 'Work Center Lead', 'Advanced');
INSERT INTO `roles` VALUES (30004, 'Field Worker', 'Basic');

INSERT INTO `users` VALUES (40001, 'Bill', 'Adams', '$argon2id$v=19$m=12,t=20,p=2$77+977+9SO+/ve+/vWPvv71477+9NO+/ve+/vX1J77+977+977+977+977+9Ru+/ve+/ve+/vUZH77+977+977+977+9UO+/vSTvv70COu+/ve+/ve+/vQfvv70$ivinMa/nncP7Jk4mpoSmnLROKe5+5eDhYr1b8Iqv3HE'
, 30001, NULL,0,0);
INSERT INTO `users` VALUES (40002, 'Pepe', 'Sanders', '$argon2id$v=19$m=12,t=20,p=2$77+977+9Je+/vWkG77+977+9cxUUbEojf++/ve+/vQrvv71U77+9E145CB/vv73vv73vv73vv70PUmvvv73vv70H0act77+9$9pfMGK3o5dbGwrhCOZrHiJgNK2//834oXbxeI0tOXzs', 30002, NULL,0,0);
INSERT INTO `users` VALUES (40003, 'Dana', 'White', '$argon2id$v=19$m=12,t=20,p=2$77+9f++/ve+/vSjXuAzvv71b77+9OTrvv701IgPvv71Hau+/ve+/ve+/ve+/vQNid++/vRRk77+9S++/vS0e77+9L0vvv73vv70$hCrKmC7hnPAJA33/mCzCZawc3IE4mMSP7xfQo3WZ5Ac', 30002, NULL,0,0);
INSERT INTO `users` VALUES (40004, 'Katerina', 'Marcel', '$argon2id$v=19$m=12,t=20,p=2$77+977+9Xu+/vTfvv71VUgxQOO+/vVVI77+977+977+9FHgm77+977+9ShhN77+9zb7vv70t77+9MO+/ve+/ve+/vWjIg39q17I$3fKw+RAh21biH/76ATEnYuULdBji34Laae0/UHa58lU', 30003, 'M-ELE',0,0);
INSERT INTO `users` VALUES (40005, 'Bob', 'Lamb', '$argon2id$v=19$m=12,t=20,p=2$ae+/vSRo77+977+977+9WM6e77+9N3kKVhRa77+977+9ChY977+9x67vv70I77+9US/vv70cAu+/vQEv77+9NEBA77+9EQ$eQy6sXE3WPHkxExbGOiT+nPwIHCXDhM1twrAlc8fRfY', 30003, 'M-MECH',0,0);
INSERT INTO `users` VALUES (40006, 'Elwood', 'Adler', '$argon2id$v=19$m=12,t=20,p=2$PHcW77+9D0Xvv73vv73vv71yPjPvv70g77+9Re+/ve+/vTfvv703bM+CSTMZ77+9d37vv71UR++/vWfvv71CFgUKYDc$DwVO+iYSj6tH1ir0vfKtRCOOp3mog90yd26kFlPZ1FY', 30003, 'M-EQ',0,0);
INSERT INTO `users` VALUES (40007, 'Jamie', 'Strouse', '$argon2id$v=19$m=12,t=20,p=2$77+9Pj7vv73vv73vv73vv73vv71FClI077+977+977+9WkM0RxXvv712YO+/vVdbPemqvAbvv73vv73vv73vv73vv71e77+977+977+977+9$2yO7bKdcXnzIubUUpvsZCuHH283AAOpBdo0F15eZ5y8', 30003, 'M-PM',0,0);
INSERT INTO `users` VALUES (40010, 'Arthur', 'Luna', '$argon2id$v=19$m=12,t=20,p=2$77+94q6/Be+/vULvv71877+977+977+977+9Pu+/vTLvv73Nt3IA77+977+977+977+9Lu+/vQfqlrt577+9FO+/ve+/vUtCDxgC77+9$JfuUm6aw6/Wn09gAyiXBnGHYqSfs1B10/gPfDDNOT+A', 30004, 'M-ELE',0,0);
INSERT INTO `users` VALUES (40011, 'Brad', 'Mossman', '$argon2id$v=19$m=12,t=20,p=2$77+9b++/vU7vv73vv73vv73vv70g77+977+9IC/vv73vv73vv71RGlcZ77+9ODVh77+9WO+/ve+/ve+/vRd8Oe+/ve+/vQnvv70mJu+/vS8l77+9$JaXtAwpO1pYXokyWWwkEsuyVviIov2FxPgSKk2Lxetc', 30004, 'M-ELE',0,0);
INSERT INTO `users` VALUES (40012, 'Chadwick', 'Gunther', '$argon2id$v=19$m=12,t=20,p=2$77+977+977+9PAUU77+9QwT0g66IOnjvv73vv70NQUxr2Irvv70HTlwkBnsC77+977+9fO+/vVrvv73vv70m77+9Au+/vQ$PeuNIg4kRoRU4CoJjSUMWb4OSI/OLMK6HmH8Vo6Hi3o', 30004, 'M-ELE',0,0);
INSERT INTO `users` VALUES (40013, 'Gaston', 'Vincent', '$argon2id$v=19$m=12,t=20,p=2$cRzvv73vv73vv73vv71WJe+/ve+/ve+/ve+/vXJG77+9Ye+/ve+/vXp877+9H1jvv70y77+977+9Ee+/ve+/vT1p77+9XiFYdkjvv73vv73vv70Q$V3USds42uhzNkK5EhteGIcfNyX5UR82dWZgLeLp2HUM', 30004, 'M-MECH',0,0);
INSERT INTO `users` VALUES (40018, 'Jenae', 'Rambo', '$argon2id$v=19$m=12,t=20,p=2$YVhbUlxSXDDvv70577+977+977+9z50s77+977+9Iu+/vXw3ce+/ve+/ve+/ve+/ve+/vWbvv71QJGx5PQQc77+977+9ZA$DPLVZKxh1d/NBDhpEliqK36p33+8UWCw/tNRkMWDwSY', 30004, 'M-MECH',0,0);
INSERT INTO `users` VALUES (40019, 'Alexander', 'Laque', '$argon2id$v=19$m=12,t=20,p=2$GUNhVO+/ve+/vUPvv73vv701UEXvv70A77+977+977+9KRrvv71VYnQoG++/vRHvv716G++/vRTvv71hZ++/vVjvv73vv73KpGE$y5hwTzbNmS6RmxVu5WPn78jPdMXBRmB0t7FY+fccCbc',30004, 'M-MECH',0,0);
INSERT INTO `users` VALUES (40014, 'Marina', 'Roque', '$argon2id$v=19$m=12,t=20,p=2$77+977+977+9P++/vVwf77+9bu+/ve+/vR7vv70w77+977+9F8Okd23vv71E77+9IHbvv71aEO+/vSwaL++/vRwQfe+/ve+/ve+/ve+/ve+/vQ$pZkm+EbRPFgTx2Qn4HGjgscMncnzCHAz+s38zf4htXc',30004, 'M-EQ',0,0);
INSERT INTO `users` VALUES (40015, 'Austin', 'Simons', '$argon2id$v=19$m=12,t=20,p=2$fkHvv73vv70K77+9BELvv73vv70h77+9du+/vWQM77+9Zu+/vdyc77+9X1EZ77+9OXzvv70Te++/vQJLdHPvv73vv73vv73vv70$D9tdGca1HfcWLF5nQQyUj+F7dcoY+GZA2o47s+3B5mE',30004, 'M-EQ',0,0);
INSERT INTO `users` VALUES (40020, 'Omar', 'Stewards', '$argon2id$v=19$m=12,t=20,p=2$2YEo77+9DO+/vQLvv71J77+977+9Eng877+9QGF2Z1d8cWvvv70rKO+/ve+/vVbvv70aDUPvv73vv71Y77+9Ku+/ve+/vVls$81H5k7blXvUgWVOiJxsdW7kafXj7GIMayBkY0SM+WfE',30004, 'M-EQ',0,0);
INSERT INTO `users` VALUES (40016, 'Karl', 'Bell', '$argon2id$v=19$m=12,t=20,p=2$Gk3vv71a77+977+977+9S++/vVzvv73vv71277+9dO+/vTwxQu+/vRPvv70rOgQK77+977+977+977+9dVJx77+977+9NO+/ve+/vXA1$NzbUP3q9iMuIPGK7oDfcMpp90Pw+eRrX9pvctTlfg9g',30004, 'M-PM',0,0);
INSERT INTO `users` VALUES (40017, 'Roman', 'Groot', '$argon2id$v=19$m=12,t=20,p=2$CF7vv71m77+977+9HRjvv70h77+9XsyP77+9RQwl77+9Lxvvv70GPu+/ve+/ve+/vTwJ77+977+977+9EO+/vXN877+9GXHvv70$zIVG0FCWZ8y/86w9OZWb/vVfDqavezW2UoBBV1sfwpk',30004, 'M-PM',0,0);
INSERT INTO `users` VALUES (40021, 'Javier', 'Siegmund', '$argon2id$v=19$m=12,t=20,p=2$Q++/ve+/ve+/vSzvv73vv71m77+9T++/vXIs77+9Yu+/vXY8ARLvv73vv71wMu+/ve+/ve+/ve+/vWRAwpwM77+9Au+/vXEI77+977+9$z2CwPLYzN38UDQWunKislWrQNZiWl81ywAjXR6SJFTw',30004, 'M-PM',0,0);

INSERT INTO `notif_type` VALUES ('M1', 'Maintenance Request');
INSERT INTO `notif_type` VALUES ('M2', 'Malfunction Report');
INSERT INTO `notif_type` VALUES ('M3', 'Activity Report');

INSERT INTO `order_type` VALUES ('O1', 'General Order');
INSERT INTO `order_type` VALUES ('O2', 'Maintenance Order');
INSERT INTO `order_type` VALUES ('O3', 'Inspection Order');

INSERT INTO `notification` VALUES (1000001, 'M1', 2005, 1002, 0003, 00000002, 40001, 'M-EQ', 3);
INSERT INTO `notification` VALUES (1000002, 'M2', 2002, 1001, 0001, 00000001, 40002, 'M-PM',3);
INSERT INTO `notification` VALUES (1000003, 'M3', 2004, 1004, 0004, 00000003, 40003, 'M-MECH',3);

INSERT INTO `work_order` VALUES (8000001, 'O1', 'Crane has a vibration in hydrolics', '2021-05-09', '2021-05-15', 1000001, 3, '');
INSERT INTO `work_order` VALUES (8000002, 'O2', 'Motor Pump is leaking oil', '2020-11-01', '2020-11-05', 1000002, 3, '');
INSERT INTO `work_order` VALUES (8000003, 'O3', 'Pressure Gauge is stuck and not returning readings', '2021-02-27', '2021-03-01', 1000003, 3, '');

INSERT INTO `operations` VALUES (80101, 'Check and fix hydraulic oil condition', NULL, 8000001, 5, 40014);
INSERT INTO `operations` VALUES (80102, 'Check and fix pump suction line', NULL, 8000001, 5, 40015);
INSERT INTO `operations` VALUES (80103, 'Confirm hydraulic system works', NULL, 8000001, 5, 40020);
INSERT INTO `operations` VALUES (80201, 'Check mounting points are secure', NULL, 8000002, 5, 40016);
INSERT INTO `operations` VALUES (80202, 'Replace damage seals and hoses', NULL, 8000002, 5, 40017);
INSERT INTO `operations` VALUES (80203, 'Confirm leakage is fixed', NULL, 8000002, 5, 40021);
INSERT INTO `operations` VALUES (80301, 'Replace pressure gauge', NULL, 8000003, 5, 40013);
INSERT INTO `operations` VALUES (80302, 'Energize and put in operation', NULL, 8000003, 5, 40018);
INSERT INTO `operations` VALUES (80303, 'Confirm gauge working properly', NULL, 8000003, 5, 40019);
