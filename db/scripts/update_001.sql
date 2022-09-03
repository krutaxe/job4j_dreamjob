CREATE TABLE IF NOT EXISTS post (
   id SERIAL PRIMARY KEY,
   name text,
   description text,
   visible boolean,
   created date,
   city_id INT
);

CREATE TABLE IF NOT EXISTS candidate (
   id SERIAL PRIMARY KEY,
   name text,
   description text,
   photo bytea,
   created timestamp
);
