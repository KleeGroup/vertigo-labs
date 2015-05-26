-- ************************
-- Parametrage
-- ************************
set @tbs_data = 'pg_default';
set @tbs_index = 'pg_default';
set @user_role = 'snowblood';
set @base_name = 'tdc';

-- ************************
-- Rejeu
-- ************************
/*
Drop sequence seq_jobdefinition;
Drop sequence seq_jobdexecution;
DROP TABLE job_delta_complet;
DROP TABLE job_etat;
DROP TABLE job_mode;
DROP TABLE job_rejet;
DROP TABLE job_sens;
DROP INDEX idx_jod_code;
DROP INDEX jobdefinition_jdc_cd_fk;
DROP INDEX jobdefinition_jre_cd_fk;
DROP INDEX jobdefinition_jse_cd_fk;
DROP TABLE jobdefinition;
DROP INDEX jobexecution_jet_cd_fk;
DROP INDEX jobexecution_jmo_cd_fk;
DROP INDEX jobexecution_jod_id_fk;
DROP TABLE jobexecution;
*/

-- ************************
-- Objets
-- ************************

-- Table: job_delta_complet
CREATE TABLE job_delta_complet (
  jdc_cd  character varying(20) NOT NULL, -- JDC_CD
  libelle character varying(50),          -- Libellé
  CONSTRAINT pk_job_delta_complet PRIMARY KEY (jdc_cd)
  USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE job_delta_complet OWNER TO @user_role;
GRANT ALL ON TABLE job_delta_complet TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE job_delta_complet TO @user_role;

-- Table: job_etat
CREATE TABLE job_etat (
  jet_cd character varying(20) NOT NULL, -- JET_CD
  libelle character varying(150),        -- Libellé
  CONSTRAINT pk_job_etat PRIMARY KEY (jet_cd)
  USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE job_etat OWNER TO @user_role;
GRANT ALL ON TABLE job_etat TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE job_etat TO @user_role;

-- Table: job_mode
CREATE TABLE job_mode (
  jmo_cd character varying(20) NOT NULL, -- JMO_CD
  libelle character varying(150),        -- Libellé
  CONSTRAINT pk_job_mode PRIMARY KEY (jmo_cd)
  USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE job_mode OWNER TO @user_role;
GRANT ALL ON TABLE job_mode TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE job_mode TO @user_role;

-- Table: job_rejet
CREATE TABLE job_rejet (
  jre_cd character varying(20) NOT NULL, -- JRE_CD
  libelle character varying(150),        -- Libellé
  CONSTRAINT pk_job_rejet PRIMARY KEY (jre_cd)
  USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE job_rejet OWNER TO @user_role;
GRANT ALL ON TABLE job_rejet TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE job_rejet TO @user_role;

-- Table: job_sens
CREATE TABLE job_sens (
  jse_cd character varying(20) NOT NULL, -- JSE_CD
  libelle character varying(150),        -- Libellé
  CONSTRAINT pk_job_sens PRIMARY KEY (jse_cd)
  USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE job_sens OWNER TO @user_role;
GRANT ALL ON TABLE job_sens TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE job_sens TO @user_role;

-- Table: jobdefinition
CREATE TABLE jobdefinition (
  jod_id bigint NOT NULL,                              -- JOD_ID
  code character varying(20),                          -- Code
  libelle character varying(150),                      -- Libellé
  description character varying(2000),                 -- Description
  retention_journaux integer,                          -- Rétention journaux
  fenetre_supervision integer,                         -- Fenêtre supervision
  attente_interruption integer,                        -- Attente interruption
  source character varying(20),                        -- Source
  cible character varying(20),                         -- Cible
  encodage character varying(50),                      -- Encodage
  multi_executions boolean NOT NULL,                   -- Multi-exécutions
  manuel_autorise boolean NOT NULL,                    -- Manuel autorisé
  activation boolean NOT NULL,                         -- Activation
  interruptible boolean NOT NULL,                      -- Interruptible
  testable boolean NOT NULL,                           -- Testable
  frequence character varying(50),                     -- Fréquence
  affinite character varying(150),                     -- Affinité
  parametres_etendus character varying(2000),          -- Paramètres étendus
  implementation character varying(150),               -- Implémentation
  complet_possible boolean NOT NULL,                   -- Complet possible
  repertoire_distant_d_echange character varying(300), -- Répertoire distant d'échange
  jse_cd character varying(20),                        -- Sens
  jre_cd character varying(20),                        -- Rejet
  jdc_cd character varying(20),                        -- Job delta complet
  CONSTRAINT pk_jobdefinition PRIMARY KEY (jod_id) USING INDEX TABLESPACE @tbs_index,
  CONSTRAINT fk_association_8 FOREIGN KEY (jdc_cd) REFERENCES job_delta_complet (jdc_cd) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_jod_jre FOREIGN KEY (jre_cd) REFERENCES job_rejet (jre_cd) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_jod_jse FOREIGN KEY (jse_cd) REFERENCES job_sens (jse_cd) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) TABLESPACE @tbs_data;

ALTER TABLE jobdefinition OWNER TO @user_role;
GRANT ALL ON TABLE jobdefinition TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE jobdefinition TO @user_role;

CREATE UNIQUE INDEX idx_jod_code ON jobdefinition USING btree (code COLLATE pg_catalog."default");
CREATE INDEX jobdefinition_jdc_cd_fk ON jobdefinition USING btree (jdc_cd COLLATE pg_catalog."default");
CREATE INDEX jobdefinition_jre_cd_fk ON jobdefinition USING btree (jre_cd COLLATE pg_catalog."default");
CREATE INDEX jobdefinition_jse_cd_fk ON jobdefinition USING btree (jse_cd COLLATE pg_catalog."default");

Create sequence seq_jobdefinition minvalue 1000 increment 1;

-- Table: jobexecution
CREATE TABLE jobexecution
(
  joe_id bigint NOT NULL,             -- JOE_ID
  parametres character varying(2000), -- Paramètres
  debut timestamp without time zone,  -- Début
  fin timestamp without time zone,    -- Fin
  serveur character varying(50),      -- Serveur
  rapport character varying(2000),    -- Rapport
  logs character varying(150),        -- Logs
  data character varying(150),        -- Data
  jod_id bigint NOT NULL,             -- Définition
  jmo_cd character varying(20),       -- Mode
  jet_cd character varying(20),       -- Etat
  CONSTRAINT pk_jobexecution PRIMARY KEY (joe_id) USING INDEX TABLESPACE @tbs_index,
  CONSTRAINT fk_joe_jet FOREIGN KEY (jet_cd) REFERENCES job_etat (jet_cd) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_joe_jmo FOREIGN KEY (jmo_cd) REFERENCES job_mode (jmo_cd) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_joe_jod FOREIGN KEY (jod_id) REFERENCES jobdefinition (jod_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) TABLESPACE @tbs_data;

ALTER TABLE jobexecution OWNER TO @user_role;
GRANT ALL ON TABLE jobexecution TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE jobexecution TO @user_role;

CREATE INDEX jobexecution_jet_cd_fk ON jobexecution USING btree (jet_cd COLLATE pg_catalog."default");
CREATE INDEX jobexecution_jmo_cd_fk ON jobexecution USING btree (jmo_cd COLLATE pg_catalog."default");
CREATE INDEX jobexecution_jod_id_fk ON jobexecution USING btree (jod_id);

Create sequence seq_jobexecution minvalue 1000 increment 1;

-- ************************
-- Données initiales
-- ************************

insert into job_delta_complet (jdc_cd, libelle) values('C', 'Complet');
insert into job_delta_complet (jdc_cd, libelle) values('D', 'Delta');

insert into job_etat (jet_cd, libelle) values ('ECH', 'Echec');
insert into job_etat (jet_cd, libelle) values ('ENC', 'En cours');
insert into job_etat (jet_cd, libelle) values ('SUC', 'Succès');
insert into job_etat (jet_cd, libelle) values ('SUP', 'Succès partiel');

insert into job_mode (jmo_cd, libelle) values ('A', 'Automatique');
insert into job_mode (jmo_cd, libelle) values ('E', 'Extérieur');
insert into job_mode (jmo_cd, libelle) values ('M', 'Manuel');
insert into job_mode (jmo_cd, libelle) values ('T', 'Déclenché sur règle');

insert into job_rejet (jre_cd, libelle) values ('GLO', 'Global');
insert into job_rejet (jre_cd, libelle) values ('LAL', 'Ligne à ligne');

insert into job_sens (jse_cd, libelle) values ('ENT', 'Entrant (import)');
insert into job_sens (jse_cd, libelle) values ('INT', 'Interne');
insert into job_sens (jse_cd, libelle) values ('SER', 'Service');
insert into job_sens (jse_cd, libelle) values ('SOR', 'Sortant (export)');

commit;