/* Populate tables */

INSERT INTO region (id, nombre) VALUES (1,'Sudamérica');
INSERT INTO region (id, nombre) VALUES (2,'Centroamérica');
INSERT INTO region (id, nombre) VALUES (3,'Norteamérica');
INSERT INTO region (id, nombre) VALUES (4,'Europa');
INSERT INTO region (id, nombre) VALUES (5,'Asia');
INSERT INTO region (id, nombre) VALUES (6,'África');
INSERT INTO region (id, nombre) VALUES (7,'Oceanía');
INSERT INTO region (id, nombre) VALUES (8,'Antártida');

INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(2,'John', 'Doe', 'john.doe@gmail.com', '2017-08-02');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(4,'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(4,'Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04' );
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(4,'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(3,'Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(3,'Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(3,'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(3,'John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(3,'James', 'Gosling', 'james.gosling@gmail.com', '2017-08-10');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(5,'Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(6,'Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(7,'John', 'Roe', 'john.roe@gmail.com', '2017-08-13');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(7,'Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19'); 
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'John', 'Smith', 'john.smith@gmail.com', '2017-08-22');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24');
INSERT INTO cliente (region_id, nombre, apellido, email, create_at) VALUES(1,'Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25');

INSERT INTO usuario (username,password,enabled, nombre, apellido, email) VALUES ('nico','$2a$10$vsHu4jFR7tb05SEeT03aIO1FdwKOpVg2C8PQ5Rdo8htu2aKNBCw0.',1,'Nicolas', 'Pfleger', 'nikopfleger@gmail.com');
INSERT INTO usuario (username,password,enabled, nombre, apellido, email) VALUES ('admin','$2a$10$Qhn2Lp0ovSJk7dW7ZL2Ve.vSBe0/E6O0KwyGxmRN55DOhrDXZ/5NW',1,'Tamara', 'Cardozo', 'c7@gmail.com');

INSERT INTO role (nombre) VALUES ('ROLE_USER');
INSERT INTO role (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuario_role (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (2,1);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (2,2);

/* Populate tabla producto */
INSERT INTO producto (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO producto (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO producto (nombre, precio, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO producto (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO producto (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO producto (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO producto (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

/* Creamos algunas factura */
INSERT INTO factura (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());

INSERT INTO item_factura (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO item_factura (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO item_factura (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO item_factura (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO factura (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO item_factura (cantidad, factura_id, producto_id) VALUES(3, 2, 6);
