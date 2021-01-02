CREATE   TABLE user_table (
   user_id SERIAL PRIMARY KEY,
   user_name varchar(255),
   email varchar(255),
   password varchar(255)
);


CREATE  TABLE user_login (
   user_login_id SERIAL PRIMARY KEY,
   user_id INTEGER REFERENCES user_table(user_id),
   token varchar(255),
   token_expire_time varchar(255)
);


INSERT INTO user_table (user_name , email , password)
VALUES ('razvan', 'razvan@gmail.com', 'razvanPassword');

INSERT INTO user_table (user_name , email , password)
VALUES('mihai', 'mihai@gmail.com', 'mihaiPassword');


INSERT INTO user_table (user_name , email , password)
VALUES('andrei', 'andrei@gmail.com','AndreisPassword');

---------


CREATE ROLE new_user LOGIN PASSWORD 'new_user_password';

REVOKE CONNECT ON DATABASE users  FROM PUBLIC;
GRANT CONNECT on DATABASE users  TO new_user;

GRANT USAGE ON SCHEMA public TO new_user;

GRANT ALL PRIVILEGES ON DATABASE users TO new_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO new_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO new_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO new_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO new_user;