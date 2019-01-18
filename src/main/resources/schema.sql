--create database game_app;
--use game_app;
--show tables;

CREATE TABLE user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT user_pk PRIMARY KEY (id),
  CONSTRAINT user_un UNIQUE KEY (username)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE layer (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT layer_pk PRIMARY KEY (id),
  CONSTRAINT layer_un UNIQUE KEY (name)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE game (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT game_pk PRIMARY KEY (id),
  CONSTRAINT game_un UNIQUE KEY (name)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE rank (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT rank_pk PRIMARY KEY (id),
  CONSTRAINT rank_un UNIQUE KEY (name)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE language (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  abbreviation varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT language_pk PRIMARY KEY (id),
  CONSTRAINT language_un_n UNIQUE KEY (name),
  CONSTRAINT language_un_a UNIQUE KEY (abbreviation)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE genre (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT genre_pk PRIMARY KEY (id),
  CONSTRAINT genre_un UNIQUE KEY (name)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE tag (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT tag_pk PRIMARY KEY (id),
  CONSTRAINT tag_un UNIQUE KEY (name)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci; 

CREATE TABLE video (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  path varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT video_pk PRIMARY KEY (id),
  CONSTRAINT video_un_n UNIQUE KEY (name),
  CONSTRAINT video_un_p UNIQUE KEY (path)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE screenshot (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  path varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT screenshot_pk PRIMARY KEY (id),
  CONSTRAINT screenshot_un_n UNIQUE KEY (name),
  CONSTRAINT screenshot_un_p UNIQUE KEY (path)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE user_layer (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL,
  layer_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT user_layer_pk PRIMARY KEY (id),
  CONSTRAINT user_layer_user_fk FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT user_layer_layer_fk FOREIGN KEY (layer_id) REFERENCES layer (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE user_layer_game (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_layer_id bigint(20) NOT NULL,
  game_id bigint(20) NOT NULL,
  rank_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT user_game_pk PRIMARY KEY (id),
  KEY user_game_user_layer_fk (user_layer_id),
  CONSTRAINT user_game_user_layer_fk FOREIGN KEY (user_layer_id) REFERENCES user_layer (id),
  CONSTRAINT user_game_game_fk FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT user_game_rank_fk FOREIGN KEY (rank_id) REFERENCES rank (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE game_language (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  game_id bigint(20) NOT NULL,
  language_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT game_language_pk PRIMARY KEY (id),
  CONSTRAINT game_language_game_fk FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT game_language_language_fk FOREIGN KEY (language_id) REFERENCES language (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE game_genre (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  game_id bigint(20) NOT NULL,
  genre_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT game_genre_pk PRIMARY KEY (id),
  CONSTRAINT game_genre_game_fk FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT game_genre_genre_fk FOREIGN KEY (genre_id) REFERENCES genre (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE game_tag (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  game_id bigint(20) NOT NULL,
  tag_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT game_tag_pk PRIMARY KEY (id),
  CONSTRAINT game_tag_game_fk FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT game_tag_tag_fk FOREIGN KEY (tag_id) REFERENCES tag (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE game_video (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  game_id bigint(20) NOT NULL,
  video_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT game_video_pk PRIMARY KEY (id),
  CONSTRAINT game_video_game_fk FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT game_video_video_fk FOREIGN KEY (video_id) REFERENCES video (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE game_screenshot (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  game_id bigint(20) NOT NULL,
  screenshot_id bigint(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT game_screenshot_pk PRIMARY KEY (id),
  CONSTRAINT game_screenshot_game_fk FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT game_screenshot_screenshot_fk FOREIGN KEY (screenshot_id) REFERENCES screenshot (id)
);--ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

