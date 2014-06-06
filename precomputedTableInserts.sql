INSERT INTO productsales (SELECT products.id, states.state, sum(quantity*sales.price) AS sumamt
FROM products 
LEFT JOIN sales ON products.id = pid
JOIN users ON users.id = uid
JOIN states ON states.state = users.state
GROUP BY products.id, states.state);

INSERT INTO UserSales (
SELECT users.id, users.state, categories.name, SUM(quantity*sales.price) AS sumamt
FROM users 
LEFT JOIN sales ON users.id = uid
JOIN products ON pid = products.id
JOIN categories ON cid = categories.id
GROUP BY users.id, users.state, categories.name
);
