--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: mikel; Tablespace: 
--

CREATE TABLE categories (
    id integer NOT NULL,
    name text,
    description text
);


ALTER TABLE public.categories OWNER TO mikel;

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: mikel
--

CREATE SEQUENCE categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categories_id_seq OWNER TO mikel;

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: mikel
--

ALTER SEQUENCE categories_id_seq OWNED BY categories.id;


--
-- Name: products; Type: TABLE; Schema: public; Owner: mikel; Tablespace: 
--

CREATE TABLE products (
    id integer NOT NULL,
    name text NOT NULL,
    sku integer NOT NULL,
    price numeric(7,2) NOT NULL,
    category integer,
    owner integer NOT NULL,
    CONSTRAINT products_price_check CHECK ((price >= (0)::numeric))
);


ALTER TABLE public.products OWNER TO mikel;

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: mikel
--

CREATE SEQUENCE products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO mikel;

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: mikel
--

ALTER SEQUENCE products_id_seq OWNED BY products.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: mikel; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    name text NOT NULL,
    age integer NOT NULL,
    state character(2) NOT NULL,
    owner boolean NOT NULL,
    CONSTRAINT users_age_check CHECK ((age > 0))
);


ALTER TABLE public.users OWNER TO mikel;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: mikel
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO mikel;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: mikel
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: mikel
--

ALTER TABLE ONLY categories ALTER COLUMN id SET DEFAULT nextval('categories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: mikel
--

ALTER TABLE ONLY products ALTER COLUMN id SET DEFAULT nextval('products_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: mikel
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: mikel
--

COPY categories (id, name, description) FROM stdin;
10	Electronics	buy it use it break it fix it smash it....;
2	Test	This is a test category.
11	Wood	All things made of wood
23	Toys	These are toys.
22	new	this is new
16	Food	yum
25	Turnt	Down
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: mikel
--

SELECT pg_catalog.setval('categories_id_seq', 30, true);


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: mikel
--

COPY products (id, name, sku, price, category, owner) FROM stdin;
2	ps3	2	599.99	10	1
8	serato	5	500.00	10	1
9	master sword	999	999.00	23	1
10	vallartas	7	10.00	16	1
11	lego	1891	149.99	23	1
12	desk	84	39.99	11	1
13	baseball bat	82	30.00	11	1
14	macbook	43	999.99	10	1
15	Up	11	0.00	25	1
17	double decker couch	864	777.00	22	1
18	microwave	35	100.00	10	1
21	SAT	2400	1.00	2	1
24	Cheese	333	3.00	16	1
25	aaa	123456	5.00	10	1
26	another	54321	9.00	10	1
4	dog	67	1.00	16	20
29	refrigerator	24	400.00	10	20
30	neon trees	101	0.01	22	1
1	ps4	1	399.99	10	1
\.


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: mikel
--

SELECT pg_catalog.setval('products_id_seq', 30, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: mikel
--

COPY users (id, name, age, state, owner) FROM stdin;
1	Mike	21	CA	t
2	Test	22	AL	t
5	eric	21	CA	t
19	Bryant	21	NE	t
20	Eric	21	CA	t
24	aaaaa	12	AL	f
25	Customer	2	CA	f
28	Yannis	55	CA	t
29	Consumer	14	AR	f
30	Mc'Davis	12	AL	f
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: mikel
--

SELECT pg_catalog.setval('users_id_seq', 30, true);


--
-- Name: categories_name_key; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_name_key UNIQUE (name);


--
-- Name: categories_pkey; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: products_name_key; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_name_key UNIQUE (name);


--
-- Name: products_pkey; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: products_sku_key; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_sku_key UNIQUE (sku);


--
-- Name: users_name_key; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_name_key UNIQUE (name);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: mikel; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: products_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: mikel
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_category_fkey FOREIGN KEY (category) REFERENCES categories(id);


--
-- Name: products_owner_fkey; Type: FK CONSTRAINT; Schema: public; Owner: mikel
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_owner_fkey FOREIGN KEY (owner) REFERENCES users(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

