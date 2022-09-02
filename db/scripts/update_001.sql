create TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
	visibl boolean,
	data TEXT,
	city_id INT
);


CREATE TABLE candidate (
	id SERIAL PRIMARY KEY,
	name TEXT,
	description TEXT,
	data TEXT,
	photo integer[]
);
