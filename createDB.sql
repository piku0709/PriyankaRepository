CREATE DATABASE 'C:\TestDB\testDb.db' LOG ON 'testDb.log' COLLATION '1252LATIN1' NCHAR COLLATION 'UCA' DBA USER 'testdb' DBA PASSWORD '***';



dbinit -z "1252LATIN1" -zn "UCA" -dba "testdb","***" -t "testDb.log" "C:\TestDB\testDb.db"


create table STU_INFO
(   SCH_YR		   char(4)	    not null,
    CAMPUS_ID      char(3)     not null,
    STU_ID         char(4)     not null,
	STU_NAME       char(45)    not null default '',
	ENTRY_DT       char(10)    not null default '',
	GRADE		   char(2)		not null
    primary key (SCH_YR, CAMPUS_ID, STU_ID, GRADE)
);



