INSERT INTO demo(name, age, hobby)
     VALUES ('하니', 14, '달리기');
INSERT INTO demo(name, age, hobby)
     VALUES ('홍길동', 18, '반항');

INSERT INTO user 
     VALUES ('abc', '$2a$10$0HnXJMA2TLbYt1vrh9h5TeXAotSsrnt9W6mz2lODFwO1j5CaZT/iS', '사용자', true, true, true, true); 
INSERT INTO user 
     VALUES ('admin', '$2a$10$0HnXJMA2TLbYt1vrh9h5TeXAotSsrnt9W6mz2lODFwO1j5CaZT/iS', '관리자', true, true, true, true); 

INSERT INTO authority 
     VALUES ('admin', 'ADMIN'); 
INSERT INTO authority 
     VALUES ('admin', 'USER'); 
INSERT INTO authority 
     VALUES ('abc', 'USER');