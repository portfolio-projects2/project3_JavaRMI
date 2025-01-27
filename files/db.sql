CREATE DATABASE db;
USE db;
CREATE TABLE User(
    ID int(5) unsigned auto_increment Primary key,
    username varchar(15) not null,
    hashedPassword varchar(50) not null,
    salt varchar(50) not null
);
INSERT INTO User(username, hashedPassword, salt) values ('alice23','r9zwUzv7zAA5LSbrv/NIaMqPHcNfay8NSoVWYJ5VE1I=' ,'MMq/BHKa5Lva/OwRduHi7g==');
INSERT INTO User(username, hashedPassword, salt) values ('bob23','gTnUhEIuNMf0AuTXq103PUkB0zA8+ZLMOTnSsVlGro8=','KrJr//&jijss--DDjkg');
INSERT INTO User(username, hashedPassword, salt) values('cecilia23','hUTVY0/GCGRBqhfY2vRFJ/ztbBBNtwYcHeIZ3djEbk0=','tyyHKD//787dddd');
INSERT INTO User(username, hashedPassword, salt) values('david23','bAE/LBs+XxLyWVaWurIP4H4E88zG8wC7IaEosuiP+/8=','njnkjDFDG9//');
INSERT INTO User(username, hashedPassword, salt) values('erica23','IomW9HaYgsOZPO9tL7p3Qo4QbqnNH44KtzQ7MFuI5fQ=','bhbhubuRRR/&%REF--');
INSERT INTO User(username, hashedPassword, salt) values('fred23','t1b2FXFFOGrhFNIDgBEsgkjA+As4nNnXM/o7VEjfvCM=','JNIvbgyGHV88_i##mko');
INSERT INTO User(username, hashedPassword, salt) values('george23','7YMkx84ti9swNvuzK3Q8BNYy9rreBzNH5w18zSK0/io=','HTUnsb(rjbd--frrt');
INSERT INTO User(username, hashedPassword, salt) values('ida23','9BRtPzmRpFd41F9Tlt6XG+XIdz0xVh8UCM31/Ax5QYM=','3Q8BNYy9rreBzNH5');
INSERT INTO User(username, hashedPassword, salt) values('henry23','1dsYWFrJDGaOs/H4f4RQCOIBIj5KWaNsaKzZzOCNu4s=','8wC7IaEosuiP+/8=');

