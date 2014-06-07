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

CREATE TABLE UsersPrecomp (
    uid		INTEGER REFERENCES users (id) ON DELETE CASCADE,
    sumamt	INTEGER
);

CREATE TABLE ProdPrecomp (
    pid		INTEGER REFERENCES products (id) ON DELETE CASCADE,
    sumamt	INTEGER
);

CREATE TABLE StatesPrecomp (
    state	TEXT NOT NULL,
    sumamt	INTEGER
);
