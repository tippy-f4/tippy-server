DROP TABLE IF EXISTS praises;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    id varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    nick_name varchar(255) NOT NULL,
    face_image varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE cards (
    id varchar(36) NOT NULL,
    message text NOT NULL,
    employee_id varchar(36) NOT NULL,
    created_at timestamp NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE praises (
    card_id varchar(36) NOT NULL,
    employee_id varchar(36) NOT NULL,
    CONSTRAINT fk_card_id FOREIGN KEY (card_id) REFERENCES cards (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

\set user_1 '11111111-1111-1111-1111-111111111111'
\set user_2 '22222222-2222-2222-2222-222222222222'
\set user_3 '33333333-3333-3333-3333-333333333333'

-- initial data
INSERT INTO employees
    (id, name, nick_name, face_image)
VALUES
    (:'user_1', '金美鈴', 'みりょん', 'http://i.pravatar.cc/150?img=5'),
    (:'user_2', '泉せいや', 'seiya', 'http://i.pravatar.cc/150?img=8'),
    (:'user_3', '井出優太', 'いでっち', 'http://i.pravatar.cc/150?img=9')
;

\set card_1 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'
\set card_2 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'
\set card_3 'cccccccc-cccc-cccc-cccc-cccccccccccc'

INSERT INTO cards
    (id, message, employee_id, created_at)
VALUES
(:'card_1', 'ありがとう', :'user_1', current_timestamp),
(:'card_2', 'Thank you', :'user_2', (current_timestamp + interval '1 hour')),
(:'card_3', 'Merci', :'user_3', (current_timestamp + interval '2 hour'))
;

INSERT INTO praises
    (card_id, employee_id)
VALUES
(:'card_1', :'user_2'),
(:'card_2', :'user_1'),
(:'card_2', :'user_3')
;
