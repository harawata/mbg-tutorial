DROP TABLE person IF EXISTS;
CREATE TABLE person (
  id int,
  name varchar(32),
  gender varchar(8),
  email varchar(255),
  PRIMARY KEY (id)
);

DROP TABLE pet IF EXISTS;
CREATE TABLE pet (
  pet_id int,
  owner_id int,
  pet_name varchar(32),
  PRIMARY KEY (pet_id)
);

INSERT INTO person (id, name, gender, email) VALUES (1, 'yamada tarou', 'male', 'tarou@example.com');
INSERT INTO person (id, name, gender, email) VALUES (2, 'yamada hanako', 'female', 'hanako@example.com');

INSERT INTO pet (pet_id, owner_id, pet_name) VALUES (11, 1, 'taro');
INSERT INTO pet (pet_id, owner_id, pet_name) VALUES (12, 1, 'jiro');
INSERT INTO pet (pet_id, owner_id, pet_name) VALUES (13, 2, 'pochi');
INSERT INTO pet (pet_id, owner_id, pet_name) VALUES (14, 2, 'tama');
INSERT INTO pet (pet_id, owner_id, pet_name) VALUES (15, 2, 'mike');
