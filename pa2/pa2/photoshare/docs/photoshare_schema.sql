DROP VIEW comms;
DROP VIEW pics;

DROP TABLE Tags;
DROP TABLE Comments;
DROP TABLE Pictures;
DROP TABLE Friends;
DROP TABLE Albums;
DROP TABLE Users;

DROP SEQUENCE Pictures_picture_id_seq;
DROP SEQUENCE Albums_album_id_seq;
DROP SEQUENCE Tags_tag_id_seq;
DROP SEQUENCE Comments_comment_id_seq;
DROP SEQUENCE Users_user_id_seq;

CREATE SEQUENCE Users_user_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Users
(
  uid int4 NOT NULL DEFAULT nextval('Users_user_id_seq') PRIMARY KEY,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  dob varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role_name varchar(255) NOT NULL DEFAULT 'RegisteredUser',
  gender varchar(255),
  hometown_city varchar(255),
  hometown_state varchar(255),
  hometown_country varchar(255),
  current_city varchar(255),
  current_state varchar(255),
  current_country varchar(255),
  highschool varchar(255),
  highschool_grad varchar(255),
  college varchar(255),
  college_grad varchar(255)
);

CREATE TABLE Friends
(
  fid int4 NOT NULL REFERENCES Users(uid) ON DELETE CASCADE,
  uid int4 NOT NULL REFERENCES Users(uid) ON DELETE CASCADE
  -- CONSTRAINT friends_pk PRIMARY KEY (fid, uid)
);

CREATE SEQUENCE Albums_album_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Albums
(
  aid int4 NOT NULL DEFAULT nextval('Albums_album_id_seq') PRIMARY KEY,
  album_name varchar(255) NOT NULL,
  uid int4 NOT NULL REFERENCES Users(uid) ON DELETE CASCADE
);

CREATE SEQUENCE Pictures_picture_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Pictures
(
  picture_id int4 NOT NULL DEFAULT nextval('Pictures_picture_id_seq') PRIMARY KEY,
  album_id int4 NOT NULL REFERENCES Albums(aid) ON DELETE CASCADE,
  caption varchar(255) NOT NULL,
  imgdata bytea NOT NULL,
  size int4 NOT NULL,
  content_type varchar(255) NOT NULL,
  thumbdata bytea NOT NULL
); 

CREATE SEQUENCE Tags_tag_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Tags
(
  tid int4 NOT NULL DEFAULT nextval('Tags_tag_id_seq') PRIMARY KEY,
  tag_name varchar(255) NOT NULL,
  pid int4 NOT NULL REFERENCES Pictures(picture_id) ON DELETE CASCADE
);

CREATE SEQUENCE Comments_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Comments
(
  cid int4 NOT NULL DEFAULT nextval('Comments_comment_id_seq') PRIMARY KEY,
  comment_text varchar(255) NOT NULL,
  comment_date varchar(255) NOT NULL,
  uid int4 NOT NULL REFERENCES Users(uid) ON DELETE CASCADE,
  pid int4 NOT NULL REFERENCES Pictures(picture_id) ON DELETE CASCADE
);

INSERT INTO Users (first_name, last_name, email, dob, password) VALUES ('test', 'test', 'test@bu.edu', '2015-04-09', 'test');

INSERT INTO Users (uid, first_name, last_name, email, dob, password) VALUES (-1, 'anonymous', '', '', '', '');
