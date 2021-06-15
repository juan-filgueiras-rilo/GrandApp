ALTER TABLE DBRoutine DROP COLUMN fecha_fin;
ALTER TABLE DBRoutine DROP COLUMN fecha_inicio;
ALTER TABLE DBRoutine DROP COLUMN accion;
ALTER TABLE DBRoutine DROP COLUMN dispositivo;
ALTER TABLE DBRoutine ADD COLUMN descripcion VARCHAR;
ALTER TABLE DBRoutine ADD COLUMN userId INTEGER;
ALTER TABLE DBRoutine ADD COLUMN hour INTEGER;
ALTER TABLE DBRoutine ADD COLUMN minute INTEGER;
ALTER TABLE DBRoutine ADD FOREIGN KEY (userId) REFERENCES DBUser(userId);
CREATE TABLE DiaEnum(id INTEGER, day VARCHAR, PRIMARY KEY (id));
CREATE TABLE Routine_day(Id INTEGER, day VARCHAR, FOREIGN KEY (Id) references DBRoutine (routineId) ON DELETE CASCADE,
FOREIGN KEY (day) REFERENCES DiaEnum (day));
CREATE TABLE Routine_devices(IdRoutine INTEGER, IdDevice INTEGER, FOREIGN KEY (IdRoutine) references DBRoutine(routineId) ON DELETE CASCADE,
FOREIGN KEY (IdDevice) references DBDevice (deviceId));
INSERT INTO DiaEnum (id, day) VALUES (0, "SUNDAY");
INSERT INTO DiaEnum (id, day) VALUES (1, "MONDAY");
INSERT INTO DiaEnum (id, day) VALUES (2, "TUESDAY");
INSERT INTO DiaEnum (id, day) VALUES (3, "WEDNESDAY");
INSERT INTO DiaEnum (id, day) VALUES (4, "THURSDAY");
INSERT INTO DiaEnum (id, day) VALUES (5, "FRIDAY");
INSERT INTO DiaEnum (id, day) VALUES (6, "SATURDAY");