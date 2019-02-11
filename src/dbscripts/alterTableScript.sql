/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mjapon
 * Created: 14/05/2018
 */

--Crecion de tabla de cajas

CREATE TABLE public.catcajas
(
    cc_id SERIAL PRIMARY KEY NOT NULL,
    cc_nombre VARCHAR(40),
    cc_observacion TEXT,
    cc_fechacreacion TIMESTAMP
);

CREATE UNIQUE INDEX catcajas_cc_id_uindex ON public.catcajas (cc_id);

insert into catcajas(cc_id,cc_nombre,cc_observacion,cc_fechacreacion) VALUES (1, 'GENERAL', 'CAJA GENERAL', now());

--Por defecto toda categoria creada va ha la CAJA GENERAL
ALTER TABLE public.categorias ADD cat_caja INT DEFAULT 1 NOT NULL;


ALTER TABLE public.catcajas ADD cc_status INT DEFAULT 0 NOT NULL;




CREATE TABLE public.caja
(
    cj_id SERIAL PRIMARY KEY NOT NULL,
    cj_estado INT DEFAULT 0 NOT NULL,
    cj_user INT DEFAULT 0 NOT NULL,
    cj_useranul INT,
    cj_fecaper TIMESTAMP NOT NULL,
    cj_fecanul TIMESTAMP,
    cj_obsaper TEXT,
    cj_obscierre TEXT,
    cj_obsanul TEXT,
    cj_feccierre TIMESTAMP
);
CREATE UNIQUE INDEX caja_cj_id_uindex ON public.caja (cj_id);


CREATE TABLE public.cajadet
(
    dc_id SERIAL PRIMARY KEY NOT NULL,
    cj_id INT NOT NULL,
    cc_id INT NOT NULL,
    dc_saldoini NUMERIC(15,4) DEFAULT 0.0,
    dc_ventas NUMERIC(15,4) DEFAULT 0.0,
    dc_aboncob NUMERIC(15,4) DEFAULT 0.0,
    dc_abonpag NUMERIC(15,4) DEFAULT 0.0,
    dc_ajuste NUMERIC(15,4) DEFAULT 0.0,
    dc_saldofin NUMERIC(15,4) DEFAULT 0.0,
    CONSTRAINT cajadet_caja_cj_id_fk FOREIGN KEY (cj_id) REFERENCES caja (cj_id),
    CONSTRAINT cajadet_catcajas_cc_id_fk FOREIGN KEY (cc_id) REFERENCES catcajas (cc_id)
);
CREATE UNIQUE INDEX cajadet_dc_id_uindex ON public.cajadet (dc_id);



ALTER TABLE public.cajadet ADD dc_saldoant NUMERIC(15,4) DEFAULT 0.0 NULL;


CREATE OR REPLACE FUNCTION get_iva(detfactid INTEGER) RETURNS NUMERIC
    AS 'select CASE WHEN d.detf_iva THEN 0.12*((d.detf_cant*d.detf_precio)-d.detf_desc) ELSE 0.0 end as iva from detallesfact d where d.detf_id=$1;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;


CREATE FUNCTION get_subtotal(detfactid INTEGER) RETURNS NUMERIC
    AS 'select (d.detf_cant*d.detf_precio)-d.detf_desc as subtotal from detallesfact d where d.detf_id=$1;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;


CREATE FUNCTION get_descgbydet(factid INTEGER) RETURNS NUMERIC
    AS 'select f.fact_descg/count(*) from detallesfact d join facturas f ON d.fact_id = f.fact_id
and f.fact_id = $1 GROUP BY d.fact_id, f.fact_descg;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;


CREATE FUNCTION get_porcdescg(factid INTEGER) RETURNS NUMERIC
    AS 'select f.fact_descg/f.fact_total from facturas f where f.fact_id = $1;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;


ALTER TABLE public.cajadet ADD dc_compras NUMERIC(15,4) DEFAULT 0.0 NULL;








-->VERSION FUSAY TICKETS


CREATE TABLE public.tickets
(
    tk_id SERIAL PRIMARY KEY NOT NULL,
    cli_id INT NOT NULL,
    tk_monto NUMERIC(10,4) DEFAULT 0.0 NOT NULL,
    tk_fecreg TIMESTAMP NOT NULL,
    tk_obs TEXT,
    tk_nro INT NOT NULL,
    CONSTRAINT tickets_clientes_cli_id_fk FOREIGN KEY (cli_id) REFERENCES clientes (cli_id)
);
CREATE UNIQUE INDEX tickets_tk_id_uindex ON public.tickets (tk_id);

insert into secuencias(sec_id, sec_clave, sec_valor) VALUES (4,'TIKSEC', '1');



ALTER SEQUENCE catcajas_cc_id_seq RESTART WITH 2;