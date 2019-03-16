DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    id varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    nick_name varchar(255) NOT NULL,
    face_image varchar(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS cards;

CREATE TABLE cards (
    id varchar(36) NOT NULL,
    message text NOT NULL,
    employee_id varchar(36) NOT NULL,
    created_at datetime NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET @user_1 = '11111111-1111-1111-1111-111111111111';
SET @user_2 = '22222222-2222-2222-2222-222222222222';
SET @user_3 = '33333333-3333-3333-3333-333333333333';


-- initial data
INSERT INTO employees
    (id, name, nick_name, face_image)
VALUES
    (@user_1, '金美鈴', 'みりょん', 'http://example.com/miryon'),
    (@user_2, '泉せいや', 'seiya', 'http://example.com/seiya'),
    (@user_3, '井出優太', 'いでっち', 'http://example.com/idetti')
;

SET @card_1 = 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa';
SET @card_2 = 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb';
SET @card_3 = 'cccccccc-cccc-cccc-cccc-cccccccccccc';

SET @created_time = NOW()

INSERT INTO cards
    (id, message, employee_id, created_at)
VALUES
(@card_1, 'ありがとう', @user_1, @created_time),
(@card_2, 'Thank you', @user_2,(@created_time + INTERVAL 1 HOUR)),
(@card_3, 'Merci', @user_3, (@created_time + INTERVAL 2 HOUR))
;

