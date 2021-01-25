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

CREATE TABLE watchlist_table (
    entry_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES user_table(user_id),
    position INTEGER NOT NULL,
    stock_symbol varchar(255) NOT NULL,
    stock_name varchar(255) NOT NULL,
);

--------------


CREATE ROLE agoramati_admin LOGIN PASSWORD 'agoramati_admin';

CREATE DATABASE agoramati_db WITH OWNER agoramati_admin;

REVOKE CONNECT ON DATABASE agoramati_db FROM PUBLIC;
GRANT CONNECT on DATABASE agoramati_db TO agoramati_admin;

GRANT USAGE ON SCHEMA public TO agoramati_admin;

GRANT ALL PRIVILEGES ON DATABASE agoramati_db TO agoramati_admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agoramati_admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agoramati_admin;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO agoramati_admin;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO agoramati_admin;