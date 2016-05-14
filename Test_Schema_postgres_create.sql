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
	"name" character varying(100),
	CONSTRAINT examination_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "question" (
	"id" serial NOT NULL,
	"text" character varying(100),
	"subject" character varying(100),
	CONSTRAINT question_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "examination_2_question" (
	"examination_id" int NOT NULL,
	"question_id" int NOT NULL,
	CONSTRAINT examination_2_question_pk PRIMARY KEY ("examination_id")
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
	"text" character varying(100),
	"correct" BOOLEAN,
	CONSTRAINT answer_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "mistakes" (
	"result_id" int NOT NULL,
	"answer_id" int NOT NULL,
	CONSTRAINT mistakes_pk PRIMARY KEY ("result_id")
) WITH (
  OIDS=FALSE
);



ALTER TABLE "account_profile" ADD CONSTRAINT "account_profile_fk0" FOREIGN KEY ("id") REFERENCES "account"("id");


ALTER TABLE "examination" ADD CONSTRAINT "examination_fk0" FOREIGN KEY ("account_profile_id") REFERENCES "account_profile"("id");


ALTER TABLE "examination_2_question" ADD CONSTRAINT "examination_2_question_fk0" FOREIGN KEY ("examination_id") REFERENCES "examination"("id");
ALTER TABLE "examination_2_question" ADD CONSTRAINT "examination_2_question_fk1" FOREIGN KEY ("question_id") REFERENCES "question"("id");

ALTER TABLE "result" ADD CONSTRAINT "result_fk0" FOREIGN KEY ("examination_id") REFERENCES "examination"("id");
ALTER TABLE "result" ADD CONSTRAINT "result_fk1" FOREIGN KEY ("account_profile_id") REFERENCES "account_profile"("id");

ALTER TABLE "answer" ADD CONSTRAINT "answer_fk0" FOREIGN KEY ("question_id") REFERENCES "question"("id");

ALTER TABLE "mistakes" ADD CONSTRAINT "mistakes_fk0" FOREIGN KEY ("result_id") REFERENCES "result"("id");
ALTER TABLE "mistakes" ADD CONSTRAINT "mistakes_fk1" FOREIGN KEY ("answer_id") REFERENCES "answer"("id");

