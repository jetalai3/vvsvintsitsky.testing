CREATE TABLE "account_profile" (
	"id" serial NOT NULL,
	"first_name" character varying(100),
	"last_name" character varying(100),
	CONSTRAINT account_profile_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "account" (
	"id" serial NOT NULL,
	"password" character varying(100),
	"email" character varying(100),
	"role" int,
	CONSTRAINT account_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "examination" (
	"id" serial NOT NULL,
	"account_profile_id" int NOT NULL,
	"begin_date" TIMESTAMP,
	"end_date" TIMESTAMP,
	"examination_names_id" int NOT NULL,
	"subject_id" int NOT NULL,
	CONSTRAINT examination_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "question" (
	"id" serial NOT NULL,
	"subject_id" int,
	"question_texts_id" int NOT NULL,
	CONSTRAINT question_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "examination_2_question" (
	"examination_id" int NOT NULL,
	"question_id" int NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE "result" (
	"id" serial NOT NULL,
	"examination_id" int NOT NULL,
	"account_profile_id" int NOT NULL,
	"points" int,
	CONSTRAINT result_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "answer" (
	"id" serial NOT NULL,
	"question_id" int NOT NULL,
	"correct" BOOLEAN,
	"answer_texts_id" int NOT NULL,
	CONSTRAINT answer_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "mistakes" (
	"result_id" int NOT NULL,
	"answer_id" int NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE "subject" (
	"id" serial NOT NULL,
	"subject_names_id" int NOT NULL,
	CONSTRAINT subject_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "local_texts" (
	"id" serial NOT NULL,
	"rus_text_id" int NOT NULL,
	"eng_text_id" int NOT NULL,
	CONSTRAINT local_texts_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "various_texts" (
	"id" serial NOT NULL,
	"txt" character varying(255) NOT NULL,
	CONSTRAINT various_texts_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



ALTER TABLE "account_profile" ADD CONSTRAINT "account_profile_fk0" FOREIGN KEY ("id") REFERENCES "account"("id");


ALTER TABLE "examination" ADD CONSTRAINT "examination_fk0" FOREIGN KEY ("account_profile_id") REFERENCES "account_profile"("id");
ALTER TABLE "examination" ADD CONSTRAINT "examination_fk1" FOREIGN KEY ("examination_names_id") REFERENCES "local_texts"("id");
ALTER TABLE "examination" ADD CONSTRAINT "examination_fk2" FOREIGN KEY ("subject_id") REFERENCES "subject"("id");

ALTER TABLE "question" ADD CONSTRAINT "question_fk0" FOREIGN KEY ("subject_id") REFERENCES "subject"("id");
ALTER TABLE "question" ADD CONSTRAINT "question_fk1" FOREIGN KEY ("question_texts_id") REFERENCES "local_texts"("id");

ALTER TABLE "examination_2_question" ADD CONSTRAINT "examination_2_question_fk0" FOREIGN KEY ("examination_id") REFERENCES "examination"("id");
ALTER TABLE "examination_2_question" ADD CONSTRAINT "examination_2_question_fk1" FOREIGN KEY ("question_id") REFERENCES "question"("id");

ALTER TABLE "result" ADD CONSTRAINT "result_fk0" FOREIGN KEY ("examination_id") REFERENCES "examination"("id");
ALTER TABLE "result" ADD CONSTRAINT "result_fk1" FOREIGN KEY ("account_profile_id") REFERENCES "account_profile"("id");

ALTER TABLE "answer" ADD CONSTRAINT "answer_fk0" FOREIGN KEY ("question_id") REFERENCES "question"("id");
ALTER TABLE "answer" ADD CONSTRAINT "answer_fk1" FOREIGN KEY ("answer_texts_id") REFERENCES "local_texts"("id");

ALTER TABLE "mistakes" ADD CONSTRAINT "mistakes_fk0" FOREIGN KEY ("result_id") REFERENCES "result"("id");
ALTER TABLE "mistakes" ADD CONSTRAINT "mistakes_fk1" FOREIGN KEY ("answer_id") REFERENCES "answer"("id");

ALTER TABLE "subject" ADD CONSTRAINT "subject_fk0" FOREIGN KEY ("subject_names_id") REFERENCES "local_texts"("id");

ALTER TABLE "local_texts" ADD CONSTRAINT "local_texts_fk0" FOREIGN KEY ("rus_text_id") REFERENCES "various_texts"("id");
ALTER TABLE "local_texts" ADD CONSTRAINT "local_texts_fk1" FOREIGN KEY ("eng_text_id") REFERENCES "various_texts"("id");


