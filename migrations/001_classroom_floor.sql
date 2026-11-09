-- Apply with the application stopped, before restarting Hibernate ddl-auto=update:
-- mysql --database=mydb < migrations/001_classroom_floor.sql
-- Floors below/above the supported range are clamped; valid floors are preserved.
-- MySQL 8.0.16+ is required for enforced CHECK constraints.
UPDATE classroom SET floor = GREATEST(1, LEAST(5, floor))
WHERE floor < 1 OR floor > 5;

-- Hibernate may already have created check_floor on a new database.
SET @floor_ddl = IF(
    EXISTS (SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
            WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 'classroom'
              AND CONSTRAINT_NAME = 'check_floor' AND CONSTRAINT_TYPE = 'CHECK'),
    'SELECT 1',
    'ALTER TABLE classroom ADD CONSTRAINT check_floor CHECK (floor >= 1 AND floor <= 5)'
);
PREPARE floor_statement FROM @floor_ddl;
EXECUTE floor_statement;
DEALLOCATE PREPARE floor_statement;
