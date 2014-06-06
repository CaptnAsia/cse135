INSERT INTO ProdStatePrecomp (
SELECT products.id, states.state, SUM(quantity*sales.price) AS sumamt
FROM products 
LEFT JOIN sales ON products.id = pid
JOIN users ON users.id = uid
JOIN states ON states.state = users.state
GROUP BY products.id, states.state
);

INSERT INTO UsersCatPrecomp (
SELECT users.id, categories.name, SUM(quantity*sales.price) AS sumamt
FROM users 
LEFT JOIN sales ON users.id = uid
JOIN products ON pid = products.id
JOIN categories ON cid = categories.id
GROUP BY users.id, users.state, categories.name
);

INSERT INTO StateCatPrecomp (
SELECT users.state, categories.name, SUM(quantity*sales.price) AS sumamt
FROM users 
LEFT JOIN sales ON users.id = uid
JOIN products ON pid = products.id
JOIN categories ON cid = categories.id
GROUP BY users.state, categories.name
);

INSERT INTO UsersCatProdStatePrecomp (
SELECT users.id, categories.name, products.id, users.state, SUM(quantity*sales.price) AS sumamt
FROM users
LEFT JOIN sales ON users.id = uid
JOIN products ON pid = products.id
JOIN categories ON cid = categories.id
GROUP BY users.id, products.id, categories.name, users.state
);
