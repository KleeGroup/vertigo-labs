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
DROP SEQUENCE seq_jobdefinition;
DROP SEQUENCE seq_jobdexecution;
DROP sEQUENCE seq_job_file_info;
DROP INDEX idx_jod_code;
DROP TABLE jobdefinition cascade;
DROP TABLE jobexecution  cascade;
DROP TABLE job_file_info cascade;
*/

-- ************************
-- Objets
-- ************************

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
  data_mode_cd character varying(20),                  -- Job delta complet
  reject_rule_cd character varying(20),                -- Reject rule
  direction_cd character varying(20),                  -- direction
  CONSTRAINT pk_jobdefinition PRIMARY KEY (jod_id) USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE jobdefinition OWNER TO @user_role;
GRANT ALL ON TABLE jobdefinition TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE jobdefinition TO @user_role;

CREATE UNIQUE INDEX idx_jod_code ON jobdefinition USING btree (code COLLATE pg_catalog."default");

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
  status_cd character varying(20),    -- Status
  trigger_cd character varying(20),   -- Triggering event nature
  CONSTRAINT pk_jobexecution PRIMARY KEY (joe_id) USING INDEX TABLESPACE @tbs_index,
  CONSTRAINT fk_joe_jod FOREIGN KEY (jod_id) REFERENCES jobdefinition (jod_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) TABLESPACE @tbs_data;

ALTER TABLE jobexecution OWNER TO @user_role;
GRANT ALL ON TABLE jobexecution TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE jobexecution TO @user_role;

CREATE INDEX jobexecution_jod_id_fk ON jobexecution USING btree (jod_id);

Create sequence seq_jobexecution minvalue 1000 increment 1;

-- Table: job_file_info
CREATE TABLE job_file_info (
  fil_id bigint                             NOT NULL, -- FIL_ID
  file_name character varying(150)          NOT NULL, -- FILE_NAME
  mime_type character varying(150)          NOT NULL, -- MIME_TYPE
  last_modified timestamp without time zone NOT NULL, -- LAST_MODIFIED
  length bigint,                                      -- LENGTH
  file_path character varying(300),         NOT NULL  -- FILE_PATH
  CONSTRAINT pk_job_file_info PRIMARY KEY (fil_id) USING INDEX TABLESPACE @tbs_index
) TABLESPACE @tbs_data;

ALTER TABLE job_file_info OWNER TO @user_role;
GRANT ALL ON TABLE job_file_info TO @user_role;
GRANT SELECT, UPDATE, INSERT ON TABLE job_file_info TO @user_role;

Create sequence seq_job_file_info minvalue 1000 increment 1;

-- ************************
-- Données initiales
-- ************************

delete from jobdefinition;
