﻿DROP TABLE users CASCADE;
DROP TABLE categories CASCADE;
DROP TABLE products CASCADE;
DROP TABLE sales CASCADE;


/**table 1: [entity] users**/
CREATE TABLE users (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    role        TEXT NOT NULL,
    age   	INTEGER NOT NULL,
    state  	TEXT NOT NULL
);

/**table 2: [entity] category**/
CREATE TABLE categories (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT
);

/**table 3: [entity] product**/
CREATE TABLE products (
    id          SERIAL PRIMARY KEY,
    cid         INTEGER REFERENCES categories (id) ON DELETE CASCADE,
    name        TEXT NOT NULL,
    SKU         TEXT NOT NULL UNIQUE,
    price       INTEGER NOT NULL
);



/**table 4: [relation] carts**/
CREATE TABLE sales (
    id          SERIAL PRIMARY KEY,
    uid         INTEGER REFERENCES users (id) ON DELETE CASCADE,
    pid         INTEGER REFERENCES products (id) ON DELETE CASCADE,
    quantity    INTEGER NOT NULL,
    price	INTEGER NOT NULL
);

SELECT * FROM sales order by id desc;

CREATE TABLE carts (
    id          SERIAL PRIMARY KEY,
    uid         INTEGER REFERENCES users (id) ON DELETE CASCADE,
    pid         INTEGER REFERENCES products (id) ON DELETE CASCADE,
    quantity    INTEGER NOT NULL,
    price	INTEGER NOT NULL
);

CREATE TABLE ProdStatePrecomp (
    pid		INTEGER REFERENCES products (id) ON DELETE CASCADE,
    state	TEXT NOT NULL,
    sumamt	INTEGER
);

CREATE TABLE UsersCatPrecomp (
    uid		INTEGER REFERENCES users (id) ON DELETE CASCADE,
    category	TEXT NOT NULL,
    sumamt	INTEGER
);

CREATE TABLE StateCatPrecomp (
    state	TEXT NOT NULL,
    category	TEXT NOT NULL,
    sumamt	INTEGER
);

CREATE TABLE UsersCatProdStatePrecomp (
    uid		INTEGER REFERENCES users (id) ON DELETE CASCADE,
    category	TEXT NOT NULL,
    pid		INTEGER REFERENCES products (id) ON DELETE CASCADE,
    state	TEXT NOT NULL,
    sumamt	INTEGER
);

