CREATE TABLE "imagem"
(
    "id" BIGINT PRIMARY KEY NOT NULL,
    "caminho" VARCHAR(50) NOT NULL,
    "nome" VARCHAR(50) NOT NULL,
    "width" INTEGER NOT NULL,
    "height" INTEGER NOT NULL
);

CREATE SEQUENCE imagem_id_seq INCREMENT 1;

