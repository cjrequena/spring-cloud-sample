-- Database: foo

-- DROP DATABASE foo;

CREATE DATABASE foo WITH  OWNER = postgres;

\c foo

CREATE SEQUENCE public.boo_id_seq;

ALTER SEQUENCE public.boo_id_seq OWNER TO postgres;

CREATE SEQUENCE public.foo_id_seq;

ALTER SEQUENCE public.foo_id_seq OWNER TO postgres;

-- DROP TABLE public.foo;

CREATE TABLE public.foo
(
    id integer NOT NULL DEFAULT nextval('foo_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    creation_date date DEFAULT CURRENT_DATE,
    CONSTRAINT foo_pkey PRIMARY KEY (id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;

ALTER TABLE public.foo OWNER to postgres;

-- Table: public.boo

-- DROP TABLE public.boo;

CREATE TABLE public.boo
(
    id integer NOT NULL DEFAULT nextval('boo_id_seq'::regclass),
    foo_id integer,
    name text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    creation_date date DEFAULT CURRENT_DATE,
    CONSTRAINT boo_pkey PRIMARY KEY (id),
    CONSTRAINT boo_id_foo_id_key UNIQUE (id, foo_id),
    CONSTRAINT fk_foo_id FOREIGN KEY (foo_id)
    REFERENCES public.foo (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;

ALTER TABLE public.boo OWNER to postgres;
