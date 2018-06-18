-- Insert Droids
insert into character (id, name, primary_function, dtype) values ('2000', 'C-3PO', 'Protocol', 'Droid');
insert into character (id, name, primary_function, dtype) values ('2001', 'R2-D2', 'Astromech', 'Droid');

-- Insert Humans
insert into character (id, name, home_planet, favorite_droid_id, dtype) values ('1000', 'Luke Skywalker', 'Tatooine', '2000', 'Human');
insert into character (id, name, home_planet, favorite_droid_id, dtype) values ('1001', 'Darth Vader', 'Tatooine', '2001', 'Human');
insert into character (id, name, home_planet, favorite_droid_id, dtype) values ('1002', 'Han Solo', NULL, NULL, 'Human');
insert into character (id, name, home_planet, favorite_droid_id, dtype) values ('1003', 'Leia Organa', 'Alderaan', NULL, 'Human');
insert into character (id, name, home_planet, favorite_droid_id, dtype) values ('1004', 'Wilhuff Tarkin', NULL, NULL, 'Human');

-- Luke's friends
insert into character_friends (source_id, friend_id) values ('1000', '1002');
insert into character_friends (source_id, friend_id) values ('1000', '1003');
insert into character_friends (source_id, friend_id) values ('1000', '2000');
insert into character_friends (source_id, friend_id) values ('1000', '2001');

-- Luke Appears in
insert into character_appearsIn (character_id, appearsIn) values ('1000', 3);
insert into character_appearsIn (character_id, appearsIn) values ('1000', 4);
insert into character_appearsIn (character_id, appearsIn) values ('1000', 5);
insert into character_appearsIn (character_id, appearsIn) values ('1000', 6);

-- Vader's friends
insert into character_friends (source_id, friend_id) values ('1001', '1004');

-- Vader Appears in
insert into character_appearsIn (character_id, appearsIn) values ('1001', 3);
insert into character_appearsIn (character_id, appearsIn) values ('1001', 4);
insert into character_appearsIn (character_id, appearsIn) values ('1001', 5);

-- Solo's friends
insert into character_friends (source_id, friend_id) values ('1002', '1000');
insert into character_friends (source_id, friend_id) values ('1002', '1003');
insert into character_friends (source_id, friend_id) values ('1002', '2001');

-- Solo Appears in
insert into character_appearsIn (character_id, appearsIn) values ('1002', 3);
insert into character_appearsIn (character_id, appearsIn) values ('1002', 4);
insert into character_appearsIn (character_id, appearsIn) values ('1002', 5);
insert into character_appearsIn (character_id, appearsIn) values ('1002', 6);

-- Leia's friends
insert into character_friends (source_id, friend_id) values ('1003', '1000');
insert into character_friends (source_id, friend_id) values ('1003', '1002');
insert into character_friends (source_id, friend_id) values ('1003', '2000');
insert into character_friends (source_id, friend_id) values ('1003', '2001');

-- Leia Appears in
insert into character_appearsIn (character_id, appearsIn) values ('1003', 3);
insert into character_appearsIn (character_id, appearsIn) values ('1003', 4);
insert into character_appearsIn (character_id, appearsIn) values ('1003', 5);
insert into character_appearsIn (character_id, appearsIn) values ('1003', 6);

-- Wilhuff's friends
insert into character_friends (source_id, friend_id) values ('1004', '1001');

-- Wilhuff Appears in
insert into character_appearsIn (character_id, appearsIn) values ('1004', 3);

-- C3PO's friends
insert into character_friends (source_id, friend_id) values ('2000', '1000');
insert into character_friends (source_id, friend_id) values ('2000', '1002');
insert into character_friends (source_id, friend_id) values ('2000', '1003');
insert into character_friends (source_id, friend_id) values ('2000', '2001');

-- C3PO Appears in
insert into character_appearsIn (character_id, appearsIn) values ('2000', 3);
insert into character_appearsIn (character_id, appearsIn) values ('2000', 4);
insert into character_appearsIn (character_id, appearsIn) values ('2000', 5);
insert into character_appearsIn (character_id, appearsIn) values ('2000', 6);

-- R2's friends
insert into character_friends (source_id, friend_id) values ('2001', '1000');
insert into character_friends (source_id, friend_id) values ('2001', '1002');
insert into character_friends (source_id, friend_id) values ('2001', '1003');

-- R2 Appears in
insert into character_appearsIn (character_id, appearsIn) values ('2001', 3);
insert into character_appearsIn (character_id, appearsIn) values ('2001', 4);
insert into character_appearsIn (character_id, appearsIn) values ('2001', 5);
insert into character_appearsIn (character_id, appearsIn) values ('2001', 6);